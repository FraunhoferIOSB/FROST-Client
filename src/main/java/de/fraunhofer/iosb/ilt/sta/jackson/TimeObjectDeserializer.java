package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import de.fraunhofer.iosb.ilt.sta.model.TimeObject;
import java.io.IOException;
import org.threeten.extra.Interval;

/**
 * Deserializer for TimeObject instances.
 */
public class TimeObjectDeserializer extends StdDeserializer<TimeObject> {

    private static final long serialVersionUID = 3674342381623629828L;

    public TimeObjectDeserializer() {
        super(Interval.class);
    }

    @Override
    public TimeObject deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        return TimeObject.parse(((JsonNode) parser.getCodec().readTree(parser)).asText());
    }
}
