package com.server.rpc;

import java.lang.annotation.Annotation;
import java.util.*;

import com.server.rpc.egls.ServerType;
import com.server.rpc.spi.Rpc;
import com.server.rpc.spi.RpcService;

import org.apache.commons.lang3.StringUtils;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;


/**
 * 服务管理父类，用于管理一个进程提供的所有服务接口
 *
 * @author LiuQi - [Created on 2018-05-22]
 */
@SuppressWarnings("unchecked")
public abstract class AbstractServiceManager {

    private Map<Class<? extends RpcService>, String> classNameMap = new HashMap<>();

    private Map<String, RpcService> nameServiceMap = new HashMap<>();

    public void init(ServerType serverType, String packageName) {
        for (Class<? extends RpcService> serviceClass : getAvailableServiceClass(serverType, packageName)) {
            try {
                Rpc rpc = searchServiceAnnotation(serviceClass);
                Class<? extends RpcService> rpcService = searchServiceInterface(serviceClass);
                if (rpc != null && rpcService != null) {
                    String name = rpc.name();
                    if (StringUtils.isBlank(name)) {
                        name = rpcService.getName();
                    }

                    if (StringUtils.isBlank(name)) {
                        //
                        continue;
                    }
                    if (nameServiceMap.containsKey(name)) {
                        //
                        continue;
                    }


                    RpcService rs = buildService(serviceClass);
                    classNameMap.put(rpcService, name);
                    nameServiceMap.put(name, rs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private static List<Class<? extends RpcService>> getAvailableServiceClass(ServerType serverType, String packageName) {
        List<Class<? extends RpcService>> list = new ArrayList<>();

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner());
        builder.setUrls(ClasspathHelper.forPackage(packageName));

        Reflections reflections = new Reflections(builder);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Rpc.class);
        for (Class<?> clazz : typesAnnotatedWith) {
            Rpc rpc = searchServiceAnnotation(clazz);
            if (searchServiceInterface(clazz) != null && rpc != null && Arrays.stream(rpc.value()).anyMatch(type -> type == serverType)) {
                list.add((Class<? extends RpcService>) clazz);
            }
        }
        return list;
    }

    private static Rpc searchServiceAnnotation(Class<?> clazz) {
        Set<Annotation> annotations = ReflectionUtils.getAnnotations(clazz, annotation -> annotation instanceof Rpc);
        Annotation annotation = annotations.stream().findAny().orElse(null);
        if (annotation instanceof Rpc) {
            return (Rpc) annotation;
        }
        return null;
    }

    private static Class<? extends RpcService> searchServiceInterface(Class<?> serviceClass) {
        for (Class<?> interfaceClass : serviceClass.getInterfaces()) {
            if (RpcService.class.isAssignableFrom(interfaceClass)) {
                return (Class<? extends RpcService>) interfaceClass;
            }
        }
        return null;
    }


    public <T extends RpcService> T get(String serviceName) {
        RpcService rpcService = nameServiceMap.get(serviceName);
        if (rpcService == null) {
            //
            return null;
        }
        return (T) rpcService;
    }

    public <T extends RpcService> T get(Class<T> clazz) {
        return get(getServiceName(clazz));
    }

    public String getServiceName(Class<?> clazz) {
        return classNameMap.get(clazz);
    }

    protected abstract RpcService buildService(Class<? extends RpcService> aClass) throws Exception;

}
