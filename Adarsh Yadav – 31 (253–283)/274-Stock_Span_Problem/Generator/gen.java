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
            long[] prices = createCase(tc);
            String input = "1\n" + prices.length + "\n" + join(prices) + "\n";
            String output = reference(prices) + "\n";
            String suffix = String.format("%02d", tc);
            Files.writeString(inDir.resolve("input" + suffix + ".txt"), input);
            Files.writeString(outDir.resolve("output" + suffix + ".txt"), output);
        }
        System.out.println("Generated 120 input/output testcase pairs.");
    }

    private static long[] createCase(int tc) {
        switch (tc) {
            case 0: return new long[]{120,100,60,80,90,110,115};
            case 1: return new long[]{15,13,12,14,16,20};
            case 2: return new long[]{30,20,25,28,27,29,35,40};
            case 3: return new long[]{1};
            case 4: return new long[]{1000000000L};
            case 5: return new long[]{5,5,5,5,5};
            case 6: return new long[]{9,8,7,6,5,4};
            case 7: return new long[]{1,2,3,4,5,6};
            case 8: return new long[]{1000000000L,1,1000000000L,1};
            case 9: return new long[]{1,1000000000L,1,1000000000L,1};
            case 10: return new long[]{10,10,9,10,10,11,11};
            case 11: return new long[]{2,1,2,1,2,1,2};
            case 12: return new long[]{999999999L,1000000000L,999999998L,1000000000L};
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

        long[] prices = new long[n];
        int style = tc % 6;
        long level = 1 + RNG.nextInt(1000000000);
        for (int i = 0; i < n; i++) {
            switch (style) {
                case 0: prices[i] = 1 + (long) RNG.nextInt(1000000000); break;
                case 1: prices[i] = 1 + (i % 1000000000); break;
                case 2: prices[i] = Math.max(1, 1000000000L - i); break;
                case 3: prices[i] = (i % 2 == 0) ? 1000000000L : 1L; break;
                case 4: prices[i] = 1 + RNG.nextInt(101); break;
                default:
                    long delta = RNG.nextInt(200001) - 100000;
                    level = Math.max(1, Math.min(1000000000L, level + delta));
                    prices[i] = level;
            }
        }
        return prices;
    }

    // Independent reference implementation using previous-greater indices.
    private static String reference(long[] prices) {
        int n = prices.length;
        int[] spans = new int[n];
        int[] stack = new int[n];
        int top = -1;
        for (int i = 0; i < n; i++) {
            while (top >= 0 && prices[stack[top]] <= prices[i]) top--;
            spans[i] = (top < 0) ? i + 1 : i - stack[top];
            stack[++top] = i;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(spans[i]);
        }
        return sb.toString();
    }

    private static String join(long[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(a[i]);
        }
        return sb.toString();
    }
}
