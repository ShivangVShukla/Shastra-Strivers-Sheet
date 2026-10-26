
import java.io.*;
import java.util.*;

public class gen {

    static final String[] mapping = {
        "", "", "abc", "def", "ghi",
        "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    static class TestCase {
        String digits;

        TestCase(String digits) {
            this.digits = digits;
        }
    }

    static void backtrack(
            String digits,
            int index,
            StringBuilder current,
            List<String> result) {

        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        String letters = mapping[digits.charAt(index) - '0'];

        for (int i = 0; i < letters.length(); i++) {
            current.append(letters.charAt(i));

            backtrack(
                digits,
                index + 1,
                current,
                result
            );

            current.deleteCharAt(current.length() - 1);
        }
    }

    static List<String> solve(String digits) {
        List<String> result = new ArrayList<>();

        if (digits.isEmpty()) {
            return result;
        }

        backtrack(
            digits,
            0,
            new StringBuilder(),
            result
        );

        return result;
    }

    public static void main(String[] args) throws IOException {

        String baseDir = "Testcases";

        if (!new File(baseDir).exists()
                && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "input");
        File outputDir = new File(baseDir, "output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        List<TestCase> testCases = new ArrayList<>();

        // Single-digit test cases: 1 to 8
        for (int digit = 2; digit <= 9; digit++) {
            testCases.add(
                new TestCase(String.valueOf(digit))
            );
        }

        // Two-digit test cases
        for (int first = 2; first <= 9; first++) {
            for (int second = 2; second <= 9; second++) {
                testCases.add(
                    new TestCase("" + first + second)
                );
            }
        }

        // Three-digit test cases
        String[] threeDigitCases = {
            "222", "234", "279", "345", "456",
            "567", "678", "789", "777", "999",
            "272", "383", "494", "565", "676",
            "787", "898", "929", "234", "987"
        };

        for (String digits : threeDigitCases) {
            testCases.add(new TestCase(digits));
        }

        // Four-digit test cases
        String[] fourDigitCases = {
            "2222", "2345", "2799", "3456", "4567",
            "5678", "6789", "7777", "8888", "9999",
            "2349", "9876", "2727", "3838", "4949",
            "5679", "7892", "9292", "2468", "8642"
        };

        for (String digits : fourDigitCases) {
            testCases.add(new TestCase(digits));
        }

        // Deterministic random test cases
        Random random = new Random(42);

        while (testCases.size() < 101) {
            int length = 1 + random.nextInt(4);
            StringBuilder digits = new StringBuilder();

            for (int i = 0; i < length; i++) {
                digits.append(2 + random.nextInt(8));
            }

            testCases.add(new TestCase(digits.toString()));
        }

        // Write each test case and its expected output
        for (int i = 0; i < 101; i++) {
            TestCase testCase = testCases.get(i);

            File inputFile = new File(
                inputDir, (i + 1) + ".txt"
            );

            File outputFile = new File(
                outputDir, (i + 1) + ".txt"
            );

            try (PrintWriter writer =
                    new PrintWriter(inputFile)) {
                writer.println(testCase.digits);
            }

            List<String> result = solve(testCase.digits);

            try (PrintWriter writer =
                    new PrintWriter(outputFile)) {
                writer.println(String.join(" ", result));
            }
        }

        System.out.println(
            "Successfully generated 101 test cases."
        );
        System.out.println("Input directory: " + inputDir.getPath());
        System.out.println("Output directory: " + outputDir.getPath());
    }
}
