import java.io.*;
import java.util.*;

public class gen {
    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";
        if (!new File(baseDir).exists() && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "input");
        File outputDir = new File(baseDir, "output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        // Each test file holds exactly one test case (T = 1)
        List<int[]> testCases = new ArrayList<>();
        addExamples(testCases);       // 2 files
        addHandcrafted(testCases);    // 18 files
        addSmallRandom(testCases);    // 45 files
        addMediumRandom(testCases);   // 33 files
        addLarge(testCases);          // 12 files

        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%03d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            int[] a = testCases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inWriter.println(1);
            inWriter.println(a.length);
            inWriter.println(join(a));
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outWriter.println(solve(a));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully! Total: " + testCases.size());
    }

    static String join(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(a[i]);
        }
        return sb.toString();
    }

    // Reference solution: dynamic programming over subarrays ending at each index
    // (different formulation from the contribution counting in Solution.java).
    //   f[i] = sum of minimums of all subarrays ending at i
    //   p    = index of the previous element STRICTLY smaller than a[i] (or -1)
    //   f[i] = (p >= 0 ? f[p] : 0) + a[i] * (i - p)
    // because every subarray starting after p has minimum a[i], and every one
    // starting at or before p has the same minimum as the subarray ending at p.
    // The previous smaller element is found by pointer jumping.
    static long solve(int[] a) {
        int n = a.length;
        int[] prevSmaller = new int[n];
        long[] f = new long[n];
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int p = i - 1;
            while (p >= 0 && a[p] >= a[i]) {
                p = prevSmaller[p];
            }
            prevSmaller[i] = p;

            f[i] = ((p >= 0 ? f[p] : 0) + (long) a[i] * (i - p)) % MOD;
            ans = (ans + f[i]) % MOD;
        }

        if (n <= 3000) {
            check(a, ans);
        }
        return ans;
    }

    // O(n^2) brute-force cross-check, used for small arrays only
    static void check(int[] a, long ans) {
        long total = 0;
        for (int i = 0; i < a.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = i; j < a.length; j++) {
                min = Math.min(min, a[j]);
                total += min;
            }
        }
        if (total % MOD != ans) {
            throw new IllegalStateException("Reference mismatch");
        }
    }

    static int[] arr(int... v) {
        return v;
    }

    // Examples from the problem statement
    static void addExamples(List<int[]> out) {
        out.add(arr(3, 1, 2, 5));
        out.add(arr(2, 3, 1));
    }

    // Edge cases: single element, ties, sorted arrays, extreme values, patterns
    static void addHandcrafted(List<int[]> out) {
        out.add(arr(11, 81, 94, 43, 3));
        out.add(arr(1));
        out.add(arr(1000000));
        out.add(arr(5, 5));
        out.add(arr(1, 1, 1, 1));
        out.add(arr(1, 2, 3, 4, 5));
        out.add(arr(5, 4, 3, 2, 1));
        out.add(arr(2, 2, 1, 2, 2));
        out.add(arr(1000000, 1000000, 1000000, 1000000, 1000000, 1000000));
        out.add(arr(1, 1000000, 1));
        out.add(arr(1000000, 1, 1000000));
        out.add(arr(4, 1, 4, 1, 4));
        out.add(arr(2, 1));
        out.add(arr(1, 2));
        out.add(arr(7, 7, 7));
        out.add(arr(10, 20, 10, 20, 10));
        out.add(arr(9, 1, 9, 1, 9, 1));
        out.add(arr(6, 5, 6, 5));
    }

    // 45 small arrays (n = 2..20) with different value ranges
    static void addSmallRandom(List<int[]> out) {
        Random rand = new Random(2024);
        int[] ranges = {3, 10, 1000000};
        for (int k = 0; k < 45; k++) {
            int n = 2 + (k % 19);
            out.add(randomArray(n, ranges[k % 3], rand));
        }
    }

    // 33 medium arrays (n = 50..3000) with different shapes
    static void addMediumRandom(List<int[]> out) {
        Random rand = new Random(99);
        int[] sizes = {50, 100, 250, 500, 1000, 2000, 3000};
        int[] ranges = {2, 10, 1000, 1000000};
        for (int k = 0; k < 33; k++) {
            int n = sizes[k % sizes.length];
            int range = ranges[k % ranges.length];
            int shape = k % 6;
            int[] a;
            if (shape == 0) {
                a = randomArray(n, range, rand);                 // random
            } else if (shape == 1) {
                a = new int[n];                                  // non-decreasing
                for (int i = 0; i < n; i++) {
                    a[i] = 1 + (int) ((long) i * (range - 1) / Math.max(1, n - 1));
                }
            } else if (shape == 2) {
                a = new int[n];                                  // non-increasing
                for (int i = 0; i < n; i++) {
                    a[i] = range - (int) ((long) i * (range - 1) / Math.max(1, n - 1));
                }
            } else if (shape == 3) {
                a = new int[n];                                  // sawtooth
                int period = 2 + rand.nextInt(20);
                for (int i = 0; i < n; i++) {
                    a[i] = 1 + (i % period) % range;
                }
            } else if (shape == 4) {
                a = new int[n];                                  // all equal
                Arrays.fill(a, 1 + rand.nextInt(range));
            } else {
                a = new int[n];                                  // alternating low / high
                for (int i = 0; i < n; i++) {
                    a[i] = i % 2 == 0 ? 1 : range;
                }
            }
            out.add(a);
        }
    }

    // 12 large arrays (n up to 10^5)
    static void addLarge(List<int[]> out) {
        Random rand = new Random(7);
        int n = 100000;

        // Random over the full range
        out.add(randomArray(n, 1000000, rand));

        // Strictly increasing
        int[] inc = new int[n];
        for (int i = 0; i < n; i++) {
            inc[i] = i + 1;
        }
        out.add(inc);

        // Strictly decreasing
        int[] dec = new int[n];
        for (int i = 0; i < n; i++) {
            dec[i] = n - i;
        }
        out.add(dec);

        // All equal to the maximum value (largest possible sum, needs the modulo)
        int[] eq = new int[n];
        Arrays.fill(eq, 1000000);
        out.add(eq);

        // All ones
        int[] ones = new int[n];
        Arrays.fill(ones, 1);
        out.add(ones);

        // Tiny value range (lots of ties)
        out.add(randomArray(n, 3, rand));

        // Sawtooth: 1..1000 repeated
        int[] saw = new int[n];
        for (int i = 0; i < n; i++) {
            saw[i] = 1 + i % 1000;
        }
        out.add(saw);

        // Mountain: increasing then decreasing
        int[] mountain = new int[n];
        for (int i = 0; i < n; i++) {
            mountain[i] = 1 + (i < n / 2 ? i : n - 1 - i);
        }
        out.add(mountain);

        // Valley: decreasing then increasing
        int[] valley = new int[n];
        for (int i = 0; i < n; i++) {
            valley[i] = 1 + (i < n / 2 ? n / 2 - i : i - n / 2);
        }
        out.add(valley);

        // Alternating 1 and 10^6
        int[] alt = new int[n];
        for (int i = 0; i < n; i++) {
            alt[i] = i % 2 == 0 ? 1 : 1000000;
        }
        out.add(alt);

        // Another full-range random array
        out.add(randomArray(n, 1000000, rand));

        // Half-size random array
        out.add(randomArray(50000, 1000000, rand));
    }

    // n random values in [1, maxValue]
    static int[] randomArray(int n, int maxValue, Random rand) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = 1 + rand.nextInt(maxValue);
        }
        return a;
    }
}
