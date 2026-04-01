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

import de.fraunhofer.iosb.ilt.sta.model.Actuator;
import de.fraunhofer.iosb.ilt.sta.model.Task;
import de.fraunhofer.iosb.ilt.sta.model.TaskingCapability;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.swe.common.AbstractDataComponent;
import de.fraunhofer.iosb.ilt.swe.common.complex.DataRecord;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for any {@link EntityBuilder} of {@link TaskingCapability}
 *
 * @param <U> the type of the concrete class that extends this
 * {@link AbstractTaskingCapabilityBuilder}
 * @author Michael Jacoby
 */
public abstract class AbstractTaskingCapabilityBuilder<U extends AbstractTaskingCapabilityBuilder<U>> extends EntityBuilder<TaskingCapability, U> {

    @Override
    protected TaskingCapability newBuildingInstance() {
        return new TaskingCapability();
    }

    public U name(final String name) {
        getBuildingInstance().setName(name);
        return getSelf();
    }

    public U description(final String description) {
        getBuildingInstance().setDescription(description);
        return getSelf();
    }

    public U properties(final Map<String, Object> properties) {
        getBuildingInstance().setProperties(properties);
        return getSelf();
    }

    public U property(final String key, final Object value) {
        if (getBuildingInstance().getProperties() == null) {
            getBuildingInstance().setProperties(new HashMap<>());
        }
        getBuildingInstance().getProperties().put(key, value);
        return getSelf();
    }

    public U taskingParameters(final DataRecord taskingParameters) {
        getBuildingInstance().setTaskingParameters(taskingParameters);
        return getSelf();
    }

    public U taskingParameter(AbstractDataComponent field) {
        if (getBuildingInstance().getTaskingParameters() == null) {
            getBuildingInstance().setTaskingParameters(new DataRecord());
        }
        getBuildingInstance().getTaskingParameters().getField().add(field);
        return getSelf();
    }

    public U taskingParameter(String name, AbstractDataComponent taskingParameter) {
        if (!name.equals(taskingParameter.getName())) {
            taskingParameter.setName(name);
        }
        return taskingParameter(taskingParameter);
    }

    public U actuator(final Actuator actuator) {
        getBuildingInstance().setActuator(actuator);
        return getSelf();
    }

    public U thing(final Thing thing) {
        getBuildingInstance().setThing(thing);
        return getSelf();
    }

    public U task(final Task task) {
        getBuildingInstance().getTasks().add(task);
        return getSelf();
    }

    public U tasks(final List<Task> tasks) {
        getBuildingInstance().getTasks().addAll(tasks);
        return getSelf();
    }

}
