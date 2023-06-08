package com.mec.dictionarygrpcserver.exception;

import lombok.Getter;

public class TickerNotFoundException extends RuntimeException {
    @Getter
    private final Throwable throwable;

    public TickerNotFoundException(Throwable throwable) {
        super(throwable.getMessage());
        this.throwable = throwable;
    }
}
