package test.tool;

import java.io.File;

import com.server.tool.file.FileWatchKind;
import com.server.tool.file.FileWatcher;
import com.server.tool.file.SimpleFileMonitor;

import org.junit.Test;

/**
 * @author LiuQi - [Created on 2018-05-23]
 */
public class FileWatcherTest {

    @Test
    public void test1() {
        File file = new File("/");
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


        FileWatcher.registerFile(file, simpleFileMonitor, FileWatchKind.create, FileWatchKind.delete, FileWatchKind.modify);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
