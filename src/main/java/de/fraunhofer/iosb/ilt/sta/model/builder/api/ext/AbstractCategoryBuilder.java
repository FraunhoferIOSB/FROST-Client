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
import de.fraunhofer.iosb.ilt.swe.common.constraint.AllowedTokens;
import de.fraunhofer.iosb.ilt.swe.common.simple.Category;
import java.util.Arrays;
import java.util.List;

/**
 * /**
 * Base class for any {@link Category} builder.
 * <p>
 * Any {@link AbstractCategoryBuilder} is an {@link ExtensibleBuilder}.
 *
 * @param <T> the concrete {@link Category} type to build
 * @author Michael Jacoby
 */
public abstract class AbstractCategoryBuilder<T extends AbstractCategoryBuilder<T>> extends AbstractSimpleComponentBuilder<Category, T> {

    @Override
    protected Category newBuildingInstance() {
        return new Category();
    }

    public T value(final String value) {
        getBuildingInstance().setValue(value);
        return getSelf();
    }

    public T constraint(final AllowedTokens allowedTokens) {
        getBuildingInstance().setConstraint(allowedTokens);
        return getSelf();
    }

    public T allowedValue(final String value) {
        if (getBuildingInstance().getConstraint() == null) {
            getBuildingInstance().setConstraint(new AllowedTokens());
        }
        getBuildingInstance().getConstraint().getValue().add(value);
        return getSelf();
    }

    public T allowedValues(final List<String> values) {
        AllowedTokens constraint = getBuildingInstance().getConstraint();
        if (constraint == null) {
            constraint = new AllowedTokens();
            getBuildingInstance().setConstraint(constraint);
        }
        final List<String> value = constraint.getValue();
        if (value == null) {
            constraint.setValue(values);
        } else {
            value.addAll(values);
        }
        return getSelf();
    }

    public T allowedValues(final String... values) {
        return allowedValues(Arrays.asList(values));
    }

    public T pattern(final String pattern) {
        if (getBuildingInstance().getConstraint() == null) {
            getBuildingInstance().setConstraint(new AllowedTokens());
        }
        getBuildingInstance().getConstraint().setPattern(pattern);
        return getSelf();
    }
}
