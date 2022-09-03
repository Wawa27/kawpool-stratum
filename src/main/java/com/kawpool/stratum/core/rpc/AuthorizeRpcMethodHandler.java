package com.kawpool.stratum.core.rpc;

import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.shared.rpc.RpcRequestDto;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;

public class AuthorizeRpcMethodHandler extends RpcMethodHandler {

    public AuthorizeRpcMethodHandler() {
        super("mining.authorize");
    }

    @Override
    public void process(WorkerThread workerThread, RpcRequestDto data) {
        String[] splitAddress = ((String) data.getParams()[0]).split("\\.");
        workerThread.setAddress(splitAddress[0]);
        if (splitAddress.length > 1) {
            workerThread.setWorkerName(splitAddress[1]);
        }
        workerThread.sendMessage(new RpcResponseDto<>(data.getId(), true, null));
    }
}
