package com.server.rpc.egls.service;


import com.server.rpc.spi.RpcService;

/**
 * @author LiuQi - [Created on 2018-05-22]
 */
public interface FamilyService extends RpcService {

    void create(String name);
}
