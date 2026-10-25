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

        List<TestCase> testCases = new ArrayList<>();
        testCases.add(generateTC0());
        testCases.add(generateTC1());
        testCases.add(generateTC2());
        testCases.add(generateTC3());

        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            TestCase tc = testCases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inWriter.println(tc.arrays.size());
            for (long[] a : tc.arrays) {
                inWriter.println(a.length);
                inWriter.println(join(a));
            }
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (long[] a : tc.arrays) {
                outWriter.println(join(solve(a)));
            }
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static class TestCase {
        List<long[]> arrays;

        TestCase(List<long[]> arrays) {
            this.arrays = arrays;
        }
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

    // Reference solution: pointer jumping (different technique from the explicit
    // stack in Solution.java). nxt[i] is the index of the next strictly greater
    // element of i (or n). Scanning from the right, start at i + 1 and jump along
    // nxt[] while the candidate is not greater than arr[i]; everything skipped is
    // <= the candidate, hence <= arr[i].
    static long[] solve(long[] a) {
        int n = a.length;
        int[] nxt = new int[n];
        long[] res = new long[n];
        for (int i = n - 1; i >= 0; i--) {
            int j = i + 1;
            while (j < n && a[j] <= a[i]) {
                j = nxt[j];
            }
            nxt[i] = j;
            res[i] = j < n ? a[j] : -1;
        }
        if (n <= 3000) {
            check(a, res);
        }
        return res;
    }

    // O(n^2) brute-force cross-check, used for small arrays only
    static void check(long[] a, long[] res) {
        for (int i = 0; i < a.length; i++) {
            long expected = -1;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] > a[i]) {
                    expected = a[j];
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

    // Examples from the problem statement, plus the "Now Your Turn" input
    static TestCase generateTC0() {
        List<long[]> arrays = new ArrayList<>();
        arrays.add(arr(1, 3, 2, 4));
        arrays.add(arr(6, 8, 0, 1, 3));
        arrays.add(arr(1, 3, 2));
        return new TestCase(arrays);
    }

    // Edge cases: single elements, equal elements (strictly greater needed),
    // sorted arrays, boundary values, duplicates
    static TestCase generateTC1() {
        List<long[]> arrays = new ArrayList<>();
        arrays.add(arr(0));
        arrays.add(arr(1000000000L));
        arrays.add(arr(5, 5, 5, 5));
        arrays.add(arr(1, 2, 3, 4, 5));
        arrays.add(arr(5, 4, 3, 2, 1));
        arrays.add(arr(2, 1, 2, 1, 2));
        arrays.add(arr(0, 0, 1, 0, 0, 2));
        arrays.add(arr(3, 3, 4, 4, 2, 2, 5));
        arrays.add(arr(1000000000L, 0, 1000000000L, 0));
        arrays.add(arr(7, 1, 5, 3, 6, 4));
        return new TestCase(arrays);
    }

    // Medium sizes with different shapes
    static TestCase generateTC2() {
        List<long[]> arrays = new ArrayList<>();
        Random rand = new Random(2024);

        // Many duplicates (small value range)
        arrays.add(randomArray(50, 5, rand));

        // Full value range
        arrays.add(randomArray(200, 1000000000L, rand));

        // Very few distinct values
        arrays.add(randomArray(1000, 3, rand));

        // Random permutation of 1..1000
        long[] perm = new long[1000];
        for (int i = 0; i < perm.length; i++) {
            perm[i] = i + 1;
        }
        for (int i = perm.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            long tmp = perm[i];
            perm[i] = perm[j];
            perm[j] = tmp;
        }
        arrays.add(perm);

        // Sawtooth: 0..99 repeated
        long[] saw = new long[1000];
        for (int i = 0; i < saw.length; i++) {
            saw[i] = i % 100;
        }
        arrays.add(saw);

        // Bitonic: increasing then decreasing
        long[] bit = new long[500];
        for (int i = 0; i < bit.length; i++) {
            bit[i] = i < 250 ? i * 4L : (499 - i) * 4L;
        }
        arrays.add(bit);

        return new TestCase(arrays);
    }

    // Maximum size (n = 10^5) cases
    static TestCase generateTC3() {
        List<long[]> arrays = new ArrayList<>();
        Random rand = new Random(7);
        int n = 100000;

        // Random values over the full range
        arrays.add(randomArray(n, 1000000000L, rand));

        // Strictly increasing
        long[] inc = new long[n];
        for (int i = 0; i < n; i++) {
            inc[i] = i;
        }
        arrays.add(inc);

        // Strictly decreasing
        long[] dec = new long[n];
        for (int i = 0; i < n; i++) {
            dec[i] = n - i;
        }
        arrays.add(dec);

        // Random with a tiny value range (lots of ties)
        arrays.add(randomArray(n, 10, rand));

        // All equal, maximum value
        long[] eq = new long[n];
        Arrays.fill(eq, 1000000000L);
        arrays.add(eq);

        return new TestCase(arrays);
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
