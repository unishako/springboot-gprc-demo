package com.github.unishako.demo.helloworld;

import com.google.rpc.LocalizedMessage;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class GreeterServiceImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

        Metadata metadata = new Metadata();
        LocalizedMessage message = LocalizedMessage.newBuilder().setLocale("ja-JP").setMessage("バリデーションエラー①").build();
        metadata.put(ProtoUtils.keyForProto(message), message);

//        StatusRuntimeException exception = Status.INVALID_ARGUMENT
//                .withDescription("バリデーションエラー②")
//                .asRuntimeException(metadata); // Metadataを付加

        throw new InvalidArgumentException(metadata);

        //responseObserver.onError(exception);

        //super.sayHello(request, responseObserver);
    }
}
