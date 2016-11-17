package de.fraunhofer.iosb.ilt.sta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.fraunhofer.iosb.ilt.sta.dao.BaseDao;
import de.fraunhofer.iosb.ilt.sta.dao.SensorDao;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.util.Objects;

public class Sensor extends Entity<Sensor> {

	private String name;
	private String description;
	private String encodingType;
	private Object metadata;

	@JsonProperty("Datastreams")
	private EntityList<Datastream> datastreams = new EntityList<>(EntityType.DATASTREAMS);

	public Sensor() {
		super(EntityType.DATASTREAM);
	}

	public Sensor(String name, String description, String encodingType, Object metadata) {
		this();
		this.name = name;
		this.description = description;
		this.encodingType = encodingType;
		this.metadata = metadata;
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
		final Sensor other = (Sensor) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.description, other.description)) {
			return false;
		}
		if (!Objects.equals(this.encodingType, other.encodingType)) {
			return false;
		}
		if (!Objects.equals(this.metadata, other.metadata)) {
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
		hash = 59 * hash + Objects.hashCode(this.metadata);
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

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}

	public BaseDao<Datastream> datastreams() {
		return getService().datastreams().setParent(this);
	}

	public EntityList<Datastream> getDatastreams() {
		return this.datastreams;
	}

	public void setDatastreams(EntityList<Datastream> datastreams) {
		this.datastreams = datastreams;
	}

	@Override
	public BaseDao<Sensor> getDao(SensorThingsService service) {
		return new SensorDao(service);
	}

	@Override
	public Sensor withOnlyId() {
		Sensor copy = new Sensor();
		copy.setId(id);
		return copy;
	}
}
