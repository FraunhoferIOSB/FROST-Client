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
package de.fraunhofer.iosb.ilt.sta.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.IOException;
import tools.jackson.core.JsonGenerator;

/**
 * the interface that all Id implementations need to implement.
 *
 * @author scf, Michael Jacoby
 * @param <T> concrete implementation class, needed for typesafe implementation of compare interface
 */
public interface Id<T extends Id> extends Comparable<T> {

    public static Id tryToParse(String input) {
        if (input.startsWith("'")) {
            return new IdString(input.substring(1, input.length() - 1));
        }
        try {
            return new IdLong(Long.parseLong(input));
        } catch (NumberFormatException exc) {
            // not a long.
        }
        return new IdString(input);
    }

    /**
     * Get the raw value of this Id.
     *
     * @return the raw value of this Id.
     */
    @JsonValue
    public Object getValue();

    /**
     * Get the value, formatted for use in a url. String values will be quoted
     * with single quotes.
     *
     * @return the value, formatted for use in a url.
     */
    public String getUrl();

    /**
     * Get the value, formatted for inserting into a JSON document. In general,
     * it is better to use a json mapper, and pass it the Object returned by
     * getValue().
     *
     * @return the value, formatted for use in JSON.
     */
    public String getJson();

    /**
     * Write the value to the given JsonGenerator.
     *
     * @param gen The JsonGenerator to write to.
     * @throws IOException if the generator throws.
     */
    public void writeTo(JsonGenerator gen) throws IOException;
}
