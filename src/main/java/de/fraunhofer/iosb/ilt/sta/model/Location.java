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
import de.fraunhofer.iosb.ilt.sta.dao.BaseDao;
import de.fraunhofer.iosb.ilt.sta.dao.LocationDao;
import de.fraunhofer.iosb.ilt.sta.jackson.LocationDeserializer;
import de.fraunhofer.iosb.ilt.sta.jackson.LocationSerializer;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

public class Location extends Entity<Location> {

    private String name;
    private String description;
    private String encodingType;
    @JsonDeserialize(using = LocationDeserializer.class)
    @JsonSerialize(using = LocationSerializer.class)
    private Object location;
    private Map<String, Object> properties;

    private final EntityList<Thing> things = new EntityList<>(EntityType.THINGS);
    private final EntityList<HistoricalLocation> historicalLocations = new EntityList<>(EntityType.HISTORICAL_LOCATIONS);

    public Location() {
        super(EntityType.LOCATION);
    }

    public Location(String name, String description, String encodingType, Object location) {
        this();
        this.name = name;
        this.description = description;
        this.encodingType = encodingType;
        this.location = location;
    }

    @Override
    protected void ensureServiceOnChildren(SensorThingsService service) {
        things.setService(service, Thing.class);
        historicalLocations.setService(service, HistoricalLocation.class);
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
        final Location other = (Location) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.encodingType, other.encodingType)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.properties, other.properties)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.description);
        hash = 59 * hash + Objects.hashCode(this.encodingType);
        hash = 59 * hash + Objects.hashCode(this.location);
        hash = 59 * hash + Objects.hashCode(this.properties);
        return hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public BaseDao<HistoricalLocation> historicalLocations() {
        return getService().historicalLocations().setParent(this);
    }

    @JsonProperty("HistoricalLocations")
    public EntityList<HistoricalLocation> getHistoricalLocations() {
        return this.historicalLocations;
    }

    @JsonProperty("HistoricalLocations")
    public void setHistoricalLocations(List<HistoricalLocation> historicalLocations) {
        this.historicalLocations.replaceAll(historicalLocations);
    }

    public Object getLocation() {
        return this.location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public BaseDao<Thing> things() {
        return getService().things().setParent(this);
    }

    @JsonProperty("Things")
    public EntityList<Thing> getThings() {
        return this.things;
    }

    @JsonProperty("Things")
    public void setThings(List<Thing> things) {
        this.things.replaceAll(things);
    }

    @Override
    public BaseDao<Location> getDao(SensorThingsService service) {
        return new LocationDao(service);
    }

    @Override
    public Location withOnlyId() {
        Location copy = new Location();
        copy.setId(id);
        return copy;
    }

    @Override
    public String toString() {
        return super.toString() + " " + getName();
    }
}
