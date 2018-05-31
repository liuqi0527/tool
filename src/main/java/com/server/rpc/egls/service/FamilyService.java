package com.egls.server.core.rpc.egls.service;

import com.egls.server.core.rpc.spi.RpcService;

/**
 * @author LiuQi - [Created on 2018-05-22]
 */
public interface FamilyService extends RpcService {

    void create(String name);
}
