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

    // Preorder traversal for expected output
    public static List<Integer> getPreorder(Node root) {
        List<Integer> list = new ArrayList<>();
        Node curr = root;
        while (curr != null) {
            if (curr.left == null) {
                list.add(curr.val);
                curr = curr.right;
            } else {
                Node prev = curr.left;
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }
                if (prev.right == null) {
                    list.add(curr.val);
                    prev.right = curr;
                    curr = curr.left;
                } else {
                    prev.right = null;
                    curr = curr.right;
                }
            }
        }
        return list;
    }

    // Level-order serialization for input tree
    public static String serializeTree(Node root) {
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

    public static String formatList(List<Integer> list) {
        if (list == null || list.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(list.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    // Helper: random val in [-100, 100]
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

        Random rnd = new Random(999);
        List<Node> testcases = new ArrayList<>();

        // 0..3: Sample Cases from TUF
        testcases.add(fromTokens(new String[]{"1", "4", "null", "4", "2"}));
        testcases.add(new Node(1));
        testcases.add(fromTokens(new String[]{"1", "4", "2", "9", "null", "null", "6"}));
        testcases.add(fromTokens(new String[]{"1", "2", "3", "null", "4", "5"}));

        // 4..11: Boundary & small trees
        testcases.add(new Node(0));
        testcases.add(new Node(-100)); // min val
        testcases.add(new Node(100));  // max val
        testcases.add(fromTokens(new String[]{"1", "2"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2"}));
        testcases.add(fromTokens(new String[]{"-50", "-100"}));
        testcases.add(fromTokens(new String[]{"1", "2", "3"}));
        testcases.add(fromTokens(new String[]{"-10", "20", "-30"}));

        // 12..19: 3 to 7 node variations
        testcases.add(fromTokens(new String[]{"1", "2", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "2", "null", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2", "3"}));
        testcases.add(fromTokens(new String[]{"10", "20", "30", "40", "50", "60", "70"}));
        testcases.add(fromTokens(new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7"}));
        testcases.add(fromTokens(new String[]{"0", "0", "0", "0", "0"}));
        testcases.add(fromTokens(new String[]{"10", "null", "20", "null", "30", "null", "40"}));

        // 20..29: Left skewed trees of various sizes
        testcases.add(genLeftSkewed(5, rnd));
        testcases.add(genLeftSkewed(10, rnd));
        testcases.add(genLeftSkewed(20, rnd));
        testcases.add(genLeftSkewed(35, rnd));
        testcases.add(genLeftSkewed(50, rnd));
        testcases.add(genLeftSkewed(65, rnd));
        testcases.add(genLeftSkewed(80, rnd));
        testcases.add(genLeftSkewed(90, rnd));
        testcases.add(genLeftSkewed(95, rnd));
        testcases.add(genLeftSkewed(100, rnd)); // Max constraint skewed

        // 30..39: Right skewed trees of various sizes
        testcases.add(genRightSkewed(5, rnd));
        testcases.add(genRightSkewed(10, rnd));
        testcases.add(genRightSkewed(20, rnd));
        testcases.add(genRightSkewed(35, rnd));
        testcases.add(genRightSkewed(50, rnd));
        testcases.add(genRightSkewed(65, rnd));
        testcases.add(genRightSkewed(80, rnd));
        testcases.add(genRightSkewed(90, rnd));
        testcases.add(genRightSkewed(95, rnd));
        testcases.add(genRightSkewed(100, rnd)); // Max constraint right-skewed

        // 40..49: Zig-zag trees of various sizes
        testcases.add(genZigZag(5, rnd));
        testcases.add(genZigZag(10, rnd));
        testcases.add(genZigZag(20, rnd));
        testcases.add(genZigZag(35, rnd));
        testcases.add(genZigZag(50, rnd));
        testcases.add(genZigZag(65, rnd));
        testcases.add(genZigZag(80, rnd));
        testcases.add(genZigZag(90, rnd));
        testcases.add(genZigZag(95, rnd));
        testcases.add(genZigZag(100, rnd)); // Max constraint zig-zag

        // 50..59: Complete / balanced binary trees
        testcases.add(genCompleteTree(7, rnd));
        testcases.add(genCompleteTree(15, rnd));
        testcases.add(genCompleteTree(25, rnd));
        testcases.add(genCompleteTree(31, rnd));
        testcases.add(genCompleteTree(45, rnd));
        testcases.add(genCompleteTree(63, rnd));
        testcases.add(genCompleteTree(75, rnd));
        testcases.add(genCompleteTree(85, rnd));
        testcases.add(genCompleteTree(95, rnd));
        testcases.add(genCompleteTree(100, rnd)); // Max constraint complete

        // 60..79: Small to medium random trees (8 to 50 nodes)
        for (int i = 60; i < 80; i++) {
            int n = 8 + rnd.nextInt(43);
            testcases.add(genRandomTree(n, rnd));
        }

        // 80..89: Large random trees (50 to 85 nodes)
        for (int i = 80; i < 90; i++) {
            int n = 50 + rnd.nextInt(36);
            testcases.add(genRandomTree(n, rnd));
        }

        // 90..94: Very large random trees (85 to 98 nodes)
        for (int i = 90; i < 95; i++) {
            int n = 85 + rnd.nextInt(14);
            testcases.add(genRandomTree(n, rnd));
        }

        // 95..99: Maximum boundary stress tests (N = 100 nodes)
        testcases.add(genCompleteTree(100, rnd)); // 95: complete 100
        testcases.add(genRandomTree(100, rnd));   // 96: random 100
        testcases.add(genLeftSkewed(100, rnd));   // 97: left-skewed 100
        testcases.add(genRightSkewed(100, rnd));  // 98: right-skewed 100
        testcases.add(genZigZag(100, rnd));       // 99: zig-zag 100

        System.out.println("Total testcases created: " + testcases.size());

        for (int i = 0; i < 100; i++) {
            Node root = testcases.get(i);
            String treeInput = serializeTree(root);
            List<Integer> preorderList = getPreorder(root);
            String outputStr = formatList(preorderList);

            String inFilename = String.format("input%02d.txt", i);
            String outFilename = String.format("output%02d.txt", i);

            try (PrintWriter pwIn = new PrintWriter(new File(inputDir, inFilename))) {
                pwIn.println(treeInput);
            }

            try (PrintWriter pwOut = new PrintWriter(new File(outputDir, outFilename))) {
                pwOut.println(outputStr);
            }
        }

        System.out.println("All 100 input and output testcases generated successfully!");
    }
}
