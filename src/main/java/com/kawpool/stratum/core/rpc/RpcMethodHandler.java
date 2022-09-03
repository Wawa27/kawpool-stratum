package com.kawpool.stratum.core.rpc;

import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.shared.rpc.RpcRequestDto;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;
import org.apache.commons.codec.DecoderException;

import java.io.IOException;

public abstract class RpcMethodHandler {
    private final String name;
    private RpcMethodHandler next;

    public RpcMethodHandler(String name) {
        this.name = name;
    }

    public void handle(WorkerThread workerThread, RpcRequestDto rpcRequestDto) throws UnsupportedRpcMethodException, InterruptedException, IOException, DecoderException {
        if (name.equals(rpcRequestDto.getMethod())) {
            this.process(workerThread, rpcRequestDto);
            return;
        } else if (next != null) {
            next.handle(workerThread, rpcRequestDto);
            return;
        }
        workerThread.sendMessage(new RpcResponseDto<>(rpcRequestDto.getId(), null, "unsupported_method"));
    }

    public abstract void process(WorkerThread workerThread, RpcRequestDto data) throws InterruptedException, IOException, DecoderException;

    public void addAllMethods(RpcMethodHandler[] rpcMethodHandlers) {
        for (RpcMethodHandler rpcMethodHandler : rpcMethodHandlers) {
            this.addNextMethod(rpcMethodHandler);
        }
    }

    public void addNextMethod(RpcMethodHandler rpcMethodHandler) {
        if (this.next == null) {
            this.next = rpcMethodHandler;
        } else {
            this.next.addNextMethod(rpcMethodHandler);
        }
    }

    public String getName() {
        return name;
    }

    public RpcMethodHandler getNext() {
        return next;
    }
}
