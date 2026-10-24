import java.io.*;
import java.util.*;

public class gen {
    static int[] solve(int[] p) {
        int n = p.length;
        int[] s = new int[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && p[st.peek()] <= p[i]) st.pop();
            s[i] = st.isEmpty() ? i + 1 : i - st.peek();
            st.push(i);
        }
        return s;
    }

    static void writeCase(File inDir, File outDir, int idx, int[] p) throws Exception {
        String id = String.format("%02d", idx);
        try (PrintWriter w = new PrintWriter(new File(inDir, "input" + id + ".txt"))) {
            w.println(p.length);
            for (int i = 0; i < p.length; i++) {
                if (i > 0) w.print(' ');
                w.print(p[i]);
            }
            w.println();
        }
        int[] s = solve(p);
        try (PrintWriter w = new PrintWriter(new File(outDir, "output" + id + ".txt"))) {
            for (int i = 0; i < s.length; i++) {
                if (i > 0) w.print(' ');
                w.print(s[i]);
            }
            w.println();
        }
    }

    public static void main(String[] args) throws Exception {
        File base = new File("../Testcases");
        File inDir = new File(base, "Input");
        File outDir = new File(base, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random r = new Random(2262026L);
        ArrayList<int[]> cases = new ArrayList<>();

        cases.add(new int[]{100});
        cases.add(new int[]{100, 80, 60, 70, 60, 75, 85});
        cases.add(new int[]{1, 2, 3, 4, 5, 6, 7});
        cases.add(new int[]{7, 6, 5, 4, 3, 2, 1});
        cases.add(new int[]{5, 5, 5, 5, 5, 5});
        cases.add(new int[]{100000, 1, 99999, 2, 99998, 3, 99997});
        cases.add(new int[]{1, 100000, 1, 100000, 1, 100000});
        cases.add(new int[]{10, 20, 15, 25, 10, 30, 20, 40});
        cases.add(new int[]{50, 40, 30, 20, 10, 20, 30, 40, 50});
        cases.add(new int[]{1, 1, 2, 2, 3, 3, 2, 2, 1, 1});

        for (int c = cases.size(); c < 30; c++) {
            int n = 2 + r.nextInt(99);
            int[] p = new int[n];
            for (int i = 0; i < n; i++) p[i] = 1 + r.nextInt(1000);
            cases.add(p);
        }

        int[] sizes = {500, 1000, 2000, 5000, 10000, 20000, 50000, 100000};
        while (cases.size() < 100) {
            int n = sizes[(cases.size() - 30) % sizes.length];
            int type = (cases.size() - 30) % 6;
            int[] p = new int[n];

            if (type == 0) {
                for (int i = 0; i < n; i++) p[i] = i + 1;
            } else if (type == 1) {
                for (int i = 0; i < n; i++) p[i] = n - i;
            } else if (type == 2) {
                Arrays.fill(p, 50000);
            } else if (type == 3) {
                for (int i = 0; i < n; i++) p[i] = (i % 2 == 0) ? 100000 : 1;
            } else if (type == 4) {
                for (int i = 0; i < n; i++) p[i] = 1 + r.nextInt(100000);
            } else {
                for (int i = 0; i < n; i++) {
                    int block = (i / 100) % 2;
                    p[i] = block == 0 ? 100000 - (i % 100) : 1 + (i % 100);
                }
            }
            cases.add(p);
        }

        for (int i = 0; i < 100; i++) writeCase(inDir, outDir, i, cases.get(i));
    }
}
