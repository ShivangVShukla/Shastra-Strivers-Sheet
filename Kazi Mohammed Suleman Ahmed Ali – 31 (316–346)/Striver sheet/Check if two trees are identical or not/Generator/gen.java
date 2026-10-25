import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

/**
 * Self-contained Test Case Generator for "Check if two trees are identical or not".
 *
 * Generates exactly 100 input and 100 output test files:
 * - Testcases/Input/input00.txt to input99.txt
 * - Testcases/Output/output00.txt to output99.txt
 *
 * Spans:
 * - Edge / Base cases (both empty, one empty, single node boundaries, TUF samples)
 * - Small structured trees (perfect, skewed, zig-zag, V-trees)
 * - Mid-range random stress cases (both matching and non-matching)
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
     * Deep clone of a binary tree.
     */
    static TreeNode cloneTree(TreeNode root) {
        if (root == null) return null;
        TreeNode copy = new TreeNode(root.val);
        copy.left = cloneTree(root.left);
        copy.right = cloneTree(root.right);
        return copy;
    }

    /**
     * Optimal internal solver to determine if two binary trees are identical.
     * Uses iterative BFS to ensure stack-safety for depths up to 10^4.
     */
    static boolean solve(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        ArrayDeque<TreeNode> queueP = new ArrayDeque<>();
        ArrayDeque<TreeNode> queueQ = new ArrayDeque<>();
        queueP.add(p);
        queueQ.add(q);

        while (!queueP.isEmpty() && !queueQ.isEmpty()) {
            TreeNode nodeP = queueP.poll();
            TreeNode nodeQ = queueQ.poll();

            if (nodeP.val != nodeQ.val) {
                return false;
            }

            if (nodeP.left != null && nodeQ.left != null) {
                queueP.add(nodeP.left);
                queueQ.add(nodeQ.left);
            } else if (nodeP.left != null || nodeQ.left != null) {
                return false;
            }

            if (nodeP.right != null && nodeQ.right != null) {
                queueP.add(nodeP.right);
                queueQ.add(nodeQ.right);
            } else if (nodeP.right != null || nodeQ.right != null) {
                return false;
            }
        }

        return queueP.isEmpty() && queueQ.isEmpty();
    }

    /**
     * Writes input (two lines) or output (single line) to a file.
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
            nodes[i] = new TreeNode(rng.nextInt(20001) - 10000);
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
        TreeNode root = new TreeNode(rng.nextInt(20001) - 10000);
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.left = new TreeNode(rng.nextInt(20001) - 10000);
            curr = curr.left;
        }
        return root;
    }

    static TreeNode buildRightSkewedTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(rng.nextInt(20001) - 10000);
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            curr.right = new TreeNode(rng.nextInt(20001) - 10000);
            curr = curr.right;
        }
        return root;
    }

    static TreeNode buildZigZagTree(int n, Random rng) {
        if (n <= 0) return null;
        TreeNode root = new TreeNode(rng.nextInt(20001) - 10000);
        TreeNode curr = root;
        for (int i = 1; i < n; i++) {
            TreeNode next = new TreeNode(rng.nextInt(20001) - 10000);
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
        TreeNode root = new TreeNode(rng.nextInt(20001) - 10000);
        ArrayList<TreeNode> candidates = new ArrayList<>();
        candidates.add(root);
        int created = 1;

        while (created < n && !candidates.isEmpty()) {
            int idx = rng.nextInt(candidates.size());
            TreeNode parent = candidates.get(idx);

            boolean attachLeft = parent.left == null && (parent.right != null || rng.nextBoolean());
            if (attachLeft) {
                parent.left = new TreeNode(rng.nextInt(20001) - 10000);
                candidates.add(parent.left);
                created++;
            } else if (parent.right == null) {
                parent.right = new TreeNode(rng.nextInt(20001) - 10000);
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

        Random rng = new Random(7777777L);

        for (int testId = 0; testId < 100; testId++) {
            TreeNode p = null;
            TreeNode q = null;

            // Suite 1: Edge & Base Cases (00 - 19)
            if (testId == 0) {
                // Both empty
                p = null;
                q = null;
            } else if (testId == 1) {
                p = null;
                q = new TreeNode(1);
            } else if (testId == 2) {
                p = new TreeNode(1);
                q = null;
            } else if (testId == 3) {
                // Identical single node
                p = new TreeNode(42);
                q = new TreeNode(42);
            } else if (testId == 4) {
                // Single node value mismatch
                p = new TreeNode(42);
                q = new TreeNode(99);
            } else if (testId == 5) {
                // Boundary min -10000 identical
                p = new TreeNode(-10000);
                q = new TreeNode(-10000);
            } else if (testId == 6) {
                // Boundary max 10000 identical
                p = new TreeNode(10000);
                q = new TreeNode(10000);
            } else if (testId == 7) {
                // Boundary min vs max
                p = new TreeNode(-10000);
                q = new TreeNode(10000);
            } else if (testId == 8) {
                // TUF Sample 1: [1, 2, 3] vs [1, 2, 3] -> true
                p = new TreeNode(1);
                p.left = new TreeNode(2);
                p.right = new TreeNode(3);
                q = cloneTree(p);
            } else if (testId == 9) {
                // TUF Sample 2: [1, 2, 1] vs [1, 1, 2] -> false
                p = new TreeNode(1);
                p.left = new TreeNode(2);
                p.right = new TreeNode(1);
                q = new TreeNode(1);
                q.left = new TreeNode(1);
                q.right = new TreeNode(2);
            } else if (testId == 10) {
                // TUF Sample 3: [1, 2] vs [1, null, 2] -> false
                p = new TreeNode(1);
                p.left = new TreeNode(2);
                q = new TreeNode(1);
                q.right = new TreeNode(2);
            } else if (testId == 11) {
                // TUF Gamification Sample -> false
                p = new TreeNode(5);
                p.left = new TreeNode(1);
                p.right = new TreeNode(2);
                p.left.left = new TreeNode(8);
                p.right.right = new TreeNode(5);
                p.left.left.right = new TreeNode(4);
                p.left.left.right.right = new TreeNode(7);

                q = new TreeNode(5);
                q.left = new TreeNode(1);
                q.right = new TreeNode(2);
                q.left.left = new TreeNode(8);
                q.right.right = new TreeNode(4); // mutated value 4 instead of 5
                q.left.left.right = new TreeNode(5);
                q.left.left.right.right = new TreeNode(7);
            } else if (testId == 12) {
                // Two nodes identical left
                p = new TreeNode(10);
                p.left = new TreeNode(20);
                q = cloneTree(p);
            } else if (testId == 13) {
                // Two nodes identical right
                p = new TreeNode(10);
                p.right = new TreeNode(20);
                q = cloneTree(p);
            } else if (testId == 14) {
                // Two nodes structure match, value mismatch
                p = new TreeNode(10);
                p.left = new TreeNode(20);
                q = new TreeNode(10);
                q.left = new TreeNode(25);
            } else if (testId == 15) {
                // 3 nodes zig identical
                p = new TreeNode(1);
                p.left = new TreeNode(2);
                p.left.right = new TreeNode(3);
                q = cloneTree(p);
            } else if (testId == 16) {
                // 3 nodes zag identical
                p = new TreeNode(1);
                p.right = new TreeNode(2);
                p.right.left = new TreeNode(3);
                q = cloneTree(p);
            } else if (testId == 17) {
                // 3 nodes zig vs zag
                p = new TreeNode(1);
                p.left = new TreeNode(2);
                p.left.right = new TreeNode(3);
                q = new TreeNode(1);
                q.right = new TreeNode(2);
                q.right.left = new TreeNode(3);
            } else if (testId == 18) {
                // Negative values identical
                p = new TreeNode(-5);
                p.left = new TreeNode(-10);
                p.right = new TreeNode(-15);
                q = cloneTree(p);
            } else if (testId == 19) {
                // Negative vs positive
                p = new TreeNode(-1);
                p.left = new TreeNode(-2);
                q = new TreeNode(1);
                q.left = new TreeNode(2);
            }
            // Suite 2: Small Trees (20 - 39)
            else if (testId == 20) {
                p = buildCompleteTree(7, rng);
                q = cloneTree(p);
            } else if (testId == 21) {
                p = buildCompleteTree(7, rng);
                q = cloneTree(p);
                q.val += 1; // root mismatch
            } else if (testId == 22) {
                p = buildCompleteTree(7, rng);
                q = cloneTree(p);
                q.right.right.val += 1; // leaf value mismatch
            } else if (testId == 23) {
                p = buildCompleteTree(7, rng);
                q = cloneTree(p);
                q.right.right = null; // leaf missing
            } else if (testId == 24) {
                p = buildCompleteTree(15, rng);
                q = cloneTree(p);
            } else if (testId == 25) {
                p = buildCompleteTree(15, rng);
                q = cloneTree(p);
                q.left.right = null; // structural mismatch
            } else if (testId == 26) {
                p = buildLeftSkewedTree(15, rng);
                q = cloneTree(p);
            } else if (testId == 27) {
                p = buildRightSkewedTree(15, rng);
                q = cloneTree(p);
            } else if (testId == 28) {
                p = buildLeftSkewedTree(15, rng);
                q = buildRightSkewedTree(15, rng);
            } else if (testId == 29) {
                p = buildZigZagTree(20, rng);
                q = cloneTree(p);
            } else if (testId == 30) {
                p = buildZigZagTree(20, rng);
                q = cloneTree(p);
                // mutate 10th node
                TreeNode curr = q;
                for (int i = 0; i < 10; i++) {
                    curr = (curr.left != null) ? curr.left : curr.right;
                }
                curr.val += 5;
            } else if (testId == 31) {
                // V-shaped tree identical (N = 21)
                p = new TreeNode(0);
                TreeNode l = p, r = p;
                for (int i = 1; i <= 10; i++) {
                    l.left = new TreeNode(i);
                    l = l.left;
                    r.right = new TreeNode(-i);
                    r = r.right;
                }
                q = cloneTree(p);
            } else if (testId == 32) {
                // V-shaped tree with extra leaf on left
                p = new TreeNode(0);
                TreeNode l = p, r = p;
                for (int i = 1; i <= 10; i++) {
                    l.left = new TreeNode(i);
                    l = l.left;
                    r.right = new TreeNode(-i);
                    r = r.right;
                }
                q = cloneTree(p);
                l = q;
                while (l.left != null) l = l.left;
                l.left = new TreeNode(99);
            } else if (testId == 33) {
                p = buildRandomTree(12, 0.5, rng);
                q = cloneTree(p);
            } else if (testId == 34) {
                p = buildRandomTree(12, 0.5, rng);
                q = cloneTree(p);
                q.val ^= 1;
            } else if (testId == 35) {
                p = buildRandomTree(25, 0.6, rng);
                q = cloneTree(p);
            } else if (testId == 36) {
                p = buildRandomTree(25, 0.6, rng);
                q = cloneTree(p);
                if (q.left != null) q.left = null;
                else q.right = null;
            } else if (testId == 37) {
                p = buildRandomTree(40, 0.5, rng);
                q = cloneTree(p);
            } else if (testId == 38) {
                p = buildRandomTree(40, 0.5, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.left != null) curr = curr.left;
                curr.left = new TreeNode(777);
            } else if (testId == 39) {
                p = buildRandomTree(30, 0.5, rng);
                q = buildRandomTree(30, 0.5, rng);
            }
            // Suite 3: Mid-Range Random Stress Cases (40 - 69)
            else if (testId == 40) {
                p = buildCompleteTree(127, rng);
                q = cloneTree(p);
            } else if (testId == 41) {
                p = buildCompleteTree(127, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.right != null) curr = curr.right;
                curr.val += 1;
            } else if (testId == 42) {
                p = buildCompleteTree(255, rng);
                q = cloneTree(p);
            } else if (testId == 43) {
                p = buildCompleteTree(255, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.left != null && curr.left.left != null) curr = curr.left;
                curr.left = null;
            } else if (testId == 44) {
                p = buildCompleteTree(511, rng);
                q = cloneTree(p);
            } else if (testId == 45) {
                p = buildCompleteTree(511, rng);
                q = cloneTree(p);
                q.left.val ^= 1;
            } else if (testId == 46) {
                p = buildCompleteTree(1023, rng);
                q = cloneTree(p);
            } else if (testId == 47) {
                p = buildCompleteTree(1023, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.right != null) curr = curr.right;
                curr.val += 10;
            } else if (testId == 48) {
                p = buildCompleteTree(2047, rng);
                q = cloneTree(p);
            } else if (testId == 49) {
                p = buildCompleteTree(2047, rng);
                q = cloneTree(p);
                q.right.val += 1;
            } else if (testId == 50) {
                p = buildLeftSkewedTree(500, rng);
                q = cloneTree(p);
            } else if (testId == 51) {
                p = buildLeftSkewedTree(500, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.left != null) curr = curr.left;
                curr.val += 1; // deepest leaf mismatch
            } else if (testId == 52) {
                p = buildRightSkewedTree(500, rng);
                q = cloneTree(p);
            } else if (testId == 53) {
                p = buildRightSkewedTree(500, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                for (int i = 0; i < 498; i++) curr = curr.right;
                curr.right = null; // length 499 vs 500
            } else if (testId == 54) {
                p = buildZigZagTree(800, rng);
                q = cloneTree(p);
            } else if (testId == 55) {
                p = buildZigZagTree(800, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                for (int i = 0; i < 798; i++) curr = (curr.left != null) ? curr.left : curr.right;
                curr.val += 1;
            } else if (testId == 56) {
                // Comb tree identical (N = 1000)
                p = new TreeNode(1);
                TreeNode curr = p;
                for (int i = 0; i < 500; i++) {
                    curr.left = new TreeNode(i);
                    curr.right = new TreeNode(-i);
                    curr = curr.right;
                }
                q = cloneTree(p);
            } else if (testId == 57) {
                // Comb tree tooth missing
                p = new TreeNode(1);
                TreeNode curr = p;
                for (int i = 0; i < 500; i++) {
                    curr.left = new TreeNode(i);
                    curr.right = new TreeNode(-i);
                    curr = curr.right;
                }
                q = cloneTree(p);
                q.left = null;
            } else if (testId >= 58 && testId <= 63) {
                int n = 300 + (testId - 58) * 200;
                int[] vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = rng.nextInt(20001) - 10000;
                p = buildBST(vals);
                q = cloneTree(p);
                if (testId % 2 == 1) {
                    TreeNode c = q;
                    while (c.left != null) c = c.left;
                    c.val += 1;
                }
            } else if (testId >= 64 && testId <= 69) {
                int n = 500 + (testId - 64) * 350;
                p = buildRandomTree(n, 0.5, rng);
                q = cloneTree(p);
                if (testId % 2 == 1) {
                    TreeNode c = q;
                    while (c.right != null) c = c.right;
                    c.val += 1;
                }
            }
            // Suite 4: Pathological & Maximum Boundary Cases (70 - 99)
            else if (testId == 70) {
                p = buildCompleteTree(4095, rng);
                q = cloneTree(p);
            } else if (testId == 71) {
                p = buildCompleteTree(4095, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.right != null) curr = curr.right;
                curr.val += 1;
            } else if (testId == 72) {
                p = buildCompleteTree(8191, rng);
                q = cloneTree(p);
            } else if (testId == 73) {
                p = buildCompleteTree(8191, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.right != null && curr.right.right != null) curr = curr.right;
                curr.right = null;
            } else if (testId == 74) {
                // Boundary max N = 10,000 complete tree identical
                p = buildCompleteTree(10000, rng);
                q = cloneTree(p);
            } else if (testId == 75) {
                // Boundary max N = 10,000 complete tree deepest leaf mismatch
                p = buildCompleteTree(10000, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.right != null) curr = curr.right;
                curr.val ^= 1;
            } else if (testId == 76) {
                p = buildLeftSkewedTree(5000, rng);
                q = cloneTree(p);
            } else if (testId == 77) {
                p = buildLeftSkewedTree(5000, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.left != null) curr = curr.left;
                curr.val += 1;
            } else if (testId == 78) {
                p = buildRightSkewedTree(5000, rng);
                q = cloneTree(p);
            } else if (testId == 79) {
                p = buildRightSkewedTree(5000, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.right != null) curr = curr.right;
                curr.val += 1;
            } else if (testId == 80) {
                p = buildZigZagTree(5000, rng);
                q = cloneTree(p);
            } else if (testId == 81) {
                p = buildZigZagTree(5000, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.left != null || curr.right != null) {
                    curr = (curr.left != null) ? curr.left : curr.right;
                }
                curr.val += 1;
            } else if (testId == 82) {
                // Extreme maximum left-skewed N = 10,000 identical
                p = buildLeftSkewedTree(10000, rng);
                q = cloneTree(p);
            } else if (testId == 83) {
                // Extreme maximum left-skewed N = 10,000 deepest leaf mutated
                p = buildLeftSkewedTree(10000, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.left != null) curr = curr.left;
                curr.val += 1;
            } else if (testId == 84) {
                // Extreme maximum right-skewed N = 10,000 identical
                p = buildRightSkewedTree(10000, rng);
                q = cloneTree(p);
            } else if (testId == 85) {
                // Extreme maximum right-skewed N = 10,000 deepest leaf missing
                p = buildRightSkewedTree(10000, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                for (int i = 0; i < 9998; i++) curr = curr.right;
                curr.right = null;
            } else if (testId == 86) {
                // Extreme maximum zig-zag N = 10,000 identical
                p = buildZigZagTree(10000, rng);
                q = cloneTree(p);
            } else if (testId == 87) {
                // Extreme maximum zig-zag N = 10,000 deepest leaf mutated
                p = buildZigZagTree(10000, rng);
                q = cloneTree(p);
                TreeNode curr = q;
                while (curr.left != null || curr.right != null) {
                    curr = (curr.left != null) ? curr.left : curr.right;
                }
                curr.val += 1;
            } else if (testId == 88) {
                // V-tree N = 10,000 identical
                p = new TreeNode(0);
                TreeNode l = p, r = p;
                for (int i = 1; i < 5000; i++) {
                    l.left = new TreeNode(i);
                    l = l.left;
                    r.right = new TreeNode(-i);
                    r = r.right;
                }
                q = cloneTree(p);
            } else if (testId == 89) {
                // V-tree right tail missing 1 node
                p = new TreeNode(0);
                TreeNode l = p, r = p;
                for (int i = 1; i < 5000; i++) {
                    l.left = new TreeNode(i);
                    l = l.left;
                    r.right = new TreeNode(-i);
                    r = r.right;
                }
                q = cloneTree(p);
                r = q;
                for (int i = 1; i < 4998; i++) r = r.right;
                r.right = null;
            } else if (testId == 90) {
                // Caterpillar tree N = 10,000 identical
                p = new TreeNode(1);
                TreeNode curr = p;
                for (int i = 0; i < 4999; i++) {
                    curr.right = new TreeNode(rng.nextInt(20001) - 10000);
                    curr.left = new TreeNode(rng.nextInt(20001) - 10000);
                    curr = curr.right;
                }
                q = cloneTree(p);
            } else if (testId == 91) {
                // Caterpillar tree 1 leg mutated
                p = new TreeNode(1);
                TreeNode curr = p;
                for (int i = 0; i < 4999; i++) {
                    curr.right = new TreeNode(rng.nextInt(20001) - 10000);
                    curr.left = new TreeNode(rng.nextInt(20001) - 10000);
                    curr = curr.right;
                }
                q = cloneTree(p);
                q.left.val += 1;
            } else if (testId == 92) {
                int[] vals = new int[8000];
                for (int i = 0; i < vals.length; i++) vals[i] = rng.nextInt(20001) - 10000;
                p = buildBST(vals);
                q = cloneTree(p);
            } else if (testId == 93) {
                int[] vals = new int[8000];
                for (int i = 0; i < vals.length; i++) vals[i] = rng.nextInt(20001) - 10000;
                p = buildBST(vals);
                q = cloneTree(p);
                TreeNode c = q;
                while (c.right != null) c = c.right;
                c.val += 1;
            } else if (testId == 94) {
                int[] vals = new int[10000];
                for (int i = 0; i < vals.length; i++) vals[i] = rng.nextInt(20001) - 10000;
                p = buildBST(vals);
                q = cloneTree(p);
            } else if (testId == 95) {
                int[] vals = new int[10000];
                for (int i = 0; i < vals.length; i++) vals[i] = rng.nextInt(20001) - 10000;
                p = buildBST(vals);
                q = cloneTree(p);
                TreeNode c = q;
                while (c.left != null) c = c.left;
                c.val += 1;
            } else {
                // 96 to 99: Large random trees (N = 5000 to 10000)
                int n = 5000 + (testId - 96) * 1500;
                p = buildRandomTree(n, 0.45, rng);
                q = cloneTree(p);
                if (testId % 2 == 1) {
                    TreeNode c = q;
                    while (c.left != null || c.right != null) {
                        c = (c.left != null) ? c.left : c.right;
                    }
                    c.val += 1;
                }
            }

            // Serialize both trees into two lines
            String serP = serialize(p);
            String serQ = serialize(q);
            String inputContent = serP + "\n" + serQ;

            // Solve using internal optimal solver
            boolean expected = solve(p, q);
            String outputContent = expected ? "true" : "false";

            // Write files inputXX.txt and outputXX.txt
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
