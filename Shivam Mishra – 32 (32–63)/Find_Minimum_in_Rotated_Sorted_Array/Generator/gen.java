
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static int findMin(int[] arr) {
        int low = 0;
        int high = arr.length - 1;

        while (low < high) {
            if (arr[low] < arr[high]) {
                return arr[low];
            }

            int mid = low + (high - low) / 2;

            if (arr[mid] > arr[high]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return arr[low];
    }

    static int[] createArray(int n, int rotation) {
        int[] arr = new int[n];
        int start = -(n / 2);

        // Create a sorted array of distinct integers
        for (int i = 0; i < n; i++) {
            arr[i] = start + i;
        }

        // Rotate the array to the right
        int[] rotated = new int[n];

        for (int i = 0; i < n; i++) {
            rotated[(i + rotation) % n] = arr[i];
        }

        return rotated;
    }

    public static void main(String[] args) throws IOException {
        Random random = new Random(42);

        Path inputDir = Paths.get("Testcases/input");
        Path outputDir = Paths.get("Testcases/output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int tc = 1; tc <= 101; tc++) {
            int n;
            int rotation;

            if (tc == 1) {
                n = 8;
                rotation = 4;
            } else if (tc == 2) {
                n = 5;
                rotation = 3;
            } else if (tc == 3) {
                n = 1;
                rotation = 0;
            } else if (tc == 4) {
                n = 10;
                rotation = 0;
            } else if (tc == 5) {
                n = 10;
                rotation = 9;
            } else {
                n = 1 + random.nextInt(10000);
                rotation = random.nextInt(n);
            }

            int[] arr = createArray(n, rotation);
            int answer = findMin(arr);

            // Write individual input file
            Path inputFile = inputDir.resolve(tc + ".txt");

            try (BufferedWriter writer =
                     Files.newBufferedWriter(inputFile)) {

                writer.write(Integer.toString(n));
                writer.newLine();

                for (int i = 0; i < n; i++) {
                    if (i > 0) {
                        writer.write(" ");
                    }
                    writer.write(Integer.toString(arr[i]));
                }

                writer.newLine();
            }

            // Write individual expected output file
            Path outputFile = outputDir.resolve(tc + ".txt");

            try (BufferedWriter writer =
                     Files.newBufferedWriter(outputFile)) {

                writer.write(Integer.toString(answer));
                writer.newLine();
            }
        }

        System.out.println("Generated 101 test cases successfully.");
        System.out.println("Input files: Testcases/input/1.txt to 101.txt");
        System.out.println("Output files: Testcases/output/1.txt to 101.txt");
    }
}
