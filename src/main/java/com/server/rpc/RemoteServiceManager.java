package com.server.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.server.rpc.spi.RemoteServer;
import com.server.rpc.spi.RpcService;

/**
 * 用于管理某一远程进程提供的服务的本地接口
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
public class RemoteServiceManager extends AbstractServiceManager implements InvocationHandler {

    private RemoteServer remoteServer;

    @SuppressWarnings("unchecked")
    protected RpcService buildService(Class<? extends RpcService> serviceClass) {
        return (RpcService) Proxy.newProxyInstance(serviceClass.getClassLoader(), serviceClass.getInterfaces(), this);
    }

    public void setRemoteServer(RemoteServer remoteServer) {
        if (isRemoteValid()) {
            this.remoteServer.close();
        }
        this.remoteServer = remoteServer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String serviceName = getServiceName(method.getDeclaringClass());
        String methodName = method.getName();

        if (isRemoteValid()) {
            remoteServer.sendInvoke(new Invoke(serviceName, methodName, args));
        } else {
            //print log
        }
        return null;
    }

    public void tick() {
        if (remoteServer != null) {
            remoteServer.processRemoteInvoke();
        }
    }

    private boolean isRemoteValid() {
        return this.remoteServer != null && this.remoteServer.isValid();
    }
}
