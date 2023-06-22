package com.mec.dictionarygrpcserver.service.impl;

import com.mec.dictionarygrpcserver.exception.TickerNotFoundException;
import com.mec.dictionarygrpcserver.service.DictionaryService;
import com.mec.dictionarygrpcserver.service.data.DataStore;
import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DataStore dataStore;

    @Override
    public BigDecimal getPrice(String ticker) {
        var tickers = dataStore.getTickers();
        log.info(String.valueOf(tickers));
        if (!tickers.containsKey(ticker)) {
            throw new TickerNotFoundException(Status.NOT_FOUND
                    .withDescription(String.format("Ticker '%s' not found", ticker))
                    .asException());
        }
        return tickers.get(ticker);
    }
}
