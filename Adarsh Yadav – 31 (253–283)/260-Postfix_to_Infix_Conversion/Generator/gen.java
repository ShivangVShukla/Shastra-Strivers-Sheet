import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws Exception {
        // Deep expressions need a large stack for the recursive reference
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

    // Reference solution: build the expression tree recursively from the END of
    // the postfix string (last character is the root; it is followed, going
    // backwards, by its right subtree and then its left subtree), then print the
    // tree in-order with parentheses (different technique from the left-to-right
    // stack in Solution.java).
    static String src;
    static int pos;
    static char[] ch;
    static int[] lc;
    static int[] rc;
    static int cnt;
    static StringBuilder out;

    static String[] solve(List<String> exprs) {
        String[] ans = new String[exprs.size()];
        for (int i = 0; i < exprs.size(); i++) {
            src = exprs.get(i);
            int n = src.length();
            ch = new char[n];
            lc = new int[n];
            rc = new int[n];
            cnt = 0;
            pos = n - 1;
            int root = build();
            out = new StringBuilder();
            print(root);
            ans[i] = out.toString();
        }
        return ans;
    }

    static int build() {
        char c = src.charAt(pos--);
        int id = cnt++;
        ch[id] = c;
        if (!Character.isLetter(c)) {
            rc[id] = build();
            lc[id] = build();
        }
        return id;
    }

    static void print(int id) {
        if (Character.isLetter(ch[id])) {
            out.append(ch[id]);
        } else {
            out.append('(');
            print(lc[id]);
            out.append(ch[id]);
            print(rc[id]);
            out.append(')');
        }
    }

    // Examples from the problem statement, plus the "Now Your Turn" input
    static TestCase generateTC0() {
        List<String> exprs = Arrays.asList("ab+", "abc*+", "abc/-ak/l-*");
        return new TestCase(exprs);
    }

    // Tiny expressions: lone operand, every operator, both nesting directions
    static TestCase generateTC1() {
        List<String> exprs = Arrays.asList(
                "a", "ab+", "ab-", "ab*", "ab/",
                "ab+c+", "abc++", "abc*+", "ab*c+", "ab-c-", "abc--", "ab/c*", "abc*/");
        return new TestCase(exprs);
    }

    // Mixed operators and nesting (operand order matters for - and /)
    static TestCase generateTC2() {
        List<String> exprs = Arrays.asList(
                "ab+cd-*",
                "ab+c*de-/",
                "abc*-d+",
                "abcd---",
                "abcd+++",
                "ab*cd*+",
                "ab/cd/-",
                "abc+-d*",
                "ab+cd+*ef+*",
                "abcde+*-/");
        return new TestCase(exprs);
    }

    // Maximum length cases (up to 16000 characters)
    static TestCase generateTC3() {
        List<String> exprs = new ArrayList<>();
        Random rand = new Random(2024);
        int len = 15999; // a valid postfix expression always has odd length
        int operands = (len + 1) / 2; // 8000

        // operand operand op, then (operand op) repeated: left-nested
        StringBuilder leftNested = new StringBuilder();
        leftNested.append(randomOperand(rand)).append(randomOperand(rand)).append(randomOp(rand));
        for (int i = 0; i < operands - 2; i++) {
            leftNested.append(randomOperand(rand)).append(randomOp(rand));
        }
        exprs.add(leftNested.toString());

        // All operands first, then all operators: right-nested
        StringBuilder rightNested = new StringBuilder();
        for (int i = 0; i < operands; i++) {
            rightNested.append(randomOperand(rand));
        }
        for (int i = 0; i < operands - 1; i++) {
            rightNested.append(randomOp(rand));
        }
        exprs.add(rightNested.toString());

        // Random expression tree
        exprs.add(randomPostfix(len, rand));

        // Left-nested chain of '-' only
        StringBuilder minus = new StringBuilder();
        minus.append(randomOperand(rand)).append(randomOperand(rand)).append('-');
        for (int i = 0; i < operands - 2; i++) {
            minus.append(randomOperand(rand)).append('-');
        }
        exprs.add(minus.toString());

        // Another random expression tree
        exprs.add(randomPostfix(len, rand));

        // Smaller random expression tree
        exprs.add(randomPostfix(1001, rand));

        return new TestCase(exprs);
    }

    static char randomOperand(Random rand) {
        return (char) ('a' + rand.nextInt(26));
    }

    static char randomOp(Random rand) {
        return "+-*/".charAt(rand.nextInt(4));
    }

    // Random valid postfix expression of exact odd length len
    static String randomPostfix(int len, Random rand) {
        StringBuilder sb = new StringBuilder();
        buildPostfix(len, rand, sb);
        return sb.toString();
    }

    static void buildPostfix(int len, Random rand, StringBuilder sb) {
        if (len == 1) {
            sb.append(randomOperand(rand));
            return;
        }
        int l = 1 + 2 * rand.nextInt((len - 1) / 2); // odd, 1..len-2
        int r = len - 1 - l;                          // odd
        buildPostfix(l, rand, sb);
        buildPostfix(r, rand, sb);
        sb.append(randomOp(rand));
    }
}
