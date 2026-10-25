import java.io.*;
import java.util.*;

public class gen {
    private static final long MIN_VALUE = -1_000_000_000L;
    private static final long MAX_VALUE = 1_000_000_000L;
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

        Random random = new Random(20261025L);

        for (int i = 0; i < TESTCASE_COUNT; i++) {
            int[] nums = createCase(i, random);
            String fileIndex = String.format("%02d", i);
            File inputFile = new File(inputDir, "input" + fileIndex + ".txt");
            File outputFile = new File(outputDir, "output" + fileIndex + ".txt");

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(inputFile)))) {
                writer.println(1);
                writer.println(nums.length);
                for (int j = 0; j < nums.length; j++) {
                    if (j > 0) writer.print(' ');
                    writer.print(nums[j]);
                }
                writer.println();
            }

            long answer = bruteForce(nums);
            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFile)))) {
                writer.println(answer);
            }
        }

        System.out.println("Generated " + TESTCASE_COUNT
                + " input/output testcase pairs successfully.");
    }

    private static int[] createCase(int id, Random random) {
        switch (id) {
            case 0: return new int[] {1, 2, 3};
            case 1: return new int[] {1, 3, 3};
            case 2: return new int[] {5};
            case 3: return new int[] {7, 7, 7, 7, 7};
            case 4: return new int[] {-1, -2, -3, -4};
            case 5: return new int[] {4, 3, 2, 1};
            case 6: return new int[] {1, 2, 3, 4, 5};
            case 7: return new int[] {-1_000_000_000, 1_000_000_000};
            case 8: return new int[] {-1_000_000_000, 0, 1_000_000_000};
            case 9: return new int[] {0, 0, -1, 0, 0};
            case 10: return new int[] {2, -2, 2, -2, 2, -2};
            case 11: return new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE};
            default:
                break;
        }

        int n;
        if (id < 30) {
            n = 1 + random.nextInt(12);
        } else if (id < 60) {
            n = 10 + random.nextInt(91);
        } else if (id < 85) {
            n = 100 + random.nextInt(401);
        } else {
            n = (id % 5 == 0) ? 1000 : 500 + random.nextInt(501);
        }

        int[] nums = new int[n];
        int mode = id % 8;

        for (int i = 0; i < n; i++) {
            switch (mode) {
                case 0: // Small random values, with duplicates.
                    nums[i] = random.nextInt(21) - 10;
                    break;
                case 1: // Full allowed range.
                    nums[i] = (int) (MIN_VALUE
                            + (long) (random.nextDouble()
                            * (MAX_VALUE - MIN_VALUE + 1)));
                    break;
                case 2: // Increasing values.
                    nums[i] = -n / 2 + i;
                    break;
                case 3: // Decreasing values.
                    nums[i] = n / 2 - i;
                    break;
                case 4: // Repeated values.
                    nums[i] = (id % 3 == 0) ? 42 : -42;
                    break;
                case 5: // Alternating extremes.
                    nums[i] = (i % 2 == 0) ? 1_000_000_000 : -1_000_000_000;
                    break;
                case 6: // Narrow range with many ties.
                    nums[i] = random.nextInt(5) - 2;
                    break;
                default: // Random signed values.
                    nums[i] = random.nextInt(2_000_001) - 1_000_000;
                    break;
            }
        }
        return nums;
    }

    // Independent O(N^2) reference implementation for trustworthy expected outputs.
    private static long bruteForce(int[] nums) {
        long total = 0L;
        for (int left = 0; left < nums.length; left++) {
            int minimum = nums[left];
            int maximum = nums[left];
            for (int right = left; right < nums.length; right++) {
                minimum = Math.min(minimum, nums[right]);
                maximum = Math.max(maximum, nums[right]);
                total += (long) maximum - minimum;
            }
        }
        return total;
    }
}
