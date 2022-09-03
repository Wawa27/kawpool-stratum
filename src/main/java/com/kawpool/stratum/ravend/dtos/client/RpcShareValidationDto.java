package com.kawpool.stratum.ravend.dtos.client;

import com.kawpool.stratum.shared.rpc.RpcRequestDto;

public class RpcShareValidationDto extends RpcRequestDto {

    public RpcShareValidationDto(String headerHash, String mixHash, String nonce, long height, String target) {
        super(0, "getkawpowhash", new Object[]{headerHash, mixHash, nonce, height, target});
    }
}
