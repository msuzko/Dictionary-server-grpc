package com.mec.dictionarygrpcserver.service.validator.impl;

import com.mec.dictionarygrpcserver.service.validator.GrpcRequestValidator;
import com.mec.proto.dictionary.InstrumentRq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GrpcRequestValidatorImplTest {
    private final GrpcRequestValidator validator = new GrpcRequestValidatorImpl();

    @Test
    @DisplayName("InstrumentRq is correct")
    void validate_isOk() {
        var rq = InstrumentRq.newBuilder().setTicker("AAPL").build();

        assertDoesNotThrow(() -> validator.validate(rq));
    }

    private static Stream<Arguments> invalidTickers() {
        return Stream.of(
                Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of(" ")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTickers")
    @DisplayName("InstrumentRq has problems with fields")
    void validate_exception(String ticker) {

        var rq = ticker == null ? InstrumentRq.getDefaultInstance() : InstrumentRq.newBuilder().setTicker(ticker).build();

        var exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(rq));
        assertThat(exception.getMessage()).isEqualTo("INVALID_ARGUMENT: ticker must be filled");
    }
}