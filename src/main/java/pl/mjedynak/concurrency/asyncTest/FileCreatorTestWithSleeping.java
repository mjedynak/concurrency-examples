package pl.mjedynak.concurrency.asyncTest;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertThat;
import static pl.mjedynak.concurrency.asyncTest.matcher.FileMatcher.exists;

public class FileCreatorTestWithSleeping {

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
        Thread.sleep(1000);

        // then
        verifyThatFileExists(fileName);
    }

    private void verifyThatFileExists(String fileName) {
        Path path = Paths.get(fileName);
        assertThat(path, exists());
    }


}
