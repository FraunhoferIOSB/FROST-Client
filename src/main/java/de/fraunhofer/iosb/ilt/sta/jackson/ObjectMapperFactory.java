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
import de.fraunhofer.iosb.ilt.swe.common.AbstractDataComponent;
import de.fraunhofer.iosb.ilt.swe.common.AbstractSWEIdentifiable;
import de.fraunhofer.iosb.ilt.swe.common.complex.DataRecord;
import de.fraunhofer.iosb.ilt.swe.common.constraint.AbstractConstraint;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
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
