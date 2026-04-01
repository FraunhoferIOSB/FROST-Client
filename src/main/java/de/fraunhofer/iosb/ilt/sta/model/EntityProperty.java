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
package de.fraunhofer.iosb.ilt.sta.model;

/**
 *
 * @author Michael Jacoby
 */
public enum EntityProperty {
    CREATIONTIME("creationTime"),
    DESCRIPTION("description"),
    DEFINITION("definition"),
    ENCODINGTYPE("encodingType"),
    FEATURE("feature"),
    ID("id"),
    LOCATION("location"),
    METADATA("metadata"),
    MULTIOBSERVATIONDATATYPES("multiObservationDataTypes"),
    NAME("name"),
    OBSERVATIONTYPE("observationType"),
    OBSERVEDAREA("observedArea"),
    PHENOMENONTIME("phenomenonTime"),
    PARAMETERS("parameters"),
    PROPERTIES("properties"),
    RESULT("result"),
    RESULTTIME("resultTime"),
    RESULTQUALITY("resultQuality"),
    TASKINGPARAMETERS("taskingParameters"),
    TIME("time"),
    UNITOFMEASUREMENT("unitOfMeasurement"),
    UNITOFMEASUREMENTS("unitOfMeasurements"),
    VALIDTIME("validTime");

    private final String name;

    EntityProperty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
