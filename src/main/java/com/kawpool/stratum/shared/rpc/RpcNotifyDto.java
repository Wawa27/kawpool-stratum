
package com.kawpool.stratum.shared.rpc;

public class RpcNotifyDto {
    private Integer id;
    private String method;
    private Object params;

    public RpcNotifyDto(Integer id, String method, Object params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
