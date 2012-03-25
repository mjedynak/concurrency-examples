package pl.mjedynak.concurrency.asyncTest.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.nio.file.Files;
import java.nio.file.Path;

public final class FileMatcher {

    private FileMatcher() {
    }

    public static Matcher<Path> exists() {
        return new TypeSafeMatcher<Path>() {
            Path fileTested;

            public boolean matchesSafely(Path item) {
                fileTested = item;
                return Files.exists(item);
            }

            public void describeTo(Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText(" exists");
            }
        };
    }
}
