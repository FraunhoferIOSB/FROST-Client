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
package de.iosb.fraunhofer.ilt.sta;

import de.fraunhofer.iosb.ilt.sta.Utils;
import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import de.fraunhofer.iosb.ilt.sta.model.builder.ObservedPropertyBuilder;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import de.fraunhofer.iosb.ilt.sta.service.TokenManagerJWT;
import java.net.URI;
import java.net.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TokenManagerJWTTest {

    private String staRootUrl;
    private String authUrl;
    private String jwtId;
    private String jwtKey;
    private boolean configured;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        configured = false;
        staRootUrl = System.getenv("STA_ROOT_URL");
        authUrl = System.getenv("AUTH_URL");
        jwtId = System.getenv("JWT_ID");
        jwtKey = System.getenv("JWT_KEY");
        if (staRootUrl != null && authUrl != null
                && jwtId != null && jwtKey != null) {
            configured = true;
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testJwtAuth() throws Exception {
        if (configured) {
            URL serviceEndpoint = new URL(staRootUrl);
            SensorThingsService service = new SensorThingsService();
            Utils.createInsecureHttpClient(service);
            service.setEndpoint(serviceEndpoint);
            TokenManagerJWT tokenMgr = new TokenManagerJWT()
                    .setJwtId(jwtId)
                    .setJwtKey(jwtKey)
                    .setTokenServerUrl(authUrl);
            service.setTokenManager(tokenMgr);
            ObservedProperty op = ObservedPropertyBuilder.builder()
                    .name("Test ObservedProperty")
                    .definition(URI.create("http://example.com/test_obs_prop"))
                    .description("Which describes the Test ObservedProperty")
                    .build();
            service.create(op);
            service.delete(op);
        }
    }
}
