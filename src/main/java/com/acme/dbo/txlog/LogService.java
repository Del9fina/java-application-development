package com.acme.dbo.txlog;

import com.acme.dbo.txlog.message.IntMessage;
import com.acme.dbo.txlog.message.Message;
import com.acme.dbo.txlog.message.StringMessage;

public class LogService {
    private static final String PRIMITIVE_PREFIX = "primitive: ";
    private static final String STRING_PREFIX = "string: ";

    private static Type lastType;
    private static IntMessage intAggregate;
    private static StringMessage stringAggregate;

    public LogService() {
        reset();
    }

    public void log(IntMessage message) {
        if (lastType == Type.INT && intAggregate.shouldAggregate(message)) {
            intAggregate.aggregate(message);
        } else {
            flush();
            intAggregate = message;
        }
        lastType = Type.INT;
    }

    public void log(StringMessage message) {
        if (lastType == Type.STRING && stringAggregate.shouldAggregate(message)) {
            stringAggregate.aggregate(message);
        } else {
            flush();
            stringAggregate = message;
        }
        lastType = Type.STRING;
    }

    public void flush() {
        switch (lastType) {
            case INT:
                printDecorated(intAggregate, PRIMITIVE_PREFIX);
                break;
            case STRING:
                printDecorated(stringAggregate, STRING_PREFIX);
                break;
        }
        reset();
    }

    private void reset() {
        lastType = Type.OTHER;
        intAggregate = new IntMessage(0);
        stringAggregate = new StringMessage("");
    }

    private static void printDecorated(Message<?> message, String prefix) {
        System.out.println(Decorator.decorate(message.getMessage(), prefix));
    }

    enum Type {
        INT,
        STRING,
        OTHER
    }
}
