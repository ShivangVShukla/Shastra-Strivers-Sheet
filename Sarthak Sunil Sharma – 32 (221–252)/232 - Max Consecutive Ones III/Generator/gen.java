import java.io.*;
import java.util.*;

public class gen {
    static int solve(int[] a, int k) {
        int left = 0, zeros = 0, ans = 0;

        for (int right = 0; right < a.length; right++) {
            if (a[right] == 0) zeros++;

            while (zeros > k) {
                if (a[left] == 0) zeros--;
                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }

    static void writeCase(int idx, int[] a, int k) throws Exception {
        String in = String.format("../Testcases/Input/input%02d.txt", idx);
        String out = String.format("../Testcases/Output/output%02d.txt", idx);

        try (PrintWriter pw = new PrintWriter(new FileWriter(in))) {
            pw.println(a.length + " " + k);
            for (int i = 0; i < a.length; i++) {
                if (i > 0) pw.print(" ");
                pw.print(a[i]);
            }
            pw.println();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println(solve(a, k));
        }
    }

    static int[] randomArray(Random rnd, int n, int mode) {
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            if (mode == 0) {
                a[i] = rnd.nextInt(2);
            } else if (mode == 1) {
                a[i] = rnd.nextInt(100) < 20 ? 0 : 1;
            } else if (mode == 2) {
                a[i] = rnd.nextInt(100) < 80 ? 0 : 1;
            } else if (mode == 3) {
                a[i] = i % 3 == 0 ? 0 : 1;
            } else {
                a[i] = i % 4 == 0 ? 1 : 0;
            }
        }

        return a;
    }

    public static void main(String[] args) throws Exception {
        Random rnd = new Random(2322026L);

        List<int[]> arrays = new ArrayList<>();
        List<Integer> ks = new ArrayList<>();

        arrays.add(new int[]{1,1,1,0,0,0,1,1,1,1,0});
        ks.add(2);

        arrays.add(new int[]{0,0,1,1,0,0,1,1,1,0,1,1,0,0,1,1,1,1,0});
        ks.add(3);

        arrays.add(new int[]{1,1,1,1,1});
        ks.add(0);

        arrays.add(new int[]{0,0,0,0,0});
        ks.add(2);

        arrays.add(new int[]{0,0,0,0,0});
        ks.add(5);

        arrays.add(new int[]{0});
        ks.add(0);

        arrays.add(new int[]{0});
        ks.add(1);

        arrays.add(new int[]{1});
        ks.add(0);

        arrays.add(new int[]{1,0,1,1,0,0,1,1,1,0,1});
        ks.add(1);

        arrays.add(new int[]{1,0,0,1,0,1,1,0,1,0,0,1});
        ks.add(2);

        while (arrays.size() < 100) {
            int idx = arrays.size();
            int n;

            if (idx < 40) {
                n = 1 + rnd.nextInt(50);
            } else if (idx < 80) {
                n = 100 + rnd.nextInt(900);
            } else {
                n = 5000 + rnd.nextInt(20001);
            }

            int mode = idx % 5;
            int[] a = randomArray(rnd, n, mode);

            int k;
            if (idx % 7 == 0) {
                k = 0;
            } else if (idx % 7 == 1) {
                k = n;
            } else {
                k = rnd.nextInt(n + 1);
            }

            arrays.add(a);
            ks.add(k);
        }

        for (int i = 0; i < 100; i++) {
            writeCase(i, arrays.get(i), ks.get(i));
        }
    }
}
