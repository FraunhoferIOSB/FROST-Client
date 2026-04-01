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
package de.fraunhofer.iosb.ilt.sta.model.builder.api;

/**
 * Base class for any {@link Builder}.
 * <p>
 * Any {@link AbstractBuilder} keeps a reference to the instance under
 * construction. This reference is created thanks to
 * {@link #newBuildingInstance()} and finally returned when calling
 * {@link #build()}
 *
 * @param <T> the instance class type to build
 * @author Aurelien Bourdon
 */
public abstract class AbstractBuilder<T> implements Builder<T> {

    private final T buildingInstance;

    protected AbstractBuilder() {
        buildingInstance = newBuildingInstance();
    }

    /**
     * Create the new instance that will be build by this
     * {@link AbstractBuilder}
     *
     * @return the new instance that will be build by this
     * {@link AbstractBuilder}
     */
    protected abstract T newBuildingInstance();

    /**
     * Get the instance under construction
     *
     * @return the instance under construction
     */
    protected T getBuildingInstance() {
        return buildingInstance;
    }

    /**
     * Finalize the build of the instance under construction and get it
     *
     * @return the created instance by this {@link AbstractBuilder}
     */
    @Override
    public T build() {
        return buildingInstance;
    }

}
