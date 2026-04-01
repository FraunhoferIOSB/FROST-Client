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

import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author scf
 */
public class Utils {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    /**
     * Removes characters that might break logging output. Currently: \n, \r and
     * \t
     *
     * @param string The string to clean.
     * @return The cleaned string.
     */
    public static String cleanForLogging(String string) {
        return StringUtils.replaceChars(string, "\n\r\t", "");
    }

    /**
     * Removes characters that might break logging output and truncates to a
     * maximum length.Currently: \n, \r and \t
     *
     * @param string The string to clean.
     * @param maxLength The maximum length of the returned String.
     * @return The cleaned string.
     */
    public static String cleanForLogging(String string, int maxLength) {
        return StringUtils.replaceChars(StringUtils.abbreviate(string, maxLength), "\n\r\t", "");
    }

    /**
     * Returns true if the given string is null, or empty.
     *
     * @param string the string to check.
     * @return true if string == null || string.isEmpty()
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Replaces all ' in the string with ''.
     *
     * @param in The string to escape.
     * @return The escaped string.
     */
    public static String escapeForStringConstant(String in) {
        return in.replaceAll("'", "''");
    }

    public static String quoteForUrl(Object in) {
        if (in instanceof Number) {
            return in.toString();
        }
        return "'" + escapeForStringConstant(in.toString()) + "'";
    }

    /**
     * Urlencodes the given string, optionally not encoding forward slashes.
     *
     * In urls, forward slashes before the "?" must never be urlEncoded.
     * Urlencoding of slashes could otherwise be used to obfuscate phising URLs.
     *
     * @param string The string to urlEncode.
     * @param notSlashes If true, forward slashes are not encoded.
     * @return The urlEncoded string.
     */
    public static String urlEncode(String string, boolean notSlashes) {
        if (notSlashes) {
            return urlEncodeNotSlashes(string);
        }
        try {
            return URLEncoder.encode(string, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error("Should not happen, UTF-8 should always be supported.", ex);
        }
        return string;
    }

    /**
     * Urlencodes the given string
     *
     * @param string The string to urlEncode.
     * @return The urlEncoded string.
     */
    public static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error("Should not happen, UTF-8 should always be supported.", ex);
        }
        return string;
    }

    /**
     * Urlencodes the given string, except for the forward slashes.
     *
     * @param string The string to urlEncode.
     * @return The urlEncoded string.
     */
    public static String urlEncodeNotSlashes(String string) {
        try {
            String[] split = string.split("/");
            for (int i = 0; i < split.length; i++) {
                split[i] = URLEncoder.encode(split[i], StandardCharsets.UTF_8.name());
            }
            return String.join("/", split);
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error("Should not happen, UTF-8 should always be supported.", ex);
        }
        return string;
    }

    /**
     * Throws a StatusCodeException if the given response did not have status
     * code 2xx
     *
     * @param request The request that generated the response.
     * @param response The response to check the status code of.
     * @throws StatusCodeException If the response was not 2xx.
     */
    public static void throwIfNotOk(HttpRequestBase request, CloseableHttpResponse response) throws StatusCodeException {
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode < 200 || statusCode >= 300) {
            String returnContent = null;
            try {
                returnContent = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            } catch (IOException exc) {
                LOGGER.warn("Failed to get content from error response.", exc);
            }
            if (statusCode == 401 || statusCode == 403) {
                request.getURI();
                throw new NotAuthorizedException(request.getURI().toString(), response.getStatusLine().getReasonPhrase(), returnContent);
            }
            if (statusCode == 404) {
                throw new NotFoundException(request.getURI().toString(), response.getStatusLine().getReasonPhrase(), returnContent);
            }
            throw new StatusCodeException(request.getURI().toString(), statusCode, response.getStatusLine().getReasonPhrase(), returnContent);
        }
    }

    public static void createInsecureHttpClient(SensorThingsService service) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
        service.getClientBuilder().setSSLSocketFactory(connectionFactory);
        service.rebuildHttpClient();
    }
}
