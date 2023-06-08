package com.mec.dictionarygrpcserver.service.data;

import java.math.BigDecimal;
import java.util.Map;

public interface DataStore {
    Map<String, BigDecimal> getTickers();
}
