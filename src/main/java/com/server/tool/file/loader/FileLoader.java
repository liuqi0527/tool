package com.server.tool.file.loader;

import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

import com.server.tool.file.SimpleFileMonitor;

/**
 * 文件加载父类，实现了文件重载功能。
 * 加载具体文件时需要继承此类，并实现loadFile方法。
 * 如果有必要需要实现onReload方法，在文件重载时执行清理操作
 *
 * @author LiuQi - [Created on 2018-05-24]
 */
public abstract class FileLoader implements SimpleFileMonitor, Comparable<FileLoader> {

    private static final AtomicLong indexBuilder = new AtomicLong(0);

    /**
     * 记录不同实例的实例化顺序，以便于在重载时按照首次加载的顺序执行重载
     */
    private final long reloadIndex = indexBuilder.incrementAndGet();

    @Override
    public int compareTo(FileLoader o) {
        return Long.compare(reloadIndex, o.reloadIndex);
    }

    @Override
    public void onCreate(File file) {
        ReloadManager.addFileLoader(this, file);
    }

    @Override
    public void onModify(File file) {
        ReloadManager.addFileLoader(this, file);
    }

    @Override
    public void onDelete(File file) {
        ReloadManager.addFileLoader(this, file);
    }

    public void load(File file) {
        loadFile(file);
    }

    public void reload(File file) {
        onReload(file);
        load(file);
    }

    public void onReload(File file) {

    }

    public abstract void loadFile(File file);
}
