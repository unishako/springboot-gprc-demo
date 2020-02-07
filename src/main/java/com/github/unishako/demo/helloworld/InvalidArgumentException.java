package com.github.unishako.demo.helloworld;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import javax.annotation.Nullable;

public class InvalidArgumentException extends StatusRuntimeException {

    public InvalidArgumentException(@Nullable Metadata trailers) {
        super(Status.INVALID_ARGUMENT.withDescription("バリデーションエラーが発生しました"), trailers);
    }
}
