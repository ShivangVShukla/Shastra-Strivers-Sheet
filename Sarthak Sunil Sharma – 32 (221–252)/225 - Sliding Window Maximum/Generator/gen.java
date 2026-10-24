import java.io.*;
import java.util.*;

public class gen {
    static class TestCase {
        int[] nums;
        int k;
        TestCase(int[] nums, int k) {
            this.nums = nums;
            this.k = k;
        }
    }

    static int[] solve(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        int p = 0;

        for (int i = 0; i < n; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1) ans[p++] = nums[dq.peekFirst()];
        }
        return ans;
    }

    static void writeCase(File dirIn, File dirOut, int idx, TestCase tc) throws Exception {
        String id = String.format("%02d", idx);
        File in = new File(dirIn, "input" + id + ".txt");
        File out = new File(dirOut, "output" + id + ".txt");

        try (PrintWriter pw = new PrintWriter(in)) {
            pw.println(tc.nums.length + " " + tc.k);
            for (int i = 0; i < tc.nums.length; i++) {
                if (i > 0) pw.print(' ');
                pw.print(tc.nums[i]);
            }
            pw.println();
        }

        int[] ans = solve(tc.nums, tc.k);
        try (PrintWriter pw = new PrintWriter(out)) {
            for (int i = 0; i < ans.length; i++) {
                if (i > 0) pw.print(' ');
                pw.print(ans[i]);
            }
            pw.println();
        }
    }

    public static void main(String[] args) throws Exception {
        File base = new File("../Testcases");
        File inDir = new File(base, "Input");
        File outDir = new File(base, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rnd = new Random(2252026L);
        ArrayList<TestCase> cases = new ArrayList<>();

        cases.add(new TestCase(new int[]{1}, 1));
        cases.add(new TestCase(new int[]{1, 2, 3, 4, 5}, 1));
        cases.add(new TestCase(new int[]{5, 4, 3, 2, 1}, 5));
        cases.add(new TestCase(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3));
        cases.add(new TestCase(new int[]{7, 7, 7, 7, 7}, 3));
        cases.add(new TestCase(new int[]{-5, -2, -8, -1, -3}, 2));
        cases.add(new TestCase(new int[]{10000, -10000, 9999, -9999}, 2));
        cases.add(new TestCase(new int[]{0, 0, 0, 0, 0, 0}, 4));

        int[] a9 = new int[20];
        for (int i = 0; i < a9.length; i++) a9[i] = i - 10;
        cases.add(new TestCase(a9, 7));

        int[] a10 = new int[20];
        for (int i = 0; i < a10.length; i++) a10[i] = 10 - i;
        cases.add(new TestCase(a10, 8));

        int[] a11 = new int[30];
        for (int i = 0; i < a11.length; i++) a11[i] = (i % 3 == 0) ? 100 : (i % 3 == 1 ? 100 : -100);
        cases.add(new TestCase(a11, 6));

        int[] a12 = new int[50];
        Arrays.fill(a12, -1);
        a12[17] = 10000;
        a12[41] = 9999;
        cases.add(new TestCase(a12, 10));

        int[] a13 = new int[100];
        for (int i = 0; i < a13.length; i++) a13[i] = (i * 37) % 201 - 100;
        cases.add(new TestCase(a13, 50));

        int[] a14 = new int[100];
        for (int i = 0; i < a14.length; i++) a14[i] = (i % 2 == 0) ? 10000 : -10000;
        cases.add(new TestCase(a14, 99));

        for (int c = cases.size(); c < 30; c++) {
            int n = 2 + rnd.nextInt(99);
            int k = 1 + rnd.nextInt(n);
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = rnd.nextInt(2001) - 1000;
            cases.add(new TestCase(nums, k));
        }

        int[] sizes = {500, 1000, 2000, 5000, 10000, 20000, 50000, 100000};
        while (cases.size() < 100) {
            int n = sizes[(cases.size() - 30) % sizes.length];
            int k;
            int type = (cases.size() - 30) % 6;
            if (type == 0) k = 1;
            else if (type == 1) k = n;
            else if (type == 2) k = Math.max(1, n / 2);
            else if (type == 3) k = Math.max(1, n / 3);
            else k = 1 + rnd.nextInt(n);

            int[] nums = new int[n];
            if (type == 0) {
                for (int i = 0; i < n; i++) nums[i] = i - n / 2;
            } else if (type == 1) {
                for (int i = 0; i < n; i++) nums[i] = n / 2 - i;
            } else if (type == 2) {
                Arrays.fill(nums, 1234);
            } else if (type == 3) {
                for (int i = 0; i < n; i++) nums[i] = (i % 2 == 0) ? 10000 : -10000;
            } else {
                for (int i = 0; i < n; i++) nums[i] = rnd.nextInt(20001) - 10000;
            }
            cases.add(new TestCase(nums, k));
        }

        for (int i = 0; i < 100; i++) writeCase(inDir, outDir, i, cases.get(i));
    }
}
