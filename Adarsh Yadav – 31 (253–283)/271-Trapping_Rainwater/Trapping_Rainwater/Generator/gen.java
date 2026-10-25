import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {
    private static final Random RNG = new Random(20261025L);

    public static void main(String[] args) throws Exception {
        Path root = Paths.get(args.length > 0 ? args[0] : ".");
        Path inDir = root.resolve("Testcases/input");
        Path outDir = root.resolve("Testcases/output");
        Files.createDirectories(inDir);
        Files.createDirectories(outDir);

        for (int tc = 0; tc < 120; tc++) {
            int[] h = createCase(tc);
            String input = "1\n" + h.length + "\n" + join(h) + "\n";
            String output = reference(h) + "\n";
            String suffix = String.format("%02d", tc);
            Files.writeString(inDir.resolve("input" + suffix + ".txt"), input);
            Files.writeString(outDir.resolve("output" + suffix + ".txt"), output);
        }
        System.out.println("Generated 120 input/output testcase pairs.");
    }

    private static int[] createCase(int tc) {
        if (tc == 0) return new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        if (tc == 1) return new int[]{4, 2, 0, 3, 2, 5};
        if (tc == 2) return new int[]{7, 4, 0, 9};
        if (tc == 3) return new int[]{1};
        if (tc == 4) return new int[]{1, 2};
        if (tc == 5) return new int[]{5, 5, 5, 5, 5};
        if (tc == 6) return new int[]{5, 4, 3, 2, 1};
        if (tc == 7) return new int[]{1, 2, 3, 4, 5};
        if (tc == 8) return new int[]{0, 0, 0, 0, 0};
        if (tc == 9) return new int[]{100000, 0, 100000};
        if (tc == 10) return new int[]{100000, 0, 0, 0, 100000};
        if (tc == 11) return new int[]{0, 100000, 0, 100000, 0};
        if (tc == 12) return new int[]{3, 0, 2, 0, 4};

        int n;
        switch (tc % 8) {
            case 0: n = 100000; break;
            case 1: n = 50000 + RNG.nextInt(50001); break;
            case 2: n = 1000 + RNG.nextInt(9001); break;
            case 3: n = 100 + RNG.nextInt(901); break;
            case 4: n = 2 + RNG.nextInt(99); break;
            case 5: n = 1 + RNG.nextInt(20); break;
            case 6: n = 1000 + RNG.nextInt(4001); break;
            default: n = 2 + RNG.nextInt(999); break;
        }

        int[] h = new int[n];
        int style = tc % 6;
        int level = RNG.nextInt(100001);
        for (int i = 0; i < n; i++) {
            switch (style) {
                case 0: h[i] = RNG.nextInt(100001); break;
                case 1: h[i] = Math.min(100000, i); break;
                case 2: h[i] = Math.min(100000, n - i); break;
                case 3: h[i] = (i % 2 == 0) ? 100000 : 0; break;
                case 4: h[i] = RNG.nextInt(101); break;
                default:
                    level = Math.max(0, Math.min(100000, level + RNG.nextInt(2001) - 1000));
                    h[i] = level;
            }
        }
        return h;
    }

    // Independent reference implementation using prefix/suffix maxima.
    private static long reference(int[] h) {
        int n = h.length;
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = h[0];
        for (int i = 1; i < n; i++) left[i] = Math.max(left[i - 1], h[i]);
        right[n - 1] = h[n - 1];
        for (int i = n - 2; i >= 0; i--) right[i] = Math.max(right[i + 1], h[i]);

        long total = 0;
        for (int i = 0; i < n; i++) {
            total += Math.max(0, Math.min(left[i], right[i]) - h[i]);
        }
        return total;
    }

    private static String join(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(arr[i]);
        }
        return sb.toString();
    }
}
