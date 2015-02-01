package pl.mjedynak.concurrency.threadSafety;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NotThreadSafeCompressor implements Compressor {

    private Map<String, String> map = new HashMap<>();

    @Override
    public byte[] compress(String input) {
        String id = UUID.randomUUID().toString();
        map.put(id, input);
        return id.getBytes();
    }

    @Override
    public String decompress(byte[] input) {
        return map.get(new String(input));
    }
}
