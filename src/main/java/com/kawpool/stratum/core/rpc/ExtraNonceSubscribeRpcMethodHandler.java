package com.kawpool.stratum.core.rpc;

import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.shared.rpc.RpcRequestDto;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;

public class ExtraNonceSubscribeRpcMethodHandler extends RpcMethodHandler {

    public ExtraNonceSubscribeRpcMethodHandler() {
        super("mining.extranonce.subscribe");
    }

    @Override
    public void process(WorkerThread workerThread, RpcRequestDto data) {
        workerThread.sendMessage(new RpcResponseDto<>(data.getId(), true, null));
    }
}
