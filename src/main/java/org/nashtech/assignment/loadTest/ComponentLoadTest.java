package org.nashtech.assignment.loadTest;

import org.nashtech.assignment.bloomFilter.ScalableBloom;
import org.nashtech.assignment.countMinSketches.ScalableCMS;
import org.nashtech.assignment.hyperLogLog.ScalableHLL;

import java.util.UUID;

public class ComponentLoadTest {
    public static void main(String[] args) {
        int ops = 1_000_000;

        System.out.println("=== Probabilistic DS Load Test ===");

        benchmark("HyperLogLog", () -> {
            ScalableHLL hll = new ScalableHLL(14);
            for (int i = 0; i < ops; i++) hll.add(UUID.randomUUID().toString());
        }, ops);

        benchmark("Bloom Filter", () -> {
            ScalableBloom bloom = new ScalableBloom(ops, 0.01);
            for (int i = 0; i < ops; i++) bloom.add(UUID.randomUUID().toString());
        }, ops);

        benchmark("Count-Min Sketch", () -> {
            ScalableCMS cms = new ScalableCMS(1000, 5);
            for (int i = 0; i < ops; i++) cms.add(UUID.randomUUID().toString());
        }, ops);
    }

    private static void benchmark(String name, Runnable task, int ops) {
        for(int i=0; i<50000; i++) UUID.randomUUID().toString();
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();

        double sec = (end - start) / 1_000_000_000.0;
        System.out.printf("[%s]%n Throughput: %.2f ops/sec | Avg Latency: %.2f ns/op%n%n",
                name, ops / sec, (double)(end - start) / ops);
    }
}