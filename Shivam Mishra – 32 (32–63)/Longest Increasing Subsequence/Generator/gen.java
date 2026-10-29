import java.io.*;
import java.util.*;

public class gen {

    static class TestCase {
        int[] nums;

        TestCase(int[] nums) {
            this.nums = nums;
        }
    }

    // Efficient reference solver: O(n log n)
    static int solve(int[] nums) {
        int[] tails = new int[nums.length];
        int length = 0;

        for (int x : nums) {
            int left = 0;
            int right = length;

            // Find the first position with tails[pos] >= x.
            while (left < right) {
                int mid = left + (right - left) / 2;

                if (tails[mid] >= x) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            tails[left] = x;

            if (left == length) {
                length++;
            }
        }

        return length;
    }

    static int[] randomArray(
            Random random, int n, int min, int max) {

        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = random.nextInt(max - min + 1) + min;
        }

        return nums;
    }

    static int[] increasingArray(int n) {
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = i - n / 2;
        }

        return nums;
    }

    static int[] decreasingArray(int n) {
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = n - i;
        }

        return nums;
    }

    static int[] equalArray(int n, int value) {
        int[] nums = new int[n];
        Arrays.fill(nums, value);
        return nums;
    }

    public static void main(String[] args) throws IOException {

        File base = new File("Testcases");

        File inputDir = new File(base, "input");
        File outputDir = new File(base, "output");

        if (!inputDir.exists()) {
            inputDir.mkdirs();
        }

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        List<TestCase> cases = new ArrayList<>();

        // Test case 1: Standard example
        cases.add(new TestCase(
            new int[]{10, 9, 2, 5, 3, 7, 101, 18}
        ));

        // Test case 2: Duplicates mixed with increasing values
        cases.add(new TestCase(
            new int[]{0, 1, 0, 3, 2, 3}
        ));

        // Test case 3: All elements equal
        cases.add(new TestCase(
            new int[]{7, 7, 7, 7, 7, 7, 7}
        ));

        // Test case 4: Single element
        cases.add(new TestCase(
            new int[]{1}
        ));

        // Test case 5: Strictly increasing
        cases.add(new TestCase(
            new int[]{1, 2, 3, 4, 5, 6}
        ));

        // Test case 6: Strictly decreasing
        cases.add(new TestCase(
            new int[]{6, 5, 4, 3, 2, 1}
        ));

        // Test case 7: Negative and positive values
        cases.add(new TestCase(
            new int[]{-5, -1, -3, 0, 2, -2, 4}
        ));

        Random random = new Random(20261024);

        // Test case 8: Medium random array
        cases.add(new TestCase(
            randomArray(random, 1000, -1000, 1000)
        ));

        // Test case 9: Large random array
        cases.add(new TestCase(
            randomArray(random, 100000, -1000000, 1000000)
        ));

        // Test case 10: Large strictly increasing array
        cases.add(new TestCase(increasingArray(100000)));

        // Test case 11: Large strictly decreasing array
        cases.add(new TestCase(decreasingArray(100000)));

        // Test case 12: Large array with equal values
        cases.add(new TestCase(equalArray(100000, 42)));

        // Test cases 13 to 101: Additional test cases
        for (int i = 13; i <= 101; i++) {

            int type = (i - 13) % 7;
            int n;
            int[] nums;

            switch (type) {

                case 0:
                    // Small random array
                    n = random.nextInt(20) + 1;
                    nums = randomArray(random, n, -50, 50);
                    break;

                case 1:
                    // Medium random array
                    n = random.nextInt(1000) + 100;
                    nums = randomArray(random, n, -10000, 10000);
                    break;

                case 2:
                    // Large random array
                    n = random.nextInt(50000) + 50000;
                    nums = randomArray(random, n, -1000000, 1000000);
                    break;

                case 3:
                    // Strictly increasing array
                    n = random.nextInt(5000) + 1;
                    nums = increasingArray(n);
                    break;

                case 4:
                    // Strictly decreasing array
                    n = random.nextInt(5000) + 1;
                    nums = decreasingArray(n);
                    break;

                case 5:
                    // Array containing only duplicate values
                    n = random.nextInt(5000) + 1;
                    nums = equalArray(
                        n, random.nextInt(201) - 100
                    );
                    break;

                default:
                    // Random array with a small value range,
                    // producing many duplicate values
                    n = random.nextInt(10000) + 1;
                    nums = randomArray(random, n, -10, 10);
                    break;
            }

            cases.add(new TestCase(nums));
        }

        // Generate input and expected output files
        for (int i = 0; i < cases.size(); i++) {

            String id = String.format("%02d", i + 1);
            TestCase testCase = cases.get(i);

            File inputFile = new File(
                inputDir, "input" + id + ".txt"
            );

            File outputFile = new File(
                outputDir, "output" + id + ".txt"
            );

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(
                        new FileWriter(inputFile)))) {

                writer.println(testCase.nums.length);

                for (int j = 0; j < testCase.nums.length; j++) {
                    if (j > 0) {
                        writer.print(" ");
                    }

                    writer.print(testCase.nums[j]);
                }

                writer.println();
            }

            int answer = solve(testCase.nums);

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(
                        new FileWriter(outputFile)))) {

                writer.println(answer);
            }
        }

        System.out.println(
            "Generated " + cases.size()
                    + " test cases successfully."
        );

        System.out.println(
            "Input directory: " + inputDir.getAbsolutePath()
        );

        System.out.println(
            "Output directory: " + outputDir.getAbsolutePath()
        );
    }
}