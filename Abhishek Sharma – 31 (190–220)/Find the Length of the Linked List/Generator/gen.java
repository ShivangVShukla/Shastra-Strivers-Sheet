import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class gen {
    static final int PID = 12;
    static final Random RANDOM = new Random(20261025L + PID);

    static int value(int low, int high) { return RANDOM.nextInt(high - low + 1) + low; }
    static int chooseN(int tc) {
        int n;
        if (tc % 10 == 0) n = 0;
        else if (tc % 10 == 1) n = 1;
        else if (tc == 99) n = 100000;
        else if (tc % 10 == 9) n = 10000;
        else if (tc % 10 == 8) n = 1000;
        else if (tc % 10 == 7) n = 250;
        else if (tc % 10 == 6) n = 100;
        else n = RANDOM.nextInt(50) + 1;
        if (PID == 6 || PID == 13 || PID == 19 || PID == 23 || PID == 30) n = Math.max(1, n);
        if (PID == 20 || PID == 24) n = Math.max(2, n);
        return n;
    }
    static String joined(List<Integer> a) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < a.size(); i++) { if (i > 0) s.append(' '); s.append(a.get(i)); }
        return s.toString();
    }
    static int pickTarget(List<Integer> a) {
        if (!a.isEmpty() && RANDOM.nextInt(100) < 60) return a.get(RANDOM.nextInt(a.size()));
        return value(-15, 35);
    }
    static String makeCase(int tc) {
        if (PID == 27) {
            int n = tc == 99 ? 100000 : tc % 10 == 9 ? 10000 : RANDOM.nextInt(50) + 1;
            int m = tc == 99 ? 100000 : tc % 10 == 9 ? 10000 : RANDOM.nextInt(50) + 1;
            List<Integer> a = new ArrayList<>(), b = new ArrayList<>();
            for (int i = 0; i < n; i++) a.add(value(0, 9));
            for (int i = 0; i < m; i++) b.add(value(0, 9));
            if (n > 1 && a.get(n - 1) == 0) a.set(n - 1, value(1, 9));
            if (m > 1 && b.get(m - 1) == 0) b.set(m - 1, value(1, 9));
            return n + "\n" + joined(a) + "\n" + m + "\n" + joined(b) + "\n";
        }
        int n = chooseN(tc);
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (PID == 29) a.add(value(0, 2));
            else if (PID == 13) a.add(value(1, 100));
            else a.add(value(-10, 30));
        }
        StringBuilder s = new StringBuilder();
        s.append(n).append('\n').append(joined(a)).append('\n');

        if (PID == 2 || PID == 15) {
            int q = tc % 10 == 9 ? 100 : RANDOM.nextInt(13); s.append(q).append('\n');
            List<Integer> cur = new ArrayList<>(a);
            for (int i = 0; i < q; i++) {
                int type = RANDOM.nextInt(4);
                if (type == 0) { s.append("H\n"); if (!cur.isEmpty()) cur.remove(0); }
                else if (type == 1) { s.append("T\n"); if (!cur.isEmpty()) cur.remove(cur.size() - 1); }
                else if (type == 2) {
                    int k = value(0, cur.size() + 2); s.append("K ").append(k).append('\n');
                    if (k >= 1 && k <= cur.size()) cur.remove(k - 1);
                } else {
                    int x = pickTarget(cur); s.append("X ").append(x).append('\n'); cur.remove((Integer)x);
                }
            }
        } else if (PID == 3 || PID == 16) {
            int q = tc % 10 == 9 ? 100 : RANDOM.nextInt(13); s.append(q).append('\n');
            List<Integer> cur = new ArrayList<>(a);
            for (int i = 0; i < q; i++) {
                int type = RANDOM.nextInt(4), x = value(-15, 40);
                if (type == 0) { s.append("H ").append(x).append('\n'); cur.add(0, x); }
                else if (type == 1) { s.append("T ").append(x).append('\n'); cur.add(x); }
                else if (type == 2) {
                    int k = value(1, cur.size() + 1); s.append("K ").append(k).append(' ').append(x).append('\n'); cur.add(k - 1, x);
                } else {
                    int target = pickTarget(cur); s.append("B ").append(target).append(' ').append(x).append('\n');
                    int at = cur.indexOf(target); if (at >= 0) cur.add(at, x);
                }
            }
        } else if (PID == 6 || PID == 19 || PID == 30) {
            s.append(value(1, Math.max(1, n))).append('\n');
        } else if (PID == 20) {
            s.append(value(2, n)).append('\n');
        } else if (PID == 24) {
            s.append(value(2, n)).append(' ').append(value(-20, 50)).append('\n');
        } else if (PID == 7 || PID == 13) {
            s.append(PID == 13 ? value(1, 100) : pickTarget(a)).append('\n');
        } else if (PID == 8 || PID == 9 || PID == 21 || PID == 22) {
            s.append(value(-30, 50)).append('\n');
        } else if (PID == 10 || PID == 23) {
            int k = PID == 10 ? value(1, n + 1) : value(1, Math.max(1, n));
            s.append(k).append(' ').append(value(-30, 50)).append('\n');
        } else if (PID == 11) {
            s.append(pickTarget(a)).append(' ').append(value(-30, 50)).append('\n');
        }
        return s.toString();
    }

    public static void main(String[] args) throws Exception {
        Path inDir = Paths.get("Testcases", "Input");
        Path outDir = Paths.get("Testcases", "Output");
        Files.createDirectories(inDir); Files.createDirectories(outDir);
        StringBuilder batch = new StringBuilder();
        for (int tc = 0; tc < 100; tc++) {
            String input = makeCase(tc);
            batch.append(input);
            Files.writeString(inDir.resolve(String.format("%02d.txt", tc)), input, StandardCharsets.UTF_8);
        }
        Process compile = new ProcessBuilder("javac", "Solution/Solution.java").redirectErrorStream(true).start();
        String compileLog = new String(compile.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        if (compile.waitFor() != 0) throw new IllegalStateException("Could not compile Solution.java:\n" + compileLog);
        Process run = new ProcessBuilder("java", "-cp", "Solution", "Solution").start();
        try (OutputStream os = run.getOutputStream()) { os.write(batch.toString().getBytes(StandardCharsets.UTF_8)); }
        String result = new String(run.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String error = new String(run.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        if (run.waitFor() != 0) throw new IllegalStateException("Solution execution failed:\n" + error);
        String trimmed = result.stripTrailing();
        String[] lines = trimmed.isEmpty() ? new String[0] : trimmed.split("\\R");
        int linesPerCase = PID >= 14 && PID <= 26 ? 2 : 1;
        if (lines.length != 100 * linesPerCase) throw new IllegalStateException("Expected " + (100 * linesPerCase) + " output lines but received " + lines.length);
        for (int tc = 0; tc < 100; tc++) {
            int from = tc * linesPerCase;
            StringBuilder output = new StringBuilder();
            for (int j = 0; j < linesPerCase; j++) { if (j > 0) output.append('\n'); output.append(lines[from + j]); }
            output.append('\n');
            Files.writeString(outDir.resolve(String.format("%02d.txt", tc)), output.toString(), StandardCharsets.UTF_8);
        }
        System.out.println("Generated 100 inputs and matching outputs for problem " + PID + ".");
    }
}
