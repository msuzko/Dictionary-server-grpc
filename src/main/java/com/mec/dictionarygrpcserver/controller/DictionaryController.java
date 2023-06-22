package com.mec.dictionarygrpcserver.controller;

import com.mec.dictionarygrpcserver.dto.request.InstrumentRequest;
import com.mec.dictionarygrpcserver.dto.response.InstrumentResponse;
import com.mec.dictionarygrpcserver.service.DictionaryService;
import com.mec.dictionarygrpcserver.service.RequestHelper;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.mec.dictionarygrpcserver.model.Constants.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("${endpoint.prefix}")
public class DictionaryController {
    private final DictionaryService dictionaryService;
    private final RequestHelper helper;

    @PostMapping("${endpoint.method.instrument}")
    @Timed(value = "dictionary.server.rest.in.instrument", description = "Полуение инструмента")
    public ResponseEntity<InstrumentResponse> getInstrument(@RequestHeader(RQ_UID) String rqUid,
                                                            @RequestHeader(RQ_TM) String rqTm,
                                                            @RequestBody @Valid InstrumentRequest rq) {
        var price = dictionaryService.getPrice(rq.getTicker());

        return ResponseEntity.status(HttpStatus.OK)
                .headers(getHeaders(rqUid))
                .body(new InstrumentResponse(rq.getTicker(), price));
    }

    private HttpHeaders getHeaders(String rqUid) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(RQ_UID, rqUid);
        headers.set(RS_TM, helper.getCurrentTimeStr());
        return headers;
    }
}
