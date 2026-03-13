package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JacksonException;
import tools.jackson.core.ObjectReadContext;
import tools.jackson.core.TreeNode;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.node.ValueNode;
import java.io.IOException;
import org.geojson.GeoJsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Deserializer for TimeObject instances.
 */
public class LocationDeserializer extends StdDeserializer<Object> {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationDeserializer.class);
    private static final long serialVersionUID = 3674342381623629824L;

    public LocationDeserializer() {
        super(Object.class);
    }

    @Override
    public Object deserialize(JsonParser parser, DeserializationContext context) throws JacksonException {
        JsonNode tree = context.readTree(parser);
        return tryConvertTree(tree, context);
    }

    private static Object tryConvertTree(JsonNode tree, DeserializationContext parser) {
        if (tree.isValueNode()) {
            ValueNode value = (ValueNode) tree;
            if (value.isTextual()) {
                return value.asText();
            }
            if (value.isNumber()) {
                return value.numberValue();
            }
            if (value.isBoolean()) {
                return value.asBoolean();
            }
            if (value.isNull()) {
                return null;
            }
        }
        try {
            return parser.readTreeAsValue(tree, GeoJsonObject.class);
        } catch (Exception e) {
            LOGGER.debug("Not a geoJsonObject.");
        }
        return tree;
    }

//    public static Object deserialize(String text, ObjectMapper mapper) throws JacksonException {
//        JsonNode tree = mapper.readTree(text);
//        return tryConvertTree(tree, mapper.readtree));
//    }
}
