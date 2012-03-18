package pl.mjedynak.concurrency.multithreadedTC.stringCounter;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringCounterTest {

    private static final String STRING = "someString";
    private StringCounter stringCounter = new StringCounter();


    @Test
    public void shouldAddNonExistingValue() {
        // given
        stringCounter.addString(STRING);

        // when
        int result = stringCounter.getCounterFor(STRING);

        // then
        assertThat(result, is(1));
    }

    @Test
    public void shouldIncrementExistingValue() {
        // given
        stringCounter.addString(STRING);
        stringCounter.addString(STRING);

        // when
        int result = stringCounter.getCounterFor(STRING);

        // then
        assertThat(result, is(2));
    }

    class MyMultithreadedTest extends MultithreadedTestCase {
        StringCounter stringCounter;
        private String someString = "someString";

        @Override
        public void initialize() {
            stringCounter = new StringCounter();
        }

        public void thread1() {
            stringCounter.addString(someString);
        }

        public void thread2() {
            stringCounter.addString(someString);
        }

        public void thread3() {
            stringCounter.addString(someString);
        }

        public void thread4() {
            stringCounter.addString(someString);
        }

        public void thread5() {
            stringCounter.addString(someString);
        }

        @Override
        public void finish() {
            assertThat(stringCounter.getCounterFor(someString), is(5));
        }
    }

    @Test
    public void shouldBeThreadSafe() throws Throwable {
        TestFramework.runManyTimes(new MyMultithreadedTest(), 100);
    }
}
