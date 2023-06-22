package com.mec.dictionarygrpcserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InstrumentRequest {
    @NotBlank(message = "Field 'ticker' can't be blank")
    private String ticker;
}
