package pl.mjedynak.concurrency.asyncTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileCreator {

    private ExecutorService executor = Executors.newCachedThreadPool();

    public void createFile(final String fileName) {
        executor.submit(new Callable<Path>() {
            @Override
            public Path call() throws Exception {
                // simulate delay
                Thread.sleep(500);
                return Files.createFile(Paths.get(fileName));
            }
        });

    }
}
