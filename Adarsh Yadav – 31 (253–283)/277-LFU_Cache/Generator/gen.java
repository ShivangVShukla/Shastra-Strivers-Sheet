import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {
    private static final Random RNG = new Random(20261025L);

    private static class RefCache {
        private static class Entry {
            int value;
            int frequency;
            long lastUsed;
            Entry(int value, long tick) {
                this.value = value;
                this.frequency = 1;
                this.lastUsed = tick;
            }
        }

        int capacity;
        long tick = 0;
        Map<Integer, Entry> entries = new HashMap<>();

        RefCache(int capacity) { this.capacity = capacity; }

        Integer get(int key) {
            Entry e = entries.get(key);
            if (e == null) return null;
            e.frequency++;
            e.lastUsed = ++tick;
            return e.value;
        }

        void put(int key, int value) {
            Entry e = entries.get(key);
            if (e != null) {
                e.value = value;
                e.frequency++;
                e.lastUsed = ++tick;
                return;
            }
            if (entries.size() >= capacity) {
                int victim = -1;
                int minFreq = Integer.MAX_VALUE;
                long oldest = Long.MAX_VALUE;
                for (Map.Entry<Integer, Entry> item : entries.entrySet()) {
                    Entry candidate = item.getValue();
                    if (candidate.frequency < minFreq ||
                        (candidate.frequency == minFreq && candidate.lastUsed < oldest)) {
                        victim = item.getKey();
                        minFreq = candidate.frequency;
                        oldest = candidate.lastUsed;
                    }
                }
                entries.remove(victim);
            }
            Entry added = new Entry(value, ++tick);
            entries.put(key, added);
        }
    }

    public static void main(String[] args) throws Exception {
        Path root = Paths.get(args.length > 0 ? args[0] : ".");
        Path inDir = root.resolve("Testcases/input");
        Path outDir = root.resolve("Testcases/output");
        Files.createDirectories(inDir);
        Files.createDirectories(outDir);

        for (int tc = 0; tc < 120; tc++) {
            String input = createInput(tc);
            String output = referenceOutput(input);
            String suffix = String.format("%02d", tc);
            Files.writeString(inDir.resolve("input" + suffix + ".txt"), input);
            Files.writeString(outDir.resolve("output" + suffix + ".txt"), output);
        }
        System.out.println("Generated 120 input/output testcase pairs.");
    }

    private static String createInput(int tc) {
        if (tc == 0) {
            return serialize(2, new int[][]{
                {1,1,1},{1,2,2},{2,1},{1,3,3},{2,2},{2,3},{1,4,4},{2,1},{2,3},{2,4}
            });
        }
        if (tc == 1) {
            return serialize(3, new int[][]{
                {1,5,7},{1,4,6},{1,3,5},{1,2,4},{1,1,3},{2,1},{2,2},{2,3},{2,4},{2,5}
            });
        }
        if (tc == 2) {
            return serialize(1, new int[][]{{1,1,10},{2,1},{1,2,20},{2,1},{2,2}});
        }

        int capacity;
        int q;
        switch (tc % 8) {
            case 0: capacity = 1000; q = 100000; break;
            case 1: capacity = 1; q = 1000 + RNG.nextInt(4001); break;
            case 2: capacity = 2; q = 500 + RNG.nextInt(1501); break;
            case 3: capacity = 10 + RNG.nextInt(91); q = 100 + RNG.nextInt(901); break;
            case 4: capacity = 1 + RNG.nextInt(10); q = 1 + RNG.nextInt(20); break;
            case 5: capacity = 50 + RNG.nextInt(451); q = 1000 + RNG.nextInt(4001); break;
            case 6: capacity = 100 + RNG.nextInt(901); q = 10000 + RNG.nextInt(10001); break;
            default: capacity = 1 + RNG.nextInt(1000); q = 2 + RNG.nextInt(998); break;
        }

        int[][] ops = new int[q][];
        for (int i = 0; i < q; i++) {
            int op = (i == 0 || RNG.nextInt(100) < 58) ? 1 : 2;
            int key;
            if (RNG.nextInt(100) < 60) key = RNG.nextInt(Math.max(1, capacity * 2));
            else key = RNG.nextInt(10001);
            if (op == 1) {
                int value = RNG.nextInt(100001);
                ops[i] = new int[]{1, key, value};
            } else {
                ops[i] = new int[]{2, key};
            }
        }
        return serialize(capacity, ops);
    }

    private static String serialize(int capacity, int[][] ops) {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n").append(capacity).append(' ').append(ops.length).append('\n');
        for (int[] op : ops) {
            for (int i = 0; i < op.length; i++) {
                if (i > 0) sb.append(' ');
                sb.append(op[i]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private static String referenceOutput(String input) {
        Scanner sc = new Scanner(input);
        int t = sc.nextInt();
        StringBuilder out = new StringBuilder();
        for (int tc = 0; tc < t; tc++) {
            int capacity = sc.nextInt();
            int q = sc.nextInt();
            RefCache cache = new RefCache(capacity);
            for (int i = 0; i < q; i++) {
                int op = sc.nextInt();
                if (op == 1) {
                    int key = sc.nextInt();
                    int value = sc.nextInt();
                    cache.put(key, value);
                    out.append("null");
                } else {
                    int key = sc.nextInt();
                    Integer value = cache.get(key);
                    out.append(value == null ? -1 : value);
                }
                if (i + 1 < q) out.append(' ');
            }
            out.append('\n');
        }
        sc.close();
        return out.toString();
    }
}
