package com.server.tool.file;

import java.io.File;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

/**
 * @author LiuQi
 * @version 1.0 Create on  2017/10/11
 */

public interface FileMonitor {

    void dispatcher(File file, WatchEvent.Kind kind);
}
