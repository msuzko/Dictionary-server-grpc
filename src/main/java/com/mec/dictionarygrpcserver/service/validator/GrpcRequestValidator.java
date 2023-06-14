package com.mec.dictionarygrpcserver.service.validator;

import com.mec.proto.dictionary.InstrumentRq;

public interface GrpcRequestValidator {

    /**
     * Check if all fields aren't empty
     *
     * @param request request with ticker
     */
    void validate(InstrumentRq request);
}
