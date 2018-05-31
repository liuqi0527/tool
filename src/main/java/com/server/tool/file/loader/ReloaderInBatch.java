package com.server.tool.file.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会记录所有发生变动的加载器
 * 需要其他模块主动调用reloadAll方法来执行重载
 *
 * @author LiuQi - [Created on 2018-05-24]
 */
public class ReloaderInBatch implements IReloader {

    private volatile Map<FileLoader, File> reloadMap = new ConcurrentHashMap<>();

    @Override
    public void addFileLoader(FileLoader fileLoader, File file) {
        reloadMap.put(fileLoader, file);
    }

    public void reloadAll() {
        Map<FileLoader, File> localMap = reloadMap;
        this.reloadMap = new ConcurrentHashMap<>();

        List<FileLoader> loaders = new ArrayList<>(localMap.keySet());
        Collections.sort(loaders);
        loaders.forEach(loader -> loader.reload(localMap.get(loader)));
    }
}
