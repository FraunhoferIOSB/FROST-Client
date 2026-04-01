package de.fraunhofer.iosb.ilt.sta.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import de.fraunhofer.iosb.ilt.swe.common.AbstractDataComponent;
import de.fraunhofer.iosb.ilt.swe.common.AbstractSWEIdentifiable;
import de.fraunhofer.iosb.ilt.swe.common.complex.DataRecord;
import de.fraunhofer.iosb.ilt.swe.common.constraint.AbstractConstraint;
import tools.jackson.databind.json.JsonMapper;

/**
 * Factory for ObjectMapper instances. Keeps track of configuration.
 *
 * @author Nils Sommer, Hylke van der Schaaf, Michael Jacoby
 *
 */
public final class ObjectMapperFactory {

    private static ObjectMapper mapper;

    private ObjectMapperFactory() {
    }

    /**
     * Get a preconfigured, long living instance of {@link ObjectMapper} with
     * all custom modules needed.
     *
     * @return the object mapper
     */
    public static ObjectMapper get() {
        if (mapper == null) {
            mapper = JsonMapper.builder()
                    .addModule(new EntityModule())
                    .addMixIn(DataRecord.class, DataRecordMixin.class)
                    .addMixIn(AbstractDataComponent.class, AbstractDataComponentMixin.class)
                    .addMixIn(AbstractSWEIdentifiable.class, AbstractSWEIdentifiableMixin.class)
                    .addMixIn(AbstractConstraint.class, AbstractConstraintMixin.class)
                    .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                    .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_EMPTY))
                    .changeDefaultPropertyInclusion(incl -> incl.withContentInclusion(JsonInclude.Include.NON_EMPTY))
                    .build();

        }
        return mapper;
    }

}
