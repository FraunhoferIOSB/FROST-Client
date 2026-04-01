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

import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.MultiDatastream;
import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import java.net.URI;
import java.util.List;

/**
 * Base class for any {@link EntityBuilder} of {@link ObservedProperty}
 *
 * @param <U> the type of the concrete class that extends this
 * {@link AbstractObservedPropertyBuilder}
 * @author Aurelien Bourdon
 */
public abstract class AbstractObservedPropertyBuilder<U extends AbstractObservedPropertyBuilder<U>> extends EntityBuilder<ObservedProperty, U> {

    @Override
    protected ObservedProperty newBuildingInstance() {
        return new ObservedProperty();
    }

    public U name(final String name) {
        getBuildingInstance().setName(name);
        return getSelf();
    }

    public U definition(final URI definition) {
        getBuildingInstance().setDefinition(definition.toString());
        return getSelf();
    }

    public U definition(final String definition) {
        getBuildingInstance().setDefinition(definition);
        return getSelf();
    }

    public U description(final String description) {
        getBuildingInstance().setDescription(description);
        return getSelf();
    }

    public U datastreams(final List<Datastream> datastreams) {
        getBuildingInstance().getDatastreams().addAll(datastreams);
        return getSelf();
    }

    public U datastream(final Datastream datastream) {
        getBuildingInstance().getDatastreams().add(datastream);
        return getSelf();
    }

    public U multiDatastreams(final List<MultiDatastream> multiDatastreams) {
        getBuildingInstance().getMultiDatastreams().addAll(multiDatastreams);
        return getSelf();
    }

    public U multiDatastream(final MultiDatastream multiDatastream) {
        getBuildingInstance().getMultiDatastreams().add(multiDatastream);
        return getSelf();
    }

}
