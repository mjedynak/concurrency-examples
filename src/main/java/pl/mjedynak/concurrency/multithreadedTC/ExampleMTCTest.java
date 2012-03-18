package pl.mjedynak.concurrency.multithreadedTC;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ExampleMTCTest {

    class MyMultithreadedTest extends MultithreadedTestCase {
        AtomicInteger ai;

        @Override
        public void initialize() {
            ai = new AtomicInteger(1);
        }

        public void thread1() {
            System.out.println("t1");
            while (!ai.compareAndSet(2, 3)) Thread.yield();
        }

        public void thread2() {
            System.out.println("t2");
            assertTrue(ai.compareAndSet(1, 2));
        }

        @Override
        public void finish() {
            System.out.println("aa");
            assertEquals(ai.get(), 3);
        }
    }

    @Test
    public void test() throws Throwable {
//        TestFramework.runOnce(new MyMultithreadedTest());
        TestFramework.runManyTimes(new MyMultithreadedTest(), 100);
    }
}
