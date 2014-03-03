package pl.mjedynak.concurrency.exceptionHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.logging.Logger;

import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerTest {

    @Mock private Logger logger;

    @Test
    public void shouldCatchException() throws InterruptedException {
        // given
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler(logger));

        // when
        new Thread(new MyRunnable()).start();

        // then
        verify(logger).info(startsWith("Caught exception "));
    }

    private static class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        private MyUncaughtExceptionHandler(Logger logger) {
            this.logger = logger;
        }

        private final Logger logger;

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            logger.info("Caught exception " + t + ", " + e);
        }
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            throw new RuntimeException("some message");
        }
    }
}
