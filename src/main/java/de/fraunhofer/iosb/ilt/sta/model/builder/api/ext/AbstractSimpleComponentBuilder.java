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
import de.fraunhofer.iosb.ilt.swe.common.simple.AbstractSimpleComponent;
import de.fraunhofer.iosb.ilt.swe.common.util.NillValue;
import java.util.List;

/**
 * Base class for any {@link AbstractSimpleComponent} builder.
 * <p>
 * Any {@link AbstractDataComponentBuilder} is an {@link ExtensibleBuilder}.
 *
 * @param <T> the concrete {@link AbstractSimpleComponent} type to build
 * @param <U> the concrete type of this {@link AbstractDataComponentBuilder}
 * @author Michael Jacoby
 */
public abstract class AbstractSimpleComponentBuilder<T extends AbstractSimpleComponent, U extends AbstractSimpleComponentBuilder<T, U>> extends AbstractDataComponentBuilder<T, U> {

    public U axisId(final String axisId) {
        getBuildingInstance().setAxisID(axisId);
        return getSelf();
    }

    public U nilValues(final List<NillValue> nilValues) {
        getBuildingInstance().setNilValues(nilValues);
        return getSelf();
    }

    public U nilValue(final NillValue nilValue) {
        getBuildingInstance().getNilValues().add(nilValue);
        return getSelf();
    }

    public U referenceFrame(final String referenceFrame) {
        getBuildingInstance().setReferenceFrame(referenceFrame);
        return getSelf();
    }
}
