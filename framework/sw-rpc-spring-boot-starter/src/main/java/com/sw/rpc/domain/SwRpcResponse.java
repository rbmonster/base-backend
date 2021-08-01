package com.sw.rpc.domain;

import lombok.Data;

@Data
public class SwRpcResponse {

    private String requestId;

    private boolean success;

    private String errorMsg;

    private Object data;
}
