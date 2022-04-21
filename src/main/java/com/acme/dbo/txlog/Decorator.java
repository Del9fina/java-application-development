package com.acme.dbo.txlog;

public class Decorator {

    static String decorate(Object message, String prefix) {
        return prefix + message;
    }
}
