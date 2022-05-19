package com.acme.dbo.txlog.message;

public class IntMessage implements Message<Integer> {
    private Integer value;

    public IntMessage(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return value.toString();
    }

    public Integer getValue() {
        return value;
    }

    public void aggregate(Message<Integer> other) {
        value += other.getValue();
    }

    public boolean shouldAggregate(Message<Integer> other) {
        Integer otherValue = other.getValue();
        return !(otherValue > 0 && this.value > Integer.MAX_VALUE - otherValue || otherValue < 0 && this.value < Integer.MIN_VALUE - otherValue);
    }
}
