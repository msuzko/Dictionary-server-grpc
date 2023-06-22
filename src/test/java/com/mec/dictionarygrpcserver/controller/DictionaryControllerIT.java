package com.mec.dictionarygrpcserver.controller;

import com.mec.dictionarygrpcserver.dto.response.ErrorData;
import com.mec.dictionarygrpcserver.dto.response.InstrumentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.mec.dictionarygrpcserver.model.Constants.RQ_TM;
import static com.mec.dictionarygrpcserver.model.Constants.RQ_UID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("rest")
class DictionaryControllerIT {
    private static final String HOST = "http://localhost:";
    private final TestRestTemplate rt = new TestRestTemplate();
    @LocalServerPort
    private int port;
    @Value("${endpoint.prefix}")
    private String prefix;
    @Value("${endpoint.method.instrument}")
    private String instrumentMethod;

    private static Stream<Arguments> headers() {
        return Stream.of(
                Arguments.of(null, RQ_TM, "Required request header 'rqUid' for method parameter type String is not present"),
                Arguments.of(RQ_UID, null, "Required request header 'rqTm' for method parameter type String is not present")
        );
    }

    @Test
    void getInstrument_isOk() {
        var requestBody = "{\"ticker\": \"AAPL\"}";
        var url = HOST + port + "/" + prefix + instrumentMethod;
        var entity = new HttpEntity<>(requestBody, getHeaders(RQ_UID, RQ_TM));

        var result = rt.exchange(url, POST, entity, InstrumentResponse.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        var response = result.getBody();
        assertThat(response).isEqualTo(new InstrumentResponse("AAPL", new BigDecimal("179.21")));
    }

    @ParameterizedTest
    @MethodSource("headers")
    void checkHeaders(String rqUid, String rqTm, String errorDescription) {
        var requestBody = "{\"ticker\": \"AAPL\"}";
        var url = HOST + port + "/" + prefix + instrumentMethod;
        var entity = new HttpEntity<>(requestBody, getHeaders(rqUid, rqTm));

        var result = rt.exchange(url, POST, entity, ErrorData.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        var response = result.getBody();
        assert response != null;
        assertThat(response.getErrorDesc()).isEqualTo(errorDescription);
    }

    @Test
    void checkFieldNotEmpty() {
        var requestBody = "{}";
        var url = HOST + port + "/" + prefix + instrumentMethod;
        var entity = new HttpEntity<>(requestBody, getHeaders(RQ_UID, RQ_TM));

        var result = rt.exchange(url, POST, entity, ErrorData.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        var response = result.getBody();
        assert response != null;
        assertThat(response.getErrorDesc()).isEqualTo("Field 'ticker' can't be blank");
    }

    private HttpHeaders getHeaders(String rqUid, String rqTm) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (rqUid != null) {
            headers.set(RQ_UID, rqUid);
        }
        if (rqTm != null) {
            headers.set(RQ_TM, rqTm);
        }
        return headers;
    }
}