import java.io.*;
import java.util.*;

public class gen {

    static class TestCase {
        int[] nums;

        TestCase(int[] nums) {
            this.nums = nums;
        }
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";

        if (!new File(baseDir).exists()
                && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        List<TestCase> testCases = new ArrayList<>();

        // Original 8 test cases
        testCases.add(generateTC0());
        testCases.add(generateTC1());
        testCases.add(generateTC2());
        testCases.add(generateTC3());
        testCases.add(generateTC4());
        testCases.add(generateTC5());
        testCases.add(generateTC6());
        testCases.add(generateTC7());

        Random random = new Random(20261024);

        // Add 93 additional test cases: cases 9 to 101
        for (int tc = 9; tc <= 101; tc++) {
            int type = (tc - 9) % 10;
            int[] nums;

            switch (type) {
                case 0:
                    // Small random array
                    nums = randomArray(random, random.nextInt(20) + 1);
                    break;

                case 1:
                    // Medium random array
                    nums = randomArray(random, random.nextInt(1000) + 100);
                    break;

                case 2:
                    // Large random array
                    nums = randomArray(random, random.nextInt(50000) + 50000);
                    break;

                case 3:
                    // All zeroes
                    nums = filledArray(random.nextInt(5000) + 1, 0);
                    break;

                case 4:
                    // All ones
                    nums = filledArray(random.nextInt(5000) + 1, 1);
                    break;

                case 5:
                    // All twos
                    nums = filledArray(random.nextInt(5000) + 1, 2);
                    break;

                case 6:
                    // Sorted in ascending order
                    nums = sortedArray(random.nextInt(5000) + 1);
                    break;

                case 7:
                    // Sorted in descending order
                    nums = reverseSortedArray(random.nextInt(5000) + 1);
                    break;

                case 8:
                    // Alternating values: 0, 2, 1
                    nums = alternatingArray(random.nextInt(5000) + 1);
                    break;

                default:
                    // Large array with uneven frequencies
                    nums = unevenArray(random, random.nextInt(20000) + 1);
                    break;
            }

            testCases.add(new TestCase(nums));
        }

        // Generate all input and expected output files
        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%02d", i);

            File inFile = new File(
                inputDir, "input" + fileName + ".txt"
            );

            File outFile = new File(
                outputDir, "output" + fileName + ".txt"
            );

            int[] nums = testCases.get(i).nums;

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(inFile)))) {

                writer.println(nums.length);

                for (int j = 0; j < nums.length; j++) {
                    if (j > 0) {
                        writer.print(" ");
                    }
                    writer.print(nums[j]);
                }

                writer.println();
            }

            int[] sorted = nums.clone();
            solve(sorted);

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(outFile)))) {

                for (int j = 0; j < sorted.length; j++) {
                    if (j > 0) {
                        writer.print(" ");
                    }
                    writer.print(sorted[j]);
                }

                writer.println();
            }
        }

        System.out.println(
            testCases.size() + " test cases generated successfully!"
        );

        System.out.println("Input directory: " + inputDir.getAbsolutePath());
        System.out.println("Output directory: " + outputDir.getAbsolutePath());
    }

    // Dutch National Flag Algorithm
    static void solve(int[] nums) {
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;

                low++;
                mid++;
            } else if (nums[mid] == 1) {
                mid++;
            } else {
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;

                high--;
            }
        }
    }

    static TestCase generateTC0() {
        return new TestCase(new int[]{2, 0, 2, 1, 1, 0});
    }

    static TestCase generateTC1() {
        return new TestCase(new int[]{0, 0, 1, 1, 1});
    }

    static TestCase generateTC2() {
        return new TestCase(new int[]{2, 2, 2, 2, 2});
    }

    static TestCase generateTC3() {
        return new TestCase(new int[]{0, 0, 0, 0, 0});
    }

    static TestCase generateTC4() {
        return new TestCase(new int[]{1, 1, 1, 1, 1, 1});
    }

    static TestCase generateTC5() {
        return new TestCase(new int[]{2, 1, 0});
    }

    static TestCase generateTC6() {
        return new TestCase(randomArray(new Random(42), 1000));
    }

    static TestCase generateTC7() {
        return new TestCase(randomArray(new Random(12345), 100000));
    }

    static int[] randomArray(Random random, int n) {
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = random.nextInt(3);
        }

        return nums;
    }

    static int[] filledArray(int n, int value) {
        int[] nums = new int[n];
        Arrays.fill(nums, value);
        return nums;
    }

    static int[] sortedArray(int n) {
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = i % 3;
        }

        return nums;
    }

    static int[] reverseSortedArray(int n) {
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = 2 - (i % 3);
        }

        return nums;
    }

    static int[] alternatingArray(int n) {
        int[] nums = new int[n];
        int[] pattern = {0, 2, 1};

        for (int i = 0; i < n; i++) {
            nums[i] = pattern[i % pattern.length];
        }

        return nums;
    }

    static int[] unevenArray(Random random, int n) {
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            int chance = random.nextInt(10);

            if (chance < 7) {
                nums[i] = 0;
            } else if (chance < 9) {
                nums[i] = 1;
            } else {
                nums[i] = 2;
            }
        }

        return nums;
    }
}