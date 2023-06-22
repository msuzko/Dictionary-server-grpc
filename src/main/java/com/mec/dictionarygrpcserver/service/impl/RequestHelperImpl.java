package com.mec.dictionarygrpcserver.service.impl;

import com.mec.dictionarygrpcserver.service.RequestHelper;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RequestHelperImpl implements RequestHelper {

    @Override
    public String getCurrentTimeStr() {
        return ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Override
    public String getNewUid() {
        return UUID.randomUUID().toString();
    }

}
