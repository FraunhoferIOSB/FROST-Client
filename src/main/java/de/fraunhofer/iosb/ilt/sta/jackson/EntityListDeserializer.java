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

import static de.fraunhofer.iosb.ilt.sta.model.Entity.AT_IOT_COUNT;
import static de.fraunhofer.iosb.ilt.sta.model.Entity.AT_IOT_NEXT_LINK;

import de.fraunhofer.iosb.ilt.sta.model.Entity;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;

public class EntityListDeserializer<T extends Entity<T>> extends StdDeserializer<EntityList<T>> {

    private static final long serialVersionUID = 8376494553925868647L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityListDeserializer.class);

    private Class<T> type;

    public EntityListDeserializer() {
        super(EntityList.class);
    }

    public EntityListDeserializer(Class<T> type) {
        super(EntityList.class);
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ValueDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
            throws JacksonException {
        final JavaType wrapperType;
        if (property == null) {
            wrapperType = ctxt.getContextualType();
        } else {
            wrapperType = property.getType();
        }

        JavaType valueType = wrapperType.containedType(0);
        return new EntityListDeserializer(valueType.getRawClass());
    }

    @Override
    public EntityList<T> deserialize(JsonParser parser, DeserializationContext context)
            throws JacksonException {

        final EntityList<T> entities = new EntityList<>(EntityType.listForClass(type));

        JsonToken currentToken = parser.currentToken();
        if (currentToken == JsonToken.START_ARRAY) {
            // Direct array, probably expanded.
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                entities.add(parser.readValueAs(type));
            }
        } else {
            boolean done = false;
            while (!done) {
                JsonToken mainToken = parser.nextToken();
                switch (mainToken) {
                    case END_OBJECT:
                        done = true;
                        break;

                    case PROPERTY_NAME:
                        String fieldName = parser.currentName();
                        JsonToken valueToken = parser.nextToken();
                        switch (fieldName) {
                            case AT_IOT_COUNT:
                                entities.setCount(parser.getValueAsLong());
                                break;

                            case AT_IOT_NEXT_LINK:
                                try {
                                    entities.setNextLink(URI.create(parser.getValueAsString()));
                                } catch (IllegalArgumentException e) {
                                    LOGGER.warn("@iot.nextLink field contains malformed URI", e);
                                }
                                break;

                            case "value":
                                if (valueToken == JsonToken.START_ARRAY) {
                                    while (parser.nextToken() != JsonToken.END_ARRAY) {
                                        entities.add(parser.readValueAs(type));
                                    }
                                } else {
                                    LOGGER.warn("value field is not an array!");
                                }
                                break;

                        }
                        break;

                    default:
                        LOGGER.warn("Unhandled token: {}", mainToken);
                }
            }
        }
        return entities;
    }

}
