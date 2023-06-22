package com.mec.dictionarygrpcserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentResponse {
    private String ticker;
    private BigDecimal price;
}
