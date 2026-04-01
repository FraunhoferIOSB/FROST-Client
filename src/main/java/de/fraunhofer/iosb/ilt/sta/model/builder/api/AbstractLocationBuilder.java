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

import de.fraunhofer.iosb.ilt.sta.model.HistoricalLocation;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import java.util.List;
import org.geojson.GeoJsonObject;

/**
 * Base class for any {@link EntityBuilder} of {@link Location}
 *
 * @param <U> the type of the concrete class that extends this
 * {@link AbstractLocationBuilder}
 * @author Aurelien Bourdon
 */
public abstract class AbstractLocationBuilder<U extends AbstractLocationBuilder<U>> extends EntityBuilder<Location, U> {

    @Override
    protected Location newBuildingInstance() {
        return new Location();
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

    public U location(final Object location) {
        if (!(location instanceof GeoJsonObject)) {
            throw new BuildingException("Whereas the OGC SensorThings API specifies the Location#location as an Any type (so any Object can be used), "
                    + "the FROST-Client only accepts GeoJSONObject type");
        }
        getBuildingInstance().setLocation((GeoJsonObject) location);
        return getSelf();
    }

    public U things(final List<Thing> things) {
        getBuildingInstance().getThings().addAll(things);
        return getSelf();
    }

    public U thing(final Thing thing) {
        getBuildingInstance().getThings().add(thing);
        return getSelf();
    }

    public U historicalLocations(final List<HistoricalLocation> historicalLocations) {
        getBuildingInstance().getHistoricalLocations().addAll(historicalLocations);
        return getSelf();
    }

    public U historicalLocation(final HistoricalLocation historicalLocation) {
        getBuildingInstance().getHistoricalLocations().add(historicalLocation);
        return getSelf();
    }

    /**
     * All the possible values for a {@link Location#encodingType} attribute
     *
     * @author Aurelien Bourdon
     */
    public enum ValueCode {

        GeoJSON("application/vnd.geo+json");

        private final String value;

        ValueCode(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

}
