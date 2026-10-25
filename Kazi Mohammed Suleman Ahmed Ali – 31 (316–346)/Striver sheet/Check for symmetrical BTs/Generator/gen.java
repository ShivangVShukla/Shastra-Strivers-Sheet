import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

/**
 * Self-contained Test Case Generator for "Check for symmetrical BTs".
 *
 * Generates exactly 100 input and 100 output test files:
 * - Testcases/Input/input00.txt to input99.txt
 * - Testcases/Output/output00.txt to output99.txt
 *
 * Spans:
 * - Edge / Base cases (empty tree, single node, 2-node, 3-node, TUF samples)
 * - Small structured trees (perfect, V-trees, zig-zag)
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
     * Creates a mirrored copy of a binary tree.
     */
    static TreeNode mirror(TreeNode root) {
        if (root == null) return null;
        TreeNode copy = new TreeNode(root.val);
        copy.left = mirror(root.right);
        copy.right = mirror(root.left);
        return copy;
    }

    /**
     * Optimal internal solver to determine if a binary tree is symmetric.
     * Uses stack-safe iterative BFS mirror comparison with explicit queues.
     */
    static boolean solve(TreeNode root) {
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        if (root.left == null || root.right == null) return false;

        ArrayDeque<TreeNode> q1 = new ArrayDeque<>();
        ArrayDeque<TreeNode> q2 = new ArrayDeque<>();
        q1.add(root.left);
        q2.add(root.right);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode n1 = q1.poll();
            TreeNode n2 = q2.poll();

            if (n1.val != n2.val) return false;

            if (n1.left != null && n2.right != null) {
                q1.add(n1.left);
                q2.add(n2.right);
            } else if (n1.left != null || n2.right != null) {
                return false;
            }

            if (n1.right != null && n2.left != null) {
                q1.add(n1.right);
                q2.add(n2.left);
            } else if (n1.right != null || n2.left != null) {
                return false;
            }
        }

        return q1.isEmpty() && q2.isEmpty();
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

    static TreeNode buildRandomSubtree(int n, Random rng) {
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

    static TreeNode buildCompleteSubtree(int n, Random rng) {
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

        Random rng = new Random(5555555L);

        for (int testId = 0; testId < 100; testId++) {
            TreeNode root = null;

            // Suite 1: Edge & Base Cases (00 - 19)
            if (testId == 0) {
                root = null; // empty -> true
            } else if (testId == 1) {
                root = new TreeNode(42); // single node -> true
            } else if (testId == 2) {
                root = new TreeNode(-100); // boundary min -> true
            } else if (testId == 3) {
                root = new TreeNode(100); // boundary max -> true
            } else if (testId == 4) {
                // TUF Sample 1: [1, 2, 2, 3, 4, 4, 3] -> true
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(2);
                root.left.left = new TreeNode(3);
                root.left.right = new TreeNode(4);
                root.right.left = new TreeNode(4);
                root.right.right = new TreeNode(3);
            } else if (testId == 5) {
                // TUF Sample 2: [1, 2, 2, null, 3, null, 3] -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(2);
                root.left.right = new TreeNode(3);
                root.right.right = new TreeNode(3);
            } else if (testId == 6) {
                // TUF Sample 3: [1, 2, 3] -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
            } else if (testId == 7) {
                // 2 nodes: root + left -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
            } else if (testId == 8) {
                // 2 nodes: root + right -> false
                root = new TreeNode(1);
                root.right = new TreeNode(2);
            } else if (testId == 9) {
                // 3 nodes symmetric: [1, 2, 2] -> true
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(2);
            } else if (testId == 10) {
                // 3 nodes asymmetric: [1, 2, 3] -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
            } else if (testId == 11) {
                // 5 nodes symmetric V: [1, 2, 2, 3, null, null, 3] -> true
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(2);
                root.left.left = new TreeNode(3);
                root.right.right = new TreeNode(3);
            } else if (testId == 12) {
                // 5 nodes symmetric inner: [1, 2, 2, null, 3, 3, null] -> true
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(2);
                root.left.right = new TreeNode(3);
                root.right.left = new TreeNode(3);
            } else if (testId == 13) {
                // 5 nodes asymmetric values: [1, 2, 2, 3, null, null, 4] -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(2);
                root.left.left = new TreeNode(3);
                root.right.right = new TreeNode(4);
            } else if (testId == 14) {
                // 5 nodes asymmetric structure: [1, 2, 2, 3, null, 3, null] -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(2);
                root.left.left = new TreeNode(3);
                root.right.left = new TreeNode(3);
            } else if (testId == 15) {
                // Negative values symmetric -> true
                root = new TreeNode(1);
                root.left = new TreeNode(-2);
                root.right = new TreeNode(-2);
                root.left.left = new TreeNode(-3);
                root.left.right = new TreeNode(-4);
                root.right.left = new TreeNode(-4);
                root.right.right = new TreeNode(-3);
            } else if (testId == 16) {
                // Negative values sign mismatch -> false
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(-2);
            } else if (testId == 17) {
                // Perfect tree N = 7 symmetric -> true
                TreeNode sub = buildCompleteSubtree(3, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
            } else if (testId == 18) {
                // Perfect tree N = 7 left leaf mutated -> false
                TreeNode sub = buildCompleteSubtree(3, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                root.left.left.val ^= 1;
            } else if (testId == 19) {
                // Perfect tree N = 7 rightmost leaf missing -> false
                TreeNode sub = buildCompleteSubtree(3, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                root.right.right = null;
            }
            // Suite 2: Small Structured Trees (20 - 39)
            else if (testId == 20) {
                TreeNode sub = buildCompleteSubtree(7, rng);
                root = new TreeNode(10);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId == 21) {
                TreeNode sub = buildCompleteSubtree(7, rng);
                root = new TreeNode(10);
                root.left = sub;
                root.right = mirror(sub);
                root.right.val += 1; // false
            } else if (testId == 22) {
                TreeNode sub = buildCompleteSubtree(7, rng);
                root = new TreeNode(10);
                root.left = sub;
                root.right = mirror(sub);
                root.right.right = null; // false
            } else if (testId == 23) {
                TreeNode sub = buildCompleteSubtree(15, rng);
                root = new TreeNode(10);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId == 24) {
                TreeNode sub = buildCompleteSubtree(15, rng);
                root = new TreeNode(10);
                root.left = sub;
                root.right = mirror(sub);
                root.left.right.left.val += 5; // false
            } else if (testId == 25) {
                TreeNode sub = buildCompleteSubtree(31, rng);
                root = new TreeNode(10);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId == 26) {
                TreeNode sub = buildCompleteSubtree(31, rng);
                root = new TreeNode(10);
                root.left = sub;
                root.right = mirror(sub);
                root.right.left.right.val ^= 1; // false
            } else if (testId == 27) {
                // Symmetric V-tree of 21 nodes -> true
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 10; i++) {
                    l.left = new TreeNode(i);
                    l = l.left;
                    r.right = new TreeNode(i);
                    r = r.right;
                }
            } else if (testId == 28) {
                // Symmetric V-tree with 1 extra node on left -> false
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 10; i++) {
                    l.left = new TreeNode(i);
                    l = l.left;
                    r.right = new TreeNode(i);
                    r = r.right;
                }
                l.left = new TreeNode(99);
            } else if (testId == 29) {
                // Symmetric zig-zag tree of 21 nodes -> true
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 10; i++) {
                    TreeNode nextL = new TreeNode(i);
                    TreeNode nextR = new TreeNode(i);
                    if (i % 2 == 1) {
                        l.left = nextL;
                        r.right = nextR;
                    } else {
                        l.right = nextL;
                        r.left = nextR;
                    }
                    l = nextL;
                    r = nextR;
                }
            } else if (testId >= 30 && testId <= 34) {
                // Symmetric random trees of size 15 to 30 -> true
                int n = 7 + (testId - 30) * 4;
                TreeNode sub = buildRandomSubtree(n, rng);
                root = new TreeNode(rng.nextInt(201) - 100);
                root.left = sub;
                root.right = mirror(sub);
            } else if (testId >= 35 && testId <= 39) {
                // Asymmetric mutated variants -> false
                int n = 7 + (testId - 35) * 4;
                TreeNode sub = buildRandomSubtree(n, rng);
                root = new TreeNode(rng.nextInt(201) - 100);
                root.left = sub;
                root.right = mirror(sub);
                if (root.right != null) root.right.val += 1;
            }
            // Suite 3: Mid-Range Random Stress Cases (40 - 69)
            else if (testId == 40) {
                TreeNode sub = buildCompleteSubtree(63, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId == 41) {
                TreeNode sub = buildCompleteSubtree(63, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                TreeNode c = root.right;
                while (c.right != null) c = c.right;
                c.val += 1; // false
            } else if (testId == 42) {
                TreeNode sub = buildCompleteSubtree(127, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId == 43) {
                TreeNode sub = buildCompleteSubtree(127, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                TreeNode c = root.right;
                while (c.right != null && c.right.right != null) c = c.right;
                c.right = null; // false
            } else if (testId == 44) {
                TreeNode sub = buildCompleteSubtree(255, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId == 45) {
                TreeNode sub = buildCompleteSubtree(255, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                root.left.right.val ^= 1; // false
            } else if (testId == 46) {
                TreeNode sub = buildCompleteSubtree(511, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId == 47) {
                TreeNode sub = buildCompleteSubtree(511, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                root.left.val += 1; // false
            } else if (testId == 48) {
                TreeNode sub = buildCompleteSubtree(1023, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId == 49) {
                TreeNode sub = buildCompleteSubtree(1023, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                TreeNode c = root.left;
                while (c.left != null) c = c.left;
                c.val += 1; // false
            } else if (testId == 50) {
                // Symmetric V-tree of 501 nodes -> true
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 250; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    r.right = new TreeNode(v);
                    r = r.right;
                }
            } else if (testId == 51) {
                // Symmetric V-tree of 501 nodes with 250th leaf mutated -> false
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 250; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    r.right = new TreeNode(v);
                    r = r.right;
                }
                r.val += 1;
            } else if (testId == 52) {
                // Symmetric V-tree of 1001 nodes -> true
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 500; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    r.right = new TreeNode(v);
                    r = r.right;
                }
            } else if (testId == 53) {
                // Symmetric V-tree of 1001 with right arm shorter by 1 -> false
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 500; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    if (i < 500) {
                        r.right = new TreeNode(v);
                        r = r.right;
                    }
                }
            } else if (testId >= 54 && testId <= 61) {
                int n = 200 + (testId - 54) * 80;
                TreeNode sub = buildRandomSubtree(n, rng);
                root = new TreeNode(rng.nextInt(201) - 100);
                root.left = sub;
                root.right = mirror(sub); // true
            } else if (testId >= 62 && testId <= 69) {
                int n = 200 + (testId - 62) * 80;
                TreeNode sub = buildRandomSubtree(n, rng);
                root = new TreeNode(rng.nextInt(201) - 100);
                root.left = sub;
                root.right = mirror(sub);
                TreeNode c = root.right;
                while (c.left != null || c.right != null) {
                    c = (c.left != null) ? c.left : c.right;
                }
                c.val += 1; // false
            }
            // Suite 4: Pathological & Maximum Boundary Stress Cases (70 - 99)
            else if (testId == 70) {
                TreeNode sub = buildCompleteSubtree(2047, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub); // true (N = 4095)
            } else if (testId == 71) {
                TreeNode sub = buildCompleteSubtree(2047, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                TreeNode c = root.right;
                while (c.right != null) c = c.right;
                c.val += 1; // false
            } else if (testId == 72) {
                TreeNode sub = buildCompleteSubtree(4095, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub); // true (N = 8191)
            } else if (testId == 73) {
                TreeNode sub = buildCompleteSubtree(4095, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                TreeNode c = root.left;
                while (c.left != null && c.left.left != null) c = c.left;
                c.left = null; // false
            } else if (testId == 74) {
                // Boundary maximum complete tree N = 9,999 -> true
                TreeNode sub = buildCompleteSubtree(4999, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
            } else if (testId == 75) {
                // Boundary maximum complete tree N = 9,999 with deepest leaf mutated -> false
                TreeNode sub = buildCompleteSubtree(4999, rng);
                root = new TreeNode(1);
                root.left = sub;
                root.right = mirror(sub);
                TreeNode c = root.right;
                while (c.right != null) c = c.right;
                c.val ^= 1;
            } else if (testId == 76) {
                // Extreme symmetric V-tree N = 5,001 -> true
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 2500; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    r.right = new TreeNode(v);
                    r = r.right;
                }
            } else if (testId == 77) {
                // Extreme symmetric V-tree N = 5,001 with 2500th leaf mutated -> false
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 2500; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    r.right = new TreeNode(v);
                    r = r.right;
                }
                r.val += 1;
            } else if (testId == 78) {
                // Extreme symmetric zig-zag N = 5,001 -> true
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 2500; i++) {
                    int v = rng.nextInt(201) - 100;
                    TreeNode nextL = new TreeNode(v);
                    TreeNode nextR = new TreeNode(v);
                    if (i % 2 == 1) {
                        l.left = nextL;
                        r.right = nextR;
                    } else {
                        l.right = nextL;
                        r.left = nextR;
                    }
                    l = nextL;
                    r = nextR;
                }
            } else if (testId == 79) {
                // Extreme symmetric zig-zag N = 5,001 with 2500th node mutated -> false
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 2500; i++) {
                    int v = rng.nextInt(201) - 100;
                    TreeNode nextL = new TreeNode(v);
                    TreeNode nextR = new TreeNode(v);
                    if (i % 2 == 1) {
                        l.left = nextL;
                        r.right = nextR;
                    } else {
                        l.right = nextL;
                        r.left = nextR;
                    }
                    l = nextL;
                    r = nextR;
                }
                r.val += 1;
            } else if (testId == 80) {
                // Boundary maximum symmetric V-tree N = 9,999 -> true
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 4999; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    r.right = new TreeNode(v);
                    r = r.right;
                }
            } else if (testId == 81) {
                // Boundary maximum symmetric V-tree N = 9,999 with 4999th leaf mutated -> false
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 4999; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    r.right = new TreeNode(v);
                    r = r.right;
                }
                r.val += 1;
            } else if (testId == 82) {
                // Boundary maximum symmetric V-tree N = 9,999 with 4999th leaf missing -> false
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 4999; i++) {
                    int v = rng.nextInt(201) - 100;
                    l.left = new TreeNode(v);
                    l = l.left;
                    if (i < 4999) {
                        r.right = new TreeNode(v);
                        r = r.right;
                    }
                }
            } else if (testId == 83) {
                // Boundary maximum symmetric zig-zag N = 9,999 -> true
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 4999; i++) {
                    int v = rng.nextInt(201) - 100;
                    TreeNode nextL = new TreeNode(v);
                    TreeNode nextR = new TreeNode(v);
                    if (i % 2 == 1) {
                        l.left = nextL;
                        r.right = nextR;
                    } else {
                        l.right = nextL;
                        r.left = nextR;
                    }
                    l = nextL;
                    r = nextR;
                }
            } else if (testId == 84) {
                // Boundary maximum symmetric zig-zag N = 9,999 with 4999th leaf mutated -> false
                root = new TreeNode(0);
                TreeNode l = root, r = root;
                for (int i = 1; i <= 4999; i++) {
                    int v = rng.nextInt(201) - 100;
                    TreeNode nextL = new TreeNode(v);
                    TreeNode nextR = new TreeNode(v);
                    if (i % 2 == 1) {
                        l.left = nextL;
                        r.right = nextR;
                    } else {
                        l.right = nextL;
                        r.left = nextR;
                    }
                    l = nextL;
                    r = nextR;
                }
                r.val += 1;
            } else if (testId >= 85 && testId <= 91) {
                int n = 2000 + (testId - 85) * 400;
                TreeNode sub = buildRandomSubtree(n, rng);
                root = new TreeNode(rng.nextInt(201) - 100);
                root.left = sub;
                root.right = mirror(sub); // true
            } else {
                // 92 to 99: Large random trees with subtle mutations
                int n = 2000 + (testId - 92) * 350;
                TreeNode sub = buildRandomSubtree(n, rng);
                root = new TreeNode(rng.nextInt(201) - 100);
                root.left = sub;
                root.right = mirror(sub);
                TreeNode c = root.right;
                while (c.left != null || c.right != null) {
                    c = (c.right != null) ? c.right : c.left;
                }
                c.val += 1; // false
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
