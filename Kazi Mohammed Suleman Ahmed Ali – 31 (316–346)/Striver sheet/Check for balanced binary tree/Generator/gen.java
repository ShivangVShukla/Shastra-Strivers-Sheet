import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Self-contained Test Case Generator for "Check for balanced binary tree".
 *
 * Generates exactly 100 input and 100 output test files:
 * - Testcases/Input/input00.txt to input99.txt
 * - Testcases/Output/output00.txt to output99.txt
 *
 * Spans:
 * - Edge / Base cases (empty tree, single node, 2-node, 3-node, TUF samples)
 * - Small structured trees (perfect, skewed, zig-zag, Fibonacci/AVL trees)
 * - Mid-range random stress cases (both balanced and unbalanced)
 * - Pathological worst-case boundary cases (depth up to 10^4, N = 10^4)
 */
public class gen {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Serializes a binary tree into standard space-separated level-order tokens.
     */
    static String serialize(TreeNode root) {
        if (root == null) {
            return "null";
        }

        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        int head = 0;

        while (head < queue.size()) {
            TreeNode current = queue.get(head++);
            if (current != null) {
                tokens.add(Integer.toString(current.val));
                queue.add(current.left);
                queue.add(current.right);
            } else {
                tokens.add("null");
            }
        }

        // Strip trailing "null" tokens
        int last = tokens.size() - 1;
        while (last >= 0 && tokens.get(last).equals("null")) {
            last--;
        }

        if (last < 0) {
            return "null";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= last; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(tokens.get(i));
        }

        return sb.toString();
    }

    /**
     * Optimal internal solver to determine if a binary tree is height-balanced.
     * Uses stack-safe iterative post-order traversal with explicit stack.
     */
    static boolean solve(TreeNode root) {
        if (root == null) {
            return true;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        Map<TreeNode, Integer> heights = new HashMap<>();

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode peek = stack.peek();
                if (peek.right != null && lastVisited != peek.right) {
                    current = peek.right;
                } else {
                    stack.pop();
                    int leftH = peek.left == null ? 0 : heights.getOrDefault(peek.left, 0);
                    int rightH = peek.right == null ? 0 : heights.getOrDefault(peek.right, 0);

                    if (Math.abs(leftH - rightH) > 1) {
                        return false;
                    }

                    heights.put(peek, 1 + Math.max(leftH, rightH));

                    if (peek.left != null) heights.remove(peek.left);
                    if (peek.right != null) heights.remove(peek.right);

                    lastVisited = peek;
                }
            }
        }

        return true;
    }

    /**
     * Writes content to a file.
     */
    static void writeFile(File file, String content) throws IOException {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.newLine();
        }
    }

    // ==========================================
    // Tree Builders
    // ==========================================

    static TreeNode buildCompleteTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new TreeNode(1 + rng.nextInt(100000));
        }
        for (int i = 0; i < n; i++) {
            int leftIdx = 2 * i + 1;
            int rightIdx = 2 * i + 2;
            if (leftIdx < n) nodes[i].left = nodes[leftIdx];
            if (rightIdx < n) nodes[i].right = nodes[rightIdx];
        }
        return nodes[0];
    }

    static TreeNode buildLeftSkewedTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(1 + rng.nextInt(100000));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.left = new TreeNode(1 + rng.nextInt(100000));
            curr = curr.left;
        }
        return root;
    }

    static TreeNode buildRightSkewedTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(1 + rng.nextInt(100000));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.right = new TreeNode(1 + rng.nextInt(100000));
            curr = curr.right;
        }
        return root;
    }

    static TreeNode buildZigZagTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(1 + rng.nextInt(100000));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            TreeNode next = new TreeNode(1 + rng.nextInt(100000));
            if (i % 2 == 1) {
                curr.left = next;
            } else {
                curr.right = next;
            }
            curr = next;
        }
        return root;
    }

    static TreeNode buildFibonacciAVL(int height, Random rng) {
        if (height <= 0) return null;
        if (height == 1) return new TreeNode(1 + rng.nextInt(100000));
        TreeNode root = new TreeNode(1 + rng.nextInt(100000));
        root.left = buildFibonacciAVL(height - 1, rng);
        root.right = buildFibonacciAVL(height - 2, rng);
        return root;
    }

    public static void main(String[] args) {
        File baseDir = new File(".");
        File testcasesDir;

        if (new File(baseDir, "Testcases").exists() || new File(baseDir, "Problem.md").exists()) {
            testcasesDir = new File(baseDir, "Testcases");
        } else if (new File(baseDir, "../Testcases").exists() || new File(baseDir, "../Problem.md").exists()) {
            testcasesDir = new File(baseDir, "../Testcases");
        } else {
            testcasesDir = new File("Testcases");
        }

        File inputDir = new File(testcasesDir, "Input");
        File outputDir = new File(testcasesDir, "Output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        Random rng = new Random(8888888L);

        for (int testId = 0; testId < 100; testId++) {
            TreeNode root = null;

            // Suite 1: Edge & Base Cases (00 - 19)
            if (testId == 0) {
                // Empty tree -> true
                root = null;
            } else if (testId == 1) {
                // Single node -> true
                root = new TreeNode(42);
            } else if (testId == 2) {
                // Single node min constraint -> true
                root = new TreeNode(1);
            } else if (testId == 3) {
                // Single node max constraint -> true
                root = new TreeNode(100000);
            } else if (testId == 4) {
                // TUF Sample 1: [3, 9, 20, null, null, 15, 7] -> true
                root = new TreeNode(3);
                root.left = new TreeNode(9);
                root.right = new TreeNode(20);
                root.right.left = new TreeNode(15);
                root.right.right = new TreeNode(7);
            } else if (testId == 5) {
                // TUF Sample 2: [1, 2, null, null, 3] -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.right = new TreeNode(3);
            } else if (testId == 6) {
                // TUF Sample 3: [1, 2, 3, 4, 5, 6] -> true
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.left.right = new TreeNode(5);
                root.right.left = new TreeNode(6);
            } else if (testId == 7) {
                // TUF Gamification: [5, 1, 2, 8, 3, null, 5, null, 4] -> false
                root = new TreeNode(5);
                root.left = new TreeNode(1);
                root.right = new TreeNode(2);
                root.left.left = new TreeNode(8);
                root.left.right = new TreeNode(3);
                root.right.right = new TreeNode(5);
                root.left.left.right = new TreeNode(4);
            } else if (testId == 8) {
                // Two nodes: root + left -> true
                root = new TreeNode(1);
                root.left = new TreeNode(2);
            } else if (testId == 9) {
                // Two nodes: root + right -> true
                root = new TreeNode(1);
                root.right = new TreeNode(2);
            } else if (testId == 10) {
                // Three nodes: full depth 2 -> true
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
            } else if (testId == 11) {
                // Three nodes: left chain -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(3);
            } else if (testId == 12) {
                // Three nodes: right chain -> false
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.right = new TreeNode(3);
            } else if (testId == 13) {
                // Three nodes: zig -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.right = new TreeNode(3);
            } else if (testId == 14) {
                // Three nodes: zag -> false
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.left = new TreeNode(3);
            } else if (testId == 15) {
                // 4 nodes: root with left depth 2, right depth 1 -> true
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
            } else if (testId == 16) {
                // 4 nodes: left chain of 3 -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(3);
                root.left.left.left = new TreeNode(4);
            } else if (testId == 17) {
                // 5 nodes: complete tree -> true
                root = buildCompleteTree(5, rng);
            } else if (testId == 18) {
                // 5 nodes: left subtree unbalanced -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.left.left.left = new TreeNode(5);
            } else if (testId == 19) {
                // 6 nodes complete -> true
                root = buildCompleteTree(6, rng);
            }
            // Suite 2: Small Structured Trees (20 - 39)
            else if (testId == 20) {
                root = buildCompleteTree(7, rng); // true
            } else if (testId == 21) {
                root = buildCompleteTree(7, rng);
                root.left.left.left = new TreeNode(10);
                root.left.left.left.left = new TreeNode(20); // false
            } else if (testId == 22) {
                root = buildCompleteTree(15, rng); // true
            } else if (testId == 23) {
                root = buildCompleteTree(14, rng); // true
            } else if (testId == 24) {
                root = buildCompleteTree(15, rng);
                root.right = null; // false
            } else if (testId == 25) {
                root = buildLeftSkewedTree(15, rng); // false
            } else if (testId == 26) {
                root = buildRightSkewedTree(15, rng); // false
            } else if (testId == 27) {
                root = buildZigZagTree(20, rng); // false
            } else if (testId == 28) {
                root = buildFibonacciAVL(4, rng); // true
            } else if (testId == 29) {
                root = buildFibonacciAVL(5, rng); // true
            } else if (testId == 30) {
                root = buildFibonacciAVL(5, rng);
                // attach 2 nodes to deepest leaf in left subtree to violate balance
                TreeNode c = root.left;
                while (c.left != null) c = c.left;
                c.left = new TreeNode(99);
                c.left.left = new TreeNode(100); // false
            } else if (testId == 31) {
                root = buildCompleteTree(31, rng); // true
            } else if (testId == 32) {
                root = buildCompleteTree(25, rng); // true
            } else if (testId == 33) {
                root = buildFibonacciAVL(6, rng); // true
            } else if (testId == 34) {
                root = buildFibonacciAVL(6, rng);
                TreeNode c = root;
                while (c.left != null) c = c.left;
                c.left = new TreeNode(1);
                c.left.left = new TreeNode(2); // false
            } else if (testId == 35) {
                root = buildCompleteTree(50, rng); // true
            } else if (testId == 36) {
                root = buildCompleteTree(50, rng);
                root.left.left.left.left.left = new TreeNode(1);
                root.left.left.left.left.left.left = new TreeNode(2); // false
            } else if (testId == 37) {
                root = buildFibonacciAVL(7, rng); // true
            } else if (testId == 38) {
                root = buildCompleteTree(63, rng); // true
            } else if (testId == 39) {
                // V-shaped tree: left chain 10, right chain 10 -> false
                root = new TreeNode(1);
                TreeNode l = root, r = root;
                for (int i = 0; i < 10; i++) {
                    l.left = new TreeNode(i + 2);
                    l = l.left;
                    r.right = new TreeNode(i + 20);
                    r = r.right;
                }
            }
            // Suite 3: Mid-Range Random Stress Cases (40 - 69)
            else if (testId == 40) {
                root = buildCompleteTree(127, rng); // true
            } else if (testId == 41) {
                root = buildCompleteTree(127, rng);
                TreeNode c = root;
                while (c.right != null) c = c.right;
                c.right = new TreeNode(1);
                c.right.right = new TreeNode(2); // false
            } else if (testId == 42) {
                root = buildCompleteTree(255, rng); // true
            } else if (testId == 43) {
                root = buildCompleteTree(255, rng);
                root.left.right = null;
                root.left.left = null; // false
            } else if (testId == 44) {
                root = buildCompleteTree(511, rng); // true
            } else if (testId == 45) {
                root = buildCompleteTree(511, rng);
                TreeNode c = root.left;
                while (c.left != null) c = c.left;
                c.left = new TreeNode(1);
                c.left.left = new TreeNode(2);
                c.left.left.left = new TreeNode(3); // false
            } else if (testId == 46) {
                root = buildCompleteTree(1023, rng); // true
            } else if (testId == 47) {
                root = buildCompleteTree(1023, rng);
                root.right = null; // false
            } else if (testId == 48) {
                root = buildCompleteTree(2047, rng); // true
            } else if (testId == 49) {
                root = buildCompleteTree(2047, rng);
                TreeNode c = root.right;
                while (c.right != null) c = c.right;
                c.right = new TreeNode(1);
                c.right.right = new TreeNode(2); // false
            } else if (testId == 50) {
                root = buildLeftSkewedTree(500, rng); // false
            } else if (testId == 51) {
                root = buildRightSkewedTree(500, rng); // false
            } else if (testId == 52) {
                root = buildZigZagTree(800, rng); // false
            } else if (testId == 53) {
                root = buildFibonacciAVL(10, rng); // true
            } else if (testId == 54) {
                root = buildFibonacciAVL(11, rng); // true
            } else if (testId == 55) {
                root = buildFibonacciAVL(12, rng); // true
            } else if (testId == 56) {
                root = buildFibonacciAVL(12, rng);
                TreeNode c = root;
                while (c.left != null) c = c.left;
                c.left = new TreeNode(1);
                c.left.left = new TreeNode(2); // false
            } else if (testId == 57) {
                root = buildCompleteTree(1500, rng); // true
            } else if (testId == 58) {
                root = buildCompleteTree(1500, rng);
                TreeNode c = root.left;
                while (c.left != null) c = c.left;
                c.left = new TreeNode(1);
                c.left.left = new TreeNode(2); // false
            } else if (testId == 59) {
                root = buildFibonacciAVL(13, rng); // true
            } else if (testId >= 60 && testId <= 64) {
                // Balanced complete trees with varied N
                int n = 700 + (testId - 60) * 300;
                root = buildCompleteTree(n, rng); // true
            } else if (testId >= 65 && testId <= 69) {
                // Unbalanced variants
                int n = 800 + (testId - 65) * 300;
                root = buildCompleteTree(n, rng);
                TreeNode c = root;
                for (int i = 0; i < 4; i++) {
                    if (c.left != null) c = c.left;
                }
                c.left = new TreeNode(1);
                c.left.left = new TreeNode(2);
                c.left.left.left = new TreeNode(3); // false
            }
            // Suite 4: Pathological & Maximum Boundary Stress Cases (70 - 99)
            else if (testId == 70) {
                root = buildCompleteTree(4095, rng); // true
            } else if (testId == 71) {
                root = buildCompleteTree(4095, rng);
                TreeNode c = root.left;
                while (c.right != null) c = c.right;
                c.right = new TreeNode(1);
                c.right.right = new TreeNode(2); // false
            } else if (testId == 72) {
                root = buildCompleteTree(8191, rng); // true
            } else if (testId == 73) {
                root = buildCompleteTree(8191, rng);
                root.left.right = null; // false
            } else if (testId == 74) {
                // Boundary maximum complete tree N = 10,000
                root = buildCompleteTree(10000, rng); // true
            } else if (testId == 75) {
                root = buildCompleteTree(9999, rng); // true
            } else if (testId == 76) {
                root = buildLeftSkewedTree(5000, rng); // false
            } else if (testId == 77) {
                root = buildRightSkewedTree(5000, rng); // false
            } else if (testId == 78) {
                root = buildZigZagTree(5000, rng); // false
            } else if (testId == 79) {
                // Extreme maximum left-skewed N = 10,000
                root = buildLeftSkewedTree(10000, rng); // false
            } else if (testId == 80) {
                // Extreme maximum right-skewed N = 10,000
                root = buildRightSkewedTree(10000, rng); // false
            } else if (testId == 81) {
                // Extreme maximum zig-zag N = 10,000
                root = buildZigZagTree(10000, rng); // false
            } else if (testId == 82) {
                root = buildFibonacciAVL(15, rng); // true
            } else if (testId == 83) {
                root = buildFibonacciAVL(15, rng);
                TreeNode c = root;
                while (c.right != null) c = c.right;
                c.right = new TreeNode(1);
                c.right.right = new TreeNode(2); // false
            } else if (testId == 84) {
                root = buildFibonacciAVL(16, rng); // true
            } else if (testId == 85) {
                root = buildFibonacciAVL(17, rng); // true
            } else if (testId == 86) {
                root = buildCompleteTree(6000, rng); // true
            } else if (testId == 87) {
                root = buildCompleteTree(7500, rng); // true
            } else if (testId == 88) {
                root = buildCompleteTree(9000, rng); // true
            } else if (testId == 89) {
                // Complete tree of 10,000 nodes with right subtree stripped to make root unbalanced
                root = buildCompleteTree(10000, rng);
                root.right = null; // false
            } else if (testId == 90) {
                // Balanced tree of 5,000 with single 2-node tail on one leaf
                root = buildCompleteTree(5000, rng);
                TreeNode c = root;
                while (c.left != null) c = c.left;
                c.left = new TreeNode(1);
                c.left.left = new TreeNode(2); // false
            } else if (testId == 91) {
                root = buildCompleteTree(8888, rng); // true
            } else if (testId == 92) {
                root = buildCompleteTree(8888, rng);
                TreeNode c = root.right;
                while (c.left != null) c = c.left;
                c.left = new TreeNode(1);
                c.left.left = new TreeNode(2); // false
            } else if (testId == 93) {
                root = buildFibonacciAVL(18, rng); // true
            } else if (testId == 94) {
                root = buildFibonacciAVL(18, rng);
                TreeNode c = root.left;
                while (c.right != null) c = c.right;
                c.right = new TreeNode(1);
                c.right.right = new TreeNode(2); // false
            } else if (testId == 95) {
                root = buildCompleteTree(9500, rng); // true
            } else if (testId == 96) {
                root = buildCompleteTree(9500, rng);
                root.left.left.right = null; // false
            } else if (testId == 97) {
                root = buildCompleteTree(9800, rng); // true
            } else if (testId == 98) {
                root = buildCompleteTree(9800, rng);
                TreeNode c = root;
                while (c.left != null) c = c.left;
                c.left = new TreeNode(1);
                c.left.left = new TreeNode(2); // false
            } else {
                // 99: Boundary maximum 10,000 complete tree -> true
                root = buildCompleteTree(10000, rng);
            }

            // Serialize input
            String inputContent = serialize(root);

            // Compute expected output using optimal internal solver
            boolean expected = solve(root);
            String outputContent = expected ? "true" : "false";

            // File naming: input00.txt to input99.txt, output00.txt to output99.txt
            String idStr = (testId < 10 ? "0" : "") + testId;
            File inputFile = new File(inputDir, "input" + idStr + ".txt");
            File outputFile = new File(outputDir, "output" + idStr + ".txt");

            try {
                writeFile(inputFile, inputContent);
                writeFile(outputFile, outputContent);
            } catch (IOException e) {
                System.err.println("Error writing testcase " + idStr + ": " + e.getMessage());
                System.exit(1);
            }
        }

        System.out.println("SUCCESS: Generated 100/100 test case pairs in " + testcasesDir.getAbsolutePath());
    }
}
