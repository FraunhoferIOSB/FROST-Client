package de.fraunhofer.iosb.ilt.sta.jackson;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;
import de.fraunhofer.iosb.ilt.sta.model.Entity;
import static de.fraunhofer.iosb.ilt.sta.model.Entity.AT_IOT_COUNT;
import static de.fraunhofer.iosb.ilt.sta.model.Entity.AT_IOT_NEXT_LINK;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
