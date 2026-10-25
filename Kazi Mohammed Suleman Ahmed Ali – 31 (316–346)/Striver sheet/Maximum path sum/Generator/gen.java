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
 * Self-contained Test Case Generator for "Maximum path sum".
 *
 * Generates exactly 100 input and 100 output test files:
 * - Testcases/Input/input00.txt to input99.txt
 * - Testcases/Output/output00.txt to output99.txt
 *
 * Spans:
 * - Edge / Base cases (single node, all negative, min/max values, TUF samples)
 * - Small structured trees (perfect, skewed, zig-zag, V-trees)
 * - Mid-range random stress cases (N = 100 to 2,500)
 * - Pathological worst-case boundary cases (depth up to 3 * 10^4, N = 3 * 10^4)
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
     * Optimal internal solver to compute the maximum path sum of a binary tree.
     * Uses stack-safe iterative post-order traversal with an explicit stack.
     */
    static int solve(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        Map<TreeNode, Integer> maxGains = new HashMap<>();
        int maxSum = Integer.MIN_VALUE;

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
                    int leftGain = Math.max(0, peek.left == null ? 0 : maxGains.getOrDefault(peek.left, 0));
                    int rightGain = Math.max(0, peek.right == null ? 0 : maxGains.getOrDefault(peek.right, 0));

                    int currentTurnSum = peek.val + leftGain + rightGain;
                    maxSum = Math.max(maxSum, currentTurnSum);

                    int maxGainUpward = peek.val + Math.max(leftGain, rightGain);
                    maxGains.put(peek, maxGainUpward);

                    if (peek.left != null) maxGains.remove(peek.left);
                    if (peek.right != null) maxGains.remove(peek.right);

                    lastVisited = peek;
                }
            }
        }

        return maxSum;
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

    static TreeNode buildCompleteTree(int n, int minVal, int maxVal, Random rng) {
        if (n <= 0) return null;
        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
        }
        for (int i = 0; i < n; i++) {
            int leftIdx = 2 * i + 1;
            int rightIdx = 2 * i + 2;
            if (leftIdx < n) nodes[i].left = nodes[leftIdx];
            if (rightIdx < n) nodes[i].right = nodes[rightIdx];
        }
        return nodes[0];
    }

    static TreeNode buildLeftSkewedTree(int n, int minVal, int maxVal, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.left = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
            curr = curr.left;
        }
        return root;
    }

    static TreeNode buildRightSkewedTree(int n, int minVal, int maxVal, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.right = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
            curr = curr.right;
        }
        return root;
    }

    static TreeNode buildZigZagTree(int n, int minVal, int maxVal, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            TreeNode next = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
            if (i % 2 == 1) {
                curr.left = next;
            } else {
                curr.right = next;
            }
            curr = next;
        }
        return root;
    }

    static TreeNode buildRandomTree(int n, int minVal, int maxVal, double branchProb, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
        ArrayList<TreeNode> candidates = new ArrayList<>();
        candidates.add(root);
        int created = 1;

        while (created < n && !candidates.isEmpty()) {
            int idx = rng.nextInt(candidates.size());
            TreeNode parent = candidates.get(idx);

            boolean attachLeft = parent.left == null && (parent.right != null || rng.nextBoolean());
            if (attachLeft) {
                parent.left = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
                candidates.add(parent.left);
                created++;
            } else if (parent.right == null) {
                parent.right = new TreeNode(minVal + rng.nextInt(maxVal - minVal + 1));
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

        Random rng = new Random(123456789L);

        for (int testId = 0; testId < 100; testId++) {
            TreeNode root = null;

            // Suite 1: Edge & Base Cases (00 - 19)
            if (testId == 0) {
                root = new TreeNode(42);
            } else if (testId == 1) {
                root = new TreeNode(-42);
            } else if (testId == 2) {
                root = new TreeNode(-1000);
            } else if (testId == 3) {
                root = new TreeNode(1000);
            } else if (testId == 4) {
                // TUF Sample 1: [20, 9, -10, null, null, 15, 7] -> 34
                root = new TreeNode(20);
                root.left = new TreeNode(9);
                root.right = new TreeNode(-10);
                root.right.left = new TreeNode(15);
                root.right.right = new TreeNode(7);
            } else if (testId == 5) {
                // TUF Sample 2: [-10, 9, 20, null, null, 15, 7] -> 42
                root = new TreeNode(-10);
                root.left = new TreeNode(9);
                root.right = new TreeNode(20);
                root.right.left = new TreeNode(15);
                root.right.right = new TreeNode(7);
            } else if (testId == 6) {
                // TUF Sample 3: [1, 2, -3, -4, null, 5, 8] -> 10
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(-3);
                root.left.left = new TreeNode(-4);
                root.right.left = new TreeNode(5);
                root.right.right = new TreeNode(8);
            } else if (testId == 7) {
                // TUF Gamification: [1, 2, 3, null, 4] -> 10
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.right = new TreeNode(4);
            } else if (testId == 8) {
                // All negative 3 nodes
                root = new TreeNode(-3);
                root.left = new TreeNode(-2);
                root.right = new TreeNode(-5);
            } else if (testId == 9) {
                root = new TreeNode(-10);
                root.left = new TreeNode(20);
            } else if (testId == 10) {
                root = new TreeNode(10);
                root.right = new TreeNode(-20);
            } else if (testId == 11) {
                root = new TreeNode(10);
                root.left = new TreeNode(20);
            } else if (testId == 12) {
                // Root negative, separate leaves better: [-100, 50, 70] -> 70
                root = new TreeNode(-100);
                root.left = new TreeNode(50);
                root.right = new TreeNode(70);
            } else if (testId == 13) {
                // Root negative, crossing root better: [-10, 50, 70] -> 110
                root = new TreeNode(-10);
                root.left = new TreeNode(50);
                root.right = new TreeNode(70);
            } else if (testId == 14) {
                // 3 nodes left chain: [10, 20, null, 30] -> 60
                root = new TreeNode(10);
                root.left = new TreeNode(20);
                root.left.left = new TreeNode(30);
            } else if (testId == 15) {
                // 3 nodes alternating: [10, -20, null, 30] -> 30
                root = new TreeNode(10);
                root.left = new TreeNode(-20);
                root.left.left = new TreeNode(30);
            } else if (testId == 16) {
                // Zig tree: [10, -5, null, null, 25] -> 30
                root = new TreeNode(10);
                root.left = new TreeNode(-5);
                root.left.right = new TreeNode(25);
            } else if (testId == 17) {
                // Zag tree: [-5, null, 20, 15] -> 35
                root = new TreeNode(-5);
                root.right = new TreeNode(20);
                root.right.left = new TreeNode(15);
            } else if (testId == 18) {
                // 5 nodes: root with positive arms and negative tail
                root = new TreeNode(10);
                root.left = new TreeNode(5);
                root.right = new TreeNode(8);
                root.left.left = new TreeNode(-20);
                root.right.right = new TreeNode(-30);
            } else if (testId == 19) {
                // All zeroes
                root = new TreeNode(0);
                root.left = new TreeNode(0);
                root.right = new TreeNode(0);
            }
            // Suite 2: Small Structured Trees (20 - 39)
            else if (testId == 20) {
                root = buildCompleteTree(7, 10, 100, rng);
            } else if (testId == 21) {
                root = buildCompleteTree(7, -100, -1, rng);
            } else if (testId == 22) {
                root = buildCompleteTree(7, -50, 50, rng);
            } else if (testId == 23) {
                // N = 15: negative root, positive leaves
                root = buildCompleteTree(15, -100, 100, rng);
                root.val = -500;
            } else if (testId == 24) {
                root = buildLeftSkewedTree(15, 1, 50, rng);
            } else if (testId == 25) {
                root = buildLeftSkewedTree(15, -50, -1, rng);
            } else if (testId == 26) {
                root = buildRightSkewedTree(15, -50, 50, rng);
            } else if (testId == 27) {
                root = buildZigZagTree(20, -100, 100, rng);
            } else if (testId == 28) {
                // V-tree: root is -1000, two arms of 10 nodes each
                root = new TreeNode(-1000);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 10; i++) {
                    l.left = new TreeNode(i * 10);
                    l = l.left;
                    r.right = new TreeNode(i * 10);
                    r = r.right;
                }
            } else if (testId == 29) {
                // Comb tree of 30 nodes
                root = new TreeNode(10);
                TreeNode curr = root;
                for (int i = 0; i < 15; i++) {
                    curr.left = new TreeNode(rng.nextInt(201) - 100);
                    curr.right = new TreeNode(rng.nextInt(201) - 100);
                    curr = curr.right;
                }
            } else if (testId >= 30 && testId <= 39) {
                int n = 10 + (testId - 30) * 5;
                root = buildRandomTree(n, -500, 500, 0.45, rng);
            }
            // Suite 3: Mid-Range Random Stress Cases (40 - 69)
            else if (testId == 40) {
                root = buildCompleteTree(127, 1, 50, rng);
            } else if (testId == 41) {
                root = buildCompleteTree(127, -50, -1, rng);
            } else if (testId == 42) {
                root = buildCompleteTree(255, -200, 200, rng);
            } else if (testId == 43) {
                root = buildCompleteTree(511, -300, 300, rng);
            } else if (testId == 44) {
                root = buildCompleteTree(1023, -500, 500, rng);
            } else if (testId == 45) {
                root = buildCompleteTree(2047, -1000, 1000, rng);
            } else if (testId == 46) {
                root = buildLeftSkewedTree(500, -100, 100, rng);
            } else if (testId == 47) {
                root = buildRightSkewedTree(500, -100, 100, rng);
            } else if (testId == 48) {
                root = buildZigZagTree(500, -100, 100, rng);
            } else if (testId == 49) {
                root = buildLeftSkewedTree(1000, -200, 200, rng);
            } else if (testId == 50) {
                // V-tree: two 500-node arms with mixed weights
                root = new TreeNode(-500);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 500; i++) {
                    l.left = new TreeNode(rng.nextInt(201) - 100);
                    l = l.left;
                    r.right = new TreeNode(rng.nextInt(201) - 100);
                    r = r.right;
                }
            } else if (testId == 51) {
                // Comb tree 800 nodes
                root = new TreeNode(0);
                TreeNode curr = root;
                for (int i = 0; i < 400; i++) {
                    curr.left = new TreeNode(rng.nextInt(401) - 200);
                    curr.right = new TreeNode(rng.nextInt(401) - 200);
                    curr = curr.right;
                }
            } else if (testId >= 52 && testId <= 60) {
                int n = 200 + (testId - 52) * 150;
                int[] vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = rng.nextInt(1001) - 500;
                root = buildBST(vals);
            } else if (testId >= 61 && testId <= 69) {
                int n = 500 + (testId - 61) * 250;
                root = buildRandomTree(n, -500, 500, 0.48, rng);
            }
            // Suite 4: Pathological & Maximum Boundary Stress Cases (70 - 99)
            else if (testId == 70) {
                root = buildCompleteTree(4095, -1000, 1000, rng);
            } else if (testId == 71) {
                root = buildCompleteTree(8191, -1000, 1000, rng);
            } else if (testId == 72) {
                root = buildCompleteTree(16383, -1000, 1000, rng);
            } else if (testId == 73) {
                // Boundary maximum complete tree N = 30,000
                root = buildCompleteTree(30000, -1000, 1000, rng);
            } else if (testId == 74) {
                root = buildLeftSkewedTree(5000, 1, 10, rng);
            } else if (testId == 75) {
                root = buildLeftSkewedTree(5000, -10, -1, rng);
            } else if (testId == 76) {
                root = buildRightSkewedTree(5000, -50, 50, rng);
            } else if (testId == 77) {
                root = buildZigZagTree(5000, -50, 50, rng);
            } else if (testId == 78) {
                root = buildLeftSkewedTree(10000, -100, 100, rng);
            } else if (testId == 79) {
                root = buildRightSkewedTree(10000, -100, 100, rng);
            } else if (testId == 80) {
                root = buildZigZagTree(10000, -100, 100, rng);
            } else if (testId == 81) {
                // Extreme maximum boundary left-skewed: N = 30,000
                root = buildLeftSkewedTree(30000, -10, 10, rng);
            } else if (testId == 82) {
                // Extreme maximum boundary right-skewed: N = 30,000
                root = buildRightSkewedTree(30000, -10, 10, rng);
            } else if (testId == 83) {
                // Extreme maximum boundary zig-zag: N = 30,000
                root = buildZigZagTree(30000, -10, 10, rng);
            } else if (testId == 84) {
                // Extreme V-tree: 15,000 left, 14,999 right (total 30,000)
                root = new TreeNode(-1000);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 15000; i++) {
                    l.left = new TreeNode(rng.nextInt(21) - 10);
                    l = l.left;
                }
                for (int i = 1; i < 15000; i++) {
                    r.right = new TreeNode(rng.nextInt(21) - 10);
                    r = r.right;
                }
            } else if (testId == 85) {
                // Massive off-center path sum: root is -1000, left has two 10,000-node positive arms
                root = new TreeNode(-1000);
                root.right = new TreeNode(1);
                root.left = new TreeNode(500);
                TreeNode l = root.left, r = root.left;
                for (int i = 0; i < 10000; i++) {
                    l.left = new TreeNode(1);
                    l = l.left;
                    r.right = new TreeNode(1);
                    r = r.right;
                }
            } else if (testId == 86) {
                // Caterpillar tree: backbone 7,500, each with 1 leaf -> 15,000 nodes total
                root = new TreeNode(10);
                TreeNode curr = root;
                for (int i = 0; i < 7499; i++) {
                    curr.left = new TreeNode(rng.nextInt(201) - 100);
                    curr.right = new TreeNode(rng.nextInt(201) - 100);
                    curr = curr.right;
                }
            } else if (testId == 87) {
                // Caterpillar tree: backbone 15,000 -> 30,000 nodes total
                root = new TreeNode(10);
                TreeNode curr = root;
                for (int i = 0; i < 14999; i++) {
                    curr.left = new TreeNode(rng.nextInt(21) - 10);
                    curr.right = new TreeNode(rng.nextInt(21) - 10);
                    curr = curr.right;
                }
            } else if (testId >= 88 && testId <= 92) {
                int n = 5000 + (testId - 88) * 4000;
                int[] vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = rng.nextInt(2001) - 1000;
                root = buildBST(vals);
            } else {
                // 93 to 99: Large random stress trees (N = 10,000 to 30,000)
                int n = 10000 + (testId - 93) * 3300;
                if (n > 30000) n = 30000;
                root = buildRandomTree(n, -1000, 1000, 0.45 + rng.nextDouble() * 0.2, rng);
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
