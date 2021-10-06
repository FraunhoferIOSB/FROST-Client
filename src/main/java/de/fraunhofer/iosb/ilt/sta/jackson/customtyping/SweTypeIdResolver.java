package de.fraunhofer.iosb.ilt.sta.jackson.customtyping;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import de.fraunhofer.iosb.ilt.swe.common.AbstractSWEIdentifiable;
import de.fraunhofer.iosb.ilt.swe.common.constraint.AbstractConstraint;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.reflections8.Reflections;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Jacoby
 */
public class SweTypeIdResolver implements TypeIdResolver {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SweTypeIdResolver.class.getName());

    private static final Map<String, Class<?>> annnotatedClasses;

    static {
        final Reflections reflections = new Reflections("de.fraunhofer.iosb.ilt.swe.common");
        annnotatedClasses = reflections
                .getSubTypesOf(AbstractSWEIdentifiable.class)
                .stream()
                .collect(Collectors.toMap(
                        x -> idFromClass(x),
                        x -> x));
        annnotatedClasses.putAll(
                reflections
                        .getSubTypesOf(AbstractConstraint.class)
                        .stream()
                        .collect(Collectors.toMap(
                                x -> idFromClass(x),
                                x -> x)));
    }

    private JavaType superType;

    @Override
    public void init(JavaType baseType) {
        superType = baseType;
    }

    @Override
    public String idFromValue(Object value) {
        return idFromClass(value.getClass());
    }

    public static String idFromClass(Class clazz) {
        final String className = clazz.getName();
        String name = className.substring(1 + className.lastIndexOf('.'));
        try {
            name = FieldUtils.readStaticField(clazz, "SWE_NAME").toString();
        } catch (NullPointerException | IllegalArgumentException | IllegalAccessException ex) {
            LOGGER.trace("Class {} has no SWE_NAME field.", className);
        }
        LOGGER.trace("{} -> {}", clazz.getName(), name);
        return name;
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return idFromClass(value.getClass());
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) throws IOException {
        if (!annnotatedClasses.containsKey(id)) {
            throw new RuntimeException(String.format("unkown type '%s'", id));
        }
        return context.constructSpecializedType(superType, annnotatedClasses.get(id));
    }

    @Override
    public String idFromBaseType() {
        return idFromClass(superType.getRawClass());
    }

    @Override
    public String getDescForKnownTypeIds() {
        return annnotatedClasses.toString();
    }

}
