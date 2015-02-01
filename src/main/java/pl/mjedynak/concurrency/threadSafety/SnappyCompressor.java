package pl.mjedynak.concurrency.threadSafety;

import org.xerial.snappy.Snappy;

import java.io.IOException;

public class SnappyCompressor implements Compressor {

    @Override
    public byte[] compress(String input) {
        try {
            return Snappy.compress(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String decompress(byte[] data) {
        try {
            return Snappy.uncompressString(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
