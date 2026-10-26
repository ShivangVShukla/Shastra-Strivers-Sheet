import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {
    private static final Random RANDOM = new Random(20261026);

    private static int[] generateCase(int tc) {
        switch (tc) {
            case 1: return new int[]{4, 5, 3, 7, 1, 2};
            case 2: return new int[]{-5, 0, -2};
            case 3: return new int[]{1, -2, 3, 4, -4, -3};
            case 4: return new int[]{-2};
            case 5: return new int[]{0};
            case 6: return new int[]{-2, -3};
            case 7: return new int[]{-2, 0, -1};
            case 8: return new int[]{2, 3, -2, 4};
            case 9: return new int[]{-2, 3, -4};
            case 10: return new int[]{-1, -3, -10, 0, 60};
            case 11: return new int[]{-1, -2, -3, 0};
            case 12: return new int[]{0, 2, -3, 4};
            case 13: return new int[]{-2, -3, -4};
            case 14: return new int[]{1, 2, 3, 4};
            case 15: return new int[]{-1, 2, 3, -4};
            default:
                int n;

                if (tc % 10 == 0) {
                    n = 100;
                } else if (tc % 7 == 0) {
                    n = 1 + RANDOM.nextInt(10);
                } else {
                    n = 1 + RANDOM.nextInt(30);
                }

                int[] arr = new int[n];

                for (int i = 0; i < n; i++) {
                    arr[i] = RANDOM.nextInt(21) - 10;
                }

                if (tc % 9 == 0) {
                    Arrays.fill(arr, 0);
                }

                if (tc % 11 == 0) {
                    for (int i = 0; i < n; i++) {
                        arr[i] = (i % 2 == 0) ? -2 : 3;
                    }
                }

                if (tc % 13 == 0) {
                    Arrays.fill(arr, 2);
                }

                return arr;
        }
    }

    private static long maxProduct(int[] nums) {
        long maxEnding = nums[0];
        long minEnding = nums[0];
        long answer = nums[0];

        for (int i = 1; i < nums.length; i++) {
            long x = nums[i];

            if (x < 0) {
                long temp = maxEnding;
                maxEnding = minEnding;
                minEnding = temp;
            }

            maxEnding = Math.max(x, maxEnding * x);
            minEnding = Math.min(x, minEnding * x);

            answer = Math.max(answer, maxEnding);
        }

        return answer;
    }

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("Testcases", "input");
        Path outputDir = Paths.get("Testcases", "output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int tc = 1; tc <= 101; tc++) {
            int[] nums = generateCase(tc);

            // Write input file
            StringBuilder input = new StringBuilder();
            input.append(nums.length).append('\n');

            for (int i = 0; i < nums.length; i++) {
                if (i > 0) {
                    input.append(' ');
                }
                input.append(nums[i]);
            }
            input.append('\n');

            String fileName = String.format("testcase%03d.txt", tc);

            Files.writeString(
                inputDir.resolve(fileName),
                input.toString()
            );

            // Calculate and write expected output
            long answer = maxProduct(nums);

            Files.writeString(
                outputDir.resolve(fileName),
                answer + System.lineSeparator()
            );
        }

        System.out.println(
            "Successfully generated 101 input and 101 output files."
        );
    }
}