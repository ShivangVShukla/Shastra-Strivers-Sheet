import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Self-contained Test Case Generator for "Vertical Order Traversal".
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

    static class Item implements Comparable<Item> {
        int col;
        int row;
        int val;

        Item(int col, int row, int val) {
            this.col = col;
            this.row = row;
            this.val = val;
        }

        @Override
        public int compareTo(Item other) {
            if (this.col != other.col) {
                return Integer.compare(this.col, other.col);
            }
            if (this.row != other.row) {
                return Integer.compare(this.row, other.row);
            }
            return Integer.compare(this.val, other.val);
        }
    }

    static class QueueNode {
        TreeNode node;
        int col;
        int row;

        QueueNode(TreeNode node, int col, int row) {
            this.node = node;
            this.col = col;
            this.row = row;
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
     * Optimal internal solver for vertical order traversal.
     */
    static List<List<Integer>> solve(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        List<Item> items = new ArrayList<>();
        ArrayDeque<QueueNode> queue = new ArrayDeque<>();
        queue.add(new QueueNode(root, 0, 0));

        while (!queue.isEmpty()) {
            QueueNode qn = queue.poll();
            items.add(new Item(qn.col, qn.row, qn.node.val));

            if (qn.node.left != null) {
                queue.add(new QueueNode(qn.node.left, qn.col - 1, qn.row + 1));
            }
            if (qn.node.right != null) {
                queue.add(new QueueNode(qn.node.right, qn.col + 1, qn.row + 1));
            }
        }

        Collections.sort(items);

        int prevCol = Integer.MIN_VALUE;
        List<Integer> currentCol = null;

        for (Item item : items) {
            if (currentCol == null || item.col != prevCol) {
                currentCol = new ArrayList<>();
                result.add(currentCol);
                prevCol = item.col;
            }
            currentCol.add(item.val);
        }

        return result;
    }

    /**
     * Formats 2D list into standard output string: [[...], [...], ...] or [].
     */
    static String formatResult(List<List<Integer>> result) {
        if (result == null || result.isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("[");
            List<Integer> col = result.get(i);
            for (int j = 0; j < col.size(); j++) {
                if (j > 0) {
                    sb.append(", ");
                }
                sb.append(col.get(j));
            }
            sb.append("]");
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

        Random rng = new Random(1111111L);

        for (int testId = 0; testId < 100; testId++) {
            TreeNode root = null;

            // Suite 1: Edge & Sample Cases (00 - 19)
            if (testId == 0) {
                root = null; // empty -> []
            } else if (testId == 1) {
                root = new TreeNode(42); // single node -> [[42]]
            } else if (testId == 2) {
                root = new TreeNode(-1000); // min boundary -> [[-1000]]
            } else if (testId == 3) {
                root = new TreeNode(1000); // max boundary -> [[1000]]
            } else if (testId == 4) {
                root = new TreeNode(0); // zero -> [[0]]
            } else if (testId == 5) {
                // Sample 1: [3, 9, 20, null, null, 15, 7] -> [[9], [3, 15], [20], [7]]
                root = new TreeNode(3);
                root.left = new TreeNode(9);
                root.right = new TreeNode(20);
                root.right.left = new TreeNode(15);
                root.right.right = new TreeNode(7);
            } else if (testId == 6) {
                // Sample 2 (Overlap & tie-break): [1, 2, 3, 4, 5, 6, 7] -> [[4], [2], [1, 5, 6], [3], [7]]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.left.right = new TreeNode(5);
                root.right.left = new TreeNode(6);
                root.right.right = new TreeNode(7);
            } else if (testId == 7) {
                // Sample 3 (Overlap with inverted values): [1, 2, 3, 4, 6, 5, 7] -> [[4], [2], [1, 5, 6], [3], [7]]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.left.right = new TreeNode(6);
                root.right.left = new TreeNode(5);
                root.right.right = new TreeNode(7);
            } else if (testId == 8) {
                // 2 nodes: left only -> [[2], [1]]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
            } else if (testId == 9) {
                // 2 nodes: right only -> [[1], [2]]
                root = new TreeNode(1);
                root.right = new TreeNode(2);
            } else if (testId == 10) {
                // 3 nodes: root with 2 children -> [[2], [1], [3]]
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
            } else if (testId == 11) {
                // Left-skewed chain: 1 -> 2 -> 3 -> 4
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.left = new TreeNode(3);
                root.left.left.left = new TreeNode(4);
            } else if (testId == 12) {
                // Right-skewed chain: 1 -> 2 -> 3 -> 4
                root = new TreeNode(1);
                root.right = new TreeNode(2);
                root.right.right = new TreeNode(3);
                root.right.right.right = new TreeNode(4);
            } else if (testId == 13) {
                // Zig-zag returning to col 0: 1 (0, 0) -> left 2 (-1, 1) -> right 3 (0, 2)
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.left.right = new TreeNode(3);
            } else if (testId == 14) {
                // Coordinate collision with duplicate values at (0, 2): 5 and 5
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.right = new TreeNode(5);
                root.right.left = new TreeNode(5);
            } else if (testId == 15) {
                // Coordinate collision with negative values: -10 and -20 at (0, 2)
                root = new TreeNode(0);
                root.left = new TreeNode(-5);
                root.right = new TreeNode(5);
                root.left.right = new TreeNode(-10);
                root.right.left = new TreeNode(-20);
            } else if (testId == 16) {
                // V-tree: root 1, left 2 -> 4, right 3 -> 5
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.left = new TreeNode(4);
                root.right.right = new TreeNode(5);
            } else if (testId == 17) {
                // Inverted V-tree / diamond: 1 -> left 2 -> right 4; right 3 -> left 5
                root = new TreeNode(1);
                root.left = new TreeNode(2);
                root.right = new TreeNode(3);
                root.left.right = new TreeNode(4);
                root.right.left = new TreeNode(5);
            } else if (testId == 18) {
                // Quadruple collision at (0, 4)
                root = new TreeNode(10);
                root.left = new TreeNode(20);
                root.right = new TreeNode(30);
                root.left.right = new TreeNode(40);
                root.right.left = new TreeNode(50);
                root.left.right.left = new TreeNode(60);
                root.right.left.right = new TreeNode(70);
            } else if (testId == 19) {
                // Complete binary tree of 7 nodes: all negative
                root = new TreeNode(-1);
                root.left = new TreeNode(-2);
                root.right = new TreeNode(-3);
                root.left.left = new TreeNode(-4);
                root.left.right = new TreeNode(-5);
                root.right.left = new TreeNode(-6);
                root.right.right = new TreeNode(-7);
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
            List<List<Integer>> expectedList = solve(root);
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
