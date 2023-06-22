package com.mec.dictionarygrpcserver.service.impl;

import com.mec.dictionarygrpcserver.service.RequestHelper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHelperImplTest {
    private final RequestHelper requestHelper = new RequestHelperImpl();

    @Test
    void getCurrentTimeStr() {

        var result = requestHelper.getCurrentTimeStr();

        assertThat(result).isEqualTo(ZonedDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }

    @Test
    void getNewUid() {
        var uuid = UUID.randomUUID();
        try (MockedStatic<UUID> utilities = Mockito.mockStatic(UUID.class)) {
            utilities.when(UUID::randomUUID).thenReturn(uuid);

            var result = requestHelper.getNewUid();

            assertThat(result).isEqualTo(uuid.toString());
        }
    }
}