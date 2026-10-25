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
            int[][] matrix = createCase(tc);
            String input = serialize(matrix);
            String output = reference(matrix) + "\n";
            String suffix = String.format("%02d", tc);
            Files.writeString(inDir.resolve("input" + suffix + ".txt"), input);
            Files.writeString(outDir.resolve("output" + suffix + ".txt"), output);
        }
        System.out.println("Generated 120 input/output testcase pairs.");
    }

    private static int[][] createCase(int tc) {
        if (tc == 0) return new int[][]{
            {1,0,1,0,0}, {1,0,1,1,1}, {1,1,1,1,1}, {1,0,0,1,0}
        };
        if (tc == 1) return new int[][]{{1}};
        if (tc == 2) return new int[][]{{1,0,1,0,0}, {1,0,1,1,1}};
        if (tc == 3) return new int[][]{{0}};
        if (tc == 4) return new int[][]{{1,1,1,1,1}};
        if (tc == 5) return new int[][]{{1},{1},{1},{1},{1}};
        if (tc == 6) return new int[][]{{0,0,0},{0,0,0}};
        if (tc == 7) return new int[][]{{1,1,1},{1,1,1},{1,1,1}};
        if (tc == 8) return new int[][]{{1,0,1},{0,1,0},{1,0,1}};
        if (tc == 9) return new int[][]{{1,1,0,1},{1,1,1,1},{1,1,1,1}};
        if (tc == 10) return new int[][]{{1,0,1,1,1,1},{1,1,1,1,1,1}};
        if (tc == 11) return new int[][]{{0,1,1,1},{1,1,1,0},{1,1,1,1}};

        int m, n;
        switch (tc % 10) {
            case 0: m = 1000; n = 1000; break;
            case 1: m = 1; n = 1 + RNG.nextInt(1000); break;
            case 2: m = 1 + RNG.nextInt(1000); n = 1; break;
            case 3: m = 100 + RNG.nextInt(401); n = 100 + RNG.nextInt(401); break;
            case 4: m = 2 + RNG.nextInt(49); n = 2 + RNG.nextInt(49); break;
            case 5: m = 1 + RNG.nextInt(10); n = 1 + RNG.nextInt(10); break;
            case 6: m = 20 + RNG.nextInt(181); n = 20 + RNG.nextInt(181); break;
            case 7: m = 2 + RNG.nextInt(99); n = 2 + RNG.nextInt(99); break;
            case 8: m = 300 + RNG.nextInt(201); n = 300 + RNG.nextInt(201); break;
            default: m = 1 + RNG.nextInt(100); n = 1 + RNG.nextInt(100); break;
        }

        int[][] a = new int[m][n];
        int style = tc % 6;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                switch (style) {
                    case 0: a[r][c] = RNG.nextBoolean() ? 1 : 0; break;
                    case 1: a[r][c] = (RNG.nextInt(100) < 75) ? 1 : 0; break;
                    case 2: a[r][c] = (RNG.nextInt(100) < 25) ? 1 : 0; break;
                    case 3: a[r][c] = ((r + c) % 2 == 0) ? 1 : 0; break;
                    case 4: a[r][c] = (r % 3 == 0 || c % 4 != 0) ? 1 : 0; break;
                    default: a[r][c] = (r % 5 == 0 || c % 7 == 0) ? 1 : RNG.nextInt(2); break;
                }
            }
        }
        return a;
    }

    private static String serialize(int[][] a) {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n").append(a.length).append(' ').append(a[0].length).append('\n');
        for (int[] row : a) {
            for (int c = 0; c < row.length; c++) {
                if (c > 0) sb.append(' ');
                sb.append(row[c]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // Independent reference algorithm: for each cell, expand its all-ones horizontal run
    // upward while tracking the minimum run width in that row span.
    private static long reference(int[][] a) {
        int m = a.length, n = a[0].length;
        int[][] leftRun = new int[m][n];
        for (int r = 0; r < m; r++) {
            int run = 0;
            for (int c = 0; c < n; c++) {
                run = (a[r][c] == 1) ? run + 1 : 0;
                leftRun[r][c] = run;
            }
        }

        long best = 0;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                int minWidth = Integer.MAX_VALUE;
                for (int top = r; top >= 0; top--) {
                    minWidth = Math.min(minWidth, leftRun[top][c]);
                    if (minWidth == 0) break;
                    best = Math.max(best, (long) minWidth * (r - top + 1));
                }
            }
        }
        return best;
    }
}
