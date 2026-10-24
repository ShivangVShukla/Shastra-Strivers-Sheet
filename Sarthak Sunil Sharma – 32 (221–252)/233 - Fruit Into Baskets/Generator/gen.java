import java.io.*;
import java.util.*;

public class gen {
    static int solve(int[] a) {
        Map<Integer, Integer> freq = new HashMap<>();
        int left = 0, ans = 0;

        for (int right = 0; right < a.length; right++) {
            freq.put(a[right], freq.getOrDefault(a[right], 0) + 1);

            while (freq.size() > 2) {
                int x = a[left++];
                freq.put(x, freq.get(x) - 1);
                if (freq.get(x) == 0) freq.remove(x);
            }

            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }

    static void writeCase(int idx, int[] a) throws Exception {
        String in = String.format("../Testcases/Input/input%02d.txt", idx);
        String out = String.format("../Testcases/Output/output%02d.txt", idx);

        try (PrintWriter pw = new PrintWriter(new FileWriter(in))) {
            pw.println(a.length);
            for (int i = 0; i < a.length; i++) {
                if (i > 0) pw.print(" ");
                pw.print(a[i]);
            }
            pw.println();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println(solve(a));
        }
    }

    static int[] randomArray(Random rnd, int n, int mode) {
        int[] a = new int[n];

        if (mode == 0) {
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(5);
            }
        } else if (mode == 1) {
            int x = rnd.nextInt(100000);
            int y = rnd.nextInt(100000);
            while (y == x) y = rnd.nextInt(100000);

            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextBoolean() ? x : y;
            }
        } else if (mode == 2) {
            for (int i = 0; i < n; i++) {
                a[i] = i;
            }
        } else if (mode == 3) {
            for (int i = 0; i < n; i++) {
                if (i < n / 3) a[i] = 1;
                else if (i < 2 * n / 3) a[i] = 2;
                else a[i] = 3;
            }
        } else {
            int[] values = {1, 2, 3, 4, 5, 6, 7};
            for (int i = 0; i < n; i++) {
                a[i] = values[rnd.nextInt(values.length)];
            }
        }

        return a;
    }

    public static void main(String[] args) throws Exception {
        Random rnd = new Random(2332026L);
        List<int[]> cases = new ArrayList<>();

        cases.add(new int[]{1, 2, 1});
        cases.add(new int[]{0, 1, 2, 2});
        cases.add(new int[]{1, 2, 3, 2, 2});
        cases.add(new int[]{5, 5, 5, 5});
        cases.add(new int[]{1, 2, 1, 2, 1});
        cases.add(new int[]{1, 2, 3, 4, 5});
        cases.add(new int[]{1});
        cases.add(new int[]{1, 2});
        cases.add(new int[]{1, 2, 3, 3, 3, 3});
        cases.add(new int[]{1, 1, 2, 2, 3, 3, 2, 2});

        while (cases.size() < 100) {
            int idx = cases.size();
            int n;

            if (idx < 40) {
                n = 1 + rnd.nextInt(60);
            } else if (idx < 80) {
                n = 100 + rnd.nextInt(900);
            } else {
                n = 5000 + rnd.nextInt(15001);
            }

            cases.add(randomArray(rnd, n, idx % 5));
        }

        for (int i = 0; i < 100; i++) {
            writeCase(i, cases.get(i));
        }
    }
}
