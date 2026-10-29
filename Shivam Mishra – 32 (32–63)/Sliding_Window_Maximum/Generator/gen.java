
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static int[] slidingWindowMax(int[] arr, int k) {
        int n = arr.length;
        int[] result = new int[n - k + 1];

        for (int i = 0; i <= n - k; i++) {
            int max = arr[i];

            for (int j = i + 1; j < i + k; j++) {
                max = Math.max(max, arr[j]);
            }

            result[i] = max;
        }

        return result;
    }

    static int[] createArray(int n, int type, Random random) {
        int[] arr = new int[n];

        switch (type) {
            case 0:
                // Random values, including negatives
                for (int i = 0; i < n; i++) {
                    arr[i] = random.nextInt(2001) - 1000;
                }
                break;

            case 1:
                // Strictly increasing array
                for (int i = 0; i < n; i++) {
                    arr[i] = i - n / 2;
                }
                break;

            case 2:
                // Strictly decreasing array
                for (int i = 0; i < n; i++) {
                    arr[i] = n / 2 - i;
                }
                break;

            case 3:
                // All elements equal
                Arrays.fill(arr, 7);
                break;

            case 4:
                // Alternating positive and negative values
                for (int i = 0; i < n; i++) {
                    arr[i] = (i % 2 == 0) ? 100 : -100;
                }
                break;

            case 5:
                // Repeated values from a small range
                for (int i = 0; i < n; i++) {
                    arr[i] = random.nextInt(11) - 5;
                }
                break;

            default:
                // Large and varied values
                for (int i = 0; i < n; i++) {
                    arr[i] = random.nextInt(200001) - 100000;
                }
        }

        return arr;
    }

    public static void main(String[] args) throws IOException {
        Random random = new Random(42);

        Path inputDir = Paths.get("Testcases/input");
        Path outputDir = Paths.get("Testcases/output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int tc = 1; tc <= 101; tc++) {
            int n;
            int k;
            int type = random.nextInt(7);

            if (tc == 1) {
                n = 8;
                k = 3;
                type = 0;
            } else if (tc == 2) {
                n = 5;
                k = 1;
                type = 1;
            } else if (tc == 3) {
                n = 1;
                k = 1;
                type = 0;
            } else if (tc == 4) {
                n = 10;
                k = 10;
                type = 2;
            } else if (tc == 5) {
                n = 10;
                k = 5;
                type = 3;
            } else if (tc == 6) {
                n = 20;
                k = 2;
                type = 4;
            } else if (tc == 7) {
                n = 50;
                k = 25;
                type = 5;
            } else {
                n = 1 + random.nextInt(1000);
                k = 1 + random.nextInt(n);
            }

            int[] arr = createArray(n, type, random);
            int[] expected = slidingWindowMax(arr, k);

            // Write input to an individual .txt file
            Path inputFile = inputDir.resolve(tc + ".txt");

            try (BufferedWriter writer =
                     Files.newBufferedWriter(inputFile)) {

                writer.write(n + " " + k);
                writer.newLine();

                for (int i = 0; i < n; i++) {
                    if (i > 0) {
                        writer.write(" ");
                    }
                    writer.write(Integer.toString(arr[i]));
                }

                writer.newLine();
            }

            // Write expected output to the matching .txt file
            Path outputFile = outputDir.resolve(tc + ".txt");

            try (BufferedWriter writer =
                     Files.newBufferedWriter(outputFile)) {

                for (int i = 0; i < expected.length; i++) {
                    if (i > 0) {
                        writer.write(" ");
                    }
                    writer.write(Integer.toString(expected[i]));
                }

                writer.newLine();
            }
        }

        System.out.println("Generated 101 test cases successfully.");
        System.out.println("Inputs: Testcases/input/1.txt to 101.txt");
        System.out.println("Outputs: Testcases/output/1.txt to 101.txt");
    }
}
