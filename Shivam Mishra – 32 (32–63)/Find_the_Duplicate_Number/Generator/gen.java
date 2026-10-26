import java.io.*;
import java.util.*;

public class gen {
    static final Random random = new Random(20261026);
    static final int TOTAL = 101;

    static final String INPUT_DIR = "testcases/inp";
    static final String OUTPUT_DIR = "testcases/out";

    static class TestCase {
        int[] nums;
        int duplicate;

        TestCase(int[] nums, int duplicate) {
            this.nums = nums;
            this.duplicate = duplicate;
        }

        String key() {
            return Arrays.toString(nums);
        }
    }

    static TestCase generateTestCase(int testNumber) {
        int length;

        // Include edge cases and different array sizes.
        if (testNumber == 1) {
            return new TestCase(new int[]{1, 1}, 1);
        } else if (testNumber == 2) {
            return new TestCase(
                new int[]{1, 3, 4, 2, 2}, 2
            );
        } else if (testNumber == 3) {
            return new TestCase(
                new int[]{3, 1, 3, 4, 2}, 3
            );
        } else if (testNumber == 4) {
            return new TestCase(
                new int[]{3, 3, 3, 3, 3}, 3
            );
        }

        if (testNumber <= 20) {
            length = 2 + random.nextInt(19);
        } else if (testNumber <= 60) {
            length = 20 + random.nextInt(981);
        } else if (testNumber <= 90) {
            length = 1000 + random.nextInt(9001);
        } else {
            length = 10000 + random.nextInt(90001);
        }

        // The array length is n + 1.
        int n = length - 1;

        int duplicate = 1 + random.nextInt(n);

        // Repeat the duplicate 2 to 5 times when possible.
        int repeatCount = 2 + random.nextInt(
            Math.min(4, length - 2) + 1
        );

        repeatCount = Math.min(repeatCount, length);

        ArrayList<Integer> values = new ArrayList<>();
        values.add(duplicate);

        // Choose distinct values other than the duplicate.
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int value = 1; value <= n; value++) {
            if (value != duplicate) {
                candidates.add(value);
            }
        }

        Collections.shuffle(candidates, random);

        int remaining = length - repeatCount;

        for (int i = 0; i < remaining; i++) {
            values.add(candidates.get(i));
        }

        // Add the duplicate enough times to reach the length.
        while (values.size() < length) {
            values.add(duplicate);
        }

        Collections.shuffle(values, random);

        int[] nums = new int[length];
        for (int i = 0; i < length; i++) {
            nums[i] = values.get(i);
        }

        return new TestCase(nums, duplicate);
    }

    static void writeTestCase(
        int testNumber,
        TestCase testCase
    ) throws IOException {
        File inputFile = new File(
            INPUT_DIR + "/" + testNumber + ".txt"
        );

        File outputFile = new File(
            OUTPUT_DIR + "/" + testNumber + ".txt"
        );

        try (PrintWriter writer = new PrintWriter(
            new BufferedWriter(new FileWriter(inputFile))
        )) {
            writer.println(testCase.nums.length);

            for (int i = 0; i < testCase.nums.length; i++) {
                if (i > 0) {
                    writer.print(" ");
                }
                writer.print(testCase.nums[i]);
            }

            writer.println();
        }

        try (PrintWriter writer = new PrintWriter(
            new BufferedWriter(new FileWriter(outputFile))
        )) {
            writer.println(testCase.duplicate);
        }
    }

    public static void main(String[] args) throws IOException {
        new File(INPUT_DIR).mkdirs();
        new File(OUTPUT_DIR).mkdirs();

        Set<String> seen = new HashSet<>();

        for (int testNumber = 1;
             testNumber <= TOTAL;
             testNumber++) {

            TestCase testCase;

            do {
                testCase = generateTestCase(testNumber);
            } while (!seen.add(testCase.key()));

            writeTestCase(testNumber, testCase);

            System.out.println(
                "Generated test case " + testNumber
            );
        }

        System.out.println(
            "Successfully generated " + TOTAL
            + " input/output pairs."
        );
    }
}