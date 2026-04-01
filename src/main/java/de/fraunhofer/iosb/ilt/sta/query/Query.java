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
package de.fraunhofer.iosb.ilt.sta.query;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.Utils;
import de.fraunhofer.iosb.ilt.sta.jackson.ObjectMapperFactory;
import de.fraunhofer.iosb.ilt.sta.model.Entity;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.databind.ObjectMapper;

/**
 * A query for reading operations.
 *
 * @author Nils Sommer, Hylke van der Schaaf
 * @param <T> The type of entity this query returns.
 */
public class Query<T extends Entity<T>> implements QueryRequest<T>, QueryParameter<T> {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Query.class);
    private final SensorThingsService service;
    private final EntityType plural;
    private final Class<T> entityClass;
    private final Entity<?> parent;
    private final List<NameValuePair> params = new ArrayList<>();

    public Query(SensorThingsService service, Class<T> entityClass) {
        this(service, entityClass, null);
    }

    public Query(SensorThingsService service, Class<T> entityClass, Entity<?> parent) {
        this.service = service;
        this.plural = EntityType.listForClass(entityClass);
        this.entityClass = entityClass;
        this.parent = parent;
    }

    public EntityType getEntityType() {
        return plural;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public SensorThingsService getService() {
        return service;
    }

    private void removeAllParams(String key) {
        for (Iterator<NameValuePair> it = params.iterator(); it.hasNext();) {
            NameValuePair param = it.next();
            if (param.getName().equals(key)) {
                it.remove();
                break;
            }
        }
    }

    @Override
    public Query<T> filter(String options) {
        removeAllParams("$filter");
        if (options.isEmpty()) {
            return this;
        }
        params.add(new BasicNameValuePair("$filter", options));
        return this;
    }

    @Override
    public Query<T> top(int n) {
        removeAllParams("$top");
        params.add(new BasicNameValuePair("$top", Integer.toString(n)));
        return this;
    }

    @Override
    public Query<T> orderBy(String clause) {
        removeAllParams("$orderby");
        params.add(new BasicNameValuePair("$orderby", clause));
        return this;
    }

    @Override
    public Query<T> skip(int n) {
        removeAllParams("$skip");
        params.add(new BasicNameValuePair("$skip", Integer.toString(n)));
        return this;
    }

    @Override
    public Query<T> count() {
        removeAllParams("$count");
        params.add(new BasicNameValuePair("$count", "true"));
        return this;
    }

    public Query<T> expand(Expansion expansion) {
        removeAllParams("$expand");
        params.add(new BasicNameValuePair("$expand", expansion.toString()));
        return this;
    }

    public Query<T> expand(String expansion) {
        removeAllParams("$expand");
        params.add(new BasicNameValuePair("$expand", expansion));
        return this;
    }

    public Query<T> select(String... fields) {
        removeAllParams("$select");
        if (fields == null) {
            return this;
        }
        StringBuilder selectValue = new StringBuilder();
        for (String field : fields) {
            selectValue.append(field).append(",");
        }
        if (selectValue.length() == 0) {
            return this;
        }
        String select = selectValue.substring(0, selectValue.length() - 1);
        if (select.isEmpty()) {
            return this;
        }
        params.add(new BasicNameValuePair("$select", select));
        return this;
    }

    @Override
    public T first() throws ServiceFailureException {
        this.top(1);
        List<T> asList = this.list().toList();
        if (asList.isEmpty()) {
            return null;
        }
        return asList.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public EntityList<T> list() throws ServiceFailureException {
        EntityList<T> list = new EntityList<>(plural);
        HttpGet httpGet;
        try {
            URIBuilder uriBuilder = new URIBuilder(service.getFullPath(parent, plural).toURI());
            uriBuilder.addParameters(params);
            httpGet = new HttpGet(uriBuilder.build());
        } catch (URISyntaxException ex) {
            throw new ServiceFailureException("Failed to fetch entities from query.", ex);
        }

        LOGGER.debug("Fetching: {}", httpGet.getURI());
        httpGet.addHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());

        try (CloseableHttpResponse response = service.execute(httpGet)) {
            Utils.throwIfNotOk(httpGet, response);
            String json = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            final ObjectMapper mapper = ObjectMapperFactory.get();
            list = mapper.readValue(json, plural.getTypeReference());
        } catch (IOException ex) {
            throw new ServiceFailureException("Failed to fetch entities from query.", ex);
        }

        list.setService(service, entityClass);
        return list;
    }

    public void delete() throws ServiceFailureException {
        removeAllParams("$top");
        removeAllParams("$skip");
        removeAllParams("$count");
        removeAllParams("$select");
        removeAllParams("$expand");

        HttpDelete httpDelete;
        try {
            URIBuilder uriBuilder = new URIBuilder(service.getFullPath(parent, plural).toURI());
            uriBuilder.addParameters(params);
            httpDelete = new HttpDelete(uriBuilder.build());
        } catch (URISyntaxException ex) {
            throw new ServiceFailureException("Failed to delete from query.", ex);
        }

        LOGGER.debug("Deleting: {}", httpDelete.getURI());
        httpDelete.addHeader("Accept", ContentType.APPLICATION_JSON.getMimeType());

        try (CloseableHttpResponse response = service.execute(httpDelete)) {
            Utils.throwIfNotOk(httpDelete, response);
            EntityUtils.consumeQuietly(response.getEntity());
        } catch (IOException ex) {
            throw new ServiceFailureException("Failed to delete from query.", ex);
        }

    }
}
