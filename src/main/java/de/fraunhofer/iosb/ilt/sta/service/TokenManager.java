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

import org.apache.http.HttpRequest;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * The TokenManager Interface. Before each request is sent to the Service, the
 * TokenManager has the opportunity to modify the request and add any headers
 * required for Authentication and Authorisation.
 *
 * @author scf
 * @param <T> The exact type of this token manager.
 */
public interface TokenManager<T extends TokenManager> {

    /**
     * Add any headers to the request that are required Authentication and
     * Authorisation.
     *
     * @param request The request to modify.
     */
    public void addAuthHeader(HttpRequest request);

    /**
     * Set the HTTP client this TokenManager uses to fetch tokens.
     *
     * @param client The CloseableHttpClient to use for fetching Tokens.
     * @return this TokenManager
     */
    public T setHttpClient(CloseableHttpClient client);

    /**
     * Get the HTTP client this TokenManager uses to fetch tokens.
     *
     * @return The HTTP client this TokenManager uses to fetch tokens.
     */
    public CloseableHttpClient getHttpClient();

}
