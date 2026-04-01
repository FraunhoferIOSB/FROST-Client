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
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author jab
 */
public class UtilsTest {

    public UtilsTest() {
    }

    @Test
    public void testEscapeForStringConstant() {
        Assert.assertEquals("abcdefg", Utils.escapeForStringConstant("abcdefg"));
        Assert.assertEquals("''", Utils.escapeForStringConstant("'"));
        Assert.assertEquals("''''", Utils.escapeForStringConstant("''"));
    }

    @Test
    public void testUrlEncode() {
        Assert.assertEquals("http%3A//example.org/Things%5Bxyz%27xyz%5D", Utils.urlEncode("http://example.org/Things[xyz'xyz]", true));
        Assert.assertEquals("http%3A%2F%2Fexample.org%2FThings%5Bxyz%27xyz%5D", Utils.urlEncode("http://example.org/Things[xyz'xyz]", false));
    }

}
