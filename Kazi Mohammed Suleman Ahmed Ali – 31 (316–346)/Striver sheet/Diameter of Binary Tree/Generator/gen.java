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
 * Self-contained Test Case Generator for "Diameter of Binary Tree".
 *
 * Generates exactly 100 input and 100 output test files:
 * - Testcases/Input/input00.txt to input99.txt
 * - Testcases/Output/output00.txt to output99.txt
 *
 * Spans:
 * - Edge / Base cases (empty tree, single node, 2-node, 3-node, TUF samples)
 * - Small structured trees (perfect, skewed, zig-zag, V-trees, off-center diameter)
 * - Mid-range random stress cases (N = 100 to 2,500)
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
     * Optimal internal solver to compute the diameter of a binary tree.
     * Uses stack-safe iterative post-order traversal with an explicit stack.
     */
    static int solve(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        Map<TreeNode, Integer> heights = new HashMap<>();
        int maxDiameter = 0;

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

                    maxDiameter = Math.max(maxDiameter, leftH + rightH);
                    heights.put(peek, 1 + Math.max(leftH, rightH));

                    if (peek.left != null) heights.remove(peek.left);
                    if (peek.right != null) heights.remove(peek.right);

                    lastVisited = peek;
                }
            }
        }

        return maxDiameter;
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
            nodes[i] = new TreeNode(rng.nextInt(201) - 100);
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
        TreeNode root = new TreeNode(rng.nextInt(201) - 100);
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.left = new TreeNode(rng.nextInt(201) - 100);
            curr = curr.left;
        }
        return root;
    }

    static TreeNode buildRightSkewedTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(rng.nextInt(201) - 100);
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.right = new TreeNode(rng.nextInt(201) - 100);
            curr = curr.right;
        }
        return root;
    }

    static TreeNode buildZigZagTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(rng.nextInt(201) - 100);
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            TreeNode next = new TreeNode(rng.nextInt(201) - 100);
            if (i % 2 == 1) {
                curr.left = next;
            } else {
                curr.right = next;
            }
            curr = next;
        }
        return root;
    }

    static TreeNode buildRandomTree(int n, double branchProb, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(rng.nextInt(201) - 100);
        ArrayList<TreeNode> candidates = new ArrayList<>();
        candidates.add(root);
        int created = 1;

        while (created < n && !candidates.isEmpty()) {
            int idx = rng.nextInt(candidates.size());
            TreeNode parent = candidates.get(idx);

            boolean attachLeft = parent.left == null && (parent.right != null || rng.nextBoolean());
            if (attachLeft) {
                parent.left = new TreeNode(rng.nextInt(201) - 100);
                candidates.add(parent.left);
                created++;
            } else if (parent.right == null) {
                parent.right = new TreeNode(rng.nextInt(201) - 100);
                candidates.add(parent.right);
                created++;
            }

            if (parent.left != null && parent.right != null) {
                int lastIdx = candidates.size() - 1;
                candidates.set(idx, candidates.get(lastIdx));
                candidates.remove(lastIdx);
            }
        }
        return root;
    }

    static TreeNode buildBST(int[] vals) {
        if (vals == null || vals.length == 0) return null;
        TreeNode root = new TreeNode(vals[0]);
        for (int i = 1; i < vals.length; i++) {
            int v = vals[i];
            TreeNode curr = root;
            while (true) {
                if (v <= curr.val) {
                    if (curr.left == null) {
                        curr.left = new TreeNode(v);
                        break;
                    }
                    curr = curr.left;
                } else {
                    if (curr.right == null) {
                        curr.right = new TreeNode(v);
                        break;
                    }
                    curr = curr.right;
                }
            }
        }
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

        Random rng = new Random(9999999L);

        for (int testId = 0; testId < 100; testId++) {
            TreeNode root = null;

            // Suite 1: Edge & Base Cases (00 - 19)
            if (testId == 0) {
                // Empty tree -> 0
                root = null;
            } else if (testId == 1) {
                // Single node -> 0
                root = new TreeNode(42);
            } else if (testId == 2) {
                // Single node boundary min -> 0
                root = new TreeNode(-100);
            } else if (testId == 3) {
                // Single node boundary max -> 0
                root = new TreeNode(100);
            } else if (testId == 4) {
                // TUF Sample 1: [1, 2, 3, 4, 5] -> 3
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.left.right = new TreeNode(5);
            } else if (testId == 5) {
                // TUF Sample 2: [1, 2, 3, null, 4, null, 5] -> 4
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.right = new TreeNode(4);
                root.right.right = new TreeNode(5);
            } else if (testId == 6) {
                // TUF Sample 3: [1, 2, 3, 4, null, 5, 6, null, 7] -> 5
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.right.left = new TreeNode(5);
                root.right.right = new TreeNode(6);
                root.left.left.right = new TreeNode(7);
            } else if (testId == 7) {
                // TUF Gamification: [5, 1, 2, 8, 3, null, 5, null, 4] -> 5
                root = new TreeNode(5);
                root.left = new TreeNode(1);
                root.right = new TreeNode(2);
                root.left.left = new TreeNode(8);
                root.left.right = new TreeNode(3);
                root.right.right = new TreeNode(5);
                root.left.left.right = new TreeNode(4);
            } else if (testId == 8) {
                // Two nodes: root + left -> 1
                root = new TreeNode(1);
                root.left = new TreeNode(2);
            } else if (testId == 9) {
                // Two nodes: root + right -> 1
                root = new TreeNode(1);
                root.right = new TreeNode(2);
            } else if (testId == 10) {
                // Three nodes: full tree depth 2 -> 2
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
            } else if (testId == 11) {
                // Three nodes: left chain -> 2
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(3);
            } else if (testId == 12) {
                // Three nodes: right chain -> 2
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.right = new TreeNode(3);
            } else if (testId == 13) {
                // Three nodes: zig -> 2
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.right = new TreeNode(3);
            } else if (testId == 14) {
                // Three nodes: zag -> 2
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.left = new TreeNode(3);
            } else if (testId == 15) {
                // Diameter strictly inside left subtree: root has no right child
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(3);
                root.left.right = new TreeNode(4);
            } else if (testId == 16) {
                // V-shape of 5 nodes -> 4
                root = new TreeNode(0);
                root.left = new TreeNode(1);
                root.left.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.right.right = new TreeNode(4);
            } else if (testId == 17) {
                // Off-center diameter: root has 1 right child, but left has 2 arms of length 2 -> 4
                root = new TreeNode(1);
                root.right = new TreeNode(10);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(3);
                root.left.left.left = new TreeNode(4);
                root.left.right = new TreeNode(5);
                root.left.right.right = new TreeNode(6);
            } else if (testId == 18) {
                // Star-like tree -> 4
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(4);
                root.right = new TreeNode(3);
                root.right.right = new TreeNode(5);
            } else if (testId == 19) {
                // Asymmetric: left arm 3, right arm 1 -> 4
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.left = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.left.left.left = new TreeNode(5);
            }
            // Suite 2: Small Structured Trees (20 - 39)
            else if (testId == 20) {
                root = buildCompleteTree(7, rng); // depth 3 -> diameter 4
            } else if (testId == 21) {
                root = buildCompleteTree(15, rng); // depth 4 -> diameter 6
            } else if (testId == 22) {
                root = buildCompleteTree(31, rng); // depth 5 -> diameter 8
            } else if (testId == 23) {
                root = buildCompleteTree(63, rng); // depth 6 -> diameter 10
            } else if (testId == 24) {
                root = buildLeftSkewedTree(15, rng); // diameter 14
            } else if (testId == 25) {
                root = buildRightSkewedTree(15, rng); // diameter 14
            } else if (testId == 26) {
                root = buildZigZagTree(20, rng); // diameter 19
            } else if (testId == 27) {
                // V-shaped tree: left chain 10, right chain 10 -> diameter 20
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 10; i++) {
                    l.left = new TreeNode(i);
                    l = l.left;
                    r.right = new TreeNode(-i);
                    r = r.right;
                }
            } else if (testId == 28) {
                // Off-center diameter: root has 1 right child, left has two 8-node arms -> diameter 16
                root = new TreeNode(0);
                root.right = new TreeNode(99);
                root.left = new TreeNode(1);
                TreeNode l = root.left, r = root.left;
                for (int i = 1; i <= 8; i++) {
                    l.left = new TreeNode(i + 1);
                    l = l.left;
                    r.right = new TreeNode(i + 20);
                    r = r.right;
                }
            } else if (testId == 29) {
                // Comb tree: backbone right 15 with teeth on left
                root = new TreeNode(0);
                TreeNode curr = root;
                for (int i = 0; i < 15; i++) {
                    curr.left = new TreeNode(i + 1);
                    curr.right = new TreeNode(i + 20);
                    curr = curr.right;
                }
            } else if (testId >= 30 && testId <= 39) {
                int n = 10 + (testId - 30) * 5;
                root = buildRandomTree(n, 0.45, rng);
            }
            // Suite 3: Mid-Range Random Stress Cases (40 - 69)
            else if (testId == 40) {
                root = buildCompleteTree(127, rng); // diameter 12
            } else if (testId == 41) {
                root = buildCompleteTree(255, rng); // diameter 14
            } else if (testId == 42) {
                root = buildCompleteTree(511, rng); // diameter 16
            } else if (testId == 43) {
                root = buildCompleteTree(1023, rng); // diameter 18
            } else if (testId == 44) {
                root = buildCompleteTree(2047, rng); // diameter 20
            } else if (testId == 45) {
                root = buildLeftSkewedTree(500, rng); // diameter 499
            } else if (testId == 46) {
                root = buildRightSkewedTree(500, rng); // diameter 499
            } else if (testId == 47) {
                root = buildZigZagTree(500, rng); // diameter 499
            } else if (testId == 48) {
                root = buildLeftSkewedTree(1000, rng); // diameter 999
            } else if (testId == 49) {
                root = buildRightSkewedTree(1000, rng); // diameter 999
            } else if (testId == 50) {
                // V-tree: left 500, right 500 -> diameter 1000
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 500; i++) {
                    l.left = new TreeNode(rng.nextInt(201) - 100);
                    l = l.left;
                    r.right = new TreeNode(rng.nextInt(201) - 100);
                    r = r.right;
                }
            } else if (testId == 51) {
                // Huge off-center: left child is V-tree of two 400-node arms -> diameter 800
                root = new TreeNode(0);
                root.right = new TreeNode(99);
                root.left = new TreeNode(1);
                TreeNode l = root.left, r = root.left;
                for (int i = 1; i <= 400; i++) {
                    l.left = new TreeNode(rng.nextInt(201) - 100);
                    l = l.left;
                    r.right = new TreeNode(rng.nextInt(201) - 100);
                    r = r.right;
                }
            } else if (testId == 52) {
                // Comb tree 800 nodes
                root = new TreeNode(0);
                TreeNode curr = root;
                for (int i = 0; i < 400; i++) {
                    curr.left = new TreeNode(rng.nextInt(201) - 100);
                    curr.right = new TreeNode(rng.nextInt(201) - 100);
                    curr = curr.right;
                }
            } else if (testId >= 53 && testId <= 61) {
                int n = 200 + (testId - 53) * 150;
                int[] vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = rng.nextInt(201) - 100;
                root = buildBST(vals);
            } else if (testId >= 62 && testId <= 69) {
                int n = 500 + (testId - 62) * 250;
                root = buildRandomTree(n, 0.48, rng);
            }
            // Suite 4: Pathological & Maximum Boundary Stress Cases (70 - 99)
            else if (testId == 70) {
                root = buildCompleteTree(4095, rng); // diameter 22
            } else if (testId == 71) {
                root = buildCompleteTree(8191, rng); // diameter 24
            } else if (testId == 72) {
                root = buildCompleteTree(10000, rng); // boundary maximum N = 10,000 complete tree
            } else if (testId == 73) {
                root = buildCompleteTree(9999, rng);
            } else if (testId == 74) {
                root = buildLeftSkewedTree(5000, rng); // diameter 4999
            } else if (testId == 75) {
                root = buildRightSkewedTree(5000, rng); // diameter 4999
            } else if (testId == 76) {
                root = buildZigZagTree(5000, rng); // diameter 4999
            } else if (testId == 77) {
                // Boundary maximum left-skewed: N = 10,000 -> diameter 9999
                root = buildLeftSkewedTree(10000, rng);
            } else if (testId == 78) {
                // Boundary maximum right-skewed: N = 10,000 -> diameter 9999
                root = buildRightSkewedTree(10000, rng);
            } else if (testId == 79) {
                // Boundary maximum zig-zag: N = 10,000 -> diameter 9999
                root = buildZigZagTree(10000, rng);
            } else if (testId == 80) {
                // Extreme V-tree: 4,999 on left, 5,000 on right (total 10,000) -> diameter 9999
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 4999; i++) {
                    l.left = new TreeNode(rng.nextInt(201) - 100);
                    l = l.left;
                }
                for (int i = 1; i <= 5000; i++) {
                    r.right = new TreeNode(rng.nextInt(201) - 100);
                    r = r.right;
                }
            } else if (testId == 81) {
                // Extreme off-center: root has 1 right leaf, left child has two 4,990-node arms
                root = new TreeNode(0);
                root.right = new TreeNode(99);
                root.left = new TreeNode(1);
                TreeNode l = root.left, r = root.left;
                for (int i = 1; i <= 4990; i++) {
                    l.left = new TreeNode(rng.nextInt(201) - 100);
                    l = l.left;
                    r.right = new TreeNode(rng.nextInt(201) - 100);
                    r = r.right;
                }
            } else if (testId == 82) {
                // Caterpillar tree: backbone 5,000, each with 1 leaf -> 10,000 nodes total
                root = new TreeNode(0);
                TreeNode curr = root;
                for (int i = 0; i < 4999; i++) {
                    curr.left = new TreeNode(rng.nextInt(201) - 100);
                    curr.right = new TreeNode(rng.nextInt(201) - 100);
                    curr = curr.right;
                }
            } else if (testId >= 83 && testId <= 87) {
                int n = 4000 + (testId - 83) * 1500;
                int[] vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = rng.nextInt(201) - 100;
                root = buildBST(vals);
            } else {
                // 88 to 99: Large random stress trees (N = 3,000 to 10,000)
                int n = 3000 + (testId - 88) * 600;
                if (n > 10000) n = 10000;
                root = buildRandomTree(n, 0.45 + rng.nextDouble() * 0.2, rng);
            }

            // Serialize input
            String inputContent = serialize(root);

            // Compute expected output using optimal internal solver
            int expectedOutput = solve(root);

            // File naming: input00.txt to input99.txt, output00.txt to output99.txt
            String idStr = (testId < 10 ? "0" : "") + testId;
            File inputFile = new File(inputDir, "input" + idStr + ".txt");
            File outputFile = new File(outputDir, "output" + idStr + ".txt");

            try {
                writeFile(inputFile, inputContent);
                writeFile(outputFile, Integer.toString(expectedOutput));
            } catch (IOException e) {
                System.err.println("Error writing testcase " + idStr + ": " + e.getMessage());
                System.exit(1);
            }
        }

        System.out.println("SUCCESS: Generated 100/100 test case pairs in " + testcasesDir.getAbsolutePath());
    }
}
