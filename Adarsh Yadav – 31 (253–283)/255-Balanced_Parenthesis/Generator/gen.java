import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
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
            inWriter.println(tc.strs.size());
            for (String s : tc.strs) {
                inWriter.println(s);
            }
            inWriter.close();

            String[] ans = solve(tc.strs);

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (String a : ans) {
                outWriter.println(a);
            }
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static class TestCase {
        List<String> strs;

        TestCase(List<String> strs) {
            this.strs = strs;
        }
    }

    // Reference solution: repeatedly delete adjacent matching pairs "()",
    // "[]", "{}"; the string is balanced iff it reduces to the empty string.
    // (Different technique from the stack used in Solution.java.)
    static String[] solve(List<String> strs) {
        String[] ans = new String[strs.size()];
        for (int i = 0; i < strs.size(); i++) {
            String s = strs.get(i);
            String prev;
            do {
                prev = s;
                s = s.replace("()", "").replace("[]", "").replace("{}", "");
            } while (!s.equals(prev));
            ans[i] = s.isEmpty() ? "True" : "False";
        }
        return ans;
    }

    // Examples from the problem statement, plus the "Now Your Turn" input
    static TestCase generateTC0() {
        List<String> strs = Arrays.asList("()[{}()]", "[()", "{[()]}");
        return new TestCase(strs);
    }

    // Tiny strings: single brackets, mismatched types, interleaved pairs
    static TestCase generateTC1() {
        List<String> strs = Arrays.asList(
                "(", ")", "()", "{}", "[]", "(]", "([)]", "{[}]", "{[]}", "((()))");
        return new TestCase(strs);
    }

    // Structural traps: wrong order, only openers/closers, odd length,
    // equal counts but invalid order, deep valid nesting, concatenation
    static TestCase generateTC2() {
        List<String> strs = Arrays.asList(
                ")(",
                "}{",
                "((((",
                "))))",
                "(()",
                "())",
                "(){}[]",
                "{{[[(())]]}}",
                "([{}])([{}])",
                "(((]]]");
        return new TestCase(strs);
    }

    // Maximum length (10^4) cases
    static TestCase generateTC3() {
        List<String> strs = new ArrayList<>();
        Random rand = new Random(2024);
        int n = 10000;

        // "()" repeated: valid
        strs.add(repeat("()", n / 2));

        // Cyclic-type deep nesting: valid
        StringBuilder nest = new StringBuilder();
        String open = "([{";
        String close = ")]}";
        for (int i = 0; i < n / 2; i++) {
            nest.append(open.charAt(i % 3));
        }
        for (int i = n / 2 - 1; i >= 0; i--) {
            nest.append(close.charAt(i % 3));
        }
        strs.add(nest.toString());

        // Only openers: invalid
        strs.add(repeat("(", n));

        // Random valid string
        String valid = randomValid(n, rand);
        strs.add(valid);

        // Same string with one closing bracket changed to a different type: invalid
        strs.add(corrupt(valid));

        // Equal counts of openers and closers but last two swapped: invalid
        strs.add(repeat("()", n / 2 - 1) + ")(");

        return new TestCase(strs);
    }

    static String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    // Random balanced string of even length len, built with an explicit stack
    static String randomValid(int len, Random rand) {
        String open = "([{";
        String close = ")]}";
        char[] stack = new char[len];
        int sp = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int remaining = len - i;
            boolean mustClose = sp == remaining;
            boolean mustOpen = sp == 0;
            boolean doOpen = mustOpen ? true : (mustClose ? false : rand.nextBoolean());
            if (doOpen) {
                char c = open.charAt(rand.nextInt(3));
                stack[sp++] = c;
                sb.append(c);
            } else {
                char c = stack[--sp];
                sb.append(close.charAt(open.indexOf(c)));
            }
        }
        return sb.toString();
    }

    // Change the first closing bracket at or after the middle to another closing type
    static String corrupt(String s) {
        char[] a = s.toCharArray();
        for (int i = a.length / 2; i < a.length; i++) {
            if (a[i] == ')' || a[i] == ']' || a[i] == '}') {
                a[i] = a[i] == ')' ? ']' : ')';
                break;
            }
        }
        return new String(a);
    }
}
