import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {
    private static final Random RNG = new Random(20261025L);

    private static class CacheRef {
        int capacity;
        LinkedHashMap<Integer, Integer> cache;
        CacheRef(int capacity) {
            this.capacity = capacity;
            cache = new LinkedHashMap<>(16, 0.75f, true);
        }
        Integer get(int key) { return cache.containsKey(key) ? cache.get(key) : null; }
        void put(int key, int value) {
            cache.put(key, value);
            if (cache.size() > capacity) {
                Iterator<Integer> it = cache.keySet().iterator();
                it.next();
                it.remove();
            }
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
        StringBuilder sb = new StringBuilder();
        int capacity;
        int q;
        if (tc == 0) {
            capacity = 2;
            int[][] ops = {{1,1,1},{1,2,2},{2,1},{1,3,3},{2,2},{1,4,4},{2,1},{2,3},{2,4}};
            return serialize(capacity, ops);
        }
        if (tc == 1) {
            capacity = 1;
            int[][] ops = {{1,1,1},{1,2,2},{2,1},{1,3,3},{2,2},{1,4,4},{2,3}};
            return serialize(capacity, ops);
        }
        if (tc == 2) {
            capacity = 2;
            int[][] ops = {{1,1,1},{1,2,2},{2,1},{1,3,3},{1,4,4},{2,2},{2,4}};
            return serialize(capacity, ops);
        }

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

        sb.append("1\n").append(capacity).append(' ').append(q).append('\n');
        for (int i = 0; i < q; i++) {
            int op;
            if (i == 0 || RNG.nextInt(100) < 58) op = 1;
            else op = 2;

            int key;
            if (RNG.nextInt(100) < 55) key = RNG.nextInt(Math.max(1, capacity * 2));
            else key = RNG.nextInt(10001);

            if (op == 1) {
                int value = RNG.nextInt(100001);
                sb.append("1 ").append(key).append(' ').append(value).append('\n');
            } else {
                sb.append("2 ").append(key).append('\n');
            }
        }
        return sb.toString();
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
            CacheRef cache = new CacheRef(capacity);
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
