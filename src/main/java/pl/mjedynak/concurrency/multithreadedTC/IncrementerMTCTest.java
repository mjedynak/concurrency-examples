package pl.mjedynak.concurrency.multithreadedTC;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IncrementerMTCTest {

    class NonThreadSafeIncrementer {
        private int value;

        public void incrementValue() {
            value++;
        }

        public int getValue() {
            return value;
        }
    }

    class ThreadSafeIncrementer {
        private int value;

        public synchronized void incrementValue() {
            value++;
        }

        public int getValue() {
            return value;
        }
    }
                                     
    class MyMultithreadedTest extends MultithreadedTestCase {
        NonThreadSafeIncrementer incrementer;

        @Override
        public void initialize() {
            incrementer = new NonThreadSafeIncrementer();
        }

        public void thread1() {
//            System.out.println("t1");
           incrementer.incrementValue();
        }

        public void thread2() {
//            System.out.println("t2");
            incrementer.incrementValue();
        }

        @Override
        public void finish() {
            assertThat(incrementer.getValue(), is(2));
        }
    }

    @Test
    public void test() throws Throwable {
        TestFramework.runManyTimes(new MyMultithreadedTest(), 1000);
    }
}
