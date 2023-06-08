package com.mec.dictionarygrpcserver.exception;

import lombok.Getter;

/**
 * Checking incoming params in gRPS service with error text
 */
public class GrpcIllegalArgumentException extends IllegalArgumentException {

    @Getter
    private final Throwable throwable;

    public GrpcIllegalArgumentException(Throwable throwable) {
        super(throwable.getMessage());
        this.throwable = throwable;
    }
}
