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

    // Reference solution: ArrayDeque-backed queue (independent of the linked
    // list implementation in Solution.java). push -> "null", pop/peek -> value,
    // isEmpty -> "true"/"false".
    static List<String> solve(List<String> ops) {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        List<String> ans = new ArrayList<>();
        for (String op : ops) {
            String[] p = op.split(" ");
            switch (p[0]) {
                case "push":
                    queue.addLast(Integer.parseInt(p[1]));
                    ans.add("null");
                    break;
                case "pop":
                    ans.add(String.valueOf(queue.pollFirst()));
                    break;
                case "peek":
                    ans.add(String.valueOf(queue.peekFirst()));
                    break;
                default:
                    ans.add(String.valueOf(queue.isEmpty()));
            }
        }
        return ans;
    }

    // Example 1 from the problem statement
    static TestCase generateTC0() {
        List<String> ops = Arrays.asList("push 3", "push 7", "peek", "pop", "isEmpty");
        return new TestCase(ops);
    }

    // Example 2: single element pushed then popped, queue becomes empty
    static TestCase generateTC1() {
        List<String> ops = Arrays.asList("push 2", "pop", "isEmpty");
        return new TestCase(ops);
    }

    // isEmpty on a new queue, fill, drain completely (FIFO order check),
    // then reuse the queue (exposes a tail pointer not reset on empty)
    static TestCase generateTC2() {
        List<String> ops = new ArrayList<>();
        ops.add("isEmpty");
        for (int v = 10; v <= 50; v += 10) {
            ops.add("push " + v);
        }
        ops.add("peek");
        for (int i = 0; i < 5; i++) {
            ops.add("pop");
        }
        ops.add("isEmpty");
        ops.add("push 100");
        ops.add("push 99");
        ops.add("peek");
        ops.add("pop");
        ops.add("peek");
        ops.add("pop");
        ops.add("isEmpty");
        return new TestCase(ops);
    }

    // Maximum Q (100) random valid operations; pop/peek only when non-empty
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
                ops.add("peek");
            } else {
                ops.add("isEmpty");
            }
        }
        return new TestCase(ops);
    }
}
