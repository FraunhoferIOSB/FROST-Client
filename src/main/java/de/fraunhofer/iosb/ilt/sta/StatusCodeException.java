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
package de.fraunhofer.iosb.ilt.sta;

/**
 * The exception that is thrown when the service returns something else than a
 * 200 OK status.
 *
 * @author scf
 */
public class StatusCodeException extends ServiceFailureException {

    private final String url;
    private final int statusCode;
    private final String statusMessage;
    private final String returnedContent;

    public StatusCodeException(String url, int statusCode, String statusMessage, String returnedContent) {
        super("StatusCode: " + statusCode + ": " + statusMessage);
        this.url = url;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.returnedContent = returnedContent;
    }

    /**
     * The URL that generated the failure response.
     *
     * @return The URL that generated the failure response.
     */
    public String getUrl() {
        return url;
    }

    /**
     * The status code returned by the server.
     *
     * @return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * The status message returned by the server.
     *
     * @return the statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * The content returned by the server.
     *
     * @return the returnedContent
     */
    public String getReturnedContent() {
        return returnedContent;
    }

    @Override
    public String toString() {
        return getClass().getName() + " Code: " + statusCode + " " + statusMessage;
    }

}
