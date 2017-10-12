package com.server.tool.file;

import java.io.File;

/**
 * @author LiuQi
 * @version 1.0 Create on  2017/10/11
 */

public interface SimpleFileMonitor {

    void onCreate(File file);

    void onModify(File file);

    void onDelete(File file);
}
