import java.io.*;
import java.util.*;

public class gen {
    static int solve(int[][] m) {
        int n = m.length;
        int candidate = 0;

        for (int i = 1; i < n; i++) {
            if (m[candidate][i] == 1) candidate = i;
        }

        for (int i = 0; i < n; i++) {
            if (i == candidate) continue;
            if (m[candidate][i] == 1 || m[i][candidate] == 0) return -1;
        }

        return candidate;
    }

    static int brute(int[][] m) {
        int n = m.length;
        for (int c = 0; c < n; c++) {
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                if (i == c) continue;
                if (m[c][i] != 0 || m[i][c] != 1) {
                    ok = false;
                    break;
                }
            }
            if (ok) return c;
        }
        return -1;
    }

    static void writeCase(File inDir, File outDir, int idx, int[][] m) throws Exception {
        String id = String.format("%02d", idx);

        try (PrintWriter w = new PrintWriter(new File(inDir, "input" + id + ".txt"))) {
            int n = m.length;
            w.println(n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j > 0) w.print(' ');
                    w.print(m[i][j]);
                }
                w.println();
            }
        }

        try (PrintWriter w = new PrintWriter(new File(outDir, "output" + id + ".txt"))) {
            w.println(solve(m));
        }
    }

    static int[][] noCelebrity(int n, Random r) {
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) m[i][j] = r.nextBoolean() ? 1 : 0;
            }
        }

        // Make sure person 0 cannot be a celebrity.
        if (n > 1) m[0][1] = 1;
        return m;
    }

    static int[][] withCelebrity(int n, int c, Random r) {
        int[][] m = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) m[i][j] = r.nextBoolean() ? 1 : 0;
            }
        }

        for (int j = 0; j < n; j++) m[c][j] = 0;
        for (int i = 0; i < n; i++) m[i][c] = (i == c ? 0 : 1);

        return m;
    }

    public static void main(String[] args) throws Exception {
        File base = new File("../Testcases");
        File inDir = new File(base, "Input");
        File outDir = new File(base, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random r = new Random(2272026L);
        ArrayList<int[][]> cases = new ArrayList<>();

        cases.add(new int[][]{{0}});
        cases.add(withCelebrity(2, 0, r));
        cases.add(withCelebrity(3, 1, r));
        cases.add(withCelebrity(5, 4, r));
        cases.add(noCelebrity(2, r));
        cases.add(noCelebrity(3, r));
        cases.add(noCelebrity(5, r));
        cases.add(withCelebrity(10, 0, r));
        cases.add(withCelebrity(10, 9, r));

        int[][] allKnown = new int[6][6];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 6; j++)
                if (i != j) allKnown[i][j] = 1;
        cases.add(allKnown);

        int[][] nobodyKnown = new int[6][6];
        cases.add(nobodyKnown);

        for (int c = cases.size(); c < 30; c++) {
            int n = 2 + r.nextInt(29);
            cases.add(r.nextBoolean() ? withCelebrity(n, r.nextInt(n), r) : noCelebrity(n, r));
        }

        int[] sizes = {50, 75, 100, 150, 200, 300, 400, 500, 750, 1000};
        while (cases.size() < 100) {
            int n = sizes[(cases.size() - 30) % sizes.length];
            int type = (cases.size() - 30) % 4;

            if (type == 0) cases.add(withCelebrity(n, 0, r));
            else if (type == 1) cases.add(withCelebrity(n, n - 1, r));
            else if (type == 2) cases.add(withCelebrity(n, r.nextInt(n), r));
            else cases.add(noCelebrity(n, r));
        }

        for (int i = 0; i < 100; i++) {
            int bruteAnswer = brute(cases.get(i));
            int fastAnswer = solve(cases.get(i));
            if (bruteAnswer != fastAnswer) throw new RuntimeException("Generator verification failed at " + i);
            writeCase(inDir, outDir, i, cases.get(i));
        }
    }
}
