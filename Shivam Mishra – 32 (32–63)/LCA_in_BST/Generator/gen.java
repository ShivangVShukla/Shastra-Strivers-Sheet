
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class gen {
    private static final int TOTAL = 101;
    private static final Random random = new Random();

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

    private static Node insert(Node root, int value) {
        if (root == null) {
            return new Node(value);
        }

        if (value < root.value) {
            root.left = insert(root.left, value);
        } else if (value > root.value) {
            root.right = insert(root.right, value);
        }

        return root;
    }

    private static Node buildBST(List<Integer> values) {
        Node root = null;

        for (int value : values) {
            root = insert(root, value);
        }

        return root;
    }

    private static List<Integer> generateValues(int test) {
        List<Integer> values = new ArrayList<>();

        int n;

        if (test == 1) {
            return Arrays.asList(5, 3, 6, 2, 4, 7);
        }

        if (test == 2) {
            return Arrays.asList(10);
        }

        if (test == 3) {
            return Arrays.asList(8, 4, 12, 2, 6, 10, 14);
        }

        if (test == 4) {
            return Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        }

        if (test == 5) {
            return Arrays.asList(7, 6, 5, 4, 3, 2, 1);
        }

        if (test == TOTAL) {
            n = 500;
        } else if (test % 10 == 0) {
            n = 100 + random.nextInt(201);
        } else {
            n = 1 + random.nextInt(80);
        }

        Set<Integer> unique = new HashSet<>();

        while (unique.size() < n) {
            unique.add(1 + random.nextInt(100_000));
        }

        values.addAll(unique);

        // Randomize insertion order to create different BST shapes.
        Collections.shuffle(values, random);

        return values;
    }

    private static String serialize(Node root) {
        if (root == null) {
            return "null";
        }

        List<String> tokens = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (node == null) {
                tokens.add("null");
            } else {
                tokens.add(Integer.toString(node.value));
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        while (!tokens.isEmpty()
                && tokens.get(tokens.size() - 1).equals("null")) {
            tokens.remove(tokens.size() - 1);
        }

        return String.join(" ", tokens);
    }

    private static int lowestCommonAncestor(
            Node root, int p, int q) {

        int low = Math.min(p, q);
        int high = Math.max(p, q);

        Node current = root;

        while (current != null) {
            if (high < current.value) {
                current = current.left;
            } else if (low > current.value) {
                current = current.right;
            } else {
                return current.value;
            }
        }

        throw new IllegalArgumentException(
                "Target node not found in BST");
    }

    private static void writeFile(
            Path directory, String filename, String content)
            throws IOException {

        Files.writeString(
                directory.resolve(filename),
                content + System.lineSeparator());
    }

    public static void main(String[] args) throws IOException {
        Path baseDir = findTestcasesDir();

        Path inputDir = baseDir.resolve("input");
        Path outputDir = baseDir.resolve("output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int test = 1; test <= TOTAL; test++) {
            List<Integer> values = generateValues(test);
            Node root = buildBST(values);

            // Select actual nodes from the generated BST.
            int p = values.get(random.nextInt(values.size()));
            int q;

            if (test % 7 == 0) {
                // Include cases where both targets are identical.
                q = p;
            } else {
                q = values.get(random.nextInt(values.size()));
            }

            String input = serialize(root) + " | " + p + " " + q;

            int answer = lowestCommonAncestor(root, p, q);

            String suffix = String.format(
                    Locale.ROOT, "%02d", test);

            writeFile(
                    inputDir,
                    "input" + suffix + ".txt",
                    input);

            writeFile(
                    outputDir,
                    "output" + suffix + ".txt",
                    Integer.toString(answer));

            System.out.println(
                    "Generated test case " + test
                    + " | LCA = " + answer);
        }

        System.out.println(
                "\nGenerated " + TOTAL + " test cases successfully.");

        System.out.println(
                "Input directory: " + inputDir.toAbsolutePath());

        System.out.println(
                "Output directory: " + outputDir.toAbsolutePath());
    }
}
