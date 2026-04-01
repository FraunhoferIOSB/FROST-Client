package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

import org.threeten.extra.Interval;

/**
 * Serializer for intervals. Serializes the interval into an ISO-8601 interval
 * string representation.
 *
 * @author Nils Sommer
 *
 */
public class IntervalSerializer extends StdSerializer<Interval> {

    public IntervalSerializer() {
        super(Interval.class);
    }

    @Override
    public void serialize(Interval interval, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        gen.writeString(interval.toString());

    }
}
