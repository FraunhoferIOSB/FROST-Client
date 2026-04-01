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

import de.fraunhofer.iosb.ilt.sta.model.Entity;
import de.fraunhofer.iosb.ilt.sta.model.Id;
import de.fraunhofer.iosb.ilt.sta.model.TimeObject;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;
import org.threeten.extra.Interval;
import tools.jackson.core.Version;
import tools.jackson.databind.module.SimpleModule;

/**
 * Module that contains all custom Serializer and Deserializer registrations
 * written as part of this library.
 *
 * @author Nils Sommer, Michael Jacoby
 *
 */
public class EntityModule extends SimpleModule {

    private static final long serialVersionUID = -667555967846254500L;

    public EntityModule() {
        super(new Version(0, 0, 1, null, null, null));
        addSerializer(Entity.class, new EntitySerializer());
        addSerializer(UnitOfMeasurement.class, new UnitOfMeasurementSerializer());
        addSerializer(Interval.class, new IntervalSerializer());
        addSerializer(TimeObject.class, new TimeObjectSerializer());
        addDeserializer(EntityList.class, new EntityListDeserializer<>());
        addDeserializer(Id.class, new IdDeserializer());
        addDeserializer(Interval.class, new IntervalDeserializer());
        addDeserializer(TimeObject.class, new TimeObjectDeserializer());
    }
}
