package com.kawpool.stratum.core.rpc;

public class UnsupportedRpcMethodException extends RuntimeException {
    public UnsupportedRpcMethodException(String methodName) {
        super(methodName);
    }
}
