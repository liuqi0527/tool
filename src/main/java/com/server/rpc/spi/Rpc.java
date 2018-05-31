package com.egls.server.core.rpc.spi;

import java.lang.annotation.*;

import com.egls.server.core.ServerType;

/**
 * @author LiuQi - [Created on 2018-05-22]
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Rpc {

    /**
     * 服务名称，默认是类名
     */
    String name() default "";

    /**
     * 服务所在的服务器类型
     */
    ServerType[] value();
}
