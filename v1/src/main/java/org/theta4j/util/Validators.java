/*
 * Copyright (C) 2015 theta4j project
 */

package org.theta4j.util;

import java.util.Locale;

public final class Validators {
    private Validators() {
        throw new AssertionError();
    }

    public static void notNull(String name, Object value) {
        if (value == null) {
            throw new NullPointerException(String.format("%s must not be null.", name));
        }
    }

    public static void length(String name, byte[] value, int length) {
        if (value.length != length) {
            String message = String.format(Locale.US, "%s must consists of %d Bytes. But actual is %d.", name, length, value.length);
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void rangeEq(String name, T value, Comparable<T> min, Comparable<T> max) {
        if (0 < min.compareTo(value) || max.compareTo(value) < 0) {
            String message = String.format(Locale.US, "Expected %s <= %s <= %s, but given was %s.", min, name, max, value);
            throw new IllegalArgumentException(message);
        }
    }

    public static void portNumber(int port) {
        if (port < 0 || 65535 < port) {
            String message = String.format(Locale.US, "TCP or UDP port number must be in 0-65535, but was %d.", port);
            throw new IllegalArgumentException(message);
        }
    }
}
