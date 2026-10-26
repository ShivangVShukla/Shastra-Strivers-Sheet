import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
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

    // In-place Morris flattening
    public static void flatten(Node root) {
        Node curr = root;
        while (curr != null) {
            if (curr.left != null) {
                Node pred = curr.left;
                while (pred.right != null) {
                    pred = pred.right;
                }
                pred.right = curr.right;
                curr.right = curr.left;
                curr.left = null;
            }
            curr = curr.right;
        }
    }

    // Iterative deep copy of a tree (100% stack-safe for N=2000 skewed trees)
    public static Node cloneTree(Node root) {
        if (root == null) return null;
        Node copyRoot = new Node(root.val);
        Queue<Node> srcQ = new ArrayDeque<>();
        Queue<Node> dstQ = new ArrayDeque<>();
        srcQ.add(root);
        dstQ.add(copyRoot);

        while (!srcQ.isEmpty()) {
            Node src = srcQ.poll();
            Node dst = dstQ.poll();

            if (src.left != null) {
                dst.left = new Node(src.left.val);
                srcQ.add(src.left);
                dstQ.add(dst.left);
            }
            if (src.right != null) {
                dst.right = new Node(src.right.val);
                srcQ.add(src.right);
                dstQ.add(dst.right);
            }
        }
        return copyRoot;
    }

    // Serialize tree into level-order bracketed format
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
            if (i > 0) sb.append(",");
            sb.append(list.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    // Helper: random val between -100 and 100
    static int randVal(Random rnd) {
        return rnd.nextInt(201) - 100;
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

    // Custom tree from explicit level-order tokens
    static Node fromTokens(String[] tokens) {
        if (tokens.length == 0 || tokens[0].equals("null")) return null;
        Node root = new Node(Integer.parseInt(tokens[0]));
        Queue<Node> q = new ArrayDeque<>();
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

        Random rnd = new Random(42);
        List<Node> testcases = new ArrayList<>();

        // 0..3: Sample Cases
        testcases.add(fromTokens(new String[]{"1","2","5","3","4","null","6"}));
        testcases.add(null); // []
        testcases.add(fromTokens(new String[]{"0"}));
        testcases.add(fromTokens(new String[]{"1","2","3","4","null","null","5"}));

        // 4..11: Small edge & boundary trees
        testcases.add(new Node(-100)); // min constraint
        testcases.add(new Node(100));  // max constraint
        testcases.add(fromTokens(new String[]{"1", "2"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2"}));
        testcases.add(fromTokens(new String[]{"-50", "-75"}));
        testcases.add(fromTokens(new String[]{"50", "null", "-100"}));
        testcases.add(fromTokens(new String[]{"1", "2", "3"}));
        testcases.add(fromTokens(new String[]{"-10", "20", "-30"}));

        // 12..19: 3 and 4 node variations
        testcases.add(fromTokens(new String[]{"1", "2", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "2", "null", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2", "3"}));
        testcases.add(fromTokens(new String[]{"1", "2", "3", "4", "5", "6", "7"}));
        testcases.add(fromTokens(new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7"}));
        testcases.add(fromTokens(new String[]{"0", "0", "0", "0", "0"}));
        testcases.add(fromTokens(new String[]{"10", "null", "20", "null", "30", "null", "40"}));

        // 20..29: Left skewed trees of various sizes
        testcases.add(genLeftSkewed(5, rnd));
        testcases.add(genLeftSkewed(10, rnd));
        testcases.add(genLeftSkewed(25, rnd));
        testcases.add(genLeftSkewed(50, rnd));
        testcases.add(genLeftSkewed(100, rnd));
        testcases.add(genLeftSkewed(250, rnd));
        testcases.add(genLeftSkewed(500, rnd));
        testcases.add(genLeftSkewed(1000, rnd));
        testcases.add(genLeftSkewed(1500, rnd));
        testcases.add(genLeftSkewed(2000, rnd)); // Max constraint skewed

        // 30..39: Right skewed trees (already flattened) of various sizes
        testcases.add(genRightSkewed(5, rnd));
        testcases.add(genRightSkewed(10, rnd));
        testcases.add(genRightSkewed(25, rnd));
        testcases.add(genRightSkewed(50, rnd));
        testcases.add(genRightSkewed(100, rnd));
        testcases.add(genRightSkewed(250, rnd));
        testcases.add(genRightSkewed(500, rnd));
        testcases.add(genRightSkewed(1000, rnd));
        testcases.add(genRightSkewed(1500, rnd));
        testcases.add(genRightSkewed(2000, rnd)); // Max constraint right-skewed

        // 40..49: Zig-zag trees of various sizes
        testcases.add(genZigZag(5, rnd));
        testcases.add(genZigZag(10, rnd));
        testcases.add(genZigZag(20, rnd));
        testcases.add(genZigZag(50, rnd));
        testcases.add(genZigZag(100, rnd));
        testcases.add(genZigZag(250, rnd));
        testcases.add(genZigZag(500, rnd));
        testcases.add(genZigZag(1000, rnd));
        testcases.add(genZigZag(1500, rnd));
        testcases.add(genZigZag(2000, rnd));

        // 50..59: Complete / balanced binary trees
        testcases.add(genCompleteTree(15, rnd));
        testcases.add(genCompleteTree(31, rnd));
        testcases.add(genCompleteTree(63, rnd));
        testcases.add(genCompleteTree(127, rnd));
        testcases.add(genCompleteTree(255, rnd));
        testcases.add(genCompleteTree(511, rnd));
        testcases.add(genCompleteTree(750, rnd));
        testcases.add(genCompleteTree(1023, rnd));
        testcases.add(genCompleteTree(1500, rnd));
        testcases.add(genCompleteTree(2000, rnd)); // Max constraint complete

        // 60..79: Random trees of small to medium sizes
        for (int i = 60; i < 80; i++) {
            int n = 10 + rnd.nextInt(300);
            testcases.add(genRandomTree(n, rnd));
        }

        // 80..89: Large random trees (400 to 1200 nodes)
        for (int i = 80; i < 90; i++) {
            int n = 400 + rnd.nextInt(800);
            testcases.add(genRandomTree(n, rnd));
        }

        // 90..94: Very large random trees (1400 to 1950 nodes)
        for (int i = 90; i < 95; i++) {
            int n = 1400 + rnd.nextInt(550);
            testcases.add(genRandomTree(n, rnd));
        }

        // 95..99: Maximum boundary stress tests (exactly N = 2000 nodes)
        testcases.add(genRandomTree(2000, rnd)); // 95: random 2000
        testcases.add(genRandomTree(2000, rnd)); // 96: random 2000
        testcases.add(genCompleteTree(2000, rnd)); // 97: complete 2000
        testcases.add(genLeftSkewed(2000, rnd));  // 98: left-skewed 2000
        testcases.add(genRightSkewed(2000, rnd)); // 99: right-skewed 2000

        System.out.println("Total testcases created: " + testcases.size());

        for (int i = 0; i < 100; i++) {
            Node original = testcases.get(i);

            // Serialize original for input
            String inputStr = serialize(original);

            // Clone and flatten for output
            Node copy = cloneTree(original);
            flatten(copy);
            String outputStr = serialize(copy);

            String inFilename = String.format("input%02d.txt", i);
            String outFilename = String.format("output%02d.txt", i);

            try (PrintWriter pwIn = new PrintWriter(new File(inputDir, inFilename))) {
                pwIn.println(inputStr);
            }

            try (PrintWriter pwOut = new PrintWriter(new File(outputDir, outFilename))) {
                pwOut.println(outputStr);
            }
        }

        System.out.println("All 100 input and output testcases generated successfully!");
    }
}
