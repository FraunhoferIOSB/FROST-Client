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
package de.fraunhofer.iosb.ilt.sta.model.ext;

import de.fraunhofer.iosb.ilt.sta.model.Observation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author scf
 */
public class DataArrayDocument {

    private long count = -1;
    private String nextLink;
    private List<DataArrayValue> value = new ArrayList<>();

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public void addDataArrayValue(DataArrayValue dav) {
        value.add(dav);
    }

    public List<DataArrayValue> getValue() {
        return value;
    }

    /**
     * All observations in all Datastreams in this DataArrayDocument.
     *
     * @return All observations in all Datastreams in this DataArrayDocument.
     */
    public List<Observation> getObservations() {
        List<Observation> retval = new ArrayList<>();
        for (DataArrayValue dav : value) {
            retval.addAll(dav.getObservations());
        }
        return retval;
    }
}
