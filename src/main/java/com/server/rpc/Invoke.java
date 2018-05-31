package com.egls.server.core.rpc;

/**
 * @author LiuQi - [Created on 2018-05-22]
 */
public class Invoke {

    private String serviceName;
    private String methodName;
    private Object[] methodParams;

    private Invoke() {
    }

    public Invoke(String serviceName, String methodName, Object[] methodParams) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.methodParams = methodParams;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Class<?>[] getMethodParamTypes() {
        Class<?>[] types = new Class<?>[methodParams.length];
        for (int i = 0; i < methodParams.length; i++) {
            types[i] = methodParams[i].getClass();
        }
        return types;
    }
}
