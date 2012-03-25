package pl.mjedynak.concurrency.asyncTest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static com.jayway.awaitility.Awaitility.await;

public class FileCreatorTestWithAwaitility {

    private FileCreator fileCreator = new FileCreator();

    private String fileName = "file.txt";

    @Before
    public void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(fileName));
    }

    @Test
    public void shouldCreateFile() throws Exception {
        // when
        fileCreator.createFile(fileName);

        // then
        await().until(fileIsCreated(fileName));
    }

    private Callable<Boolean> fileIsCreated(final String fileName) {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Path path = Paths.get(fileName);
                return Files.exists(path);
            }
        };
    }
}
