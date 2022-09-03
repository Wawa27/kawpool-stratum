package com.kawpool.stratum.shared.rpc;

/**
 * TODO: add docs
 */
public class RpcResponseDto<T, K> {
    private int id;
    private T result;
    private K error;

    public RpcResponseDto(int id, T result, K error) {
        this.id = id;
        this.result = result;
        this.error = error;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public K getError() {
        return error;
    }

    public void setError(K error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RpcResponseDto{" +
                "id=" + id +
                ", result=" + result +
                ", error='" + error + '\'' +
                '}';
    }
}
