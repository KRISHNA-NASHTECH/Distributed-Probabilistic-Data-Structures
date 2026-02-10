package org.nashtech.assignment;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.nio.charset.StandardCharsets;

public class ScalableBloom {
    private final BloomFilter<String> filter;

    public ScalableBloom(int expectedInsertions, double fpp) {
        this.filter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                expectedInsertions, fpp
        );
    }

    public void add(String key) { filter.put(key); }
    public boolean mightContain(String key) { return filter.mightContain(key); }

    public void merge(ScalableBloom other) {
        this.filter.putAll(other.filter);
    }
}
