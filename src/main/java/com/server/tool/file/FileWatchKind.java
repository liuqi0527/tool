package com.server.tool.file;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

/**
 * @author LiuQi
 * @version 1.0 Create on  2017/10/11
 */

public enum FileWatchKind {

    create(StandardWatchEventKinds.ENTRY_CREATE),
    delete(StandardWatchEventKinds.ENTRY_DELETE),
    modify(StandardWatchEventKinds.ENTRY_MODIFY),;

    public static FileWatchKind of(WatchEvent.Kind kind) {
        for (FileWatchKind fileWatchKind : FileWatchKind.values()) {
            if (fileWatchKind.getKind() == kind) {
                return fileWatchKind;
            }
        }
        return null;
    }

    private WatchEvent.Kind<Path> kind;

    FileWatchKind(WatchEvent.Kind<Path> kind) {
        this.kind = kind;
    }

    public WatchEvent.Kind<Path> getKind() {
        return kind;
    }
}
