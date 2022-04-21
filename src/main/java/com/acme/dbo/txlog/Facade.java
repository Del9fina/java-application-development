package com.acme.dbo.txlog;

import java.util.Objects;

public class Facade {
    private static final String PRIMITIVE_PREFIX = "primitive: ";
    private static final String CHAR_PREFIX = "char: ";
    private static final String STRING_PREFIX = "string: ";
    private static final String REFERENCE_PREFIX = "reference: ";

    private static Type lastType = Type.OTHER;
    private static int intAggregate = 0;
    private static byte byteAggregate = 0;
    private static String stringValue = "";
    private static int stringCount = 0;

    public static void log(int message) {
        if (lastType == Type.INT && !leadsToOverflow(message)) {
            intAggregate += message;
        } else {
            flush();
            intAggregate = message;
        }
        lastType = Type.INT;
    }

    public static void log(byte message) {
        if (lastType == Type.BYTE && !leadsToOverflow(message)) {
            byteAggregate += message;
        } else {
            flush();
            byteAggregate = message;
        }
        lastType = Type.BYTE;
    }

    public static void log(boolean message) {
        flush();
        printDecorated(message, PRIMITIVE_PREFIX);
    }

    public static void log(char message) {
        flush();
        printDecorated(message, CHAR_PREFIX);
    }

    public static void log(String message) {
        if (lastType == Type.STRING && Objects.equals(stringValue, message)) {
            stringCount += 1;
        } else {
            flush();
            stringValue = message;
            stringCount = 1;
        }
        lastType = Type.STRING;
    }

    public static void log(Object message) {
        flush();
        printDecorated(message, REFERENCE_PREFIX);
    }

    public static void flush() {
        switch (lastType) {
            case INT:
                printDecorated(intAggregate, PRIMITIVE_PREFIX);
                break;
            case BYTE:
                printDecorated(byteAggregate, PRIMITIVE_PREFIX);
                break;
            case STRING:
                printDecorated(formatStringAggregate(), STRING_PREFIX);
                break;
        }

        lastType = Type.OTHER;
        intAggregate = 0;
        stringValue = "";
        stringCount = 0;
    }

    private static String formatStringAggregate() {
        if (stringCount == 1) {
            return stringValue;
        }
        return String.format("%s (x%s)", stringValue, stringCount);
    }

    private static boolean leadsToOverflow(int value) {
        return value > 0 && intAggregate > Integer.MAX_VALUE - value || value < 0 && intAggregate < Integer.MIN_VALUE - value;
    }

    private static boolean leadsToOverflow(byte value) {
        return value > 0 && byteAggregate > Byte.MAX_VALUE - value || value < 0 && byteAggregate < Byte.MIN_VALUE - value;
    }

    private static void printDecorated(Object message, String prefix) {
        System.out.println(Decorator.decorate(message, prefix));
    }

    enum Type {
        INT,
        BYTE,
        STRING,
        OTHER
    }
}
