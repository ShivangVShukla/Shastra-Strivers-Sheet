import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Self-contained Test Case Generator for "Boundary Traversal".
 *
 * Generates exactly 100 input and 100 output test files:
 * - Testcases/Input/input00.txt to input99.txt
 * - Testcases/Output/output00.txt to output99.txt
 *
 * Spans:
 * - Edge / Base cases (empty tree, single node, 2-node, 3-node, TUF samples, boundaries)
 * - Small structured trees (perfect, skewed, zig-zag)
 * - Mid-range random stress cases (N = 50 to 2,500)
 * - Pathological worst-case boundary cases (depth up to 10^4, N = 10^4, complete trees)
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

    private static boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
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
     * Optimal internal solver for anti-clockwise boundary traversal.
     */
    static List<Integer> solve(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // 1. Root node (if not a leaf)
        if (!isLeaf(root)) {
            result.add(root.val);
        }

        // 2. Left boundary (excluding leaves)
        TreeNode curr = root.left;
        while (curr != null) {
            if (!isLeaf(curr)) {
                result.add(curr.val);
            }
            if (curr.left != null) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        // 3. Leaves (left-to-right) via iterative explicit-stack DFS
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (isLeaf(node)) {
                result.add(node.val);
            } else {
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
            }
        }

        // 4. Right boundary in reverse (excluding leaves)
        ArrayList<Integer> rightBoundary = new ArrayList<>();
        curr = root.right;
        while (curr != null) {
            if (!isLeaf(curr)) {
                rightBoundary.add(curr.val);
            }
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        for (int i = rightBoundary.size() - 1; i >= 0; i--) {
            result.add(rightBoundary.get(i));
        }

        return result;
    }

    /**
     * Formats integer list into standard output string: [val1, val2, ...] or [].
     */
    static String formatResult(List<Integer> result) {
        if (result == null || result.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(result.get(i));
        }
        sb.append("]");
        return sb.toString();
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

    /**
     * Builds a complete binary tree of n nodes.
     */
    static TreeNode buildCompleteTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new TreeNode(rng.nextInt(2001) - 1000);
        }
        for (int i = 0; i < n; i++) {
            int leftIdx = 2 * i + 1;
            int rightIdx = 2 * i + 2;
            if (leftIdx < n) nodes[i].left = nodes[leftIdx];
            if (rightIdx < n) nodes[i].right = nodes[rightIdx];
        }
        return nodes[0];
    }

    /**
     * Builds a random binary tree with n nodes.
     */
    static TreeNode buildRandomTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(rng.nextInt(2001) - 1000);
        ArrayList<TreeNode> candidates = new ArrayList<>();
        candidates.add(root);
        int created = 1;

        while (created < n && !candidates.isEmpty()) {
            int idx = rng.nextInt(candidates.size());
            TreeNode parent = candidates.get(idx);

            boolean attachLeft = parent.left == null && (parent.right != null || rng.nextBoolean());
            if (attachLeft) {
                parent.left = new TreeNode(rng.nextInt(2001) - 1000);
                candidates.add(parent.left);
                created++;
            } else if (parent.right == null) {
                parent.right = new TreeNode(rng.nextInt(2001) - 1000);
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

            // Suite 1: Edge & TUF Sample Cases (00 - 19)
            if (testId == 0) {
                root = null; // empty -> []
            } else if (testId == 1) {
                root = new TreeNode(42); // single node -> [42]
            } else if (testId == 2) {
                root = new TreeNode(-1000); // min boundary -> [-1000]
            } else if (testId == 3) {
                root = new TreeNode(1000); // max boundary -> [1000]
            } else if (testId == 4) {
                root = new TreeNode(0); // zero -> [0]
            } else if (testId == 5) {
                // TUF Sample 1: [1, 2, 3, 4, 5, 6, 7, null, null, 8, 9] -> [1, 2, 4, 8, 9, 6, 7, 3]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.left.right = new TreeNode(5);
                root.right.left = new TreeNode(6);
                root.right.right = new TreeNode(7);
                root.left.right.left = new TreeNode(8);
                root.left.right.right = new TreeNode(9);
            } else if (testId == 6) {
                // TUF Sample 2: [1, 2, null, 4, 9, 6, 5, 3, null, null, null, null, null, 7, 8]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(4);
                root.left.right = new TreeNode(9);
                root.left.left.left = new TreeNode(6);
                root.left.left.right = new TreeNode(5);
                root.left.right.left = new TreeNode(3);
                root.left.left.left.left = new TreeNode(7);
                root.left.left.left.right = new TreeNode(8);
            } else if (testId == 7) {
                // TUF Sample 3: [5, 1, 2, 8, null, 4, 5, null, 6] -> [5, 1, 8, 6, 4, 5, 2]
                root = new TreeNode(5);
                root.left = new TreeNode(1);
                root.right = new TreeNode(2);
                root.left.left = new TreeNode(8);
                root.right.left = new TreeNode(4);
                root.right.right = new TreeNode(5);
                root.left.left.right = new TreeNode(6);
            } else if (testId == 8) {
                // 2 nodes: left only -> [1, 2]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
            } else if (testId == 9) {
                // 2 nodes: right only -> [1, 2]
                root = new TreeNode(1);
                root.right = new TreeNode(2);
            } else if (testId == 10) {
                // 3 nodes: root with 2 children -> [1, 2, 3]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
            } else if (testId == 11) {
                // 3 nodes: left-skewed -> [1, 2, 3]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(3);
            } else if (testId == 12) {
                // 3 nodes: right-skewed -> [1, 3, 2]
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.right = new TreeNode(3);
            } else if (testId == 13) {
                // 3 nodes: root -> left -> right -> [1, 2, 3]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.right = new TreeNode(3);
            } else if (testId == 14) {
                // 3 nodes: root -> right -> left -> [1, 3, 2]
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.left = new TreeNode(3);
            } else if (testId == 15) {
                // V-tree: root=1, left=2->4, right=3->5 -> [1, 2, 4, 5, 3]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.right.right = new TreeNode(5);
            } else if (testId == 16) {
                // Inverted V: root=1, left=2->right=4, right=3->left=5 -> [1, 2, 4, 5, 3]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.right = new TreeNode(4);
                root.right.left = new TreeNode(5);
            } else if (testId == 17) {
                // All negative values
                root = new TreeNode(-10);
                root.left = new TreeNode(-20);
                root.right = new TreeNode(-30);
                root.left.left = new TreeNode(-40);
                root.right.right = new TreeNode(-50);
            } else if (testId == 18) {
                // Tree with only right subtree
                root = new TreeNode(100);
                root.right = new TreeNode(200);
                root.right.left = new TreeNode(300);
                root.right.right = new TreeNode(400);
            } else if (testId == 19) {
                // Tree with only left subtree
                root = new TreeNode(100);
                root.left = new TreeNode(200);
                root.left.left = new TreeNode(300);
                root.left.right = new TreeNode(400);
            }
            // Suite 2: Small & Structured Trees (20 - 49)
            else if (testId >= 20 && testId <= 29) {
                // Complete trees of depth 3 to 5 (7 to 31 nodes)
                int n = 7 + (testId - 20) * 3;
                root = buildCompleteTree(n, rng);
            } else if (testId >= 30 && testId <= 39) {
                // Skewed chains
                int len = 10 + (testId - 30) * 8;
                root = new TreeNode(rng.nextInt(2001) - 1000);
                TreeNode curr = root;
                boolean leftSkew = (testId % 2 == 0);
                for (int i = 1; i < len; i++) {
                    TreeNode next = new TreeNode(rng.nextInt(2001) - 1000);
                    if (leftSkew) curr.left = next;
                    else curr.right = next;
                    curr = next;
                }
            } else if (testId >= 40 && testId <= 49) {
                // Zig-zag chains
                int len = 12 + (testId - 40) * 6;
                root = new TreeNode(rng.nextInt(2001) - 1000);
                TreeNode curr = root;
                for (int i = 1; i < len; i++) {
                    TreeNode next = new TreeNode(rng.nextInt(2001) - 1000);
                    if (i % 2 == 1) curr.left = next;
                    else curr.right = next;
                    curr = next;
                }
            }
            // Suite 3: Mid-Size Random Stress Cases (50 - 69)
            else if (testId >= 50 && testId <= 69) {
                int n = 50 + (testId - 50) * 100; // 50 to ~1950 nodes
                root = buildRandomTree(n, rng);
            }
            // Suite 4: Pathological & Boundary Stress Cases (70 - 99)
            else if (testId == 70) {
                root = buildCompleteTree(1023, rng); // depth 10
            } else if (testId == 71) {
                root = buildCompleteTree(2047, rng); // depth 11
            } else if (testId == 72) {
                root = buildCompleteTree(4095, rng); // depth 12
            } else if (testId == 73) {
                root = buildCompleteTree(8191, rng); // depth 13
            } else if (testId == 74) {
                // Complete tree with N = 9999
                root = buildCompleteTree(9999, rng);
            } else if (testId == 75) {
                // Complete tree with N = 10000 (boundary max)
                root = buildCompleteTree(10000, rng);
            } else if (testId == 76) {
                // Left-skewed chain N = 5000
                root = new TreeNode(1);
                TreeNode curr = root;
                for (int i = 1; i < 5000; i++) {
                    curr.left = new TreeNode(i % 1000);
                    curr = curr.left;
                }
            } else if (testId == 77) {
                // Right-skewed chain N = 5000
                root = new TreeNode(-1);
                TreeNode curr = root;
                for (int i = 1; i < 5000; i++) {
                    curr.right = new TreeNode(-(i % 1000));
                    curr = curr.right;
                }
            } else if (testId == 78) {
                // Zig-zag chain N = 5000
                root = new TreeNode(42);
                TreeNode curr = root;
                for (int i = 1; i < 5000; i++) {
                    TreeNode next = new TreeNode((i % 2 == 0 ? 1 : -1) * (i % 1000));
                    if (i % 2 == 1) curr.left = next;
                    else curr.right = next;
                    curr = next;
                }
            } else if (testId == 79) {
                // 2-left 2-right alternating chain N = 5000
                root = new TreeNode(7);
                TreeNode curr = root;
                for (int i = 1; i < 5000; i++) {
                    TreeNode next = new TreeNode(i % 500);
                    if ((i / 2) % 2 == 0) curr.left = next;
                    else curr.right = next;
                    curr = next;
                }
            } else if (testId == 80) {
                // Boundary maximum left-skewed chain N = 10000
                root = new TreeNode(1000);
                TreeNode curr = root;
                for (int i = 1; i < 10000; i++) {
                    curr.left = new TreeNode((i * 17) % 2001 - 1000);
                    curr = curr.left;
                }
            } else if (testId == 81) {
                // Boundary maximum right-skewed chain N = 10000
                root = new TreeNode(-1000);
                TreeNode curr = root;
                for (int i = 1; i < 10000; i++) {
                    curr.right = new TreeNode((i * 31) % 2001 - 1000);
                    curr = curr.right;
                }
            } else if (testId == 82) {
                // Boundary maximum zig-zag chain N = 10000
                root = new TreeNode(0);
                TreeNode curr = root;
                for (int i = 1; i < 10000; i++) {
                    TreeNode next = new TreeNode(rng.nextInt(2001) - 1000);
                    if (i % 2 == 1) curr.left = next;
                    else curr.right = next;
                    curr = next;
                }
            } else if (testId == 83) {
                // Symmetric V-tree N = 9999 (two arms of 4999 nodes each)
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 4999; i++) {
                    l.left = new TreeNode(i % 1000);
                    l = l.left;
                    r.right = new TreeNode(-(i % 1000));
                    r = r.right;
                }
            } else if (testId == 84) {
                // Asymmetric V-tree N = 9999 (left arm 7000 nodes, right arm 2998 nodes)
                root = new TreeNode(1);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 7000; i++) {
                    l.left = new TreeNode(rng.nextInt(2001) - 1000);
                    l = l.left;
                }
                for (int i = 1; i <= 2998; i++) {
                    r.right = new TreeNode(rng.nextInt(2001) - 1000);
                    r = r.right;
                }
            } else if (testId == 85) {
                // Comb tree: spine of 5000 nodes, each having a right leaf child (N = 10000)
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                TreeNode curr = root;
                for (int i = 1; i < 5000; i++) {
                    curr.left = new TreeNode(i);
                    curr = curr.left;
                    curr.right = new TreeNode(-i);
                }
            } else {
                // 86 to 99: Large random stress trees N = 3000 to 9999
                int n = 3000 + (testId - 86) * 500;
                root = buildRandomTree(n, rng);
            }

            // Serialize input
            String inputContent = serialize(root);

            // Compute expected output
            List<Integer> expectedList = solve(root);
            String outputContent = formatResult(expectedList);

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
