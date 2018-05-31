package com.server.tool.file.reload;

import java.io.File;
import java.util.*;

/**
 * @author LiuQi - [Created on 2018-05-24]
 */
public class ReloadManager {

    private static Map<FileLoader, File> reloadMap = new HashMap<>();

    public static void addFileReloader(FileLoader fileLoader, File file) {
        reloadMap.put(fileLoader, file);
    }

    public static void reloadAll() {
        List<FileLoader> loaders = new ArrayList<>(reloadMap.keySet());
        Collections.sort(loaders);
        loaders.forEach(loader -> loader.reload(reloadMap.get(loader)));
        reloadMap.clear();
    }

}
