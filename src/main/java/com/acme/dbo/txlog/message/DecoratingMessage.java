package com.acme.dbo.txlog.message;

public abstract class DecoratingMessage implements Message {
    protected static final String PRIMITIVE_PREFIX = "primitive: ";
    protected static final String STRING_PREFIX = "string: ";

    private final String prefix;

    public DecoratingMessage(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getDecorated() {
        return prefix + getMessage();
    }
}
