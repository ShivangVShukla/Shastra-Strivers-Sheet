
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {
    private static final Random random = new Random();
    private static final int TOTAL = 101;

    static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
        }
    }

    private static Path findTestcasesDir() {
        Path current = Paths.get("Testcases");
        if (Files.isDirectory(current)) {
            return current;
        }

        Path parent = Paths.get("../Testcases");
        if (Files.isDirectory(parent)) {
            return parent;
        }

        return current;
    }

    private static int randomValue() {
        return random.nextInt(201) - 100;
    }

    private static Node generateTree(int test) {
        if (test == 1) {
            return null;
        }

        if (test == 2) {
            return new Node(1);
        }

        int n;

        if (test == 101) {
            n = 10_000;
        } else if (test % 10 == 0) {
            n = 100 + random.nextInt(401);
        } else {
            n = 1 + random.nextInt(60);
        }

        Node root = new Node(randomValue());

        // Some cases are deliberately skewed.
        if (test % 5 == 0) {
            Node current = root;

            for (int i = 1; i < n; i++) {
                Node next = new Node(randomValue());

                if (random.nextBoolean()) {
                    current.left = next;
                } else {
                    current.right = next;
                }

                current = next;
            }

            return root;
        }

        // Some cases use a complete, balanced-like shape.
        if (test % 5 == 1) {
            List<Node> nodes = new ArrayList<>();
            nodes.add(root);

            for (int i = 1; i < n; i++) {
                nodes.add(new Node(randomValue()));
            }

            for (int i = 0; i < n; i++) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;

                if (left < n) {
                    nodes.get(i).left = nodes.get(left);
                }

                if (right < n) {
                    nodes.get(i).right = nodes.get(right);
                }
            }

            return root;
        }

        // Generate a random binary tree by selecting
        // an available parent slot for every new node.
        List<Node> available = new ArrayList<>();
        available.add(root);

        for (int i = 1; i < n; i++) {
            Node child = new Node(randomValue());

            while (true) {
                int index = random.nextInt(available.size());
                Node parent = available.get(index);

                boolean leftFirst = random.nextBoolean();

                if (leftFirst && parent.left == null) {
                    parent.left = child;
                    break;
                }

                if (!leftFirst && parent.right == null) {
                    parent.right = child;
                    break;
                }

                if (parent.left == null) {
                    parent.left = child;
                    break;
                }

                if (parent.right == null) {
                    parent.right = child;
                    break;
                }

                available.remove(index);
            }

            available.add(child);
        }

        return root;
    }

    private static String serialize(Node root) {
        if (root == null) {
            return "null";
        }

        List<String> tokens = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (node == null) {
                tokens.add("null");
                continue;
            }

            tokens.add(Integer.toString(node.value));
            queue.add(node.left);
            queue.add(node.right);
        }

        // Remove unnecessary trailing null markers.
        while (!tokens.isEmpty()
                && tokens.get(tokens.size() - 1).equals("null")) {
            tokens.remove(tokens.size() - 1);
        }

        return String.join(" ", tokens);
    }

    // Returns -1 when the subtree is unbalanced.
    private static int height(Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = height(node.left);

        if (leftHeight == -1) {
            return -1;
        }

        int rightHeight = height(node.right);

        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }

    private static String expectedOutput(Node root) {
        return height(root) == -1 ? "No" : "Yes";
    }

    private static void writeFile(
            Path directory, String filename, String content)
            throws IOException {
        Files.writeString(
                directory.resolve(filename),
                content + System.lineSeparator());
    }

    public static void main(String[] args) throws IOException {
        Path base = findTestcasesDir();
        Path inputDir = base.resolve("input");
        Path outputDir = base.resolve("output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int test = 1; test <= TOTAL; test++) {
            Node root = generateTree(test);

            String input = serialize(root);
            String output = expectedOutput(root);

            String inputName = String.format(
                    Locale.ROOT, "input%02d.txt", test);
            String outputName = String.format(
                    Locale.ROOT, "output%02d.txt", test);

            writeFile(inputDir, inputName, input);
            writeFile(outputDir, outputName, output);

            System.out.println(
                    "Generated test case " + test
                    + " -> " + output);
        }

        System.out.println("Generated " + TOTAL + " test cases.");
        System.out.println("Input folder: " + inputDir.toAbsolutePath());
        System.out.println("Output folder: " + outputDir.toAbsolutePath());
    }
}
