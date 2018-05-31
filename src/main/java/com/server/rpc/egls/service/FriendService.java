package com.egls.server.core.rpc.egls.service;

import com.egls.server.core.rpc.spi.RpcService;

/**
 * 服务接口
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
public interface FriendService extends RpcService {

    void apply(Long playerId, Long targetId);
}
