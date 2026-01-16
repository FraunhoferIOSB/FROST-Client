package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonSerializer;
import tools.jackson.databind.SerializerProvider;
import de.fraunhofer.iosb.ilt.sta.model.TimeObject;
import java.io.IOException;

/**
 * Serializer for TimeObject class.
 */
public class TimeObjectSerializer extends JsonSerializer<TimeObject> {

    @Override
    public void serialize(TimeObject value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        gen.writeString(value.toString());
    }
}
