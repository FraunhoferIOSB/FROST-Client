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
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Base class for any {@link EntityBuilder} of {@link HistoricalLocation}
 *
 * @param <U> the type of the concrete class that extends this
 * {@link AbstractHistoricalLocationBuilder}
 * @author Aurelien Bourdon
 */
public abstract class AbstractHistoricalLocationBuilder<U extends AbstractHistoricalLocationBuilder<U>> extends EntityBuilder<HistoricalLocation, U> {

    @Override
    protected HistoricalLocation newBuildingInstance() {
        return new HistoricalLocation();
    }

    public U time(final ZonedDateTime time) {
        getBuildingInstance().setTime(time);
        return getSelf();
    }

    public U thing(final Thing thing) {
        getBuildingInstance().setThing(thing);
        return getSelf();
    }

    public U locations(final List<Location> locations) {
        getBuildingInstance().getLocations().addAll(locations);
        return getSelf();
    }

    public U location(final Location location) {
        getBuildingInstance().getLocations().add(location);
        return getSelf();
    }

}
