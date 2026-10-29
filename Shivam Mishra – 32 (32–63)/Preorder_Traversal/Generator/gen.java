
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static class TestCase {
        String input;
        String output;

        TestCase(String input, String output) {
            this.input = input;
            this.output = output;
        }
    }

    static void preorder(
            Integer[] tree, int index, List<Integer> ans) {

        if (index >= tree.length || tree[index] == null)
            return;

        ans.add(tree[index]);
        preorder(tree, 2 * index + 1, ans);
        preorder(tree, 2 * index + 2, ans);
    }

    static String formatTree(Integer[] tree) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < tree.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(tree[i] == null ? "null" : tree[i]);
        }
        return sb.append("]").toString();
    }

    static TestCase createCase(Integer[] tree) {
        List<Integer> ans = new ArrayList<>();
        preorder(tree, 0, ans);
        return new TestCase(formatTree(tree), ans.toString());
    }

    static Integer[] randomTree(Random random, int n) {
        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < n; i++)
            values.add(random.nextInt(201) - 100);

        Collections.shuffle(values, random);

        Integer[] tree = new Integer[2 * n + 1];
        tree[0] = values.get(0);

        List<Integer> available = new ArrayList<>();
        available.add(0);

        for (int i = 1; i < n; i++) {
            int p = random.nextInt(available.size());
            int parent = available.get(p);

            List<Integer> slots = new ArrayList<>();
            int left = 2 * parent + 1;
            int right = 2 * parent + 2;

            if (tree[left] == null) slots.add(left);
            if (tree[right] == null) slots.add(right);

            int child = slots.get(random.nextInt(slots.size()));
            tree[child] = values.get(i);
            available.add(child);

            if (tree[left] != null && tree[right] != null)
                available.remove(p);
        }

        int last = tree.length - 1;
        while (last >= 0 && tree[last] == null)
            last--;

        return Arrays.copyOf(tree, last + 1);
    }

    static Integer[] leftSkewed(int n) {
        Integer[] tree = new Integer[2 * n - 1];

        for (int i = 0; i < n; i++)
            tree[2 * i] = i + 1;

        return tree;
    }

    static Integer[] rightSkewed(int n) {
        Integer[] tree = new Integer[2 * n - 1];

        for (int i = 0; i < n; i++)
            tree[i] = i + 1;

        return tree;
    }

    static Integer[] balancedTree(int n) {
        Integer[] tree = new Integer[2 * n + 1];
        fillBalanced(tree, 0, 1, n);

        int last = tree.length - 1;
        while (last >= 0 && tree[last] == null)
            last--;

        return Arrays.copyOf(tree, last + 1);
    }

    static int fillBalanced(
            Integer[] tree, int index, int value, int remaining) {

        if (remaining <= 0 || index >= tree.length)
            return value;

        tree[index] = value++;
        int leftCount = (remaining - 1) / 2;
        int rightCount = remaining - 1 - leftCount;

        value = fillBalanced(
                tree, 2 * index + 1, value, leftCount);

        value = fillBalanced(
                tree, 2 * index + 2, value, rightCount);

        return value;
    }

    public static void main(String[] args) throws IOException {
        Path base = Paths.get("Testcases");

        if (!Files.exists(base) &&
                Files.exists(Paths.get("../Testcases"))) {
            base = Paths.get("../Testcases");
        }

        Path inputDir = base.resolve("input");
        Path outputDir = base.resolve("output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        Random random = new Random(42);
        List<TestCase> tests = new ArrayList<>();

        tests.add(createCase(new Integer[]{1}));
        tests.add(createCase(new Integer[]{-1}));
        tests.add(createCase(new Integer[]{0}));
        tests.add(createCase(new Integer[]{1, 2, 3}));
        tests.add(createCase(new Integer[]{1, 2, null, 3, null}));
        tests.add(createCase(new Integer[]{1, null, 2, null, 3}));
        tests.add(createCase(new Integer[]{1, 2, 3, 4, 5, 6, 7}));
        tests.add(createCase(new Integer[]{-100, 0, 100}));
        tests.add(createCase(new Integer[]{5, 1, null, 2, null}));
        tests.add(createCase(new Integer[]{10, null, 20, 30}));

        tests.add(createCase(leftSkewed(10)));
        tests.add(createCase(rightSkewed(10)));
        tests.add(createCase(balancedTree(15)));
        tests.add(createCase(balancedTree(31)));
        tests.add(createCase(leftSkewed(50)));
        tests.add(createCase(rightSkewed(50)));
        tests.add(createCase(balancedTree(63)));
        tests.add(createCase(leftSkewed(100)));
        tests.add(createCase(rightSkewed(100)));
        tests.add(createCase(balancedTree(100)));

        while (tests.size() < 101) {
            int n = 1 + random.nextInt(100);
            tests.add(createCase(randomTree(random, n)));
        }

        for (int i = 0; i < tests.size(); i++) {
            String id = String.format("%03d", i + 1);
            TestCase tc = tests.get(i);

            Files.writeString(
                    inputDir.resolve(id + ".txt"),
                    tc.input + System.lineSeparator());

            Files.writeString(
                    outputDir.resolve(id + ".txt"),
                    tc.output + System.lineSeparator());
        }

        System.out.println("Generated 101 test cases successfully.");
        System.out.println("Input: " + inputDir.toAbsolutePath());
        System.out.println("Output: " + outputDir.toAbsolutePath());
    }
}
