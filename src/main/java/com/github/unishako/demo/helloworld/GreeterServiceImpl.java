package com.github.unishako.demo.helloworld;

import com.google.rpc.LocalizedMessage;
import io.grpc.*;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;
import lombok.extern.java.Log;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
@Log
public class GreeterServiceImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        Metadata metadata = new Metadata();
        LocalizedMessage message = LocalizedMessage.newBuilder().setLocale("ja-JP").setMessage("バリデーションエラー①").build();
        metadata.put(ProtoUtils.keyForProto(message), message);
        throw new InvalidArgumentException(metadata);
    }

    @Override
    public void sayHello2(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        try {
            var stub = GreeterGrpc.newBlockingStub(channel);
            var response = stub.sayHello(request);
        } catch (StatusRuntimeException e) {
            if (Status.INVALID_ARGUMENT.getCode().equals(e.getStatus().getCode())) {
                log.info("INVALID_ARGUMENTなら無視しますよ");
            } else {
                throw e;
            }
        }
        var helloReply = HelloReply.newBuilder().setMessage("正常終了").build();
        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();
    }
}
