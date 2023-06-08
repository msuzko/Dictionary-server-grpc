package com.mec.dictionarygrpcserver.service.data.impl;

import com.mec.dictionarygrpcserver.service.data.DataStore;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;


@Service
@Profile("dev")
public class DataStoreStatic implements DataStore {
    public static final Map<String, BigDecimal> tickers = Map.of(
            "MSFT", new BigDecimal("332.68"),
            "AA", new BigDecimal("34.55"),
            "MU", new BigDecimal("67.54"),
            "AAPL", new BigDecimal("179.21"),
            "AMZN", new BigDecimal("126.61")
    );

    @Override
    public Map<String, BigDecimal> getTickers() {
        return tickers;
    }

}
