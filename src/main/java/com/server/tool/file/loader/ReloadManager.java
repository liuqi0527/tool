package com.server.tool.file.loader;

import java.io.File;
import java.util.Objects;

/**
 * 文件重载管理器
 * 默认情况下，文件发生变动立刻执行重载
 * 可以指定实现IReloader接口的其他重载实现
 *
 * @author LiuQi - [Created on 2018-05-24]
 */
public class ReloadManager {

    private static IReloader reloader = new ReloadImmediately();

    /**
     * 设置文件变动时，所有执行的操作实现
     */
    public static void setReloader(IReloader reloader) {
        Objects.requireNonNull(reloader);
        ReloadManager.reloader = reloader;
    }

    static void addFileLoader(FileLoader fileLoader, File file) {
        reloader.addFileLoader(fileLoader, file);
    }

    /**
     * 文件变动立即重载
     */
    private static class ReloadImmediately implements IReloader {

        @Override
        public void addFileLoader(FileLoader fileLoader, File file) {
            fileLoader.reload(file);
        }
    }

}
