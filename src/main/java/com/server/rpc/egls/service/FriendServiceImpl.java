package com.egls.server.core.rpc.egls.service;

import com.egls.server.core.ServerType;
import com.egls.server.core.rpc.spi.Rpc;

/**
 * 好友实现类
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
@Rpc(name = "friendService", value = ServerType.center)
public class FriendServiceImpl implements FriendService {


    @Override
    public void init() {
        System.out.println("friend service impl init");
    }

    @Override
    public void apply(Long playerId, Long targetId) {
        System.out.println("apply friend " + playerId + " -> " + targetId);
    }
}
