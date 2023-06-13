package com.mec.dictionarygrpcserver.service.data.impl;

import com.mec.dictionarygrpcserver.service.data.DataStore;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DataStoreStaticTest {
    private final DataStore dataStore = new DataStoreStatic();

    @Test
    void getTickers() {
        var expected = Map.of(
                "MSFT", new BigDecimal("332.68"),
                "AA", new BigDecimal("34.55"),
                "MU", new BigDecimal("67.54"),
                "AAPL", new BigDecimal("179.21"),
                "AMZN", new BigDecimal("126.61")
        );

        assertThat(dataStore.getTickers()).hasSize(5).containsExactlyInAnyOrderEntriesOf(expected);
    }
}