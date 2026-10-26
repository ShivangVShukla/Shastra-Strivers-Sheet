
import java.io.*;
import java.util.*;

public class gen {
    static final int TEST_CASES = 101;
    static final String BASE_DIR = "Testcases";

    static class TestCase {
        int[] heights;

        TestCase(int... heights) {
            this.heights = heights;
        }
    }

    static long largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] stack = new int[n];
        int top = -1;
        long best = 0;

        for (int i = 0; i <= n; i++) {
            int currentHeight =
                (i == n) ? 0 : heights[i];

            while (top >= 0 &&
                   heights[stack[top]] > currentHeight) {
                int height = heights[stack[top--]];

                int width = (top < 0)
                    ? i
                    : i - stack[top] - 1;

                best = Math.max(
                    best, 1L * height * width
                );
            }

            if (i < n) {
                stack[++top] = i;
            }
        }

        return best;
    }

    static List<TestCase> buildTestCases() {
        List<TestCase> tests = new ArrayList<>();

        // Fixed examples and edge cases
        tests.add(new TestCase(2, 1, 5, 6, 2, 3));
        tests.add(new TestCase(3, 5, 1, 7, 5, 9));
        tests.add(new TestCase(2, 4));
        tests.add(new TestCase(1));
        tests.add(new TestCase(0));
        tests.add(new TestCase(0, 0, 0, 0));
        tests.add(new TestCase(5, 5, 5, 5, 5));
        tests.add(new TestCase(1, 2, 3, 4, 5, 6));
        tests.add(new TestCase(6, 5, 4, 3, 2, 1));
        tests.add(new TestCase(2, 2, 1, 2, 2));
        tests.add(new TestCase(1, 3, 2, 1, 2));
        tests.add(new TestCase(10000, 10000, 10000));
        tests.add(new TestCase(0, 4, 0, 4, 0));
        tests.add(new TestCase(4, 0, 4, 0, 4));
        tests.add(new TestCase(2, 1, 2, 3, 1, 2));

        Random random = new Random(20261026L);

        // Generate remaining cases
        while (tests.size() < TEST_CASES) {
            int caseIndex = tests.size();
            int n;

            if (caseIndex % 10 == 0) {
                n = 100;
            } else if (caseIndex % 7 == 0) {
                n = 50;
            } else {
                n = 1 + random.nextInt(30);
            }

            int[] heights = new int[n];
            int pattern = caseIndex % 6;

            for (int i = 0; i < n; i++) {
                switch (pattern) {
                    case 0:
                        heights[i] = random.nextInt(21);
                        break;

                    case 1:
                        heights[i] = i + 1;
                        break;

                    case 2:
                        heights[i] = n - i;
                        break;

                    case 3:
                        heights[i] =
                            1 + random.nextInt(10000);
                        break;

                    case 4:
                        heights[i] =
                            (i % 2 == 0) ? 100 : 1;
                        break;

                    default:
                        heights[i] = random.nextInt(6);
                        break;
                }
            }

            tests.add(new TestCase(heights));
        }

        return tests;
    }

    static void writeFile(File file, String content)
            throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {
            writer.write(content);
            writer.newLine();
        }
    }

    public static void main(String[] args)
            throws IOException {
        File inputDir = new File(BASE_DIR, "input");
        File outputDir = new File(BASE_DIR, "output");

        if (!inputDir.exists() &&
            new File("../Testcases/input").exists()) {
            inputDir = new File("../Testcases/input");
            outputDir = new File("../Testcases/output");
        }

        inputDir.mkdirs();
        outputDir.mkdirs();

        List<TestCase> tests = buildTestCases();

        for (int i = 0; i < tests.size(); i++) {
            TestCase test = tests.get(i);

            StringBuilder input = new StringBuilder();
            input.append("[");

            for (int j = 0;
                 j < test.heights.length; j++) {
                if (j > 0) {
                    input.append(", ");
                }
                input.append(test.heights[j]);
            }

            input.append("]");

            long answer =
                largestRectangleArea(test.heights);

            String fileNumber =
                String.format("%03d", i + 1);

            writeFile(
                new File(inputDir, fileNumber + ".txt"),
                input.toString()
            );

            writeFile(
                new File(outputDir, fileNumber + ".txt"),
                Long.toString(answer)
            );
        }

        System.out.println(
            "Generated " + tests.size() +
            " testcases in Testcases/input and Testcases/output."
        );
    }
}
