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

import de.fraunhofer.iosb.ilt.sta.model.Task;
import de.fraunhofer.iosb.ilt.sta.model.TaskingCapability;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for any {@link EntityBuilder} of {@link Task}
 *
 * @param <U> the type of the concrete class that extends this
 * {@link AbstractTaskBuilder}
 * @author Michael Jacoby
 */
public abstract class AbstractTaskBuilder<U extends AbstractTaskBuilder<U>> extends EntityBuilder<Task, U> {

    @Override
    protected Task newBuildingInstance() {
        return new Task();
    }

    public U creationTime(final ZonedDateTime creationTime) {
        getBuildingInstance().setCreationTime(creationTime);
        return getSelf();
    }

    public U taskingParameters(final Map<String, Object> taskingParameters) {
        getBuildingInstance().setTaskingParameters(taskingParameters);
        return getSelf();
    }

    public U taskingParameter(final String key, final Object value) {
        if (getBuildingInstance().getTaskingParameters() == null) {
            getBuildingInstance().setTaskingParameters(new HashMap<>());
        }
        getBuildingInstance().getTaskingParameters().put(key, value);
        return getSelf();
    }

    public U taskingCapability(final TaskingCapability taskingCapability) {
        getBuildingInstance().setTaskingCapability(taskingCapability);
        return getSelf();
    }
}
