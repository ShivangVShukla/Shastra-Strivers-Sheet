
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static final String INPUT_DIR = "Testcases/input";
    static final String OUTPUT_DIR = "Testcases/output";
    static final int TOTAL_TESTCASES = 101;

    static int[] generateArray(Random random, int testCase) {
        int n;

        if (testCase == 1) {
            return new int[]{5};
        }

        if (testCase == 2) {
            return new int[]{1, 2, 2, 4, 3, 1, 4};
        }

        if (testCase == 3) {
            return new int[]{-7, 5, 5};
        }

        n = 1 + random.nextInt(100);

        // Odd size: one unique number and all others paired.
        if (n % 2 == 0) {
            n++;
        }

        int unique = random.nextInt(600001) - 300000;
        Set<Integer> used = new HashSet<>();
        used.add(unique);

        int[] arr = new int[n];
        arr[0] = unique;

        int index = 1;

        while (index < n) {
            int value;

            do {
                value = random.nextInt(600001) - 300000;
            } while (used.contains(value));

            used.add(value);
            arr[index++] = value;
            arr[index++] = value;
        }

        // Shuffle to avoid placing the unique value first.
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        return arr;
    }

    static int solve(int[] arr) {
        int result = 0;

        for (int value : arr) {
            result ^= value;
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        Random random = new Random(42);

        Path inputDir = Paths.get(INPUT_DIR);
        Path outputDir = Paths.get(OUTPUT_DIR);

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int tc = 1; tc <= TOTAL_TESTCASES; tc++) {
            int[] arr = generateArray(random, tc);
            int answer = solve(arr);

            StringBuilder input = new StringBuilder();
            input.append(arr.length).append('\n');

            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    input.append(' ');
                }
                input.append(arr[i]);
            }
            input.append('\n');

            Files.writeString(
                inputDir.resolve(tc + ".txt"),
                input.toString()
            );

            Files.writeString(
                outputDir.resolve(tc + ".txt"),
                answer + "\n"
            );
        }

        System.out.println(
            "Generated " + TOTAL_TESTCASES + " test cases successfully."
        );
    }
}
