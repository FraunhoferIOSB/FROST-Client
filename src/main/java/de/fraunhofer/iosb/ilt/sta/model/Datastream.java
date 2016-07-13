package de.fraunhofer.iosb.ilt.sta.model;

import org.geojson.Polygon;
import org.threeten.extra.Interval;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;

public class Datastream extends Entity {
	private String description;
	private String observationType;
	// TODO: needs proper jackson deserialization
	private UnitOfMeasurement unitOfMeasurement;
	private Polygon observedArea;
	private Interval phenomenonTime;
	private Interval resultTime;
	
	@JsonProperty("Thing")
	private Thing thing;
	
	@JsonProperty("Sensor")
	private Sensor sensor;
	
	@JsonProperty("ObservedProperty")
	private ObservedProperty observedProperty;
	
	@JsonProperty("Observations")
	private EntityList<Observation> observations;

	public Datastream() {}
	
	public Datastream(String description, String observationType, UnitOfMeasurement unitOfMeasurement, Polygon observedArea, Interval phenomenonTime, Interval resultTime) {
		this.description = description;
		this.observationType = observationType;
		this.unitOfMeasurement = unitOfMeasurement;
		this.observedArea = observedArea;
		this.phenomenonTime = phenomenonTime;
		this.resultTime = resultTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getObservationType() {
		return this.observationType;
	}

	public void setObservationType(String observationType) {
		this.observationType = observationType;
	}

	public UnitOfMeasurement getUnitOfMeasurement() {
		return this.unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Polygon getObservedArea() {
		return this.observedArea;
	}

	public void setObservedArea(Polygon observedArea) {
		this.observedArea = observedArea;
	}

	public Interval getPhenomenonTime() {
		return this.phenomenonTime;
	}

	public void setPhenomenonTime(Interval phenomenonTime) {
		this.phenomenonTime = phenomenonTime;
	}

	public Interval getResultTime() {
		return this.resultTime;
	}

	public void setResultTime(Interval resultTime) {
		this.resultTime = resultTime;
	}

	public Thing getThing() {
		return this.thing;
	}
	
	public void setThing(Thing thing) {
		this.thing = thing;
	}

	public Sensor getSensor() {
		return this.sensor;
	}
	
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public ObservedProperty getObservedProperty() {
		return this.observedProperty;
	}
	
	public void setObservedProperty(ObservedProperty observedProperty) {
		this.observedProperty = observedProperty;
	}

	public EntityList<Observation> getObservations() {
		return this.observations;
	}
	
	public void setObservations(EntityList<Observation> observations) {
		this.observations = observations;
	}
}
