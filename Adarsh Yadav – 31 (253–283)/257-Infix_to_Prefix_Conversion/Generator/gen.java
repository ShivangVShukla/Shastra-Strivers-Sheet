import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws Exception {
        // Deeply nested expressions need a large stack for the recursive reference
        Thread worker = new Thread(null, () -> {
            try {
                run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, "gen", 1L << 28);
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

    // Reference solution: recursive-descent parser that builds each prefix string
    // directly (different technique from the reverse + stack method in Solution.java).
    //   expr := term (('+'|'-') term)*     left-associative: op + left + right
    //   term := base (('*'|'/') base)*     left-associative: op + left + right
    //   base := letter | '(' expr ')'
    static String src;
    static int pos;

    static String[] solve(List<String> exprs) {
        String[] ans = new String[exprs.size()];
        for (int i = 0; i < exprs.size(); i++) {
            src = exprs.get(i);
            pos = 0;
            ans[i] = parseExpr();
        }
        return ans;
    }

    static String parseExpr() {
        String left = parseTerm();
        while (pos < src.length() && (src.charAt(pos) == '+' || src.charAt(pos) == '-')) {
            char op = src.charAt(pos++);
            String right = parseTerm();
            left = op + left + right;
        }
        return left;
    }

    static String parseTerm() {
        String left = parseBase();
        while (pos < src.length() && (src.charAt(pos) == '*' || src.charAt(pos) == '/')) {
            char op = src.charAt(pos++);
            String right = parseBase();
            left = op + left + right;
        }
        return left;
    }

    static String parseBase() {
        char c = src.charAt(pos++);
        if (c == '(') {
            String inner = parseExpr();
            pos++; // skip ')'
            return inner;
        }
        return String.valueOf(c);
    }

    // Examples from the problem statement, plus the "Now Your Turn" input
    static TestCase generateTC0() {
        List<String> exprs = Arrays.asList("(a+b)*c", "a+b*c", "((a-(b/c))*((a/k)-l))");
        return new TestCase(exprs);
    }

    // Tiny expressions: single operand, redundant parentheses, associativity basics
    static TestCase generateTC1() {
        List<String> exprs = Arrays.asList(
                "a", "(a)", "((a))", "a+b", "a-b-c", "a/b*c",
                "a-(b-c)", "a/(b*c)", "a*b+c", "a+b*c-d");
        return new TestCase(exprs);
    }

    // Precedence and associativity mixes
    static TestCase generateTC2() {
        List<String> exprs = Arrays.asList(
                "a+b-c+d",
                "a*b/c*d",
                "(a+b)*(c-d)",
                "a-b*c/d+e",
                "((a+b)*c)/(d-e)",
                "a/(b/(c/d))",
                "a-(b-(c-d))",
                "(a-b)-(c-d)",
                "a*(b+c)*d",
                "((a-(b/c))*((a/k)-l))");
        return new TestCase(exprs);
    }

    // Maximum length cases (up to 1000 characters)
    static TestCase generateTC3() {
        List<String> exprs = new ArrayList<>();
        Random rand = new Random(2024);
        int len = 999; // operands and operators alternate, so length is odd

        // Mixed operators, no parentheses
        exprs.add(chain(len, "+-*/", rand));

        // Left-associative chain: a-b-c-...
        exprs.add(chain(len, "-", rand));

        // Left-associative chain: a/b/c/...
        exprs.add(chain(len, "/", rand));

        // Deeply nested on the left: (((a+b)-c)*d)...
        int m = 249;
        String ops = "+-*/";
        StringBuilder nested = new StringBuilder();
        for (int i = 0; i < m; i++) {
            nested.append('(');
        }
        nested.append('a');
        for (int i = 0; i < m; i++) {
            nested.append(ops.charAt(i % 4)).append((char) ('b' + i % 25)).append(')');
        }
        exprs.add(nested.toString());

        // Maximum parenthesis depth around a single operand
        StringBuilder parens = new StringBuilder();
        for (int i = 0; i < 499; i++) {
            parens.append('(');
        }
        parens.append('x');
        for (int i = 0; i < 499; i++) {
            parens.append(')');
        }
        exprs.add(parens.toString());

        // Random expression with parentheses
        exprs.add(randomExpr(len, rand));

        return new TestCase(exprs);
    }

    static char randomOperand(Random rand) {
        return (char) ('a' + rand.nextInt(26));
    }

    // operand op operand op ... with operators drawn from ops, total length len
    static String chain(int len, String ops, Random rand) {
        StringBuilder sb = new StringBuilder();
        sb.append(randomOperand(rand));
        while (sb.length() < len) {
            sb.append(ops.charAt(rand.nextInt(ops.length())));
            sb.append(randomOperand(rand));
        }
        return sb.toString();
    }

    // Random valid expression of exact odd length len, with random parentheses
    static String randomExpr(int len, Random rand) {
        if (len == 1) {
            return String.valueOf(randomOperand(rand));
        }
        if (len >= 3 && rand.nextInt(100) < 20) {
            return "(" + randomExpr(len - 2, rand) + ")";
        }
        int l = 1 + 2 * rand.nextInt((len - 1) / 2); // odd, 1..len-2
        int r = len - 1 - l;                          // odd
        char op = "+-*/".charAt(rand.nextInt(4));
        return randomExpr(l, rand) + op + randomExpr(r, rand);
    }
}
