
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static final Random random = new Random(20261026L);

    static int solve(int[] nums, int k) {
        // Independent brute-force oracle
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) {
                return i;
            }
        }
        return -1;
    }

    static void generateCase(
            int testNo,
            int[] sorted,
            int rotation,
            int target,
            Path inputDir,
            Path outputDir
    ) throws IOException {

        int n = sorted.length;
        int[] nums = new int[n];

        // Right-rotate the sorted array
        for (int i = 0; i < n; i++) {
            nums[i] = sorted[(i - rotation + n) % n];
        }

        StringBuilder input = new StringBuilder();
        input.append(n).append('\n');

        for (int i = 0; i < n; i++) {
            if (i > 0) input.append(' ');
            input.append(nums[i]);
        }

        input.append('\n').append(target).append('\n');

        String output = solve(nums, target) + "\n";

        Files.writeString(
            inputDir.resolve("input" + testNo + ".txt"),
            input.toString()
        );

        Files.writeString(
            outputDir.resolve("output" + testNo + ".txt"),
            output
        );
    }

    static int[] makeSortedArray(int n) {
        int[] arr = new int[n];

        // Strictly increasing values, including negative values
        int value = random.nextInt(21) - 10;

        for (int i = 0; i < n; i++) {
            arr[i] = value;
            value += 1 + random.nextInt(5);
        }

        return arr;
    }

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("Testcases", "input");
        Path outputDir = Paths.get("Testcases", "output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        // Remove old generated cases to avoid stale files
        try (DirectoryStream<Path> files =
                 Files.newDirectoryStream(inputDir, "input*.txt")) {
            for (Path file : files) {
                Files.delete(file);
            }
        }

        try (DirectoryStream<Path> files =
                 Files.newDirectoryStream(outputDir, "output*.txt")) {
            for (Path file : files) {
                Files.delete(file);
            }
        }

        for (int tc = 1; tc <= 101; tc++) {
            int n;
            int rotation;
            int[] sorted;
            int target;

            if (tc == 1) {
                sorted = new int[]{0, 1, 2, 4, 5, 6, 7};
                rotation = 4;
                target = 0;
            } else if (tc == 2) {
                sorted = new int[]{0, 1, 2, 4, 5, 6, 7};
                rotation = 4;
                target = 3;
            } else if (tc == 3) {
                sorted = new int[]{-5};
                rotation = 0;
                target = -5;
            } else if (tc == 4) {
                sorted = new int[]{10};
                rotation = 0;
                target = -10;
            } else {
                n = 1 + random.nextInt(10000);
                sorted = makeSortedArray(n);
                rotation = random.nextInt(n);

                // Mix present and absent targets
                if (tc % 2 == 0) {
                    target = sorted[random.nextInt(n)];
                } else {
                    target = random.nextInt(20001) - 10000;

                    // Ensure an absent target
                    while (Arrays.binarySearch(sorted, target) >= 0) {
                        target = random.nextInt(20001) - 10000;
                    }
                }
            }

            generateCase(
                tc, sorted, rotation, target, inputDir, outputDir
            );
        }

        System.out.println(
            "Generated 101 testcases successfully."
        );
    }
}
