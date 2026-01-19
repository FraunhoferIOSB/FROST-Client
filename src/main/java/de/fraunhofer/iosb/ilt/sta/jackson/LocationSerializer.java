package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.ser.std.StdSerializer;
import org.geojson.GeoJsonObject;

/**
 * Serializer for Location property.
 */
public class LocationSerializer extends StdSerializer<Object> {

    public LocationSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializationContext serializers)
            throws JacksonException {
        if (value instanceof GeoJsonObject) {
            new ObjectMapper().writerFor(GeoJsonObject.class).writeValue(gen, value);
        } else {
            ValueSerializer<Object> serializer =
            		serializers.findValueSerializer(value.getClass());
            serializer.serialize(value, gen, serializers);
        }
    }
}
