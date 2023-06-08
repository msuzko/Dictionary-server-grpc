package com.mec.dictionarygrpcserver.service.grpc.server;

import com.google.rpc.Code;
import com.google.rpc.Status;
import com.mec.dictionarygrpcserver.exception.GrpcIllegalArgumentException;
import com.mec.dictionarygrpcserver.service.DictionaryService;
import com.mec.dictionarygrpcserver.service.validator.GrpcRequestValidator;
import com.mec.proto.dictionary.DictionaryServiceGrpc;
import com.mec.proto.dictionary.InstrumentRq;
import com.mec.proto.dictionary.InstrumentRs;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

import java.util.function.Supplier;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class GrpcService extends DictionaryServiceGrpc.DictionaryServiceImplBase {
    private final GrpcRequestValidator validator;
    private final DictionaryService dictionaryService;

    @Override
    public void getCost(InstrumentRq request, StreamObserver<InstrumentRs> responseObserver) {
        wrapped(responseObserver, () -> getPrice(request));
    }

    private InstrumentRs getPrice(InstrumentRq request) {
        validator.validate(request);
        var price = dictionaryService.getPrice(request.getTicker());
        return InstrumentRs.newBuilder()
                .setTicker(request.getTicker())
                .setPrice(price.doubleValue())
                .build();
    }

    private <T> void wrapped(StreamObserver<T> streamObserver, Supplier<T> valueSupplier) {
        try {
            var value = valueSupplier.get();
            streamObserver.onNext(value);
            streamObserver.onCompleted();
        } catch (GrpcIllegalArgumentException e) {
            log.error(e.getLocalizedMessage());
            streamObserver.onError(e.getThrowable());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            var status = Status.newBuilder()
                    .setCode(Code.INTERNAL_VALUE)
                    .setMessage(e.getMessage())
                    .build();
            streamObserver.onError(StatusProto.toStatusRuntimeException(status));
        }
    }
}
