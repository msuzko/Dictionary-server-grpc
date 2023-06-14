package com.mec.dictionarygrpcserver.service.impl;

import com.mec.dictionarygrpcserver.exception.TickerNotFoundException;
import com.mec.dictionarygrpcserver.service.DictionaryService;
import com.mec.dictionarygrpcserver.service.data.DataStore;
import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final DataStore dataStore;

    @Override
    public BigDecimal getPrice(String ticker) {
        var tickers = dataStore.getTickers();
        if (!tickers.containsKey(ticker)) {
            throw new TickerNotFoundException(Status.NOT_FOUND
                    .withDescription(String.format("Ticker %s isn't found", ticker))
                    .asException());
        }
        return tickers.get(ticker);
    }
}
