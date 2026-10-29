import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

/**
 * Self-contained Test Case Generator for "Maximum Depth in BT".
 *
 * Generates exactly 100 input and 100 output test files:
 * - Testcases/Input/input00.txt to input99.txt
 * - Testcases/Output/output00.txt to output99.txt
 *
 * Spans:
 * - Edge / Base cases (empty tree, single node, boundary values, TUF samples)
 * - Small structured and varied trees
 * - Mid-range random stress cases
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
     * Optimal internal solver to compute the maximum depth of a binary tree.
     * Uses iterative BFS to ensure stack-safety for depths up to 10^4.
     */
    static int solve(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
            depth++;
        }

        return depth;
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
            nodes[i] = new TreeNode(rng.nextInt(10001));
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
        TreeNode root = new TreeNode(rng.nextInt(10001));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.left = new TreeNode(rng.nextInt(10001));
            curr = curr.left;
        }
        return root;
    }

    static TreeNode buildRightSkewedTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(rng.nextInt(10001));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.right = new TreeNode(rng.nextInt(10001));
            curr = curr.right;
        }
        return root;
    }

    static TreeNode buildZigZagTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(rng.nextInt(10001));
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            TreeNode next = new TreeNode(rng.nextInt(10001));
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
        TreeNode root = new TreeNode(rng.nextInt(10001));
        ArrayList<TreeNode> candidates = new ArrayList<>();
        candidates.add(root);
        int created = 1;

        while (created < n && !candidates.isEmpty()) {
            int idx = rng.nextInt(candidates.size());
            TreeNode parent = candidates.get(idx);

            boolean attachLeft = parent.left == null && (parent.right != null || rng.nextBoolean());
            if (attachLeft) {
                parent.left = new TreeNode(rng.nextInt(10001));
                candidates.add(parent.left);
                created++;
            } else if (parent.right == null) {
                parent.right = new TreeNode(rng.nextInt(10001));
                candidates.add(parent.right);
                created++;
            }

            if (parent.left != null && parent.right != null) {
                // Remove parent when both children are filled
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
        // Resolve target directories
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

        Random rng = new Random(42424242L);

        for (int testId = 0; testId < 100; testId++) {
            TreeNode root = null;

            // Suite 1: Edge & Base Cases (00 - 14)
            if (testId == 0) {
                // Empty tree
                root = null;
            } else if (testId == 1) {
                // Single node
                root = new TreeNode(1);
            } else if (testId == 2) {
                // Single node boundary min
                root = new TreeNode(0);
            } else if (testId == 3) {
                // Single node boundary max
                root = new TreeNode(10000);
            } else if (testId == 4) {
                // TUF Sample 1: [1, 2, 3, null, null, null, 6] -> depth 3
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.right.right = new TreeNode(6);
            } else if (testId == 5) {
                // TUF Sample 2: [3, 9, 20, null, null, 15, 7] -> depth 3
                root = new TreeNode(3);
                root.left = new TreeNode(9);
                root.right = new TreeNode(20);
                root.right.left = new TreeNode(15);
                root.right.right = new TreeNode(7);
            } else if (testId == 6) {
                // TUF Sample 3: [5, 1, 2, 8, null, null, 5, null, 4, null, null, 7] -> depth 5
                root = new TreeNode(5);
                root.left = new TreeNode(1);
                root.right = new TreeNode(2);
                root.left.left = new TreeNode(8);
                root.right.right = new TreeNode(5);
                root.left.left.right = new TreeNode(4);
                root.left.left.right.right = new TreeNode(7);
            } else if (testId == 7) {
                // 2 nodes: root + left
                root = new TreeNode(10);
                root.left = new TreeNode(20);
            } else if (testId == 8) {
                // 2 nodes: root + right
                root = new TreeNode(10);
                root.right = new TreeNode(20);
            } else if (testId == 9) {
                // 3 nodes: full depth 2
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
            } else if (testId == 10) {
                // 3 nodes: left line
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(3);
            } else if (testId == 11) {
                // 3 nodes: right line
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.right = new TreeNode(3);
            } else if (testId == 12) {
                // 3 nodes: zig
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.right = new TreeNode(3);
            } else if (testId == 13) {
                // 3 nodes: zag
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.left = new TreeNode(3);
            } else if (testId == 14) {
                // 4 nodes: root with 3 nodes
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
            }
            // Suite 2: Small & Varied Structural Cases (15 - 34)
            else if (testId == 15) {
                root = buildCompleteTree(7, rng); // depth 3
            } else if (testId == 16) {
                root = buildCompleteTree(15, rng); // depth 4
            } else if (testId == 17) {
                root = buildCompleteTree(31, rng); // depth 5
            } else if (testId == 18) {
                root = buildCompleteTree(63, rng); // depth 6
            } else if (testId == 19) {
                root = buildLeftSkewedTree(15, rng); // depth 15
            } else if (testId == 20) {
                root = buildRightSkewedTree(15, rng); // depth 15
            } else if (testId == 21) {
                root = buildZigZagTree(20, rng); // depth 20
            } else if (testId == 22) {
                // V-shaped tree: left chain 10, right chain 10 -> depth 11
                root = new TreeNode(100);
                TreeNode l = root;
                for (int i = 0; i < 10; i++) {
                    l.left = new TreeNode(i);
                    l = l.left;
                }
                TreeNode r = root;
                for (int i = 0; i < 10; i++) {
                    r.right = new TreeNode(i + 20);
                    r = r.right;
                }
            } else if (testId == 23) {
                // Asymmetric: left chain 25, right leaf -> depth 26
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                TreeNode curr = root;
                for (int i = 3; i <= 26; i++) {
                    curr.left = new TreeNode(i);
                    curr = curr.left;
                }
            } else if (testId == 24) {
                // Asymmetric: left leaf, right chain 25 -> depth 26
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                TreeNode curr = root;
                for (int i = 3; i <= 26; i++) {
                    curr.right = new TreeNode(i);
                    curr = curr.right;
                }
            } else if (testId >= 25 && testId <= 34) {
                int n = 8 + (testId - 25) * 5;
                root = buildRandomTree(n, 0.4 + rng.nextDouble() * 0.4, rng);
            }
            // Suite 3: Mid-Range Random Stress Cases (35 - 64)
            else if (testId == 35) {
                root = buildCompleteTree(127, rng); // depth 7
            } else if (testId == 36) {
                root = buildCompleteTree(255, rng); // depth 8
            } else if (testId == 37) {
                root = buildCompleteTree(511, rng); // depth 9
            } else if (testId == 38) {
                root = buildCompleteTree(1023, rng); // depth 10
            } else if (testId == 39) {
                root = buildCompleteTree(2047, rng); // depth 11
            } else if (testId == 40) {
                root = buildLeftSkewedTree(500, rng);
            } else if (testId == 41) {
                root = buildRightSkewedTree(500, rng);
            } else if (testId == 42) {
                root = buildZigZagTree(500, rng);
            } else if (testId == 43) {
                root = buildLeftSkewedTree(1000, rng);
            } else if (testId == 44) {
                root = buildRightSkewedTree(1000, rng);
            } else if (testId == 45) {
                root = buildZigZagTree(1000, rng);
            } else if (testId == 46) {
                // Core balanced tree (255 nodes) + tail of 500 nodes on a leaf
                root = buildCompleteTree(255, rng);
                TreeNode curr = root;
                while (curr.left != null) curr = curr.left;
                for (int i = 0; i < 500; i++) {
                    curr.right = new TreeNode(rng.nextInt(10001));
                    curr = curr.right;
                }
            } else if (testId == 47) {
                // Comb tree: backbone on right, teeth on left
                root = new TreeNode(rng.nextInt(10001));
                TreeNode curr = root;
                for (int i = 0; i < 800; i++) {
                    curr.left = new TreeNode(rng.nextInt(10001));
                    curr.right = new TreeNode(rng.nextInt(10001));
                    curr = curr.right;
                }
            } else if (testId == 48) {
                // Comb tree: backbone on left, teeth on right
                root = new TreeNode(rng.nextInt(10001));
                TreeNode curr = root;
                for (int i = 0; i < 800; i++) {
                    curr.right = new TreeNode(rng.nextInt(10001));
                    curr.left = new TreeNode(rng.nextInt(10001));
                    curr = curr.left;
                }
            } else if (testId >= 49 && testId <= 54) {
                // Random BST trees (depth typically O(sqrt(N)) or O(log N))
                int n = 200 + (testId - 49) * 150;
                int[] vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = rng.nextInt(10001);
                root = buildBST(vals);
            } else if (testId >= 55 && testId <= 64) {
                // Random trees of mid size (500 to 2000 nodes)
                int n = 500 + (testId - 55) * 150;
                root = buildRandomTree(n, 0.5, rng);
            }
            // Suite 4: Pathological & Maximum Boundary Stress Cases (65 - 99)
            else if (testId == 65) {
                root = buildCompleteTree(4095, rng); // depth 12
            } else if (testId == 66) {
                root = buildCompleteTree(8191, rng); // depth 13
            } else if (testId == 67) {
                root = buildCompleteTree(10000, rng); // boundary maximum N=10000 complete tree
            } else if (testId == 68) {
                root = buildCompleteTree(9999, rng);
            } else if (testId == 69) {
                root = buildLeftSkewedTree(5000, rng); // depth 5000
            } else if (testId == 70) {
                root = buildRightSkewedTree(5000, rng); // depth 5000
            } else if (testId == 71) {
                root = buildZigZagTree(5000, rng); // depth 5000
            } else if (testId == 72) {
                root = buildLeftSkewedTree(8000, rng); // depth 8000
            } else if (testId == 73) {
                root = buildRightSkewedTree(8000, rng); // depth 8000
            } else if (testId == 74) {
                root = buildZigZagTree(8000, rng); // depth 8000
            } else if (testId == 75) {
                // Extreme maximum left-skewed: N = 10,000, depth = 10,000!
                root = buildLeftSkewedTree(10000, rng);
            } else if (testId == 76) {
                // Extreme maximum right-skewed: N = 10,000, depth = 10,000!
                root = buildRightSkewedTree(10000, rng);
            } else if (testId == 77) {
                // Extreme maximum zig-zag: N = 10,000, depth = 10,000!
                root = buildZigZagTree(10000, rng);
            } else if (testId == 78) {
                // Long left chain of 9,990 with 9 branches near root
                root = buildLeftSkewedTree(9990, rng);
                TreeNode curr = root;
                for (int i = 0; i < 9; i++) {
                    curr.right = new TreeNode(rng.nextInt(10001));
                    curr = curr.left;
                }
            } else if (testId == 79) {
                // Long right chain of 9,990 with 9 branches near root
                root = buildRightSkewedTree(9990, rng);
                TreeNode curr = root;
                for (int i = 0; i < 9; i++) {
                    curr.left = new TreeNode(rng.nextInt(10001));
                    curr = curr.right;
                }
            } else if (testId == 80) {
                // V-tree: root + 4999 left + 5000 right = 10,000 nodes total
                root = new TreeNode(rng.nextInt(10001));
                TreeNode currL = root;
                for (int i = 0; i < 4999; i++) {
                    currL.left = new TreeNode(rng.nextInt(10001));
                    currL = currL.left;
                }
                TreeNode currR = root;
                for (int i = 0; i < 5000; i++) {
                    currR.right = new TreeNode(rng.nextInt(10001));
                    currR = currR.right;
                }
            } else if (testId == 81) {
                // Balanced tree of 5,000 nodes + tail of 5,000 nodes
                root = buildCompleteTree(5000, rng);
                TreeNode curr = root;
                while (curr.right != null) curr = curr.right;
                for (int i = 0; i < 5000; i++) {
                    curr.left = new TreeNode(rng.nextInt(10001));
                    curr = curr.left;
                }
            } else if (testId == 82) {
                // Caterpillar tree: backbone of 5,000, each has 1 opposite leaf -> 10,000 nodes
                root = new TreeNode(rng.nextInt(10001));
                TreeNode curr = root;
                for (int i = 0; i < 4999; i++) {
                    curr.right = new TreeNode(rng.nextInt(10001));
                    curr.left = new TreeNode(rng.nextInt(10001));
                    curr = curr.right;
                }
                curr.left = new TreeNode(rng.nextInt(10001));
            } else if (testId == 83) {
                int[] vals = new int[7000];
                for (int i = 0; i < vals.length; i++) vals[i] = rng.nextInt(10001);
                root = buildBST(vals);
            } else if (testId == 84) {
                int[] vals = new int[8500];
                for (int i = 0; i < vals.length; i++) vals[i] = rng.nextInt(10001);
                root = buildBST(vals);
            } else if (testId == 85) {
                int[] vals = new int[10000];
                for (int i = 0; i < vals.length; i++) vals[i] = rng.nextInt(10001);
                root = buildBST(vals);
            } else {
                // testId 86 - 99: Large random stress trees (N from 3,000 to 10,000)
                int n = 3000 + (testId - 86) * 500;
                if (n > 10000) n = 10000;
                root = buildRandomTree(n, 0.4 + rng.nextDouble() * 0.3, rng);
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
