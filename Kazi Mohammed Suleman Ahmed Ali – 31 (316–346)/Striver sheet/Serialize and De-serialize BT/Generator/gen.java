import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class gen {

    static class Node {
        int val;
        Node left, right;
        Node(int val) {
            this.val = val;
        }
    }

    // Iterative level-order serialization (100% stack-safe)
    public static String serialize(Node root) {
        if (root == null) return "[]";

        List<String> list = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node == null) {
                list.add("null");
            } else {
                list.add(String.valueOf(node.val));
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        int lastNonNull = list.size() - 1;
        while (lastNonNull >= 0 && list.get(lastNonNull).equals("null")) {
            lastNonNull--;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i <= lastNonNull; i++) {
            if (i > 0) sb.append(", ");
            sb.append(list.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    // Helper: random val between 0 and 10000
    static int randVal(Random rnd) {
        return rnd.nextInt(10001);
    }

    // Left skewed
    static Node genLeftSkewed(int n, Random rnd) {
        if (n <= 0) return null;
        Node root = new Node(randVal(rnd));
        Node curr = root;
        for (int i = 1; i < n; i++) {
            curr.left = new Node(randVal(rnd));
            curr = curr.left;
        }
        return root;
    }

    // Right skewed
    static Node genRightSkewed(int n, Random rnd) {
        if (n <= 0) return null;
        Node root = new Node(randVal(rnd));
        Node curr = root;
        for (int i = 1; i < n; i++) {
            curr.right = new Node(randVal(rnd));
            curr = curr.right;
        }
        return root;
    }

    // Zig zag
    static Node genZigZag(int n, Random rnd) {
        if (n <= 0) return null;
        Node root = new Node(randVal(rnd));
        Node curr = root;
        for (int i = 1; i < n; i++) {
            if (i % 2 == 1) {
                curr.left = new Node(randVal(rnd));
                curr = curr.left;
            } else {
                curr.right = new Node(randVal(rnd));
                curr = curr.right;
            }
        }
        return root;
    }

    // Complete / Balanced binary tree
    static Node genCompleteTree(int n, Random rnd) {
        if (n <= 0) return null;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(randVal(rnd));
        }
        for (int i = 0; i < n; i++) {
            int leftIdx = 2 * i + 1;
            int rightIdx = 2 * i + 2;
            if (leftIdx < n) nodes[i].left = nodes[leftIdx];
            if (rightIdx < n) nodes[i].right = nodes[rightIdx];
        }
        return nodes[0];
    }

    // Random tree with N nodes
    static Node genRandomTree(int n, Random rnd) {
        if (n <= 0) return null;
        Node root = new Node(randVal(rnd));
        List<Node> open = new ArrayList<>();
        open.add(root);

        for (int i = 1; i < n; i++) {
            int idx = rnd.nextInt(open.size());
            Node parent = open.get(idx);
            Node child = new Node(randVal(rnd));

            if (parent.left == null && parent.right == null) {
                if (rnd.nextBoolean()) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
            } else if (parent.left == null) {
                parent.left = child;
                open.remove(idx);
            } else {
                parent.right = child;
                open.remove(idx);
            }
            open.add(child);
        }
        return root;
    }

    // Build tree directly from level-order tokens
    static Node fromTokens(String[] tokens) {
        if (tokens.length == 0 || tokens[0].equals("null")) return null;
        Node root = new Node(Integer.parseInt(tokens[0]));
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < tokens.length) {
            Node curr = q.poll();
            if (i < tokens.length) {
                if (!tokens[i].equals("null")) {
                    curr.left = new Node(Integer.parseInt(tokens[i]));
                    q.add(curr.left);
                }
                i++;
            }
            if (i < tokens.length) {
                if (!tokens[i].equals("null")) {
                    curr.right = new Node(Integer.parseInt(tokens[i]));
                    q.add(curr.right);
                }
                i++;
            }
        }
        return root;
    }

    public static void main(String[] args) throws Exception {
        File baseDir = new File("Testcases");
        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        Random rnd = new Random(101);
        List<Node> testcases = new ArrayList<>();

        // 0..3: Sample Cases from TUF
        testcases.add(fromTokens(new String[]{"2", "1", "3"}));
        testcases.add(fromTokens(new String[]{"7", "3", "15", "null", "null", "9", "20"}));
        testcases.add(fromTokens(new String[]{"10", "20", "30", "40", "50", "60"}));
        testcases.add(fromTokens(new String[]{"1", "4", "null", "4", "2"}));

        // 4..11: Boundary & small trees
        testcases.add(new Node(0));      // min val
        testcases.add(new Node(10000));  // max val
        testcases.add(fromTokens(new String[]{"1", "2"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2"}));
        testcases.add(fromTokens(new String[]{"0", "10000"}));
        testcases.add(fromTokens(new String[]{"5000", "null", "10000"}));
        testcases.add(fromTokens(new String[]{"1", "2", "3"}));
        testcases.add(fromTokens(new String[]{"100", "200", "300"}));

        // 12..19: 3 to 7 node variations
        testcases.add(fromTokens(new String[]{"1", "2", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "2", "null", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2", "3"}));
        testcases.add(fromTokens(new String[]{"10", "20", "30", "40", "50", "60", "70"}));
        testcases.add(fromTokens(new String[]{"0", "0", "0", "0", "0", "0", "0"}));
        testcases.add(fromTokens(new String[]{"100", "null", "200", "null", "300", "null", "400"}));
        testcases.add(fromTokens(new String[]{"400", "300", "null", "200", "null", "100"}));

        // 20..29: Left skewed trees of various sizes
        testcases.add(genLeftSkewed(5, rnd));
        testcases.add(genLeftSkewed(10, rnd));
        testcases.add(genLeftSkewed(25, rnd));
        testcases.add(genLeftSkewed(50, rnd));
        testcases.add(genLeftSkewed(100, rnd));
        testcases.add(genLeftSkewed(250, rnd));
        testcases.add(genLeftSkewed(500, rnd));
        testcases.add(genLeftSkewed(1000, rnd));
        testcases.add(genLeftSkewed(2500, rnd));
        testcases.add(genLeftSkewed(5000, rnd));

        // 30..39: Right skewed trees of various sizes
        testcases.add(genRightSkewed(5, rnd));
        testcases.add(genRightSkewed(10, rnd));
        testcases.add(genRightSkewed(25, rnd));
        testcases.add(genRightSkewed(50, rnd));
        testcases.add(genRightSkewed(100, rnd));
        testcases.add(genRightSkewed(250, rnd));
        testcases.add(genRightSkewed(500, rnd));
        testcases.add(genRightSkewed(1000, rnd));
        testcases.add(genRightSkewed(2500, rnd));
        testcases.add(genRightSkewed(5000, rnd));

        // 40..49: Zig-zag trees of various sizes
        testcases.add(genZigZag(5, rnd));
        testcases.add(genZigZag(10, rnd));
        testcases.add(genZigZag(20, rnd));
        testcases.add(genZigZag(50, rnd));
        testcases.add(genZigZag(100, rnd));
        testcases.add(genZigZag(250, rnd));
        testcases.add(genZigZag(500, rnd));
        testcases.add(genZigZag(1000, rnd));
        testcases.add(genZigZag(2500, rnd));
        testcases.add(genZigZag(5000, rnd));

        // 50..59: Complete / balanced binary trees
        testcases.add(genCompleteTree(15, rnd));
        testcases.add(genCompleteTree(31, rnd));
        testcases.add(genCompleteTree(63, rnd));
        testcases.add(genCompleteTree(127, rnd));
        testcases.add(genCompleteTree(255, rnd));
        testcases.add(genCompleteTree(511, rnd));
        testcases.add(genCompleteTree(1023, rnd));
        testcases.add(genCompleteTree(2047, rnd));
        testcases.add(genCompleteTree(4095, rnd));
        testcases.add(genCompleteTree(8191, rnd));

        // 60..79: Small to medium random trees (15 to 400 nodes)
        for (int i = 60; i < 80; i++) {
            int n = 15 + rnd.nextInt(385);
            testcases.add(genRandomTree(n, rnd));
        }

        // 80..89: Large random trees (500 to 2000 nodes)
        for (int i = 80; i < 90; i++) {
            int n = 500 + rnd.nextInt(1500);
            testcases.add(genRandomTree(n, rnd));
        }

        // 90..94: Very large random trees (2500 to 8000 nodes)
        for (int i = 90; i < 95; i++) {
            int n = 2500 + rnd.nextInt(5500);
            testcases.add(genRandomTree(n, rnd));
        }

        // 95..99: Maximum boundary stress tests (N = 10,000 nodes)
        testcases.add(genCompleteTree(10000, rnd)); // 95: complete 10,000
        testcases.add(genRandomTree(10000, rnd));   // 96: random 10,000
        testcases.add(genLeftSkewed(10000, rnd));   // 97: left-skewed 10,000
        testcases.add(genRightSkewed(10000, rnd));  // 98: right-skewed 10,000
        testcases.add(genZigZag(10000, rnd));       // 99: zig-zag 10,000

        System.out.println("Total testcases created: " + testcases.size());

        for (int i = 0; i < 100; i++) {
            Node root = testcases.get(i);
            String treeStr = serialize(root);

            String inFilename = String.format("input%02d.txt", i);
            String outFilename = String.format("output%02d.txt", i);

            try (PrintWriter pwIn = new PrintWriter(new File(inputDir, inFilename))) {
                pwIn.println(treeStr);
            }

            try (PrintWriter pwOut = new PrintWriter(new File(outputDir, outFilename))) {
                pwOut.println(treeStr);
            }
        }

        System.out.println("All 100 input and output testcases generated successfully!");
    }
}
