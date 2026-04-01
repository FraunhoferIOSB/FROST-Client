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
package de.fraunhofer.iosb.ilt.sta.service;

import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Michael Jacoby
 */
public enum SensorThingsAPIVersion {
    v1_0(1, 0),
    V1_1(1, 1);

    public static SensorThingsAPIVersion fromString(String value) {
        Optional<SensorThingsAPIVersion> result = Stream.of(SensorThingsAPIVersion.values())
                .filter(x -> x.getUrlPattern().equals(value))
                .findFirst();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    private final int major;
    private final int minor;

    private SensorThingsAPIVersion(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public String getUrlPattern() {
        return String.format("v%d.%d", getMajor(), getMinor());
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }
}
