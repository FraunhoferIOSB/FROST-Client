package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;
import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;

/**
 * Serializer for UnitOfMeasurement class.
 *
 * @author Nils Sommer
 *
 */
public class UnitOfMeasurementSerializer extends StdSerializer<UnitOfMeasurement> {

    public UnitOfMeasurementSerializer() {
		super(UnitOfMeasurement.class);
	}

	@Override
    public void serialize(UnitOfMeasurement value, JsonGenerator gen, SerializationContext serializers)
            throws JacksonException {
        gen.writeStartObject();
        gen.writeName("name");
        gen.writeString(value.getName());
        gen.writeName("symbol");
        gen.writeString(value.getSymbol());
        gen.writeName("definition");
        gen.writeString(value.getDefinition());
        gen.writeEndObject();
    }

}
