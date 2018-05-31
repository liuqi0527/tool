package com.server.tool.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LiuQi
 * @version 1.0 Create on  2017/10/11
 */

public class FileWatcher extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(FileWatcher.class);

    private static final FileWatcher instance = new FileWatcher();

    private static final Map<WatchKey, Collection<FileMonitor>> watchKeyMap = new ConcurrentHashMap<>();

    private volatile WatchService service;


    private FileWatcher() {
        setName(this.getClass().getName());
        setDaemon(true);
    }

    public synchronized static void startup() {
        if (instance.service != null) {
            logger.error("file watcher service already startup");
        } else {
            try {
                instance.service = FileSystems.getDefault().newWatchService();
                instance.start();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    public synchronized static void shutdown() {
        if (instance.service != null) {
            try {
                instance.service.close();
                instance.service = null;
                watchKeyMap.clear();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    @Override
    public void run() {
        while (instance.service != null) {
            try {
                WatchKey key;
                while ((key = service.poll(1L, TimeUnit.MILLISECONDS)) != null) {
                    Collection<FileMonitor> fileMonitors = watchKeyMap.get(key);
                    key.pollEvents().forEach(watchEvent -> {
                        Path path = (Path) watchEvent.context();
                        fileMonitors.forEach(monitor -> monitor.dispatcher(path.toFile(), watchEvent.kind()));
                    });
                    key.reset();
                }

                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
    }

    public static void registerDirectory(File file, SimpleFileMonitor monitor, FileType fileType) {
        registerDirectory(file, monitor, fileType, FileWatchKind.create, FileWatchKind.delete, FileWatchKind.modify);
    }

    public static void registerDirectory(File file, SimpleFileMonitor monitor, FileType fileType, FileWatchKind... kinds) {
        register(file, new LocalDirMonitor(monitor, fileType, kinds), kinds);
    }

    public static void registerFile(File file, SimpleFileMonitor monitor) {
        registerFile(file, monitor, FileWatchKind.create, FileWatchKind.delete, FileWatchKind.modify);
    }

    public static void registerFile(File file, SimpleFileMonitor monitor, FileWatchKind... kinds) {
        register(file, new LocalFileMonitor(file, monitor), kinds);
    }

    public static void register(File file, FileMonitor monitor, FileWatchKind... kinds) {
        if (FileType.isSvn(file)) {
            return;
        }

        WatchEvent.Kind[] kindArray = new WatchEvent.Kind[kinds.length];
        for (int i = 0; i < kinds.length; i++) {
            kindArray[i] = kinds[i].getKind();
        }

        try {
            Path path = Paths.get(file.isDirectory() ? file.getPath() : file.getParent());
            WatchKey watchKey = path.register(instance.service, kindArray);
            Collection<FileMonitor> monitors = watchKeyMap.computeIfAbsent(watchKey, key -> new ConcurrentLinkedQueue<>());//todo 去重
            monitors.add(monitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File[] files;
        if (file.isDirectory() && (files = file.listFiles()) != null) {
            for (File subFile : files) {
                if (subFile.isDirectory()) {
                    register(subFile, monitor, kinds);
                }
            }
        }
    }

    private static void dispatcher(SimpleFileMonitor monitor, File file, WatchEvent.Kind kind) {
        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
            monitor.onCreate(file);
        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
            monitor.onDelete(file);
        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
            monitor.onModify(file);
        }
    }

    private static class LocalDirMonitor implements FileMonitor {
        private SimpleFileMonitor monitor;
        private FileType fileType;
        private FileWatchKind[] kinds;

        private LocalDirMonitor(SimpleFileMonitor monitor, FileType fileType, FileWatchKind... kinds) {
            this.monitor = monitor;
            this.fileType = fileType;
            this.kinds = kinds;
        }

        @Override
        public void dispatcher(File file, WatchEvent.Kind kind) {
            if (file.isDirectory() && FileWatchKind.of(kind) == FileWatchKind.create) {
                FileWatcher.registerDirectory(file, monitor, fileType, kinds);
            }

            if (!FileType.isSvn(file) || file.isDirectory() || fileType.accept(file)) {
                FileWatcher.dispatcher(monitor, file, kind);
            }
        }
    }

    private static class LocalFileMonitor implements FileMonitor {
        private String filePath;
        private SimpleFileMonitor monitor;

        private LocalFileMonitor(File file, SimpleFileMonitor monitor) {
            this.filePath = file.getName();
            this.monitor = monitor;
        }

        @Override
        public void dispatcher(File file, WatchEvent.Kind kind) {
            if (filePath.equals(file.getName())) {
                FileWatcher.dispatcher(monitor, file, kind);
            }
        }
    }
}
