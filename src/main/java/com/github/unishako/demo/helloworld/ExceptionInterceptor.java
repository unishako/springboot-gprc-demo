package com.github.unishako.demo.helloworld;

import io.grpc.*;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;

@GRpcGlobalInterceptor
public class ExceptionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> delegate = next.startCall(call, headers);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception ex) {
                    handleException(call, headers, ex);
                    throw ex;
                }
            }

            @Override
            public void onReady() {
                try {
                    super.onReady();
                } catch (Exception ex) {
                    handleException(call, headers, ex);
                    throw ex;
                }
            }
        };
    }

    private <ReqT, RespT> void handleException(
            ServerCall<ReqT, RespT> call, Metadata headers, Exception ex) {

        if (ex instanceof StatusRuntimeException) {
            call.close(((StatusRuntimeException) ex).getStatus(), ((StatusRuntimeException) ex).getTrailers());
        } else {
            call.close(Status.INTERNAL.withDescription("死んだ"), headers);
        }
    }
}
