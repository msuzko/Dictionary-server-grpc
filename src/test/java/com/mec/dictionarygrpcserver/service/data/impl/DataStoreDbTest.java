package com.mec.dictionarygrpcserver.service.data.impl;

import com.mec.dictionarygrpcserver.entity.Instrument;
import com.mec.dictionarygrpcserver.repository.PriceRepository;
import com.mec.dictionarygrpcserver.service.data.DataStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = DataStoreDb.class)
class DataStoreDbTest {
    @Autowired
    private DataStore dataStore;
    @MockBean
    private PriceRepository priceRepository;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(priceRepository);
    }

    @Test
    void getTickers() {
        var tickers = List.of(
                new Instrument("AAPL", new BigDecimal("100")),
                new Instrument("AA", new BigDecimal("50")));
        when(priceRepository.findAll()).thenReturn(tickers);

        var result = dataStore.getTickers();

        verify(priceRepository).findAll();
        assertThat(result).hasSize(2).containsExactlyInAnyOrderEntriesOf(
                Map.of("AAPL", new BigDecimal("100"), "AA", new BigDecimal("50")));
    }
}