package org.nashtech.assignment;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class ScalableHLL {
    private final int[] registers;
    private final int m;

    public ScalableHLL(int precision) {
        this.m = 1 << precision;
        this.registers = new int[m];
    }

    public void add(String item) {
        long hash = Hashing.murmur3_128().hashString(item, StandardCharsets.UTF_8).asLong();
        int p = (int)(Math.log(m) / Math.log(2));
        int index = (int) (hash >>> (64 - p));
        long remainingBits = (hash << p) | (1L << (p - 1));
        int zeros = Long.numberOfLeadingZeros(remainingBits) + 1;
        registers[index] = Math.max(registers[index], zeros);
    }

    public void merge(ScalableHLL other) {
        for (int i = 0; i < m; i++) {
            this.registers[i] = Math.max(this.registers[i], other.registers[i]);
        }
    }
}