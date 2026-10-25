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
            {0,1,1,0}, {0,0,0,0}, {1,1,0,0}, {0,1,1,0}
        };
        if (tc == 1) return new int[][]{{0,1},{1,0}};
        if (tc == 2) return new int[][]{{0,1,0},{0,0,0},{0,1,0}};
        if (tc == 3) return new int[][]{{0}};
        if (tc == 4) return new int[][]{{0,1,1},{0,0,1},{0,0,0}};
        if (tc == 5) return new int[][]{{0,0,0},{1,0,1},{1,0,0}};
        if (tc == 6) return new int[][]{{0,1,0},{1,0,0},{0,1,0}};
        if (tc == 7) return new int[][]{{0,0,0,0},{1,0,1,1},{1,0,0,1},{1,0,1,0}};
        if (tc == 8) return new int[][]{{0,1,0,0},{0,0,0,0},{0,1,0,0},{0,1,0,0}};
        if (tc == 9) return new int[][]{{0,1,1,1},{0,0,0,0},{0,1,0,1},{0,1,1,0}};
        if (tc == 10) return new int[][]{{0,1,0,0,0},{0,0,0,0,0},{1,1,0,0,1},{0,1,0,0,0},{0,1,0,0,0}};
        if (tc == 11) return new int[][]{{0,0,0,0,0},{1,0,1,1,1},{1,0,0,1,1},{1,0,1,0,1},{1,0,1,1,0}};

        int n;
        switch (tc % 8) {
            case 0: n = 3000; break;
            case 1: n = 1000 + RNG.nextInt(1001); break;
            case 2: n = 100 + RNG.nextInt(401); break;
            case 3: n = 2 + RNG.nextInt(98); break;
            case 4: n = 1 + RNG.nextInt(20); break;
            case 5: n = 2 + RNG.nextInt(48); break;
            case 6: n = 300 + RNG.nextInt(301); break;
            default: n = 2 + RNG.nextInt(198); break;
        }

        int[][] m = new int[n][n];
        // Diagonal remains zero. Most cases are random; a few contain a guaranteed celebrity.
        boolean forceCelebrity = (tc % 4 == 0);
        int celebrity = forceCelebrity ? RNG.nextInt(n) : -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    m[i][j] = 0;
                } else if (forceCelebrity && j == celebrity) {
                    m[i][j] = 1;
                } else if (forceCelebrity && i == celebrity) {
                    m[i][j] = 0;
                } else {
                    m[i][j] = RNG.nextBoolean() ? 1 : 0;
                }
            }
        }
        return m;
    }

    private static String serialize(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n").append(matrix.length).append('\n');
        for (int[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                if (j > 0) sb.append(' ');
                sb.append(row[j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // Independent verification of every possible person.
    private static int reference(int[][] m) {
        int n = m.length;
        int found = -1;
        for (int c = 0; c < n; c++) {
            boolean celebrity = true;
            for (int j = 0; j < n && celebrity; j++) {
                if (j != c && m[c][j] != 0) celebrity = false;
            }
            for (int i = 0; i < n && celebrity; i++) {
                if (i != c && m[i][c] != 1) celebrity = false;
            }
            if (celebrity) {
                if (found != -1) return -1;
                found = c;
            }
        }
        return found;
    }
}
