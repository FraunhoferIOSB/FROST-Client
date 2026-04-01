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
package de.fraunhofer.iosb.ilt.sta.jackson;

import de.fraunhofer.iosb.ilt.sta.service.ServerSettings;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 *
 * @author Michael Jacoby
 */
public class ServerSettingsDeserializer extends StdDeserializer<ServerSettings> {

    private static final long serialVersionUID = 8376494553925868647L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerSettingsDeserializer.class);

    public ServerSettingsDeserializer() {
        super(ServerSettings.class);
    }

    public ServerSettingsDeserializer(Class<?> type) {
        super(ServerSettings.class);
    }

    @Override
    public ServerSettings deserialize(JsonParser parser, DeserializationContext context)
            throws JacksonException {
        ServerSettings result = new ServerSettings();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(parser);
        if (!root.has(ServerSettings.TAG_EXTENSIONS)) {
            context.reportInputMismatch(ServerSettings.class, "mandatory property '%s' missing", ServerSettings.TAG_EXTENSIONS);
        }
        root.get(ServerSettings.TAG_EXTENSIONS).forEach(x -> {
            String extensionName = x.asText();
            ServerSettings.Extension extension = ServerSettings.Extension.fromName(extensionName);
            if (extension == null) {
                LOGGER.warn("ignording unkown server setting '" + extensionName + "'");
                return;
            }
            Map<String, Object> extensionProperties = mapper.convertValue(
                    root.get(extensionName),
                    context.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class));
            if (extensionProperties == null) {
                extensionProperties = new HashMap<>();
            }
            result.getExtensions().put(extension, extensionProperties);
        });
        return result;
    }
}
