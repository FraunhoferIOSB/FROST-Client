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
package de.fraunhofer.iosb.ilt.sta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.dao.BaseDao;
import de.fraunhofer.iosb.ilt.sta.dao.HistoricalLocationDao;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class HistoricalLocation extends Entity<HistoricalLocation> {

    private ZonedDateTime time;

    private final EntityList<Location> locations = new EntityList<>(EntityType.LOCATIONS);

    @JsonProperty("Thing")
    private Thing thing;

    public HistoricalLocation() {
        super(EntityType.HISTORICAL_LOCATION);
    }

    public HistoricalLocation(ZonedDateTime time) {
        this();
        this.time = time;
    }

    @Override
    protected void ensureServiceOnChildren(SensorThingsService service) {
        if (thing != null) {
            thing.setService(service);
        }
        locations.setService(service, Location.class);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HistoricalLocation other = (HistoricalLocation) obj;
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 53 * hash + Objects.hashCode(this.time);
        return hash;
    }

    public ZonedDateTime getTime() {
        return this.time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public BaseDao<Location> locations() {
        return getService().locations().setParent(this);
    }

    @JsonProperty("Locations")
    public EntityList<Location> getLocations() {
        return this.locations;
    }

    @JsonProperty("Locations")
    public void setLocations(List<Location> locations) {
        this.locations.replaceAll(locations);
    }

    public Thing getThing() throws ServiceFailureException {
        if (thing == null && getService() != null) {
            thing = getService().things().find(this);
        }
        return this.thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    @Override
    public BaseDao<HistoricalLocation> getDao(SensorThingsService service) {
        return new HistoricalLocationDao(service);
    }

    @Override
    public HistoricalLocation withOnlyId() {
        HistoricalLocation copy = new HistoricalLocation();
        copy.setId(id);
        return copy;
    }

    @Override
    public String toString() {
        return super.toString() + " " + time.toString();
    }
}
