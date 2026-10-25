import java.io.*;
import java.util.*;

public class gen {
    private static final int TESTCASE_COUNT = 120;

    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";
        if (!new File(baseDir).exists() && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "input");
        File outputDir = new File(baseDir, "output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        Random random = new Random(20261028L);

        for (int tc = 0; tc < TESTCASE_COUNT; tc++) {
            TestCase testCase = createCase(tc, random);
            String index = String.format("%02d", tc);
            File inputFile = new File(inputDir, "input" + index + ".txt");
            File outputFile = new File(outputDir, "output" + index + ".txt");

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(inputFile)))) {
                writer.println(1);
                writer.println(testCase.arr.length + " " + testCase.k);
                for (int i = 0; i < testCase.arr.length; i++) {
                    if (i > 0) writer.print(' ');
                    writer.print(testCase.arr[i]);
                }
                writer.println();
            }

            int[] answer = referenceSolve(testCase.arr, testCase.k);
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)))) {
                for (int i = 0; i < answer.length; i++) {
                    if (i > 0) writer.print(' ');
                    writer.print(answer[i]);
                }
                writer.println();
            }
        }

        System.out.println("Generated " + TESTCASE_COUNT + " input/output testcase pairs successfully.");
    }

    static class TestCase {
        int[] arr;
        int k;
        TestCase(int[] arr, int k) { this.arr = arr; this.k = k; }
    }

    private static TestCase createCase(int id, Random random) {
        if (id == 0) return new TestCase(new int[] {4, 0, -1, 3, 5, 3, 6, 8}, 3);
        if (id == 1) return new TestCase(new int[] {20, 25}, 2);
        if (id == 2) return new TestCase(new int[] {1, 3, -1, -3, 5, 3, 6, 7}, 3);
        if (id == 3) return new TestCase(new int[] {5}, 1);
        if (id == 4) return new TestCase(new int[] {7, 7, 7, 7, 7}, 3);

        int n;
        if (id < 30) n = 1 + random.nextInt(20);
        else if (id < 60) n = 20 + random.nextInt(181);
        else if (id < 90) n = 200 + random.nextInt(801);
        else n = (id % 3 == 0) ? 100_000 : 1_000 + random.nextInt(9_001);

        int mode = id % 7;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            switch (mode) {
                case 0: arr[i] = random.nextInt(201) - 100; break;
                case 1: arr[i] = random.nextInt(20_001) - 10_000; break;
                case 2: arr[i] = i - n / 2; break;
                case 3: arr[i] = n / 2 - i; break;
                case 4: arr[i] = (id % 2 == 0) ? 9 : -9; break;
                case 5: arr[i] = (i % 2 == 0) ? 10_000 : -10_000; break;
                default: arr[i] = random.nextInt(11) - 5; break;
            }
        }

        int k;
        switch (id % 5) {
            case 0: k = 1; break;
            case 1: k = n; break;
            case 2: k = Math.max(1, n / 2); break;
            case 3: k = Math.max(1, n - 1); break;
            default: k = 1 + random.nextInt(n); break;
        }
        return new TestCase(arr, k);
    }

    // Independent O(N*K) reference implementation.
    private static int[] referenceSolve(int[] arr, int k) {
        int[] result = new int[arr.length - k + 1];
        for (int left = 0; left + k <= arr.length; left++) {
            int max = arr[left];
            for (int j = left + 1; j < left + k; j++) {
                max = Math.max(max, arr[j]);
            }
            result[left] = max;
        }
        return result;
    }
}
