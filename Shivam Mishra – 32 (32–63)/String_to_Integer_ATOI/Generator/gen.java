import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.math.BigInteger;
import java.util.*;

public class gen {

    static final int TEST_CASES = 101;
    static final int INT_MAX = Integer.MAX_VALUE;
    static final int INT_MIN = Integer.MIN_VALUE;

    static int referenceAtoi(String s) {
        int n = s.length();
        int i = 0;

        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        int sign = 1;

        if (i < n && (s.charAt(i) == '+' ||
                      s.charAt(i) == '-')) {
            if (s.charAt(i) == '-') {
                sign = -1;
            }
            i++;
        }

        BigInteger num = BigInteger.ZERO;
        boolean foundDigit = false;

        while (i < n && Character.isDigit(s.charAt(i))
                && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
            foundDigit = true;
            num = num.multiply(BigInteger.TEN)
                     .add(BigInteger.valueOf(s.charAt(i) - '0'));
            i++;
        }

        if (!foundDigit) {
            return 0;
        }

        if (sign == -1) {
            num = num.negate();
        }

        if (num.compareTo(BigInteger.valueOf(INT_MAX)) > 0) {
            return INT_MAX;
        }

        if (num.compareTo(BigInteger.valueOf(INT_MIN)) < 0) {
            return INT_MIN;
        }

        return num.intValue();
    }

    static void add(List<String> tests, String s) {
        tests.add(s);
    }

    public static void main(String[] args) throws IOException {
        List<String> tests = new ArrayList<>();

        // 1-20: Basic cases
        add(tests, "");
        add(tests, "0");
        add(tests, "1");
        add(tests, "42");
        add(tests, "-42");
        add(tests, "+42");
        add(tests, "123");
        add(tests, "12345");
        add(tests, "99999");
        add(tests, "0000");
        add(tests, "000123");
        add(tests, "-000123");
        add(tests, "+000123");
        add(tests, " 42");
        add(tests, "   -42");
        add(tests, "   +42");
        add(tests, "42 ");
        add(tests, "  123  ");
        add(tests, "4193 with words");
        add(tests, "words and 987");

        // 21-40: Signs, spaces and invalid strings
        add(tests, "+");
        add(tests, "-");
        add(tests, " ");
        add(tests, "     ");
        add(tests, "+-12");
        add(tests, "-+12");
        add(tests, "--12");
        add(tests, "++12");
        add(tests, "12-34");
        add(tests, "0-1");
        add(tests, "+ 12");
        add(tests, "- 12");
        add(tests, "abc");
        add(tests, "abc123");
        add(tests, ".123");
        add(tests, "3.14159");
        add(tests, "42abc");
        add(tests, "-42abc");
        add(tests, "+42xyz");
        add(tests, "  abc 123");

        // 41-60: Integer boundaries and overflow
        add(tests, "2147483647");
        add(tests, "2147483648");
        add(tests, "2147483649");
        add(tests, "-2147483648");
        add(tests, "-2147483649");
        add(tests, "-2147483650");
        add(tests, "99999999999999999999");
        add(tests, "-99999999999999999999");
        add(tests, "0002147483647");
        add(tests, "-0002147483648");
        add(tests, "+2147483647");
        add(tests, "+2147483648");
        add(tests, "2147483646");
        add(tests, "-2147483647");
        add(tests, "00000000000000000042");
        add(tests, "-00000000000000000042");
        add(tests, "214748364700000");
        add(tests, "-214748364800000");
        add(tests, "91283472332");
        add(tests, "-91283472332");

        // 61-80: Stop at first non-digit
        add(tests, "123abc456");
        add(tests, "-123abc456");
        add(tests, "+123abc456");
        add(tests, "00123xyz");
        add(tests, "000xyz123");
        add(tests, "7 8 9");
        add(tests, "  +0012a42");
        add(tests, "  -0012a42");
        add(tests, "999999999999abc");
        add(tests, "-999999999999abc");
        add(tests, "2147483647abc");
        add(tests, "2147483648abc");
        add(tests, "-2147483648abc");
        add(tests, "-2147483649abc");
        add(tests, "5+6");
        add(tests, "5-6");
        add(tests, "000000000000000000000");
        add(tests, "000000000000000000000-1");
        add(tests, "  +000");
        add(tests, "  -000");

        // 81-101: Deterministic random inputs
        Random random = new Random(20261026L);
        String alphabet = "0123456789+- abcXYZ.";

        while (tests.size() < TEST_CASES) {
            int length = 1 + random.nextInt(30);
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < length; j++) {
                sb.append(alphabet.charAt(
                    random.nextInt(alphabet.length())
                ));
            }

            String candidate = sb.toString();

            if (!tests.contains(candidate)) {
                add(tests, candidate);
            }
        }

        Path inputDir = Paths.get("input");
        Path outputDir = Paths.get("output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int i = 0; i < TEST_CASES; i++) {
            String input = tests.get(i);
            int expected = referenceAtoi(input);

            String fileName = String.format("%03d.txt", i + 1);

            Files.writeString(
                inputDir.resolve(fileName),
                input + System.lineSeparator(),
                StandardCharsets.UTF_8
            );

            Files.writeString(
                outputDir.resolve(fileName),
                expected + System.lineSeparator(),
                StandardCharsets.UTF_8
            );
        }

        System.out.println("Generated " + TEST_CASES + " test cases.");
        System.out.println("Inputs : " + inputDir.toAbsolutePath());
        System.out.println("Outputs: " + outputDir.toAbsolutePath());
    }
}