package com.mec.dictionarygrpcserver.service;

import java.math.BigDecimal;

public interface DictionaryService {

    /**
     * Get price by ticker
     *
     * @param ticker instrument's ticker
     * @return price of instrument
     */
    BigDecimal getPrice(String ticker);
}
