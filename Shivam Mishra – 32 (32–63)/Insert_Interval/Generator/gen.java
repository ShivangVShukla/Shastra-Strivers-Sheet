
import java.io.*;
import java.util.*;

public class gen {

    static class TestCase {
        int[][] intervals;
        int[] newInterval;

        TestCase(int[][] intervals, int[] newInterval) {
            this.intervals = intervals;
            this.newInterval = newInterval;
        }
    }

    static int[][] solve(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();

        int i = 0;
        int n = intervals.length;
        int start = newInterval[0];
        int end = newInterval[1];

        while (i < n && intervals[i][1] < start) {
            result.add(new int[]{
                intervals[i][0], intervals[i][1]
            });
            i++;
        }

        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }

        result.add(new int[]{start, end});

        while (i < n) {
            result.add(new int[]{
                intervals[i][0], intervals[i][1]
            });
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    static TestCase[] createTestCases() {
        TestCase[] tests = new TestCase[101];

        // Test 1: Example 1
        tests[0] = new TestCase(
            new int[][]{{1, 3}, {6, 9}},
            new int[]{2, 5}
        );

        // Test 2: Example 2
        tests[1] = new TestCase(
            new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}},
            new int[]{4, 8}
        );

        // Test 3: Empty interval list
        tests[2] = new TestCase(
            new int[][]{},
            new int[]{5, 7}
        );

        // Test 4: Insert before all intervals
        tests[3] = new TestCase(
            new int[][]{{5, 7}, {10, 12}},
            new int[]{1, 3}
        );

        // Test 5: Insert after all intervals
        tests[4] = new TestCase(
            new int[][]{{1, 3}, {5, 7}},
            new int[]{10, 12}
        );

        // Test 6: Merge all intervals
        tests[5] = new TestCase(
            new int[][]{{1, 2}, {4, 5}, {7, 8}},
            new int[]{0, 10}
        );

        // Test 7: No overlap, insert in the middle
        tests[6] = new TestCase(
            new int[][]{{1, 2}, {6, 8}},
            new int[]{3, 5}
        );

        // Test 8: Exact match
        tests[7] = new TestCase(
            new int[][]{{2, 5}, {8, 10}},
            new int[]{2, 5}
        );

        // Test 9: New interval inside an existing interval
        tests[8] = new TestCase(
            new int[][]{{1, 10}, {15, 20}},
            new int[]{3, 6}
        );

        // Test 10: Touching interval boundaries
        tests[9] = new TestCase(
            new int[][]{{1, 3}, {6, 9}},
            new int[]{3, 6}
        );

        // Remaining tests: deterministic random cases
        Random random = new Random(20261025L);

        for (int t = 10; t < 101; t++) {
            int n = random.nextInt(16); // 0 to 15 intervals
            int[][] intervals = new int[n][2];

            int current = random.nextInt(6);

            for (int i = 0; i < n; i++) {
                int start = current + 1 + random.nextInt(5);
                int end = start + 1 + random.nextInt(8);

                intervals[i][0] = start;
                intervals[i][1] = end;

                // Keep intervals sorted and non-overlapping.
                current = end;
            }

            int start = random.nextInt(100);
            int end = start + 1 + random.nextInt(20);

            // Occasionally generate intervals covering the
            // beginning or end of the existing list.
            if (n > 0 && t % 5 == 0) {
                start = Math.max(0, intervals[0][0] - 3);
                end = intervals[n - 1][1] + 3;
            } else if (n > 0 && t % 5 == 1) {
                start = intervals[0][0];
                end = intervals[0][1];
            } else if (n > 0 && t % 5 == 2) {
                start = Math.max(0, intervals[n - 1][0] - 1);
                end = intervals[n - 1][1] + 2;
            }

            tests[t] = new TestCase(
                intervals,
                new int[]{start, end}
            );
        }

        return tests;
    }

    static void writeInput(File file, TestCase test)
            throws IOException {
        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(file)))) {

            out.println(test.intervals.length);

            for (int[] interval : test.intervals) {
                out.println(interval[0] + " " + interval[1]);
            }

            out.println(
                test.newInterval[0] + " " + test.newInterval[1]
            );
        }
    }

    static void writeOutput(File file, TestCase test)
            throws IOException {
        int[][] answer = solve(test.intervals, test.newInterval);

        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(file)))) {

            for (int i = 0; i < answer.length; i++) {
                if (i > 0) out.print(" ");

                out.print(
                    "[" + answer[i][0] + "," + answer[i][1] + "]"
                );
            }

            out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";

        if (!new File(baseDir).exists()
                && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "input");
        File outputDir = new File(baseDir, "output");

        if (!inputDir.exists() && !inputDir.mkdirs()) {
            throw new IOException("Could not create input directory");
        }

        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IOException("Could not create output directory");
        }

        TestCase[] tests = createTestCases();

        for (int i = 0; i < tests.length; i++) {
            String fileName = String.format("%03d.txt", i + 1);

            writeInput(new File(inputDir, fileName), tests[i]);
            writeOutput(new File(outputDir, fileName), tests[i]);
        }

        System.out.println(
            "Successfully generated " + tests.length + " test cases."
        );
        System.out.println("Input directory: " + inputDir.getPath());
        System.out.println("Output directory: " + outputDir.getPath());
    }
}
