package com.acme.dbo.txlog.message;

public interface Message {
    String getMessage();

    default String getDecorated() {
        return getMessage();
    }

    default void aggregate(Message other) {
        // do nothing
    }

    default boolean shouldAggregate(Message other) {
        return false;
    }
}
