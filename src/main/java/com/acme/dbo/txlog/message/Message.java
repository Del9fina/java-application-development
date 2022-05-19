package com.acme.dbo.txlog.message;

import java.util.Objects;

public interface Message<T> {
    T getMessage();
    T getValue();
    void aggregate(Message<T> other);
    boolean shouldAggregate(Message<T> other);
}
