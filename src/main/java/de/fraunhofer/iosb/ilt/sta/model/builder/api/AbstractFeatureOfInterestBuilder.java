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
package de.fraunhofer.iosb.ilt.sta.model.builder.api;

import com.fasterxml.jackson.annotation.JsonValue;
import de.fraunhofer.iosb.ilt.sta.model.FeatureOfInterest;
import de.fraunhofer.iosb.ilt.sta.model.Observation;
import java.util.List;
import org.geojson.GeoJsonObject;

/**
 * Base class for any {@link EntityBuilder} of {@link FeatureOfInterest}
 *
 * @param <U> the type of the concrete class that extends this
 * {@link AbstractFeatureOfInterestBuilder}
 * @author Aurelien Bourdon
 */
public abstract class AbstractFeatureOfInterestBuilder<U extends AbstractFeatureOfInterestBuilder<U>> extends EntityBuilder<FeatureOfInterest, U> {

    @Override
    protected FeatureOfInterest newBuildingInstance() {
        return new FeatureOfInterest();
    }

    public U name(final String name) {
        getBuildingInstance().setName(name);
        return getSelf();
    }

    public U description(final String description) {
        getBuildingInstance().setDescription(description);
        return getSelf();
    }

    public U encodingType(final ValueCode encodingType) {
        getBuildingInstance().setEncodingType(encodingType.getValue());
        return getSelf();
    }

    public U feature(final Object feature) {
        if (!(feature instanceof GeoJsonObject)) {
            throw new BuildingException("Whereas the OGC SensorThings API specifies the FeatureOfInterest#feature as an Any type (so any Object can be used), "
                    + "the FROST-Client only accepts GeoJSONObject type");
        }
        getBuildingInstance().setFeature((GeoJsonObject) feature);
        return getSelf();
    }

    public U observations(final List<Observation> observations) {
        getBuildingInstance().getObservations().addAll(observations);
        return getSelf();
    }

    public U observation(final Observation observation) {
        getBuildingInstance().getObservations().add(observation);
        return getSelf();
    }

    /**
     * All the possible values for a {@link FeatureOfInterest#encodingType}
     * attribute
     *
     * @author Aurelien Bourdon
     */
    public enum ValueCode {

        GeoJSON("application/vnd.geo+json");

        private final String value;

        ValueCode(final String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

    }

}
