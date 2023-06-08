package com.mec.dictionarygrpcserver.service.data.impl;

import com.mec.dictionarygrpcserver.entity.Instrument;
import com.mec.dictionarygrpcserver.repository.PriceRepository;
import com.mec.dictionarygrpcserver.service.data.DataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Profile("!dev")
@RequiredArgsConstructor
public class DataStoreDb implements DataStore {
    private final PriceRepository priceRepository;

    @Override
    public Map<String, BigDecimal> getTickers() {
        return priceRepository.findAll().stream().collect(Collectors.toMap(Instrument::getTicker, Instrument::getPrice));
    }

}
