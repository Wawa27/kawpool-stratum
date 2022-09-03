package com.kawpool.stratum.core.rpc;

import com.kawpool.stratum.core.services.JobService;
import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.shared.rpc.RpcRequestDto;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;
import org.apache.commons.codec.DecoderException;

import java.io.IOException;

public class SubmitRpcMethodHandler extends RpcMethodHandler {

    public SubmitRpcMethodHandler() {
        super("mining.submit");
    }

    @Override
    public void process(WorkerThread workerThread, RpcRequestDto data) throws DecoderException, IOException {
        String mixHash = (String) data.getParams()[4];
        String nonce = (String) data.getParams()[2];
        boolean shareResult = JobService.submitShare(data.getId(), workerThread, mixHash, nonce);
        workerThread.sendMessage(new RpcResponseDto<>(data.getId(), shareResult, null));
    }
}
