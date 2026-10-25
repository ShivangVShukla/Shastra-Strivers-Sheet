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
            inWriter.println(tc.ops.size());
            for (String op : tc.ops) {
                inWriter.println(op);
            }
            inWriter.close();

            List<String> ans = solve(tc.ops);

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (String a : ans) {
                outWriter.println(a);
            }
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static class TestCase {
        List<String> ops;

        TestCase(List<String> ops) {
            this.ops = ops;
        }
    }

    // Reference solution: array-backed stack (independent of the linked list
    // implementation in Solution.java). push -> "null", pop/top -> value,
    // isEmpty -> "true"/"false".
    static List<String> solve(List<String> ops) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        List<String> ans = new ArrayList<>();
        for (String op : ops) {
            String[] p = op.split(" ");
            switch (p[0]) {
                case "push":
                    stack.push(Integer.parseInt(p[1]));
                    ans.add("null");
                    break;
                case "pop":
                    ans.add(String.valueOf(stack.pop()));
                    break;
                case "top":
                    ans.add(String.valueOf(stack.peek()));
                    break;
                default:
                    ans.add(String.valueOf(stack.isEmpty()));
            }
        }
        return ans;
    }

    // Example 1 from the problem statement
    static TestCase generateTC0() {
        List<String> ops = Arrays.asList("push 3", "push 7", "pop", "top", "isEmpty");
        return new TestCase(ops);
    }

    // Example 2: isEmpty on a brand-new stack
    static TestCase generateTC1() {
        List<String> ops = Arrays.asList("isEmpty");
        return new TestCase(ops);
    }

    // Fill the stack, drain it completely (LIFO order check), then reuse it
    static TestCase generateTC2() {
        List<String> ops = new ArrayList<>();
        for (int v = 10; v <= 50; v += 10) {
            ops.add("push " + v);
        }
        ops.add("top");
        for (int i = 0; i < 5; i++) {
            ops.add("pop");
        }
        ops.add("isEmpty");
        ops.add("push 100");
        ops.add("top");
        ops.add("isEmpty");
        ops.add("pop");
        ops.add("isEmpty");
        return new TestCase(ops);
    }

    // Maximum Q (100) random valid operations; pop/top only when non-empty
    static TestCase generateTC3() {
        List<String> ops = new ArrayList<>();
        Random rand = new Random(2024);
        int size = 0;
        for (int i = 0; i < 100; i++) {
            int r = rand.nextInt(10);
            if (r <= 3 || ((r == 4 || r == 5 || r == 6 || r == 7) && size == 0)) {
                ops.add("push " + (1 + rand.nextInt(100)));
                size++;
            } else if (r <= 5) {
                ops.add("pop");
                size--;
            } else if (r <= 7) {
                ops.add("top");
            } else {
                ops.add("isEmpty");
            }
        }
        return new TestCase(ops);
    }
}
