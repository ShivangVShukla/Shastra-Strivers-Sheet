import java.io.*;
import java.util.*;

public class gen {
    static final String BASE = "Testcases";

    public static void main(String[] args) throws Exception {
        File inputDir = new File(BASE, "Input");
        File outputDir = new File(BASE, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[]> cases = new ArrayList<>();
        cases.add(new int[]{1});
        cases.add(new int[]{5});
        cases.add(new int[]{1, 2, 3});
        cases.add(new int[]{3, 2, 1});
        cases.add(new int[]{2, 2, 2, 2});
        cases.add(new int[]{-1, -2, -3, -4});
        cases.add(new int[]{-5, -1, 0, 3, 2});
        cases.add(new int[]{1, -1, 1, -1, 1});
        cases.add(new int[]{1000000000, -1000000000});
        cases.add(new int[]{0, 0, -1, 1, 0});

        Random random = new Random(2212026L);

        // Small exhaustive-style random cases: useful for catching duplicate/boundary bugs.
        while (cases.size() < 40) {
            int n = 2 + random.nextInt(19);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(21) - 10;
            }
            cases.add(a);
        }

        // Medium random cases.
        while (cases.size() < 70) {
            int n = 20 + random.nextInt(181);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(2000001) - 1000000;
            }
            cases.add(a);
        }

        // Large / boundary cases.
        while (cases.size() < 100) {
            int n = 700 + random.nextInt(301);
            int[] a = new int[n];
            int type = cases.size() % 5;
            for (int i = 0; i < n; i++) {
                if (type == 0) a[i] = 1000000000;
                else if (type == 1) a[i] = -1000000000;
                else if (type == 2) a[i] = (i % 2 == 0) ? 1000000000 : -1000000000;
                else if (type == 3) a[i] = (i % 7) - 3;
                else a[i] = random.nextInt(2000000001) - 1000000000;
            }
            cases.add(a);
        }

        for (int i = 0; i < 100; i++) {
            String id = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + id + ".txt");
            File outFile = new File(outputDir, "output" + id + ".txt");
            int[] a = cases.get(i);

            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(inFile)))) {
                out.println(a.length);
                for (int j = 0; j < a.length; j++) {
                    if (j > 0) out.print(' ');
                    out.print(a[j]);
                }
                out.println();
            }

            long answer = solve(a);
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                out.println(answer);
            }
        }

        System.out.println("Generated 100 test cases successfully.");
    }

    static long solve(int[] nums) {
        int n = nums.length;
        long answer = 0;
        int[] stack = new int[n];
        int top;

        top = -1;
        for (int i = 0; i <= n; i++) {
            while (top >= 0 && (i == n || nums[stack[top]] <= nums[i])) {
                int mid = stack[top--];
                int left = top >= 0 ? stack[top] : -1;
                answer += (long) nums[mid] * (mid - left) * (i - mid);
            }
            if (i < n) stack[++top] = i;
        }

        top = -1;
        for (int i = 0; i <= n; i++) {
            while (top >= 0 && (i == n || nums[stack[top]] >= nums[i])) {
                int mid = stack[top--];
                int left = top >= 0 ? stack[top] : -1;
                answer -= (long) nums[mid] * (mid - left) * (i - mid);
            }
            if (i < n) stack[++top] = i;
        }
        return answer;
    }
}
