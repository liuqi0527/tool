package com.server.tool.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author LiuQi
 * @version 1.0 Create on  2017/10/11
 */

public class FileWatcher extends Thread {

    private static final FileWatcher instance = new FileWatcher();

    private static final AtomicBoolean running = new AtomicBoolean(false);

    private static final Map<WatchKey, Collection<FileMonitor>> watchKeyMap = new ConcurrentHashMap<>();

    private WatchService service;

    @Override
    public void run() {
        while (running.get()) {
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
                e.printStackTrace();
            }
        }
    }

    public static void startup() {
        if (running.get()) {
            //
            return;
        }

        try {
            running.set(true);
            instance.service = FileSystems.getDefault().newWatchService();
            instance.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        running.set(false);
        try {
            instance.service.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void register(File file, SimpleFileMonitor monitor, FileWatchKind... kinds) {
        register(file, (eventFile, eventKind) -> {
            if (eventKind == StandardWatchEventKinds.ENTRY_CREATE) {
                monitor.onCreate(eventFile);
            } else if (eventKind == StandardWatchEventKinds.ENTRY_DELETE) {
                monitor.onDelete(eventFile);
            } else if (eventKind == StandardWatchEventKinds.ENTRY_MODIFY) {
                monitor.onModify(eventFile);
            }
        }, kinds);
    }

    public static void register(File file, FileMonitor monitor, FileWatchKind... kinds) {
        WatchEvent.Kind[] kindArray = new WatchEvent.Kind[kinds.length];
        for (int i = 0; i < kinds.length; i++) {
            kindArray[i] = kinds[i].getKind();
        }

        try {
            Path path = Paths.get(file.isDirectory() ? file.getPath() : file.getParent());
            WatchKey watchKey = path.register(instance.service, kindArray);
            Collection<FileMonitor> monitors = watchKeyMap.computeIfAbsent(watchKey, key -> new ConcurrentLinkedQueue<>());
            monitors.add(getMonitor(file, monitor, kinds));
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

    private static FileMonitor getMonitor(File file, FileMonitor monitor, FileWatchKind... kinds) {
        return file.isDirectory() ? new LocalDirMonitor(monitor, kinds) : new LocalFileMonitor(file, monitor);
    }

    private static class LocalDirMonitor implements FileMonitor {
        private FileMonitor monitor;
        private FileWatchKind[] kinds;

        private LocalDirMonitor(FileMonitor monitor, FileWatchKind[] kinds) {
            this.monitor = monitor;
            this.kinds = kinds;
        }

        @Override
        public void dispatcher(File file, WatchEvent.Kind kind) {
            if (file.isDirectory() && FileWatchKind.of(kind) == FileWatchKind.create) {
                FileWatcher.register(file, monitor, kinds);
            }
            monitor.dispatcher(file, kind);
        }
    }

    private static class LocalFileMonitor implements FileMonitor {
        private String filePath;
        private FileMonitor monitor;

        private LocalFileMonitor(File file, FileMonitor monitor) {
            this.filePath = file.getName();
            this.monitor = monitor;
        }

        @Override
        public void dispatcher(File file, WatchEvent.Kind kind) {
            if (filePath.equals(file.getName())) {
                monitor.dispatcher(file, kind);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("E:/workspace_test/FileTest");
        FileWatcher.startup();

        SimpleFileMonitor simpleFileMonitor = new SimpleFileMonitor() {
            @Override
            public void onCreate(File file) {
                System.out.println("create : " + (file == null ? "null" : file.getName()));
            }

            @Override
            public void onModify(File file) {
                System.out.println("modify  : " + (file == null ? "null" : file.getName()));
            }

            @Override
            public void onDelete(File file) {
                System.out.println("delete : " + (file == null ? "null" : file.getName()));

            }
        };


        FileWatcher.register(file, simpleFileMonitor, FileWatchKind.create, FileWatchKind.delete, FileWatchKind.modify);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
