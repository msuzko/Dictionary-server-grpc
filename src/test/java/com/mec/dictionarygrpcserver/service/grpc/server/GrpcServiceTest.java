package com.mec.dictionarygrpcserver.service.grpc.server;

import com.mec.dictionarygrpcserver.exception.GrpcIllegalArgumentException;
import com.mec.dictionarygrpcserver.exception.TickerNotFoundException;
import com.mec.dictionarygrpcserver.service.DictionaryService;
import com.mec.dictionarygrpcserver.service.RandomServicesPortInitializer;
import com.mec.dictionarygrpcserver.service.validator.GrpcRequestValidator;
import com.mec.proto.dictionary.InstrumentRq;
import com.mec.proto.dictionary.InstrumentRs;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = GrpcService.class)
@ContextConfiguration(initializers = RandomServicesPortInitializer.class)
class GrpcServiceTest {
    private static final String TICKER_AAPL = "AAPL";
    @Autowired
    private GrpcService grpcService;
    @MockBean
    private GrpcRequestValidator validator;
    @MockBean
    private DictionaryService dictionaryService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(validator, dictionaryService);
    }

    @Test
    void getCost() {
        var rq = InstrumentRq.newBuilder().setTicker(TICKER_AAPL).build();
        var responseObserver = getInstrumentRsResponseObserver();
        when(dictionaryService.getPrice(TICKER_AAPL)).thenReturn(new BigDecimal("100"));
        var rs = InstrumentRs.newBuilder().setTicker("AAPL").setPrice(100.0).build();

        grpcService.getCost(rq, responseObserver);

        verify(dictionaryService).getPrice(TICKER_AAPL);
        verify(validator).validate(rq);
        assertThat(responseObserver.value).isEqualTo(rs);
    }

    @Test
    void getCost_BadRequest() {
        var rq = InstrumentRq.newBuilder().setTicker(TICKER_AAPL).build();
        var responseObserver = getInstrumentRsResponseObserver();
        doThrow(new GrpcIllegalArgumentException(Status.INVALID_ARGUMENT.withDescription("error").asException()))
                .when(validator).validate(rq);
        grpcService.getCost(rq, responseObserver);

        verify(validator).validate(rq);
        assertThat(responseObserver.error).isEqualTo("INVALID_ARGUMENT: error");
    }

    @Test
    void getCost_TickerNotFound() {
        var rq = InstrumentRq.newBuilder().setTicker(TICKER_AAPL).build();
        var responseObserver = getInstrumentRsResponseObserver();
        doThrow(new TickerNotFoundException(Status.NOT_FOUND.withDescription("error").asException()))
                .when(dictionaryService).getPrice(TICKER_AAPL);
        grpcService.getCost(rq, responseObserver);

        verify(validator).validate(rq);
        verify(dictionaryService).getPrice(TICKER_AAPL);
        assertThat(responseObserver.error).isEqualTo("NOT_FOUND: error");
    }

    @Test
    void getCost_RuntimeException() {
        var rq = InstrumentRq.newBuilder().setTicker(TICKER_AAPL).build();
        var responseObserver = getInstrumentRsResponseObserver();
        doThrow(new RuntimeException("Something goes wrong"))
                .when(dictionaryService).getPrice(TICKER_AAPL);
        grpcService.getCost(rq, responseObserver);

        verify(validator).validate(rq);
        verify(dictionaryService).getPrice(TICKER_AAPL);
        assertThat(responseObserver.error).isEqualTo("INTERNAL: Something goes wrong");
    }

    private ResponseObserver<InstrumentRs> getInstrumentRsResponseObserver() {
        return new ResponseObserver<>();
    }


    static class ResponseObserver<T> implements StreamObserver<T> {
        T value;
        String error;
        boolean completed;

        @Override
        public void onNext(T value) {
            this.value = value;
        }

        @Override
        public void onError(Throwable t) {
            this.error = t.getMessage();
        }

        @Override
        public void onCompleted() {
            this.completed = true;
        }
    }
}