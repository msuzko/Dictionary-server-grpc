package com.mec.dictionarygrpcserver.service.impl;

import com.mec.dictionarygrpcserver.exception.TickerNotFoundException;
import com.mec.dictionarygrpcserver.service.DictionaryService;
import com.mec.dictionarygrpcserver.service.data.DataStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DictionaryServiceImpl.class)
class DictionaryServiceImplTest {
    @Autowired
    private DictionaryService dictionaryService;
    @MockBean
    private DataStore dataStore;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(dataStore);
    }

    @Test
    void getPrice_isOK() {
        var price = new BigDecimal("132.31");
        when(dataStore.getTickers()).thenReturn(Map.of(
                "AAPL", price,
                "MU", new BigDecimal("65.2")));

        var result = dictionaryService.getPrice("AAPL");

        assertThat(result).isEqualTo(price);
        verify(dataStore).getTickers();
    }

    @Test
    void getPrice_exception() {
        var price = new BigDecimal("132.31");
        when(dataStore.getTickers()).thenReturn(Map.of(
                "MSFT", price,
                "MU", new BigDecimal("65.2")));

        var exception = assertThrows(TickerNotFoundException.class, () -> dictionaryService.getPrice("AAPL"));

        verify(dataStore).getTickers();
        assertThat(exception.getMessage()).isEqualTo("NOT_FOUND: Ticker 'AAPL' not found");
    }
}