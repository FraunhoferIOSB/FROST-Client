/*
 * Copyright (C) 2026 Fraunhofer Institut IOSB, Fraunhoferstr. 1, D 76131
 * Karlsruhe, Germany.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.fraunhofer.iosb.ilt.sta.jackson;

import org.geojson.GeoJsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.node.ValueNode;

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

}
