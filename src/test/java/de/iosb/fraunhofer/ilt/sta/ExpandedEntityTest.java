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
package de.iosb.fraunhofer.ilt.sta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import de.fraunhofer.iosb.ilt.sta.model.EntityType;
import de.fraunhofer.iosb.ilt.sta.query.ExpandedEntity;
import de.fraunhofer.iosb.ilt.sta.query.InvalidRelationException;
import org.junit.Test;

public class ExpandedEntityTest {

    @Test // Thing/Locations
    public void testRelation_1_to_n_valid() {
        try {
            ExpandedEntity expandedEntity = ExpandedEntity.from(EntityType.THING, EntityType.LOCATIONS);
            assertEquals(EntityType.THING, expandedEntity.getDirectSibling());
            assertEquals("Thing/Locations", expandedEntity.toString());
        } catch (InvalidRelationException e) {
            fail(e.getMessage());
        }
    }

    @Test // Datastream/Sensors (invalid)
    public void testRelation_1_to_n_invalid() {
        assertThrows(InvalidRelationException.class,
                () -> ExpandedEntity.from(EntityType.DATASTREAM, EntityType.SENSORS));
    }

    @Test // Datastream/Sensor
    public void testRelation_1_to_1_valid() {
        try {
            ExpandedEntity expandedEntity = ExpandedEntity.from(EntityType.DATASTREAM, EntityType.SENSOR);
            assertEquals(EntityType.DATASTREAM, expandedEntity.getDirectSibling());
            assertEquals("Datastream/Sensor", expandedEntity.toString());
        } catch (InvalidRelationException e) {
            fail(e.getMessage());
        }
    }

    @Test // Thing/Datastreams/Sensor
    public void testRelations_1_to_n_to_1_valid() {
        try {
            ExpandedEntity expandedEntity = ExpandedEntity
                    .from(EntityType.THING, EntityType.DATASTREAMS, EntityType.SENSOR);
            assertEquals(EntityType.THING, expandedEntity.getDirectSibling());
            assertEquals("Thing/Datastreams/Sensor", expandedEntity.toString());
        } catch (InvalidRelationException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSingleType() {
        try {
            ExpandedEntity.from(EntityType.THING);
        } catch (InvalidRelationException e) {
            fail(e.getMessage());
        }
    }

}
