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

import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.MultiDatastream;
import de.fraunhofer.iosb.ilt.sta.model.Sensor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Base class for any {@link EntityBuilder} of {@link Sensor}
 *
 * @param <U> the type of the concrete class that extends this
 * {@link AbstractSensorBuilder}
 * @author Aurelien Bourdon
 */
public abstract class AbstractSensorBuilder<U extends AbstractSensorBuilder<U>> extends EntityBuilder<Sensor, U> {

    @Override
    protected Sensor newBuildingInstance() {
        return new Sensor();
    }

    public U name(final String name) {
        getBuildingInstance().setName(name);
        return getSelf();
    }

    public U description(final String description) {
        getBuildingInstance().setDescription(description);
        return getSelf();
    }

    public U encodingType(final String encodingType) {
        getBuildingInstance().setEncodingType(encodingType);
        return getSelf();
    }

    public U encodingType(final ValueCode encodingType) {
        getBuildingInstance().setEncodingType(encodingType.getValue());
        return getSelf();
    }

    public U metadata(final Object metadata) {
        getBuildingInstance().setMetadata(metadata);
        return getSelf();
    }

    public U datastreams(final List<Datastream> datastreams) {
        getBuildingInstance().getDatastreams().addAll(datastreams);
        return getSelf();
    }

    public U datastream(final Datastream datastream) {
        getBuildingInstance().getDatastreams().add(datastream);
        return getSelf();
    }

    public U multiDatastreams(final List<MultiDatastream> multiDatastreams) {
        getBuildingInstance().getMultiDatastreams().addAll(multiDatastreams);
        return getSelf();
    }

    public U multiDatastream(final MultiDatastream multiDatastream) {
        getBuildingInstance().getMultiDatastreams().add(multiDatastream);
        return getSelf();
    }

    public U properties(final Map<String, Object> properties) {
        getBuildingInstance().setProperties(properties);
        return getSelf();
    }

    public U property(final String key, final Object value) {
        if (getBuildingInstance().getProperties() == null) {
            getBuildingInstance().setProperties(new HashMap<>());
        }
        getBuildingInstance().getProperties().put(key, value);
        return getSelf();
    }

    /**
     * All the possible values for a {@link Sensor#encodingType} attribute
     *
     * @author Michael Jacoby
     */
    public enum ValueCode {

        PDF("application/pdf"),
        SensorML("http://www.opengis.net/doc/IS/SensorML/2.0");

        private final String value;

        ValueCode(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static ValueCode from(String value) {
            Optional<ValueCode> result = Stream.of(ValueCode.values())
                    .filter(x -> x.value.equals(value))
                    .findAny();
            if (!result.isPresent()) {
                throw new IllegalArgumentException("unkown value code '" + value + "'");
            }
            return result.get();
        }
    }
}
