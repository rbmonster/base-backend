package com.sw.rpc.domain;

import lombok.Data;

@Data
public class SwRpcRequest {

    private String id;

    private Class<?> clazz;

    private String methodName;

    private Class<?>[] parameterTypes;// 参数类型

    private Object[] parameters;// 参数列表
}
