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

import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Serializer for UnitOfMeasurement class.
 *
 * @author Nils Sommer
 *
 */
public class UnitOfMeasurementSerializer extends StdSerializer<UnitOfMeasurement> {

    public UnitOfMeasurementSerializer() {
        super(UnitOfMeasurement.class);
    }

    @Override
    public void serialize(UnitOfMeasurement value, JsonGenerator gen, SerializationContext serializers)
            throws JacksonException {
        gen.writeStartObject();
        gen.writeName("name");
        gen.writeString(value.getName());
        gen.writeName("symbol");
        gen.writeString(value.getSymbol());
        gen.writeName("definition");
        gen.writeString(value.getDefinition());
        gen.writeEndObject();
    }

}
