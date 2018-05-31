package com.egls.server.core.rpc.egls.service;

import com.egls.server.core.ServerType;
import com.egls.server.core.rpc.spi.Rpc;

/**
 * 工会服务实现类
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
@Rpc({ServerType.center, ServerType.master})
public class FamilyServiceImpl implements FamilyService {

    @Override
    public void init() {
        System.out.println("family service impl init");
    }

    @Override
    public void create(String name) {
        System.out.println("create family -> " + name);
    }
}
