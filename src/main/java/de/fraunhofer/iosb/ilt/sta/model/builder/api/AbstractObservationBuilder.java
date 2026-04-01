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
import de.fraunhofer.iosb.ilt.sta.model.FeatureOfInterest;
import de.fraunhofer.iosb.ilt.sta.model.MultiDatastream;
import de.fraunhofer.iosb.ilt.sta.model.Observation;
import de.fraunhofer.iosb.ilt.sta.model.TimeObject;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import org.threeten.extra.Interval;

/**
 * Base class for any {@link EntityBuilder} of {@link Observation}
 *
 * @param <U> the type of the concrete class that extends this
 * {@link AbstractObservationBuilder}
 * @author Aurelien Bourdon
 */
public abstract class AbstractObservationBuilder<U extends AbstractObservationBuilder<U>> extends EntityBuilder<Observation, U> {

    @Override
    protected Observation newBuildingInstance() {
        return new Observation();
    }

    public U phenomenonTime(final TimeObject phenomenonTime) {
        getBuildingInstance().setPhenomenonTime(phenomenonTime);
        return getSelf();
    }

    public U resultTime(final ZonedDateTime resultTime) {
        getBuildingInstance().setResultTime(resultTime);
        return getSelf();
    }

    public U result(final Object result) {
        getBuildingInstance().setResult(result);
        return getSelf();
    }

    public U resultQuality(final /* DQ_Element */ Object resultQuality) {
        getBuildingInstance().setResultQuality(resultQuality);
        return getSelf();
    }

    public U validTime(final Interval validTime) {
        getBuildingInstance().setValidTime(validTime);
        return getSelf();
    }

    public U parameters(final Map<String, Object> parameters) {
        getBuildingInstance().setParameters(parameters);
        return getSelf();
    }

    public U parameter(final String key, final Object value) {
        if (getBuildingInstance().getParameters() == null) {
            getBuildingInstance().setParameters(new HashMap<>());
        }
        getBuildingInstance().getParameters().put(key, value);
        return getSelf();
    }

    public U datastream(final Datastream datastream) {
        getBuildingInstance().setDatastream(datastream);
        return getSelf();
    }

    public U multiDatastream(final MultiDatastream multiDatastream) {
        getBuildingInstance().setMultiDatastream(multiDatastream);
        return getSelf();
    }

    public U featureOfInterest(final FeatureOfInterest featureOfInterest) {
        getBuildingInstance().setFeatureOfInterest(featureOfInterest);
        return getSelf();
    }

}
