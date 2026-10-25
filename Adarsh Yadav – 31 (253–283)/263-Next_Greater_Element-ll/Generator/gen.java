import java.io.*;
import java.util.*;

public class gen {
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
        List<long[]> testCases = new ArrayList<>();
        addExamples(testCases);       // 2 files
        addHandcrafted(testCases);    // 18 files
        addSmallRandom(testCases);    // 45 files
        addMediumRandom(testCases);   // 33 files
        addLarge(testCases);          // 12 files

        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%03d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            long[] a = testCases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inWriter.println(1);
            inWriter.println(a.length);
            inWriter.println(join(a));
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outWriter.println(join(solve(a)));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully! Total: " + testCases.size());
    }

    static String join(long[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(a[i]);
        }
        return sb.toString();
    }

    // Reference solution: pointer jumping on the array written twice (different
    // technique from the explicit stack in Solution.java). nxt[j] is the index of
    // the next strictly greater element of position j in the doubled array
    // (or 2n). For j < n this is exactly the circular next greater element,
    // because the doubled array lets the scan wrap around.
    static long[] solve(long[] a) {
        int n = a.length;
        int m = 2 * n;
        long[] d = new long[m];
        for (int i = 0; i < m; i++) {
            d[i] = a[i % n];
        }

        int[] nxt = new int[m];
        for (int i = m - 1; i >= 0; i--) {
            int j = i + 1;
            while (j < m && d[j] <= d[i]) {
                j = nxt[j];
            }
            nxt[i] = j;
        }

        long[] res = new long[n];
        for (int i = 0; i < n; i++) {
            res[i] = nxt[i] < m ? d[nxt[i]] : -1;
        }

        if (n <= 3000) {
            check(a, res);
        }
        return res;
    }

    // O(n^2) brute-force cross-check, used for small arrays only: walk clockwise
    // through the other n - 1 positions
    static void check(long[] a, long[] res) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            long expected = -1;
            for (int k = 1; k < n; k++) {
                long v = a[(i + k) % n];
                if (v > a[i]) {
                    expected = v;
                    break;
                }
            }
            if (expected != res[i]) {
                throw new IllegalStateException("Reference mismatch at index " + i);
            }
        }
    }

    static long[] arr(long... v) {
        return v;
    }

    // Examples from the problem statement
    static void addExamples(List<long[]> out) {
        out.add(arr(3, 10, 4, 2, 1, 2, 6, 1, 7, 2, 9));
        out.add(arr(5, 7, 1, 7, 6, 0));
    }

    // Edge cases: single elements, equal elements (strictly greater needed),
    // sorted arrays, wrap-around, repeated maximum, boundary values
    static void addHandcrafted(List<long[]> out) {
        out.add(arr(0));
        out.add(arr(1000000000L));
        out.add(arr(1, 2));
        out.add(arr(2, 1));
        out.add(arr(5, 5));
        out.add(arr(4, 4, 4, 4, 4));
        out.add(arr(1, 2, 3, 4, 5));
        out.add(arr(5, 4, 3, 2, 1));
        out.add(arr(1, 2, 1));
        out.add(arr(2, 1, 2));
        out.add(arr(3, 1, 2));
        out.add(arr(0, 0, 0, 1));
        out.add(arr(1, 0, 0, 0));
        out.add(arr(9, 1, 9, 1, 9));
        out.add(arr(1000000000L, 0, 1000000000L));
        out.add(arr(2, 3, 2, 3, 2, 3));
        out.add(arr(1, 3, 2, 4, 2, 5, 3));
        out.add(arr(10, 9, 8, 1, 2, 3));
    }

    // 45 small arrays (n = 2..20) with different value ranges
    static void addSmallRandom(List<long[]> out) {
        Random rand = new Random(2024);
        long[] ranges = {3, 10, 1000000000L};
        for (int k = 0; k < 45; k++) {
            int n = 2 + (k % 19);
            long range = ranges[k % 3];
            out.add(randomArray(n, range, rand));
        }
    }

    // 33 medium arrays (n = 50..3000) with different shapes
    static void addMediumRandom(List<long[]> out) {
        Random rand = new Random(99);
        int[] sizes = {50, 100, 250, 500, 1000, 2000, 3000};
        long[] ranges = {2, 10, 100, 1000000000L};
        for (int k = 0; k < 33; k++) {
            int n = sizes[k % sizes.length];
            long range = ranges[k % ranges.length];
            int shape = k % 4;
            long[] a;
            if (shape == 0) {
                a = randomArray(n, range, rand);                // random
            } else if (shape == 1) {
                a = rotate(increasing(n), rand.nextInt(n));     // rotated increasing
            } else if (shape == 2) {
                a = rotate(decreasing(n), rand.nextInt(n));     // rotated decreasing
            } else {
                a = randomArray(n, 5, rand);                    // many ties
            }
            out.add(a);
        }
    }

    // 12 large arrays (up to n = 10^5)
    static void addLarge(List<long[]> out) {
        Random rand = new Random(7);
        int n = 100000;

        // Random values over the full range
        out.add(randomArray(n, 1000000000L, rand));

        // Strictly increasing
        out.add(increasing(n));

        // Strictly decreasing
        out.add(decreasing(n));

        // All equal, maximum value
        long[] eq = new long[n];
        Arrays.fill(eq, 1000000000L);
        out.add(eq);

        // Random with a tiny value range (lots of ties)
        out.add(randomArray(n, 10, rand));

        // Sawtooth: 0..999 repeated
        long[] saw = new long[n];
        for (int i = 0; i < n; i++) {
            saw[i] = i % 1000;
        }
        out.add(saw);

        // Mountain: increasing then decreasing
        long[] mountain = new long[n];
        for (int i = 0; i < n; i++) {
            mountain[i] = i < n / 2 ? i : n - 1 - i;
        }
        out.add(mountain);

        // Valley: decreasing then increasing
        long[] valley = new long[n];
        for (int i = 0; i < n; i++) {
            valley[i] = i < n / 2 ? n - i : i;
        }
        out.add(valley);

        // Unique maximum at the last index, everything else smaller
        long[] maxLast = randomArray(n, 999999999L, rand);
        maxLast[n - 1] = 1000000000L;
        out.add(maxLast);

        // Unique maximum at the first index, everything else smaller
        long[] maxFirst = randomArray(n, 999999999L, rand);
        maxFirst[0] = 1000000000L;
        out.add(maxFirst);

        // Increasing array rotated by a large offset (heavy wrap-around)
        out.add(rotate(increasing(n), 37777));

        // Half-size random array
        out.add(randomArray(50000, 1000000000L, rand));
    }

    static long[] increasing(int n) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        return a;
    }

    static long[] decreasing(int n) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = n - i;
        }
        return a;
    }

    // Left rotation by k positions
    static long[] rotate(long[] a, int k) {
        int n = a.length;
        long[] r = new long[n];
        for (int i = 0; i < n; i++) {
            r[i] = a[(i + k) % n];
        }
        return r;
    }

    // n random values in [0, maxValue]
    static long[] randomArray(int n, long maxValue, Random rand) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = (long) (rand.nextDouble() * (maxValue + 1));
            if (a[i] > maxValue) {
                a[i] = maxValue;
            }
        }
        return a;
    }
}
