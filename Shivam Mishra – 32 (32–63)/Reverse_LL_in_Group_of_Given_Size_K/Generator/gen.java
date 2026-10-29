import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static final Random random = new Random(20261026);

    static class TestCase {
        int[] values;
        int k;

        TestCase(int[] values, int k) {
            this.values = values;
            this.k = k;
        }
    }

    static TestCase createTest(int id) {
        int n;
        int k;

        switch (id) {
            case 1:
                return new TestCase(new int[]{1}, 1);
            case 2:
                return new TestCase(new int[]{1, 2}, 2);
            case 3:
                return new TestCase(new int[]{1, 2, 3, 4, 5}, 2);
            case 4:
                return new TestCase(new int[]{1, 2, 3, 4, 5}, 3);
            case 5:
                return new TestCase(new int[]{1, 2, 3, 4, 5}, 5);
            case 6:
                return new TestCase(new int[]{-1, -2, 0, 4, 4, 9}, 4);
            case 7:
                return new TestCase(new int[]{10, 20, 30, 40, 50, 60}, 1);
            case 8:
                return new TestCase(new int[]{7, 7, 7, 7, 7, 7}, 3);
            case 9:
                return new TestCase(new int[]{-10000, 0, 10000}, 2);
            case 10:
                return new TestCase(new int[]{5, 4, 3, 2, 1}, 5);
            default:
                n = 1 + random.nextInt(100);

                if (id % 10 == 0) {
                    n = 100;
                }

                if (id % 7 == 0) {
                    k = n;
                } else if (id % 7 == 1) {
                    k = 1;
                } else {
                    k = 1 + random.nextInt(n);
                }

                int[] arr = new int[n];

                for (int i = 0; i < n; i++) {
                    if (id % 11 == 0) {
                        arr[i] = 0;
                    } else if (id % 13 == 0) {
                        arr[i] = i;
                    } else if (id % 17 == 0) {
                        arr[i] = n - i;
                    } else {
                        arr[i] = random.nextInt(20001) - 10000;
                    }
                }

                return new TestCase(arr, k);
        }
    }

    static int[] solve(int[] values, int k) {
        int[] result = values.clone();

        for (int start = 0; start + k <= result.length; start += k) {
            int left = start;
            int right = start + k - 1;

            while (left < right) {
                int temp = result[left];
                result[left] = result[right];
                result[right] = temp;

                left++;
                right--;
            }
        }

        return result;
    }

    static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(arr[i]);
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("Testcases", "input");
        Path outputDir = Paths.get("Testcases", "output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int id = 1; id <= 101; id++) {
            TestCase test = createTest(id);

            String input = arrayToString(test.values)
                    + System.lineSeparator()
                    + test.k
                    + System.lineSeparator();

            int[] answer = solve(test.values, test.k);
            String output = arrayToString(answer)
                    + System.lineSeparator();

            Files.write(
                inputDir.resolve(id + ".txt"),
                input.getBytes()
            );

            Files.write(
                outputDir.resolve(id + ".txt"),
                output.getBytes()
            );
        }

        System.out.println("Generated 101 test cases successfully.");
    }
}