
import java.io.*;
import java.util.*;

public class gen {

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static class TestCase {
        int[] preorder;
        int[] inorder;

        TestCase(int[] preorder, int[] inorder) {
            this.preorder = preorder;
            this.inorder = inorder;
        }
    }

    static Random random = new Random();

    static TreeNode buildRandomTree(List<Integer> values) {
        if (values.isEmpty()) {
            return null;
        }

        int rootIndex = random.nextInt(values.size());
        TreeNode root = new TreeNode(values.get(rootIndex));

        List<Integer> leftValues =
                new ArrayList<>(values.subList(0, rootIndex));

        List<Integer> rightValues =
                new ArrayList<>(values.subList(rootIndex + 1,
                                               values.size()));

        root.left = buildRandomTree(leftValues);
        root.right = buildRandomTree(rightValues);

        return root;
    }

    static TreeNode buildSpecialTree(List<Integer> values, int type) {
        if (values.isEmpty()) {
            return null;
        }

        int mid = values.size() / 2;

        if (type == 0) {
            // Left-skewed tree
            TreeNode root = new TreeNode(values.get(values.size() - 1));
            root.left = buildSpecialTree(
                    values.subList(0, values.size() - 1), type);
            return root;
        }

        if (type == 1) {
            // Right-skewed tree
            TreeNode root = new TreeNode(values.get(0));
            root.right = buildSpecialTree(
                    values.subList(1, values.size()), type);
            return root;
        }

        // Balanced tree
        TreeNode root = new TreeNode(values.get(mid));

        root.left = buildSpecialTree(
                values.subList(0, mid), type);

        root.right = buildSpecialTree(
                values.subList(mid + 1, values.size()), type);

        return root;
    }

    static void preorder(TreeNode root, List<Integer> result) {
        if (root == null) return;

        result.add(root.val);
        preorder(root.left, result);
        preorder(root.right, result);
    }

    static void inorder(TreeNode root, List<Integer> result) {
        if (root == null) return;

        inorder(root.left, result);
        result.add(root.val);
        inorder(root.right, result);
    }

    static TestCase generateTestCase(int testNumber) {
        int n;

        if (testNumber == 1) {
            n = 1;
        } else if (testNumber == 2) {
            n = 2;
        } else if (testNumber == 3) {
            n = 3;
        } else if (testNumber == 4) {
            n = 7;
        } else if (testNumber == 5) {
            n = 15;
        } else if (testNumber == 6) {
            n = 100;
        } else if (testNumber == 7) {
            n = 1000;
        } else {
            n = random.nextInt(100) + 1;
        }

        List<Integer> values = new ArrayList<>();

        // Unique values within the problem constraints.
        for (int i = 0; i < n; i++) {
            values.add(i - n / 2);
        }

        Collections.shuffle(values, random);

        TreeNode root;

        if (testNumber == 1) {
            root = new TreeNode(values.get(0));
        } else if (testNumber == 2) {
            root = buildSpecialTree(values, testNumber % 3);
        } else if (testNumber == 3) {
            root = buildSpecialTree(values, 0);
        } else if (testNumber == 4) {
            root = buildSpecialTree(values, 1);
        } else if (testNumber == 5) {
            root = buildSpecialTree(values, 2);
        } else {
            root = buildRandomTree(values);
        }

        List<Integer> pre = new ArrayList<>();
        List<Integer> in = new ArrayList<>();

        preorder(root, pre);
        inorder(root, in);

        return new TestCase(
                pre.stream().mapToInt(Integer::intValue).toArray(),
                in.stream().mapToInt(Integer::intValue).toArray()
        );
    }

    static void writeArray(PrintWriter writer, int[] arr)
            throws IOException {
        writer.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) writer.print(", ");
            writer.print(arr[i]);
        }
        writer.println("]");
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

        for (int i = 1; i <= 101; i++) {
            TestCase test = generateTestCase(i);

            try (PrintWriter input = new PrintWriter(
                        new File(inputDir, i + ".txt"));
                 PrintWriter output = new PrintWriter(
                        new File(outputDir, i + ".txt"))) {

                writeArray(input, test.preorder);
                writeArray(input, test.inorder);

                // Expected output: level-order tree representation.
                output.println("Generate expected output using solution.cpp.");
            }
        }

        System.out.println(
                "Created 101 test case inputs in " + baseDir);
    }
}
