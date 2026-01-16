package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonProcessingException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import org.threeten.extra.Interval;

/**
 * Deserializer for ISO-8601 intervals to
 * {@link org.threeten.extra.Interval Interval} instances.
 *
 * @author Nils Sommer
 *
 */
public class IntervalDeserializer extends StdDeserializer<Interval> {

    private static final long serialVersionUID = 3674342381623629828L;

    public IntervalDeserializer() {
        super(Interval.class);
    }

    // org.threeten.extra.Interval doesn't support time zone setups yet:
    // https://github.com/ThreeTen/threeten-extra/issues/66
    // Patch submitted.
    @Override
    public Interval deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        return Interval.parse(((JsonNode) parser.getCodec().readTree(parser)).asText());
    }
}
