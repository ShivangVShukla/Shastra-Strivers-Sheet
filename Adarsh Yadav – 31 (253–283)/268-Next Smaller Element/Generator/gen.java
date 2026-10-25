import java.io.*;
import java.util.*;

public class gen {
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

        Random random = new Random(20261026L);
        for (int tc = 0; tc < TESTCASE_COUNT; tc++) {
            int[] arr = createCase(tc, random);
            String index = String.format("%02d", tc);
            File inputFile = new File(inputDir, "input" + index + ".txt");
            File outputFile = new File(outputDir, "output" + index + ".txt");

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(inputFile)))) {
                writer.println(1);
                writer.println(arr.length);
                for (int i = 0; i < arr.length; i++) {
                    if (i > 0) writer.print(' ');
                    writer.print(arr[i]);
                }
                writer.println();
            }

            int[] answer = referenceSolve(arr);
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)))) {
                for (int i = 0; i < answer.length; i++) {
                    if (i > 0) writer.print(' ');
                    writer.print(answer[i]);
                }
                writer.println();
            }
        }
        System.out.println("Generated " + TESTCASE_COUNT + " input/output testcase pairs successfully.");
    }

    private static int[] createCase(int id, Random random) {
        switch (id) {
            case 0: return new int[] {4, 8, 5, 2, 25};
            case 1: return new int[] {10, 9, 8, 7};
            case 2: return new int[] {1, 2, 3, 4, 5};
            case 3: return new int[] {5};
            case 4: return new int[] {7, 7, 7, 7};
            case 5: return new int[] {-1, -2, -3, -4};
            case 6: return new int[] {-1_000_000_000, 1_000_000_000, -1_000_000_000};
            case 7: return new int[] {2, 1, 2, 1, 2, 1};
            case 8: return new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE};
            case 9: return new int[] {3, 1, 4, 2, 5, 0};
            default: break;
        }

        int n;
        if (id < 30) n = 1 + random.nextInt(15);
        else if (id < 60) n = 20 + random.nextInt(181);
        else if (id < 90) n = 200 + random.nextInt(801);
        else n = (id % 3 == 0) ? 100_000 : 1_000 + random.nextInt(9_001);

        int[] arr = new int[n];
        int mode = id % 8;
        for (int i = 0; i < n; i++) {
            switch (mode) {
                case 0: arr[i] = random.nextInt(21) - 10; break;
                case 1: arr[i] = random.nextInt(2_000_000_001) - 1_000_000_000; break;
                case 2: arr[i] = i - n / 2; break;
                case 3: arr[i] = n / 2 - i; break;
                case 4: arr[i] = (id % 2 == 0) ? 17 : -17; break;
                case 5: arr[i] = (i % 2 == 0) ? 1_000_000_000 : -1_000_000_000; break;
                case 6: arr[i] = (i / 5) % 7 - 3; break;
                default: arr[i] = random.nextInt(2_000_001) - 1_000_000; break;
            }
        }
        return arr;
    }

    // Independent O(N^2) reference implementation.
    private static int[] referenceSolve(int[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = -1;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    result[i] = arr[j];
                    break;
                }
            }
        }
        return result;
    }
}
