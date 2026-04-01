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
package de.fraunhofer.iosb.ilt.sta.query;

import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An expanded entity is an entity to be included into a request.
 *
 * @author Nils Sommer
 *
 */
public class ExpandedEntity {

    private final List<EntityType> entities;

    private ExpandedEntity(EntityType... entities) throws InvalidRelationException {
        this.entities = new ArrayList<>(entities.length);

        for (int i = 0; i < entities.length; i++) {
            if (i > 0) {
                if (!entities[i - 1].hasRelationTo(entities[i])) {
                    throw new InvalidRelationException(
                            String.format("%s is not directly related to %s",
                                    entities[i].getName(),
                                    entities[i - 1].getName()));
                }
            }
            this.entities.add(entities[i]);
        }
    }

    /**
     * Build an expanded entity. Entities can be nested to create an expanded
     * entity, e.g. Thing/Locations in a Datastream request will include the
     * Thing of the Datastream as well as all the Locations of the Thing.
     *
     * @param entities the entity types that construct this expanded entity
     * @return the expanded entity
     * @throws InvalidRelationException the entity types are not related to each
     * other
     */
    public static ExpandedEntity from(EntityType... entities) throws InvalidRelationException {
        return new ExpandedEntity(entities);
    }

    @Override
    public String toString() {
        return this.entities
                .stream()
                .map(e -> e.getName())
                .collect(Collectors.joining("/"));
    }

    public EntityType getDirectSibling() {
        return this.entities.get(0);
    }
}
