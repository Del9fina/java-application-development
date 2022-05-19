package com.acme.dbo.txlog;

import com.acme.dbo.txlog.message.IntMessage;
import com.acme.dbo.txlog.message.Message;
import com.acme.dbo.txlog.message.StringMessage;

public class LogService {
    private static final String PRIMITIVE_PREFIX = "primitive: ";
    private static final String STRING_PREFIX = "string: ";
    private static final String CHAR_PREFIX = "char: ";
    private static final String REFERENCE_PREFIX = "reference: ";

    private static Type lastType;
    private static IntMessage intAggregate;
    private static StringMessage stringAggregate;
    // private static byte byteAggregate = 0;

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

    private <T> void log(Message<T> message, Message<T> aggregate, Type type) {
        // TODO
    }

    public void flush() {
        switch (lastType) {
            case INT:
                printDecorated(intAggregate, PRIMITIVE_PREFIX);
                break;
/*            case BYTE:
                printDecorated(byteAggregate, PRIMITIVE_PREFIX);
                break;*/
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
        // BYTE,
        STRING,
        OTHER;
    }

/*    public void log(byte message) {
        if (lastType == Type.BYTE && !leadsToOverflow(message)) {
            byteAggregate += message;
        } else {
            flush();
            byteAggregate = message;
        }
        lastType = Type.BYTE;
    }

    public void log(boolean message) {
        flush();
        printDecorated(message, PRIMITIVE_PREFIX);
    }

    public void log(char message) {
        flush();
        printDecorated(message, CHAR_PREFIX);
    }

    public void log(Object message) {
        flush();
        printDecorated(message, REFERENCE_PREFIX);
    }


    private static boolean leadsToOverflow(byte value) {
        return value > 0 && byteAggregate > Byte.MAX_VALUE - value || value < 0 && byteAggregate < Byte.MIN_VALUE - value;
    }*/
}
