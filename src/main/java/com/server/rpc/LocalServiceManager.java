package com.egls.server.core.rpc;

import java.lang.reflect.Constructor;

import com.egls.server.core.rpc.spi.RpcService;
import com.egls.server.utils.reflect.ConstructorUtil;

/**
 * 用于管理本地所提供的服务，本地提供的服务是服务接口的具体实现
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
public class LocalServiceManager extends AbstractServiceManager {


    @SuppressWarnings("unchecked")
    protected RpcService buildService(Class<? extends RpcService> serviceClass) throws Exception {
        Constructor<? extends RpcService> constructor = (Constructor<? extends RpcService>) ConstructorUtil.getNoneParamConstructor(serviceClass);
        RpcService service = constructor.newInstance();
        service.init();
        return service;
    }

}
