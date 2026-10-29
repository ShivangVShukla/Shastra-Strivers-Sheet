
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static final int TOTAL = 101;
    static final Random random = new Random(20261026L);

    static class Job {
        int start;
        int end;
        int profit;

        Job(int start, int end, int profit) {
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
    }

    static class TestCase {
        List<Job> jobs;

        TestCase(List<Job> jobs) {
            this.jobs = jobs;
        }
    }

    // Calculate maximum profit using DP + binary search.
    static long maxProfit(List<Job> inputJobs) {
        List<Job> jobs = new ArrayList<>(inputJobs);

        jobs.sort((a, b) -> {
            if (a.start != b.start) {
                return Integer.compare(a.start, b.start);
            }
            return Integer.compare(a.end, b.end);
        });

        int n = jobs.size();
        int[] starts = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = jobs.get(i).start;
        }

        long[] dp = new long[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            Job job = jobs.get(i);

            // Find first job starting at or after this job ends.
            int next = lowerBound(starts, job.end, i + 1);

            long take = job.profit + dp[next];
            long skip = dp[i + 1];

            dp[i] = Math.max(take, skip);
        }

        return dp[0];
    }

    static int lowerBound(int[] starts, int target, int low) {
        int high = starts.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (starts[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    static Job job(int start, int end, int profit) {
        return new Job(start, end, profit);
    }

    static TestCase createCase(Job... jobs) {
        return new TestCase(new ArrayList<>(Arrays.asList(jobs)));
    }

    static TestCase generate(int index) {
        // Test 1: Example 1.
        if (index == 1) {
            return createCase(
                job(1, 3, 50),
                job(2, 4, 10),
                job(3, 5, 40),
                job(3, 6, 70)
            );
        }

        // Test 2: Example 2.
        if (index == 2) {
            return createCase(
                job(1, 3, 20),
                job(2, 5, 20),
                job(3, 10, 100),
                job(4, 6, 70),
                job(6, 9, 60)
            );
        }

        // Test 3: All jobs overlap.
        if (index == 3) {
            return createCase(
                job(1, 10, 20),
                job(2, 9, 100),
                job(3, 8, 50),
                job(4, 7, 80)
            );
        }

        // Test 4: All jobs can be scheduled consecutively.
        if (index == 4) {
            return createCase(
                job(1, 2, 10),
                job(2, 3, 20),
                job(3, 4, 30),
                job(4, 5, 40)
            );
        }

        // Test 5: Multiple jobs with the same start time.
        if (index == 5) {
            return createCase(
                job(1, 4, 30),
                job(1, 3, 50),
                job(1, 2, 20),
                job(2, 5, 60),
                job(3, 6, 70)
            );
        }

        // Test 6: Only one job.
        if (index == 6) {
            return createCase(job(5, 10, 100));
        }

        // Test 7: Equal profits.
        if (index == 7) {
            return createCase(
                job(1, 3, 10),
                job(2, 4, 10),
                job(3, 5, 10),
                job(4, 6, 10)
            );
        }

        // Test 8: Large time values.
        if (index == 8) {
            return createCase(
                job(100000000, 200000000, 100),
                job(200000000, 300000000, 200),
                job(150000000, 250000000, 500),
                job(300000000, 900000000, 300)
            );
        }

        // Test 9: Nested intervals.
        if (index == 9) {
            return createCase(
                job(1, 20, 100),
                job(2, 15, 80),
                job(3, 10, 70),
                job(4, 8, 60),
                job(5, 6, 50)
            );
        }

        // Test 10: Two jobs touching at endpoints.
        if (index == 10) {
            return createCase(
                job(1, 5, 30),
                job(5, 8, 40),
                job(8, 10, 50),
                job(2, 6, 100)
            );
        }

        // Test 101: Large performance test.
        if (index == 101) {
            List<Job> jobs = new ArrayList<>();

            for (int i = 0; i < 50000; i++) {
                int start = i * 3 + 1;
                int end = start + 2;
                int profit = 1 + (i % 10000);

                jobs.add(job(start, end, profit));
            }

            return new TestCase(jobs);
        }

        // Remaining cases: randomized valid jobs.
        int n;

        if (index % 10 == 0) {
            n = 500;
        } else {
            n = 1 + random.nextInt(50);
        }

        List<Job> jobs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int start = 1 + random.nextInt(200);
            int duration = 1 + random.nextInt(30);
            int end = start + duration;
            int profit = 1 + random.nextInt(10000);

            jobs.add(job(start, end, profit));
        }

        return new TestCase(jobs);
    }

    static void writeCase(
        Path inputDir,
        Path outputDir,
        int index,
        TestCase tc
    ) throws IOException {

        String filename = String.format(
            Locale.ROOT, "testcase_%03d.txt", index
        );

        Path inputFile = inputDir.resolve(filename);
        Path outputFile = outputDir.resolve(filename);

        // Input: N followed by N lines of start, end, profit.
        try (BufferedWriter writer =
                 Files.newBufferedWriter(inputFile)) {

            writer.write(String.valueOf(tc.jobs.size()));
            writer.newLine();

            for (Job job : tc.jobs) {
                writer.write(
                    job.start + " " +
                    job.end + " " +
                    job.profit
                );
                writer.newLine();
            }
        }

        long answer = maxProfit(tc.jobs);

        try (BufferedWriter writer =
                 Files.newBufferedWriter(outputFile)) {

            writer.write(String.valueOf(answer));
            writer.newLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("inp");
        Path outputDir = Paths.get("out");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int i = 1; i <= TOTAL; i++) {
            TestCase tc = generate(i);
            writeCase(inputDir, outputDir, i, tc);
        }

        System.out.println(
            "Successfully generated " + TOTAL +
            " test cases in inp/ and out/."
        );
    }
}
