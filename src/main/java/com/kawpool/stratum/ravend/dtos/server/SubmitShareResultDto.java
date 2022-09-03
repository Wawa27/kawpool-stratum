package com.kawpool.stratum.ravend.dtos.server;

import com.kawpool.stratum.shared.rpc.RpcResponseDto;

public class SubmitShareResultDto extends RpcResponseDto {

    public SubmitShareResultDto(int id, Object result, String error) {
        super(id, result, error);
    }
}
