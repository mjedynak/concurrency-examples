package pl.mjedynak.concurrency.snappy;

import org.junit.Test;
import org.xerial.snappy.Snappy;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

public class SnappyTest {
    private final static int MULTIPLIER = 1000;
    private final static int N_THREADS = 100;
    private final static int N_TASKS = N_THREADS * MULTIPLIER;

    @Test
    public void shouldBeThreadSafe() throws Exception {
        // given
        List<String> inputData = generateInputData();
        List<Callable<String>> tasks = newArrayList();
        range(0, N_TASKS).forEach(i -> tasks.add(() -> {
                byte[] compressed = Snappy.compress(inputData.get(i));
                byte[] uncompressed = Snappy.uncompress(compressed);
                return new String(uncompressed);
        }));
        ExecutorService executorService = newFixedThreadPool(N_THREADS);
        // when
        List<Future<String>> results = executorService.invokeAll(tasks);
        // then
        verifyResults(inputData, results);
    }

    private List<String> generateInputData() {
        List<String> data = newArrayList();
        range(0, N_TASKS).forEach(i -> data.add(UUID.randomUUID().toString()));
        return data;
    }

    private void verifyResults(List<String> data, List<Future<String>> results) throws Exception {
        for (int i = 0; i < N_TASKS; i++) {
            Future<String> future = results.get(i);
            String result = future.get();
            assertThat(result).isEqualTo(data.get(i));
        }
    }

}
