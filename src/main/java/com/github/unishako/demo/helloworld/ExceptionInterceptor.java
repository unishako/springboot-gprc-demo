package com.github.unishako.demo.helloworld;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

@GRpcGlobalInterceptor
public class ExceptionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        return next.startCall(call, headers);
    }
}
