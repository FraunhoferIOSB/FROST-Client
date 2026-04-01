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
package de.fraunhofer.iosb.ilt.sta.model.builder.api.ext;

import de.fraunhofer.iosb.ilt.sta.model.builder.api.ExtensibleBuilder;
import de.fraunhofer.iosb.ilt.swe.common.AbstractSWEIdentifiable;

/**
 * Base class for any {@link AbstractSWEIdentifiable} builder.
 * <p>
 * Any {@link AbstractSWEIdentifiableBuilder} is an {@link ExtensibleBuilder}.
 *
 * @param <T> the concrete {@link AbstractSWEIdentifiable} type to build
 * @param <U> the concrete type of this {@link AbstractSWEIdentifiableBuilder}
 * @author Michael Jacoby
 */
public abstract class AbstractSWEIdentifiableBuilder<T extends AbstractSWEIdentifiable, U extends AbstractSWEIdentifiableBuilder<T, U>> extends ExtensibleBuilder<T, U> {

    public U id(final String id) {
        getBuildingInstance().setIdentifier(id);
        return getSelf();
    }

    public U label(final String label) {
        getBuildingInstance().setLabel(label);
        return getSelf();
    }

    public U description(final String description) {
        getBuildingInstance().setDescription(description);
        return getSelf();
    }
}
