package pl.mjedynak.concurrency.asyncTest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pl.mjedynak.concurrency.asyncTest.matcher.FileMatcher.exists;

public class FileCreatorTestWithCustomValidator {

    private FileCreator fileCreator = new FileCreator();

    private String fileName = "file.txt";

    @Before
    public void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(fileName));
    }

    @Test
    public void shouldCreateFile() throws InterruptedException {
        // when
        fileCreator.createFile(fileName);

        // then
        boolean fileExists = checkThatFileExists(fileName);
        assertThat(fileExists, is(true));
    }

    private boolean checkThatFileExists(String fileName) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Path path = Paths.get(fileName);
            try {
                assertThat(path, exists());
                return true;
            } catch (AssertionError e) {
                // ignore exception
            }
            Thread.sleep(100);
        }
        throw new AssertionError("Timeout exceeded");
    }
}
