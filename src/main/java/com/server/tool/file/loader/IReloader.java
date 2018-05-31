package com.server.tool.file.loader;

import java.io.File;

/**
 * 文件加载器重载接口
 *
 * @author LiuQi - [Created on 2018-05-24]
 */
public interface IReloader {

    void addFileLoader(FileLoader fileLoader, File file);
}
