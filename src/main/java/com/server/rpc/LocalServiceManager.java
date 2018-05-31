package com.server.rpc;

import com.server.rpc.spi.RpcService;
import com.server.tool.clazz.ReflectUtils;


/**
 * 用于管理本地所提供的服务，本地提供的服务是服务接口的具体实现
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
public class LocalServiceManager extends AbstractServiceManager {


    @SuppressWarnings("unchecked")
    protected RpcService buildService(Class<? extends RpcService> serviceClass) throws Exception {
        RpcService service = ReflectUtils.newObject(serviceClass);
        service.init();
        return service;
    }

}
