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

        Random random = new Random(20261027L);

        for (int tc = 0; tc < TESTCASE_COUNT; tc++) {
            List<String> operations = createOperations(tc, random);
            String index = String.format("%02d", tc);

            File inputFile = new File(inputDir, "input" + index + ".txt");
            File outputFile = new File(outputDir, "output" + index + ".txt");

            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(inputFile)))) {
                writer.println(1);
                writer.println(operations.size());
                for (String operation : operations) writer.println(operation);
            }

            List<String> answers = referenceSolve(operations);
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)))) {
                for (String answer : answers) writer.println(answer);
            }
        }

        System.out.println("Generated " + TESTCASE_COUNT + " input/output testcase pairs successfully.");
    }

    private static List<String> createOperations(int id, Random random) {
        List<String> ops = new ArrayList<>();

        if (id == 0) {
            return Arrays.asList("push -2", "push 0", "push -3", "getMin",
                    "pop", "top", "getMin", "top");
        }
        if (id == 1) {
            return Arrays.asList("push 5", "push 1", "getMin", "push 3",
                    "pop", "getMin", "top", "top");
        }
        if (id == 2) {
            return Arrays.asList("push 10", "push 15", "push 5", "top",
                    "getMin", "pop", "getMin");
        }

        int targetOps;
        if (id < 25) targetOps = 10 + random.nextInt(41);
        else if (id < 70) targetOps = 100 + random.nextInt(901);
        else targetOps = (id % 4 == 0) ? 50_000 : 1_000 + random.nextInt(4_001);

        int size = 0;
        int currentMin = Integer.MAX_VALUE;

        for (int i = 0; i < targetOps; i++) {
            int choice = random.nextInt(100);
            if (size == 0 || choice < 45) {
                int value;
                switch (id % 7) {
                    case 0: value = random.nextInt(21) - 10; break;
                    case 1: value = (i % 2 == 0) ? -100_000 : 100_000; break;
                    case 2: value = size - 50; break;
                    case 3: value = 100_000 - size; break;
                    case 4: value = -100_000 + random.nextInt(5); break;
                    case 5: value = random.nextInt(200_001) - 100_000; break;
                    default: value = (size % 2 == 0) ? 7 : -7; break;
                }
                ops.add("push " + value);
                currentMin = Math.min(currentMin, value);
                size++;
            } else if (choice < 65) {
                ops.add("pop");
                size--;
                currentMin = Integer.MAX_VALUE;
                // Recompute minimum from the current abstract stack via reference state below.
                // For generation correctness, choose operations using a tracked value stack instead.
                // This branch is replaced by the stack-aware generator in the implementation below.
            } else if (choice < 82) {
                ops.add("top");
            } else {
                ops.add("getMin");
            }
        }

        // Repair sequence using a stack-aware deterministic generator so all queries are valid.
        return createValidOperations(id, targetOps, random);
    }

    private static List<String> createValidOperations(int id, int targetOps, Random random) {
        List<String> ops = new ArrayList<>();
        ArrayList<Integer> stack = new ArrayList<>();
        for (int i = 0; i < targetOps; i++) {
            int choice = random.nextInt(100);
            if (stack.isEmpty() || choice < 45) {
                int value;
                switch (id % 7) {
                    case 0: value = random.nextInt(21) - 10; break;
                    case 1: value = (i % 2 == 0) ? -100_000 : 100_000; break;
                    case 2: value = stack.size() - 50; break;
                    case 3: value = 100_000 - stack.size(); break;
                    case 4: value = -100_000 + random.nextInt(5); break;
                    case 5: value = random.nextInt(200_001) - 100_000; break;
                    default: value = (stack.size() % 2 == 0) ? 7 : -7; break;
                }
                ops.add("push " + value);
                stack.add(value);
            } else if (choice < 65) {
                ops.add("pop");
                stack.remove(stack.size() - 1);
            } else if (choice < 82) {
                ops.add("top");
            } else {
                ops.add("getMin");
            }
        }
        if (stack.isEmpty()) ops.add("push 0");
        return ops;
    }

    private static List<String> referenceSolve(List<String> operations) {
        List<Integer> stack = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        for (String line : operations) {
            StringTokenizer st = new StringTokenizer(line);
            String op = st.nextToken();
            switch (op) {
                case "push":
                    stack.add(Integer.parseInt(st.nextToken()));
                    break;
                case "pop":
                    stack.remove(stack.size() - 1);
                    break;
                case "top":
                    answers.add(Integer.toString(stack.get(stack.size() - 1)));
                    break;
                case "getMin":
                    int min = Integer.MAX_VALUE;
                    for (int value : stack) min = Math.min(min, value);
                    answers.add(Integer.toString(min));
                    break;
            }
        }
        return answers;
    }
}
