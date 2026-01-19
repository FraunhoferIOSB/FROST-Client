package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.SerializationContext;
import de.fraunhofer.iosb.ilt.sta.model.TimeObject;
import java.io.IOException;

/**
 * Serializer for TimeObject class.
 */
public class TimeObjectSerializer extends ValueSerializer<TimeObject> {

    @Override
    public void serialize(TimeObject value, JsonGenerator gen, SerializationContext context)
            throws JacksonException {
        gen.writeString(value.toString());
    }
}
