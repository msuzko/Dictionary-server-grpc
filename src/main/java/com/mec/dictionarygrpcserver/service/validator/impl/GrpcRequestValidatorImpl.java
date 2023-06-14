package com.mec.dictionarygrpcserver.service.validator.impl;

import com.mec.dictionarygrpcserver.exception.GrpcIllegalArgumentException;
import com.mec.dictionarygrpcserver.service.validator.GrpcRequestValidator;
import com.mec.proto.dictionary.InstrumentRq;
import io.grpc.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.mec.dictionarygrpcserver.model.Constants.TICKER;

@Service
public class GrpcRequestValidatorImpl implements GrpcRequestValidator {

    @Override
    public void validate(InstrumentRq request) {
        if (StringUtils.isBlank(request.getTicker())) {
            throw new GrpcIllegalArgumentException(getEmptyFieldThrowable(TICKER));
        }
    }

    private Throwable getEmptyFieldThrowable(String fieldName) {
        return Status.INVALID_ARGUMENT
                .withDescription(String.format("%s must be filled", fieldName))
                .asException();
    }
}
