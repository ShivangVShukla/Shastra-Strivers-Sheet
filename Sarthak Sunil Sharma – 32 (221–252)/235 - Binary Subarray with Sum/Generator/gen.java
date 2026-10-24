import java.io.*;
import java.util.*;

public class gen {
    static long brute(int[] a, int goal) {
        long ans = 0;
        for (int i = 0; i < a.length; i++) {
            int sum = 0;
            for (int j = i; j < a.length; j++) {
                sum += a[j];
                if (sum == goal) ans++;
            }
        }
        return ans;
    }

    static class TestCase {
        int[] a; int goal;
        TestCase(int[] a, int goal) { this.a = a; this.goal = goal; }
    }

    public static void main(String[] args) throws Exception {
        Random rnd = new Random(2352026L);
        File inDir = new File("../Testcases/Input");
        File outDir = new File("../Testcases/Output");
        inDir.mkdirs(); outDir.mkdirs();

        for (int tc = 0; tc < 100; tc++) {
            int n;
            if (tc == 0) n = 1;
            else if (tc < 10) n = 2 + tc;
            else if (tc < 25) n = 10 + rnd.nextInt(21);
            else if (tc < 70) n = 50 + rnd.nextInt(151);
            else n = 500 + rnd.nextInt(501);

            int[] a = new int[n];
            if (tc == 0) a[0] = 0;
            else if (tc == 1) Arrays.fill(a, 0);
            else if (tc == 2) Arrays.fill(a, 1);
            else {
                for (int i = 0; i < n; i++) a[i] = rnd.nextBoolean() ? 1 : 0;
            }

            int goal;
            if (tc == 0) goal = 0;
            else if (tc == 1) goal = 0;
            else if (tc == 2) goal = n;
            else if (tc == 3) goal = 1;
            else if (tc == 4) goal = n;
            else goal = rnd.nextInt(n + 1);

            long ans = brute(a, goal);
            StringBuilder sb = new StringBuilder();
            sb.append(n).append(' ').append(goal).append('\n');
            for (int i = 0; i < n; i++) {
                if (i > 0) sb.append(' ');
                sb.append(a[i]);
            }
            sb.append('\n');
            String id = String.format("%02d", tc);
            try (FileWriter fw = new FileWriter(new File(inDir, "input" + id + ".txt"))) { fw.write(sb.toString()); }
            try (FileWriter fw = new FileWriter(new File(outDir, "output" + id + ".txt"))) { fw.write(Long.toString(ans) + "\n"); }
        }
    }
}
