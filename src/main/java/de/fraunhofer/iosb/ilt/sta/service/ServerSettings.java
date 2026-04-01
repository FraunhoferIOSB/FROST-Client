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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Michael Jacoby
 */
public class ServerSettings {

    public static final String TAG_SERVER_SETTINGS = "serverSettings";
    public static final String TAG_EXTENSIONS = "extensions";
    public static final String TAG_MQTT_ENDPOINTS = "endpoints";
    private Map<Extension, Map<String, Object>> extensions;

    public ServerSettings() {
        extensions = new HashMap<>();
    }

    public Map<Extension, Map<String, Object>> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<Extension, Map<String, Object>> extensions) {
        this.extensions = extensions;
    }

    public enum Extension {
        ACTUATION("actuation"),
        MULTIDATASTREAM("multiDatastream"),
        MQTT("mqtt");

        private String name;

        private Extension(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public static Extension fromName(String name) {
            Optional<Extension> result = Stream.of(values()).filter(x -> x.getName().equals(name)).findAny();
            if (result.isPresent()) {
                return result.get();
            }
            return null;
        }
    }
}
