package com.egls.server.core.rpc.spi;

import com.egls.server.core.rpc.Invoke;

/**
 * 远程服务器在本地一个抽象，
 * 具有向远程发起调用，处理远程发送过来的调用的功能
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
public abstract class RemoteServer {

    private String serverKey;

    public RemoteServer(String serverKey) {
        this.serverKey = serverKey;
    }

    /**
     * 获取远程服务器的位移表示
     */
    public String getServerKey() {
        return serverKey;
    }

    /**
     * 发送第一条登陆指令到远程服务器
     */
    public abstract void login();

    /**
     * 发送远程调用到远程服务器
     */
    public abstract void sendInvoke(Invoke invoke);

    /**
     * 执行接收到的远程调用
     */
    public abstract void processRemoteInvoke();

    /**
     * 关闭远程服务器连接
     */
    public abstract void close();

    /**
     * 此远程服务连接是否有效
     */
    public abstract boolean isValid();

}
