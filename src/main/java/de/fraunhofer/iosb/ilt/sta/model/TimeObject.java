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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import org.threeten.extra.Interval;

/**
 * A time object can be a time instant or a time interval.
 *
 * @author Hylke van der Schaaf
 */
public class TimeObject {

    private Interval valueInterval;
    private ZonedDateTime valueDateTime;
    private final boolean interval;

    public TimeObject(Interval value) {
        valueInterval = value;
        interval = true;
    }

    public TimeObject(ZonedDateTime value) {
        valueDateTime = value;
        interval = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TimeObject other = (TimeObject) obj;
        if (this.interval != other.interval) {
            return false;
        }
        if (!Objects.equals(this.valueInterval, other.valueInterval)) {
            return false;
        }
        return Objects.equals(this.valueDateTime, other.valueDateTime);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.valueInterval);
        hash = 29 * hash + Objects.hashCode(this.valueDateTime);
        hash = 29 * hash + (this.interval ? 1 : 0);
        return hash;
    }

    public boolean isInterval() {
        return interval;
    }

    public Interval getAsInterval() {
        if (interval) {
            return valueInterval;
        }
        return null;
    }

    public ZonedDateTime getAsDateTime() {
        if (interval) {
            return null;
        }
        return valueDateTime;
    }

    @Override
    public String toString() {
        if (interval) {
            return valueInterval.toString();
        }
        return valueDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static TimeObject parse(String value) {
        try {
            ZonedDateTime dt = ZonedDateTime.parse(value);
            return new TimeObject(dt);
        } catch (DateTimeParseException e) {
            // Not a DateTime
        }
        Interval i = Interval.parse(value);
        return new TimeObject(i);
    }
}
