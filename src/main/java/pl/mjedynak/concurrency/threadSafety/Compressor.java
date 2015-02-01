package pl.mjedynak.concurrency.threadSafety;

public interface Compressor {

    byte[] compress(String input);

    String decompress(byte[] input);
}
