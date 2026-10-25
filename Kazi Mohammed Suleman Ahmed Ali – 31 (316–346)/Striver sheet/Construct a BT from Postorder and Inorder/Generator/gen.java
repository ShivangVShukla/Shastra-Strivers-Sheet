import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
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

    // Iterative postorder traversal (100% stack-safe)
    public static List<Integer> getPostorder(Node root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) return list;
        Deque<Node> s1 = new ArrayDeque<>();
        Deque<Node> s2 = new ArrayDeque<>();
        s1.push(root);
        while (!s1.isEmpty()) {
            Node curr = s1.pop();
            s2.push(curr);
            if (curr.left != null) s1.push(curr.left);
            if (curr.right != null) s1.push(curr.right);
        }
        while (!s2.isEmpty()) {
            list.add(s2.pop().val);
        }
        return list;
    }

    // Iterative inorder traversal (100% stack-safe)
    public static List<Integer> getInorder(Node root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) return list;
        Deque<Node> stack = new ArrayDeque<>();
        Node curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            list.add(curr.val);
            curr = curr.right;
        }
        return list;
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

    // Generates N distinct integers in [-10000, 10000]
    static int[] generateUniqueValues(int n, Random rnd) {
        int minStart = -10000;
        int maxStart = 10000 - n;
        int start = minStart + (maxStart > minStart ? rnd.nextInt(maxStart - minStart + 1) : 0);

        List<Integer> pool = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            pool.add(start + i);
        }
        Collections.shuffle(pool, rnd);

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[i] = pool.get(i);
        }
        return res;
    }

    // Left skewed
    static Node genLeftSkewed(int n, Random rnd) {
        if (n <= 0) return null;
        int[] vals = generateUniqueValues(n, rnd);
        Node root = new Node(vals[0]);
        Node curr = root;
        for (int i = 1; i < n; i++) {
            curr.left = new Node(vals[i]);
            curr = curr.left;
        }
        return root;
    }

    // Right skewed
    static Node genRightSkewed(int n, Random rnd) {
        if (n <= 0) return null;
        int[] vals = generateUniqueValues(n, rnd);
        Node root = new Node(vals[0]);
        Node curr = root;
        for (int i = 1; i < n; i++) {
            curr.right = new Node(vals[i]);
            curr = curr.right;
        }
        return root;
    }

    // Zig zag
    static Node genZigZag(int n, Random rnd) {
        if (n <= 0) return null;
        int[] vals = generateUniqueValues(n, rnd);
        Node root = new Node(vals[0]);
        Node curr = root;
        for (int i = 1; i < n; i++) {
            if (i % 2 == 1) {
                curr.left = new Node(vals[i]);
                curr = curr.left;
            } else {
                curr.right = new Node(vals[i]);
                curr = curr.right;
            }
        }
        return root;
    }

    // Complete / Balanced binary tree
    static Node genCompleteTree(int n, Random rnd) {
        if (n <= 0) return null;
        int[] vals = generateUniqueValues(n, rnd);
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(vals[i]);
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
        int[] vals = generateUniqueValues(n, rnd);
        Node root = new Node(vals[0]);
        List<Node> open = new ArrayList<>();
        open.add(root);

        for (int i = 1; i < n; i++) {
            int idx = rnd.nextInt(open.size());
            Node parent = open.get(idx);
            Node child = new Node(vals[i]);

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

    static String formatArray(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(list.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        File baseDir = new File("Testcases");
        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        Random rnd = new Random(777);
        List<Node> testcases = new ArrayList<>();

        // 0..3: Sample Cases from TUF
        testcases.add(fromTokens(new String[]{"3", "9", "20", "null", "null", "15", "7"}));
        testcases.add(fromTokens(new String[]{"3", "4", "2", "5", "6", "null", "9"}));
        testcases.add(fromTokens(new String[]{"1", "2", "null", "4", "null", "5"}));
        testcases.add(fromTokens(new String[]{"5", "1", "2", "8", "null", "4", "7", "null", "6"}));

        // 4..11: Boundary & small trees
        testcases.add(new Node(0));
        testcases.add(new Node(-10000));
        testcases.add(new Node(10000));
        testcases.add(fromTokens(new String[]{"1", "2"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2"}));
        testcases.add(fromTokens(new String[]{"-500", "-1000"}));
        testcases.add(fromTokens(new String[]{"1", "2", "3"}));
        testcases.add(fromTokens(new String[]{"-100", "200", "-300"}));

        // 12..19: 3 to 7 node variations
        testcases.add(fromTokens(new String[]{"1", "2", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "2", "null", "null", "3"}));
        testcases.add(fromTokens(new String[]{"1", "null", "2", "3"}));
        testcases.add(fromTokens(new String[]{"10", "20", "30", "40", "50", "60", "70"}));
        testcases.add(fromTokens(new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7"}));
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
        testcases.add(genLeftSkewed(2000, rnd));
        testcases.add(genLeftSkewed(3000, rnd)); // Max constraint skewed

        // 30..39: Right skewed trees of various sizes
        testcases.add(genRightSkewed(5, rnd));
        testcases.add(genRightSkewed(10, rnd));
        testcases.add(genRightSkewed(25, rnd));
        testcases.add(genRightSkewed(50, rnd));
        testcases.add(genRightSkewed(100, rnd));
        testcases.add(genRightSkewed(250, rnd));
        testcases.add(genRightSkewed(500, rnd));
        testcases.add(genRightSkewed(1000, rnd));
        testcases.add(genRightSkewed(2000, rnd));
        testcases.add(genRightSkewed(3000, rnd)); // Max constraint right-skewed

        // 40..49: Zig-zag trees of various sizes
        testcases.add(genZigZag(5, rnd));
        testcases.add(genZigZag(10, rnd));
        testcases.add(genZigZag(20, rnd));
        testcases.add(genZigZag(50, rnd));
        testcases.add(genZigZag(100, rnd));
        testcases.add(genZigZag(250, rnd));
        testcases.add(genZigZag(500, rnd));
        testcases.add(genZigZag(1000, rnd));
        testcases.add(genZigZag(2000, rnd));
        testcases.add(genZigZag(3000, rnd)); // Max constraint zig-zag

        // 50..59: Complete / balanced binary trees
        testcases.add(genCompleteTree(15, rnd));
        testcases.add(genCompleteTree(31, rnd));
        testcases.add(genCompleteTree(63, rnd));
        testcases.add(genCompleteTree(127, rnd));
        testcases.add(genCompleteTree(255, rnd));
        testcases.add(genCompleteTree(511, rnd));
        testcases.add(genCompleteTree(1023, rnd));
        testcases.add(genCompleteTree(1500, rnd));
        testcases.add(genCompleteTree(2047, rnd));
        testcases.add(genCompleteTree(3000, rnd)); // Max constraint complete

        // 60..79: Small to medium random trees (15 to 400 nodes)
        for (int i = 60; i < 80; i++) {
            int n = 15 + rnd.nextInt(385);
            testcases.add(genRandomTree(n, rnd));
        }

        // 80..89: Large random trees (500 to 1800 nodes)
        for (int i = 80; i < 90; i++) {
            int n = 500 + rnd.nextInt(1300);
            testcases.add(genRandomTree(n, rnd));
        }

        // 90..94: Very large random trees (1900 to 2800 nodes)
        for (int i = 90; i < 95; i++) {
            int n = 1900 + rnd.nextInt(900);
            testcases.add(genRandomTree(n, rnd));
        }

        // 95..99: Maximum boundary stress tests (N = 3000 nodes)
        testcases.add(genCompleteTree(3000, rnd)); // 95: complete 3,000
        testcases.add(genRandomTree(3000, rnd));   // 96: random 3,000
        testcases.add(genLeftSkewed(3000, rnd));   // 97: left-skewed 3,000
        testcases.add(genRightSkewed(3000, rnd));  // 98: right-skewed 3,000
        testcases.add(genZigZag(3000, rnd));       // 99: zig-zag 3,000

        System.out.println("Total testcases created: " + testcases.size());

        for (int i = 0; i < 100; i++) {
            Node root = testcases.get(i);

            List<Integer> inorderList = getInorder(root);
            List<Integer> postorderList = getPostorder(root);

            String inStr = formatArray(inorderList);
            String postStr = formatArray(postorderList);
            String outStr = serialize(root);

            String inFilename = String.format("input%02d.txt", i);
            String outFilename = String.format("output%02d.txt", i);

            try (PrintWriter pwIn = new PrintWriter(new File(inputDir, inFilename))) {
                pwIn.println("inorder = " + inStr);
                pwIn.println("postorder = " + postStr);
            }

            try (PrintWriter pwOut = new PrintWriter(new File(outputDir, outFilename))) {
                pwOut.println(outStr);
            }
        }

        System.out.println("All 100 input and output testcases generated successfully!");
    }
}
