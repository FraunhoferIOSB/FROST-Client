/*
 * Copyright (C) 2026 Fraunhofer Institut IOSB, Fraunhoferstr. 1, D 76131
 * Karlsruhe, Germany.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.fraunhofer.iosb.ilt.sta.dao;

import de.fraunhofer.iosb.ilt.sta.MqttException;
import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.Utils;
import de.fraunhofer.iosb.ilt.sta.jackson.ObjectMapperFactory;
import de.fraunhofer.iosb.ilt.sta.model.Id;
import de.fraunhofer.iosb.ilt.sta.model.Observation;
import de.fraunhofer.iosb.ilt.sta.model.ext.DataArrayDocument;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

/**
 * A data access object for the <i>Observation</i> entity.
 *
 * @author Nils Sommer
 *
 */
public class ObservationDao extends BaseDao<Observation> {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObservationDao.class);
    /**
     * The typereference for a list of Strings, used for type-safe json
     * deserialization.
     */
    public static final TypeReference<List<String>> LIST_OF_STRING = new TypeReference<List<String>>() {
        // Empty by design.
    };

    public ObservationDao(SensorThingsService service) {
        super(service, Observation.class);
    }

    @Override
    public void create(Observation entity) throws ServiceFailureException {
        if (!entity.isResultSet()) {
            throw new IllegalArgumentException("Result must be set on Observation.");
        }
        super.create(entity);
    }

    public void createMqtt(Observation entity) throws MqttException {
        if (!entity.isResultSet()) {
            throw new IllegalArgumentException("Result must be set on Observation.");
        }
        getService().publish(getMqttTopic(), entity);
    }

    /**
     *
     * @param dataArray The Observations to create.
     * @return The response of the service.
     * @throws ServiceFailureException in case the server rejects the POST.
     */
    public List<String> create(DataArrayDocument dataArray) throws ServiceFailureException {
        List<String> result = new ArrayList<>();
        final ObjectMapper mapper = ObjectMapperFactory.get();
        URIBuilder uriBuilder;
        HttpPost httpPost;
        String json;
        try {
            json = mapper.writeValueAsString(dataArray.getValue());
            uriBuilder = new URIBuilder(getService().getEndpoint() + "CreateObservations");
            httpPost = new HttpPost(uriBuilder.build());
        } catch (JacksonException | URISyntaxException ex) {
            throw new ServiceFailureException("Failed to create Observations.", ex);
        }

        LOGGER.debug("Posting to: {}", httpPost.getURI());
        httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        try (CloseableHttpResponse response = getService().execute(httpPost)) {

            Utils.throwIfNotOk(httpPost, response);

            String jsonResponse = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            result = mapper.readValue(jsonResponse, LIST_OF_STRING);
            List<Observation> observations = dataArray.getObservations();
            if (observations.size() != result.size()) {
                LOGGER.error("Size of returned location list ({}) is not equal to number of sent Observations ({})!", result.size(), observations.size());
            }
            int i = 0;
            for (Observation o : observations) {
                String newLocation = result.get(i);
                if (newLocation.startsWith("error")) {
                    LOGGER.warn("Failed to insert Observation. Error: {}.", newLocation);
                } else {
                    int pos1 = newLocation.indexOf('(') + 1;
                    int pos2 = newLocation.indexOf(')', pos1);
                    String stringId = newLocation.substring(pos1, pos2);
                    o.setId(Id.tryToParse(stringId));
                    o.setService(getService());
                }
                i++;
            }

        } catch (IOException exc) {
            throw new ServiceFailureException("Failed to create Observations.", exc);
        }
        return result;
    }
}
