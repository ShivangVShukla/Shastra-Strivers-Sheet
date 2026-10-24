import java.io.*;
import java.util.*;

public class gen {
    static final Random R = new Random(2472026L);
    static long kthLargest(long[] a, int k) {
        long[] b = a.clone();
        Arrays.sort(b);
        return b[b.length - k];
    }
    public static void main(String[] args) throws Exception {
        File inDir = new File("../Testcases/Input");
        File outDir = new File("../Testcases/Output");
        inDir.mkdirs(); outDir.mkdirs();
        for (int tc = 0; tc < 100; tc++) {
            int n;
            if (tc < 10) n = 1 + tc;
            else if (tc < 30) n = 10 + R.nextInt(41);
            else if (tc < 70) n = 50 + R.nextInt(451);
            else n = 500 + R.nextInt(1001);
            int k = 1 + R.nextInt(n);
            long[] a = new long[n];
            for (int i = 0; i < n; i++) {
                if (tc == 0) a[i] = 7;
                else if (tc == 1) a[i] = -100 + i;
                else if (tc == 2) a[i] = 100 - i;
                else if (tc == 3) a[i] = (i % 5) - 2;
                else a[i] = R.nextInt(2000001) - 1000000;
            }
            if (tc == 4) k = 1;
            if (tc == 5) k = n;
            String id = String.format("%02d", tc);
            try (PrintWriter pw = new PrintWriter(new File(inDir, "input" + id + ".txt"))) {
                pw.println(n + " " + k);
                for (int i = 0; i < n; i++) { if (i > 0) pw.print(' '); pw.print(a[i]); }
                pw.println();
            }
            try (PrintWriter pw = new PrintWriter(new File(outDir, "output" + id + ".txt"))) {
                pw.println(kthLargest(a, k));
            }
        }
    }
}
