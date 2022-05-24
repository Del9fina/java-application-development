package com.acme.dbo.txlog.message;

public class IntMessage extends DecoratingMessage {
    private int value;

    public IntMessage(int value) {
        super(PRIMITIVE_PREFIX);
        this.value = value;
    }

    @Override
    public String getMessage() {
        return String.valueOf(value);
    }

    @Override
    public void aggregate(Message other) {
        value += ((IntMessage) other).value;
    }

    @Override
    public boolean shouldAggregate(Message other) {
        if (!isSameType(other)) {
            return false;
        }
        int otherValue = ((IntMessage) other).value;
        return !(otherValue > 0 && this.value > Integer.MAX_VALUE - otherValue || otherValue < 0 && this.value < Integer.MIN_VALUE - otherValue);
    }

    private boolean isSameType(Message other) {
        return other instanceof IntMessage;
    }
}
