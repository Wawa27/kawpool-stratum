package com.kawpool.stratum.ravend.dtos.client;

import com.kawpool.stratum.shared.rpc.RpcRequestDto;

public class RpcGetBlockTemplateDto extends RpcRequestDto {

    public RpcGetBlockTemplateDto() {
        super(0, "getblocktemplate", new Object[]{new GetBlockTemplateParameters()});
    }

    protected final static class GetBlockTemplateParameters {
        private String[] capabilities;
        private String[] rules;

        public GetBlockTemplateParameters() {
            this.capabilities = new String[]{"coinbasetxn", "workid", "coinbase/append"};
            this.rules = new String[]{"segwit"};
        }

        public String[] getCapabilities() {
            return capabilities;
        }

        public void setCapabilities(String[] capabilities) {
            this.capabilities = capabilities;
        }

        public String[] getRules() {
            return rules;
        }

        public void setRules(String[] rules) {
            this.rules = rules;
        }
    }
}
