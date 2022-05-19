package com.acme.dbo.txlog.message;

import java.util.Objects;

public class StringMessage implements Message<String> {
    private final String value;
    private int count;

    public StringMessage(String value) {
        this.value = value;
        this.count = 1;
    }

    public String getMessage() {
        if (count == 1) {
            return value;
        }
        return String.format("%s (x%s)", value, count);
    }

    public String getValue() {
        return value;
    }

    public void aggregate(Message<String> other) {
        count += 1;
    }

    public boolean shouldAggregate(Message<String> other) {
        return Objects.equals(value, other.getValue());
    }
}
