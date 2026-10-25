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
            int[] heights = createCase(tc);
            String input = "1\n" + heights.length + "\n" + join(heights) + "\n";
            String output = reference(heights) + "\n";
            String suffix = String.format("%02d", tc);
            Files.writeString(inDir.resolve("input" + suffix + ".txt"), input);
            Files.writeString(outDir.resolve("output" + suffix + ".txt"), output);
        }
        System.out.println("Generated 120 input/output testcase pairs.");
    }

    private static int[] createCase(int tc) {
        switch (tc) {
            case 0: return new int[]{2, 1, 5, 6, 2, 3};
            case 1: return new int[]{3, 5, 1, 7, 5, 9};
            case 2: return new int[]{2, 4};
            case 3: return new int[]{0};
            case 4: return new int[]{10000};
            case 5: return new int[]{1, 1, 1, 1, 1};
            case 6: return new int[]{5, 4, 3, 2, 1};
            case 7: return new int[]{1, 2, 3, 4, 5};
            case 8: return new int[]{0, 0, 0, 0};
            case 9: return new int[]{10000, 10000, 10000};
            case 10: return new int[]{1, 10000, 1};
            case 11: return new int[]{2, 1, 2};
            case 12: return new int[]{2, 2, 1, 2, 2};
            default: break;
        }

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
        int level = RNG.nextInt(10001);
        for (int i = 0; i < n; i++) {
            switch (style) {
                case 0: h[i] = RNG.nextInt(10001); break;
                case 1: h[i] = i % 10001; break;
                case 2: h[i] = Math.max(0, 10000 - (i % 10001)); break;
                case 3: h[i] = (i % 2 == 0) ? 10000 : 0; break;
                case 4: h[i] = RNG.nextInt(101); break;
                default:
                    level = Math.max(0, Math.min(10000, level + RNG.nextInt(2001) - 1000));
                    h[i] = level;
            }
        }
        return h;
    }

    // Independent reference: expand around each bar using nearest strictly smaller boundaries.
    private static long reference(int[] h) {
        int n = h.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, -1);
        Arrays.fill(right, n);
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            while (top >= 0 && h[stack[top]] >= h[i]) top--;
            left[i] = top < 0 ? -1 : stack[top];
            stack[++top] = i;
        }

        top = -1;
        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && h[stack[top]] >= h[i]) top--;
            right[i] = top < 0 ? n : stack[top];
            stack[++top] = i;
        }

        long best = 0;
        for (int i = 0; i < n; i++) {
            best = Math.max(best, (long) h[i] * (right[i] - left[i] - 1));
        }
        return best;
    }

    private static String join(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(a[i]);
        }
        return sb.toString();
    }
}
