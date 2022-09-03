package com.kawpool.stratum.ravend.dtos.client;

import com.kawpool.stratum.shared.rpc.RpcRequestDto;

public class RpcSubmitShareDto extends RpcRequestDto {

    public RpcSubmitShareDto(String blockDataHexadecimal) {
        super(0, "submitblock", new Object[]{blockDataHexadecimal});
    }
}
