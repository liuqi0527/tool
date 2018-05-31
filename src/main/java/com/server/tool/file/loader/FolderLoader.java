package com.server.tool.file.reload;

import java.io.File;
import java.util.Optional;

import com.server.tool.file.SimpleFileMonitor;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件夹加载器，实现了文件夹重载功。
 * 加载指定文件夹时需要继承此类，并实现loadFile方法执行每个文件的加载。
 * 子类需要实现onReload方法执行重载文件夹时的清理操作
 *
 * @author LiuQi - [Created on 2018-05-24]
 */
public abstract class FolderLoader extends FileLoader implements SimpleFileMonitor {

    private File loadFolder;

    public void load(File file) {
        this.loadFolder = file;
        loadFolder(file);
    }

    private void loadFolder(File file) {
        if (!StringUtils.equals(".svn", file.getName())) {
            if (file.isDirectory()) {
                Optional.ofNullable(file.listFiles()).ifPresent(files -> {
                    for (File f : files) {
                        loadFolder(f);
                    }
                });
            } else {
                loadFile(file);
            }
        }
    }

    @Override
    public void reload(File file) {
        onReload(file);
        loadFile(loadFolder);
    }

    @Override
    public abstract void onReload(File file);
}
