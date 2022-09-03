package com.kawpool.stratum.shared.rpc;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * TODO: add docs
 */
public class RpcRequestDto {
    private int id;
    private String method;
    private Object[] params;

    public RpcRequestDto(int id, String method, Object[] params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "RpcRequestDto{" +
                "id=" + id +
                ", method='" + method + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
