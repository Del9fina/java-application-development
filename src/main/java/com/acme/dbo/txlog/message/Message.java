package com.acme.dbo.txlog.message;

public interface Message<T> {
    String getMessage();
    T getValue();
    void aggregate(Message<T> other);
    boolean shouldAggregate(Message<T> other);
}
