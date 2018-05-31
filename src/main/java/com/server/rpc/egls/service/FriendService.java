package com.server.rpc.egls.service;

import com.server.rpc.spi.RpcService;

/**
 * 服务接口
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
public interface FriendService extends RpcService {

    void apply(Long playerId, Long targetId);
}
