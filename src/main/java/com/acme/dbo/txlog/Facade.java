package com.acme.dbo.txlog;

public class Facade {
    private static final String PRIMITIVE_PREFIX = "primitive: ";
    private static final String CHAR_PREFIX = "char: ";
    private static final String STRING_PREFIX = "string: ";
    private static final String REFERENCE_PREFIX = "reference: ";

    public static void log(int message) {
        printDecorated(message, PRIMITIVE_PREFIX);
    }

    public static void log(byte message) {
        printDecorated(message, PRIMITIVE_PREFIX);
    }

    public static void log(boolean message) {
        printDecorated(message, PRIMITIVE_PREFIX);
    }

    public static void log(char message) {
        printDecorated(message, CHAR_PREFIX);
    }

    public static void log(String message) {
        printDecorated(message, STRING_PREFIX);
    }

    public static void log(Object message) {
        printDecorated(message, REFERENCE_PREFIX);
    }

    private static void printDecorated(Object message, String prefix) {
        System.out.println(decorate(message, prefix));
    }

    private static String decorate(Object message, String prefix) {
        return prefix + message;
    }
}
