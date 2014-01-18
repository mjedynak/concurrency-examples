package pl.mjedynak.concurrency.listenableFuture;

import com.google.common.util.concurrent.FutureFallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.google.common.util.concurrent.Futures.immediateFuture;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ListenableFutureWithException {

    @Test
    public void shouldReturnValueFromFaultTolerantFuture() throws ExecutionException, InterruptedException {
        // given
        final String value = "default value";
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture<String> future = service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new Exception();
            }
        });

        ListenableFuture<String> faultTolerantFuture = Futures.withFallback(
                future, new FutureFallback<String>() {
            public ListenableFuture<String> create(Throwable t) {
                return immediateFuture(value);
            }
        });

        // when
        String result = faultTolerantFuture.get();

        // then
        assertThat(result, is(value));
    }
}
