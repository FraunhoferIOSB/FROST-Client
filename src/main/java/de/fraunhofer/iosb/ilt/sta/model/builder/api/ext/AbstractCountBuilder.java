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
import de.fraunhofer.iosb.ilt.swe.common.constraint.AllowedValues;
import de.fraunhofer.iosb.ilt.swe.common.simple.Count;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * /**
 * Base class for any {@link Count} builder.
 * <p>
 * Any {@link AbstractCountBuilder} is an {@link ExtensibleBuilder}.
 *
 * @param <T> the concrete {@link Count} type to build
 * @author Michael Jacoby
 */
public abstract class AbstractCountBuilder<T extends AbstractCountBuilder<T>> extends AbstractSimpleComponentBuilder<Count, T> {

    @Override
    protected Count newBuildingInstance() {
        return new Count();
    }

    public T value(final Long value) {
        getBuildingInstance().setValue(value);
        return getSelf();
    }

    public T constraint(final AllowedValues allowedValues) {
        getBuildingInstance().setConstraint(allowedValues);
        return getSelf();
    }

    public T allowedValue(final BigDecimal value) {
        if (getBuildingInstance().getConstraint() == null) {
            getBuildingInstance().setConstraint(new AllowedValues());
        }
        getBuildingInstance().getConstraint().getValue().add(value);
        return getSelf();
    }

    public T allowedValues(final List<BigDecimal> values) {
        if (getBuildingInstance().getConstraint() == null) {
            getBuildingInstance().setConstraint(new AllowedValues());
        }
        getBuildingInstance().getConstraint().getValue().addAll(values);
        return getSelf();
    }

    public T allowedValues(final BigDecimal... values) {
        return allowedValues(Arrays.asList(values));
    }

    public T interval(final List<BigDecimal> interval) {
        if (getBuildingInstance().getConstraint() == null) {
            getBuildingInstance().setConstraint(new AllowedValues());
        }
        getBuildingInstance().getConstraint().getInterval().add(interval);
        return getSelf();
    }

    public T interval(final BigDecimal start, final BigDecimal end) {
        if (getBuildingInstance().getConstraint() == null) {
            getBuildingInstance().setConstraint(new AllowedValues());
        }
        getBuildingInstance().getConstraint().getInterval().add(Arrays.asList(start, end));
        return getSelf();
    }

    public T intervals(final List<List<BigDecimal>> intervals) {
        if (getBuildingInstance().getConstraint() == null) {
            getBuildingInstance().setConstraint(new AllowedValues());
        }
        getBuildingInstance().getConstraint().setInterval(intervals);
        return getSelf();
    }

    public T significantFigures(final int significantFigures) {
        if (getBuildingInstance().getConstraint() == null) {
            getBuildingInstance().setConstraint(new AllowedValues());
        }
        getBuildingInstance().getConstraint().setSignificantFigures(significantFigures);
        return getSelf();
    }
}
