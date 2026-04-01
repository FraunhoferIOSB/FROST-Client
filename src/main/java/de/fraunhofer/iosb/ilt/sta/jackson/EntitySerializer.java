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

import com.fasterxml.jackson.annotation.JsonInclude;
import de.fraunhofer.iosb.ilt.sta.model.Entity;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.exc.StreamWriteException;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.introspect.AnnotatedMember;
import tools.jackson.databind.introspect.BeanPropertyDefinition;
import tools.jackson.databind.jsontype.TypeSerializer;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Serializer for SensorThings Entities.
 *
 * @author Nils Sommer
 *
 */
public class EntitySerializer extends StdSerializer<Entity> {

    public EntitySerializer() {
        super(Entity.class);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EntitySerializer.class);

    @Override
    public void serialize(Entity entity, JsonGenerator gen, SerializationContext serializers) throws JacksonException {
        gen.writeStartObject();

        SerializationConfig config = serializers.getConfig();
        final BeanDescription beanDesc = serializers.introspectBeanDescription(serializers.constructType(entity.getClass()));

        List<BeanPropertyDefinition> properties = beanDesc.findProperties();
        for (BeanPropertyDefinition property : properties) {
            AnnotatedMember accessor = property.getAccessor();
            if (accessor == null) {
                continue;
            }
            Object rawValue = accessor.getValue(entity);
            // TODO: Check if prop is collection

            if (rawValue instanceof Entity) {
                Entity subEntity = (Entity) rawValue;
                if (subEntity.getId() != null) {
                    // It's a referenced entity. -> <Entity>: { "@iot.id": <id> }
                    gen.writeName(rawValue.getClass().getSimpleName());
                    gen.writeStartObject();
                    gen.writeName("@iot.id");
                    try {
                        ((Entity) rawValue).getId().writeTo(gen);
                    } catch (IOException e) {
                        throw new StreamWriteException(gen, e);
                    }
                    gen.writeEndObject();
                } else {
                    gen.writeName(rawValue.getClass().getSimpleName());
                    serialize(subEntity, gen, serializers);
                }
            } else if (rawValue instanceof EntityList) {
                EntityList entityList = (EntityList) rawValue;
                if (entityList.isEmpty()) {
                    continue;
                }
                // Ignore collections during serialization.
                gen.writeName(entityList.getType().getName());
                gen.writeStartArray();
                for (Object sub : entityList) {
                    if (sub instanceof Entity) {
                        Entity subEntity = (Entity) sub;
                        if (subEntity.getId() == null) {
                            serialize(subEntity, gen, serializers);
                        } else {
                            gen.writeStartObject();
                            gen.writeName("@iot.id");
                            try {
                                subEntity.getId().writeTo(gen);
                            } catch (IOException e) {
                                throw new StreamWriteException(gen, e);
                            }
                            gen.writeEndObject();
                        }
                    }
                }
                gen.writeEndArray();
            } else {
                JsonSerialize annotation = property.getAccessor().getAnnotation(JsonSerialize.class);
                ValueSerializer serializer = null;
                if (annotation != null) {
                    try {
                        Class<? extends ValueSerializer> using = annotation.using();
                        serializer = using.getConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                        LOGGER.warn("Could not instantiate serialiser specified in annotation.", ex);
                    }
                }
                boolean suppressNulls = true;
                JsonInclude includeAnnotation = property.getAccessor().getAnnotation(JsonInclude.class);
                if (rawValue == null && includeAnnotation != null && includeAnnotation.value() == JsonInclude.Include.ALWAYS) {
                    suppressNulls = false;
                }
                TypeSerializer typeSerializer = serializers.findTypeSerializer(serializers.constructType(property.getAccessor().getRawType()));
                BeanPropertyWriter writer = new BeanPropertyWriter(
                        property,
                        property.getAccessor(),
                        beanDesc.getClassAnnotations(),
                        property.getAccessor().getType(),
                        serializer,
                        typeSerializer, // will not be searched automatically
                        property.getAccessor().getType(),
                        suppressNulls, // ignore null values
                        null, null);
                try {
                    Object value = writer.get(entity);
                    if (value != null || !suppressNulls) {
                        serializers.defaultSerializeProperty(writer.getName(), value, gen);
                    }

                } catch (Exception e) {
                    LOGGER.error("Failed to serialize entity.", e);
                }
            }
        }

        gen.writeEndObject();
    }

}
