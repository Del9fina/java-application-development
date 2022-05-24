package com.acme.dbo.txlog.message;

import java.util.Objects;

public class StringMessage extends DecoratingMessage {
    private final String value;
    private int count;

    public StringMessage(String value) {
        super(STRING_PREFIX);
        this.value = value;
        this.count = 1;
    }

    @Override
    public String getMessage() {
        if (count == 1) {
            return value;
        }
        return String.format("%s (x%s)", value, count);
    }

    @Override
    public void aggregate(Message other) {
        count += 1;
    }

    @Override
    public boolean shouldAggregate(Message other) {
        return isSameType(other) && Objects.equals(value, ((StringMessage) other).value);
    }

    private boolean isSameType(Message other) {
        return other instanceof StringMessage;
    }
}
