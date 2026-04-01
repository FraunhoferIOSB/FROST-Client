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
import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;

/**
 * Base class for any {@link UnitOfMeasurement} builder
 *
 * @param <T> the concrete type that extends this
 * {@link AbstractUnitOfMeasurementBuilder}
 * @author Aurelien Bourdon
 */
public abstract class AbstractUnitOfMeasurementBuilder<T extends AbstractUnitOfMeasurementBuilder<T>> extends ExtensibleBuilder<UnitOfMeasurement, T> {

    @Override
    protected UnitOfMeasurement newBuildingInstance() {
        return new UnitOfMeasurement();
    }

    public T name(final String name) {
        getBuildingInstance().setName(name);
        return getSelf();
    }

    public T definition(final String definition) {
        getBuildingInstance().setDefinition(definition);
        return getSelf();
    }

    public T symbol(final String symbol) {
        getBuildingInstance().setSymbol(symbol);
        return getSelf();
    }

}
