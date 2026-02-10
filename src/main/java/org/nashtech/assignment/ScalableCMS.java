package org.nashtech.assignment;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class ScalableCMS {
    private final long[][] table;
    private final int width, depth;

    public ScalableCMS(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.table = new long[depth][width];
    }

    public void add(String key) {
        for (int i = 0; i < depth; i++) {
            int hash = Hashing.murmur3_32_fixed(i).hashString(key, StandardCharsets.UTF_8).asInt();
            int col = Math.abs(hash) % width;
            table[i][col]++;
        }
    }

    public void merge(ScalableCMS other) {
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < width; j++) {
                this.table[i][j] += other.table[i][j];
            }
        }
    }
}