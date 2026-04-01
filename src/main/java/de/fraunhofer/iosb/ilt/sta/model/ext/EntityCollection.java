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
package de.fraunhofer.iosb.ilt.sta.model.ext;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.Entity;
import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import java.util.Collection;
import java.util.List;

/**
 * Methods for entity collections on top of the standard set of collection
 * methods.
 *
 * @author Nils Sommer
 *
 * @param <T> the entity's type
 */
public interface EntityCollection<T extends Entity> extends Collection<T> {

    public EntityType getType();

    /**
     * Convert the EntityCollection to a {@link List}.
     *
     * @return the list
     */
    List<T> toList();

    /**
     *
     * @return true if there is a nextLink to fetch more Entities.
     */
    boolean hasNextLink();

    /**
     * Use the nextLink to fetch more Entities.
     *
     * @throws de.fraunhofer.iosb.ilt.sta.ServiceFailureException If there is a
     * problem following the nextLink.
     */
    void fetchNext() throws ServiceFailureException;

    /**
     * Get the count value if available.
     *
     * @return the count value
     */
    long getCount();

    /**
     * Set the count value.
     *
     * @param count the count value
     */
    void setCount(long count);
}
