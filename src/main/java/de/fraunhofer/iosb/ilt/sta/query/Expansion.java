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
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Type safe builder of $expand queries. You can use it in a human readable way,
 * e.g.:
 *
 * <pre>
 * <code>
 * Expansion.of(EntityType.THING)
 * 			.with(ExpandedEntity.of(EntityType.LOCATIONS))
 * 			.and(ExpandedEntity.of(EntityType.DATASTREAMS, EntityType.OBSERVATIONS))
 * 			.and(ExpandedEntity.of(EntityType.DATASTREAMS, EntityType.SENSOR));
 * </code>
 * </pre>
 *
 * This expansion would be valid for Thing requests and expand to the thing's
 * locations, observations of the thing's datastreams and the sensor of each
 * datastream of the thing.
 *
 * @author Nils Sommer
 *
 */
public class Expansion {

    private final Set<ExpandedEntity> entities = new HashSet<>();
    private final EntityType type;

    private Expansion(EntityType type) {
        this.type = type;
    }

    /**
     * Start an expansion for the original entity type being requested.
     *
     * @param type the entity type
     * @return the expansion
     */
    public static Expansion of(EntityType type) {
        return new Expansion(type);
    }

    /**
     * Start an expansion query.
     *
     * @param entity the referenced entity
     * @return the Expansion instance
     * @throws InvalidRelationException the expanded entity is not related to
     * the requested entity
     */
    public Expansion with(ExpandedEntity entity) throws InvalidRelationException {
        if (!this.type.hasRelationTo(entity.getDirectSibling())) {
            throw new InvalidRelationException(
                    String.format("%s is not directly related to %s",
                            this.type.getName(),
                            entity.getDirectSibling().getName()));
        }

        this.entities.add(entity);
        return this;
    }

    /**
     * Expand to a referenced entity.
     *
     * @param entity the referenced entity
     * @return the Expansion instance
     * @throws InvalidRelationException the expanded entity is not related to
     * the requested entity
     */
    public Expansion and(ExpandedEntity entity) throws InvalidRelationException {
        return this.with(entity);
    }

    @Override
    public String toString() {
        return this.entities
                .stream()
                .map(e -> e.toString())
                .collect(Collectors.joining(","));
    }
}
