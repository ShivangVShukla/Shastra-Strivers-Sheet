import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws Exception {
        // Deep prefix expressions need a large stack for the recursive reference
        Thread worker = new Thread(null, () -> {
            try {
                run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "gen", 1L << 29);
        worker.start();
        worker.join();
    }

    static void run() throws IOException {
        String baseDir = "Testcases";
        if (!new File(baseDir).exists() && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "input");
        File outputDir = new File(baseDir, "output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<TestCase> testCases = new ArrayList<>();
        testCases.add(generateTC0());
        testCases.add(generateTC1());
        testCases.add(generateTC2());
        testCases.add(generateTC3());

        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            TestCase tc = testCases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inWriter.println(tc.exprs.size());
            for (String s : tc.exprs) {
                inWriter.println(s);
            }
            inWriter.close();

            String[] ans = solve(tc.exprs);

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (String a : ans) {
                outWriter.println(a);
            }
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static class TestCase {
        List<String> exprs;

        TestCase(List<String> exprs) {
            this.exprs = exprs;
        }
    }

    // Reference solution: recursive descent from the left. An operator is followed
    // by its left subexpression and then its right subexpression (different
    // technique from the right-to-left stack in Solution.java).
    static String src;
    static int pos;
    static StringBuilder out;

    static String[] solve(List<String> exprs) {
        String[] ans = new String[exprs.size()];
        for (int i = 0; i < exprs.size(); i++) {
            src = exprs.get(i);
            pos = 0;
            out = new StringBuilder();
            parse();
            ans[i] = out.toString();
        }
        return ans;
    }

    static void parse() {
        char c = src.charAt(pos++);
        if (Character.isLetterOrDigit(c)) {
            out.append(c);
        } else {
            out.append('(');
            parse();
            out.append(c);
            parse();
            out.append(')');
        }
    }

    // Examples from the problem statement, plus the "Now Your Turn" input
    static TestCase generateTC0() {
        List<String> exprs = Arrays.asList("+ab", "*+ab-cd", "^a*bc");
        return new TestCase(exprs);
    }

    // Tiny expressions: lone operands, every operator, both nesting directions
    static TestCase generateTC1() {
        List<String> exprs = Arrays.asList(
                "a", "5", "+ab", "-ab", "*ab", "/ab", "^ab",
                "+*abc", "+a*bc", "-+abc", "-a+bc", "^^abc", "^a^bc");
        return new TestCase(exprs);
    }

    // Mixed operators and nesting (operand order matters for - / ^)
    static TestCase generateTC2() {
        List<String> exprs = Arrays.asList(
                "*+ab-cd",
                "/*+abc-de",
                "+-a*bcd",
                "-a-b-cd",
                "+a+b+cd",
                "^a^b^cd",
                "*^ab^cd",
                "^*ab*cd",
                "+1*23",
                "-9/84",
                "*+12-34");
        return new TestCase(exprs);
    }

    // Maximum length cases (up to 10^4 characters)
    static TestCase generateTC3() {
        List<String> exprs = new ArrayList<>();
        Random rand = new Random(2024);
        int len = 9999; // a valid prefix expression always has odd length

        // All operators first, then operands: ((((a+b)+c)+d)...)
        exprs.add(repeat('+', 4999) + randomOperands(5000, rand));

        // operator, operand, operator, operand, ... then a final operand
        StringBuilder alt = new StringBuilder();
        for (int i = 0; i < 4999; i++) {
            alt.append("+-*/^".charAt(rand.nextInt(5)));
            alt.append(randomOperand(rand));
        }
        alt.append(randomOperand(rand));
        exprs.add(alt.toString());

        // Random expression tree
        exprs.add(randomPrefix(len, rand));

        // All '^' first, then operands
        exprs.add(repeat('^', 4999) + randomOperands(5000, rand));

        // Another random expression tree
        exprs.add(randomPrefix(len, rand));

        // Smaller random expression tree
        exprs.add(randomPrefix(1001, rand));

        return new TestCase(exprs);
    }

    static char randomOperand(Random rand) {
        int k = rand.nextInt(36);
        return k < 26 ? (char) ('a' + k) : (char) ('0' + (k - 26));
    }

    static String randomOperands(int count, Random rand) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(randomOperand(rand));
        }
        return sb.toString();
    }

    static String repeat(char c, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    // Random valid prefix expression of exact odd length len
    static String randomPrefix(int len, Random rand) {
        StringBuilder sb = new StringBuilder();
        buildPrefix(len, rand, sb);
        return sb.toString();
    }

    static void buildPrefix(int len, Random rand, StringBuilder sb) {
        if (len == 1) {
            sb.append(randomOperand(rand));
            return;
        }
        int l = 1 + 2 * rand.nextInt((len - 1) / 2); // odd, 1..len-2
        int r = len - 1 - l;                          // odd
        sb.append("+-*/^".charAt(rand.nextInt(5)));
        buildPrefix(l, rand, sb);
        buildPrefix(r, rand, sb);
    }
}
