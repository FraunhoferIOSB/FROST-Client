package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonSerializer;
import tools.jackson.databind.SerializerProvider;
import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;
import java.io.IOException;

/**
 * Serializer for UnitOfMeasurement class.
 *
 * @author Nils Sommer
 *
 */
public class UnitOfMeasurementSerializer extends JsonSerializer<UnitOfMeasurement> {

    @Override
    public void serialize(UnitOfMeasurement value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JacksonException {
        gen.writeStartObject();
        gen.writeFieldName("name");
        gen.writeString(value.getName());
        gen.writeFieldName("symbol");
        gen.writeString(value.getSymbol());
        gen.writeFieldName("definition");
        gen.writeString(value.getDefinition());
        gen.writeEndObject();
    }
}
