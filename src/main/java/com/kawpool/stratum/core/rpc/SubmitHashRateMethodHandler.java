package com.kawpool.stratum.core.rpc;

import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.shared.rpc.RpcRequestDto;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;

import java.math.BigInteger;

public class SubmitHashRateMethodHandler extends RpcMethodHandler {

    public SubmitHashRateMethodHandler() {
        super("eth_submitHashrate");
    }

    @Override
    public void process(WorkerThread workerThread, RpcRequestDto data) {
        BigInteger reportedHashRate = new BigInteger(((String) data.getParams()[0]).substring(2), 16);
        BigInteger reportedMinerId = new BigInteger(((String) data.getParams()[1]).substring(2), 16);
        workerThread.setReportedHashRate(reportedHashRate);
        workerThread.setReportedId(reportedMinerId);
        workerThread.sendMessage(new RpcResponseDto<>(data.getId(), true, null));
    }
}
