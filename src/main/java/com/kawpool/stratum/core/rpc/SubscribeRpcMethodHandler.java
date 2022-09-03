package com.kawpool.stratum.core.rpc;

import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.core.services.WorkerService;
import com.kawpool.stratum.shared.rpc.RpcRequestDto;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;

public class SubscribeRpcMethodHandler extends RpcMethodHandler {

    public SubscribeRpcMethodHandler() {
        super("mining.subscribe");
    }

    @Override
    public void process(WorkerThread workerThread, RpcRequestDto data) {
        WorkerService.subscribedWorkers.put(workerThread.getWorkerId(), workerThread);
        workerThread.sendMessage(new RpcResponseDto<>(data.getId(), new String[]{null, "194e9b"}, null));
    }
}
