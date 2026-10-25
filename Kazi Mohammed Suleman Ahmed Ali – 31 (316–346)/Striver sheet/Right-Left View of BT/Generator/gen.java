import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class gen {

    static class GenNode {
        int val;
        GenNode left;
        GenNode right;

        GenNode(int val) {
            this.val = val;
        }
    }

    /**
     * Optimal solver for Right Side View.
     */
    public static List<Integer> solveRightSideView(GenNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        ArrayDeque<GenNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                GenNode curr = queue.poll();

                if (i == levelSize - 1) {
                    result.add(curr.val);
                }

                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
        }
        return result;
    }

    /**
     * Serializes a binary tree to standard level-order string representation.
     */
    public static String serialize(GenNode root) {
        if (root == null) {
            return "null";
        }

        List<String> tokens = new ArrayList<>();
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);
        tokens.add(String.valueOf(root.val));

        while (!q.isEmpty()) {
            GenNode curr = q.poll();

            if (curr.left != null) {
                tokens.add(String.valueOf(curr.left.val));
                q.add(curr.left);
            } else {
                tokens.add("null");
            }

            if (curr.right != null) {
                tokens.add(String.valueOf(curr.right.val));
                q.add(curr.right);
            } else {
                tokens.add("null");
            }
        }

        // Trim trailing "null"s
        int last = tokens.size() - 1;
        while (last >= 0 && tokens.get(last).equals("null")) {
            last--;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= last; i++) {
            sb.append(tokens.get(i));
            if (i < last) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String formatOutput(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i + 1 < list.size()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static GenNode buildFromLevelOrder(String s) {
        if (s == null || s.trim().isEmpty() || s.trim().equals("null")) {
            return null;
        }
        String[] parts = s.trim().split("\\s+");
        if (parts.length == 0 || parts[0].equals("null")) {
            return null;
        }

        GenNode root = new GenNode(Integer.parseInt(parts[0]));
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);

        int idx = 1;
        while (!q.isEmpty() && idx < parts.length) {
            GenNode curr = q.poll();

            if (idx < parts.length) {
                if (!parts[idx].equals("null")) {
                    curr.left = new GenNode(Integer.parseInt(parts[idx]));
                    q.add(curr.left);
                }
                idx++;
            }

            if (idx < parts.length) {
                if (!parts[idx].equals("null")) {
                    curr.right = new GenNode(Integer.parseInt(parts[idx]));
                    q.add(curr.right);
                }
                idx++;
            }
        }
        return root;
    }

    public static GenNode generateRandomTree(int n, Random rng, int minVal, int maxVal) {
        if (n <= 0) return null;
        GenNode root = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
        if (n == 1) return root;

        List<GenNode> nodes = new ArrayList<>();
        nodes.add(root);
        int created = 1;

        while (created < n && !nodes.isEmpty()) {
            int idx = rng.nextInt(nodes.size());
            GenNode parent = nodes.get(idx);

            boolean attachLeft = (parent.left == null) && (parent.right != null || rng.nextBoolean());
            if (parent.left == null && parent.right == null) {
                attachLeft = rng.nextBoolean();
            } else if (parent.left != null && parent.right == null) {
                attachLeft = false;
            } else if (parent.left == null && parent.right != null) {
                attachLeft = true;
            } else {
                nodes.remove(idx);
                continue;
            }

            GenNode child = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
            if (attachLeft) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            created++;
            nodes.add(child);

            if (parent.left != null && parent.right != null) {
                nodes.remove(idx);
            }
        }
        return root;
    }

    public static GenNode generateSkewed(int n, boolean left, int minVal, int maxVal, Random rng) {
        if (n <= 0) return null;
        GenNode root = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
        GenNode curr = root;
        for (int i = 1; i < n; i++) {
            GenNode next = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
            if (left) {
                curr.left = next;
            } else {
                curr.right = next;
            }
            curr = next;
        }
        return root;
    }

    public static GenNode generateZigZag(int n, int minVal, int maxVal, Random rng) {
        if (n <= 0) return null;
        GenNode root = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
        GenNode curr = root;
        boolean goLeft = true;
        for (int i = 1; i < n; i++) {
            GenNode next = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
            if (goLeft) {
                curr.left = next;
            } else {
                curr.right = next;
            }
            goLeft = !goLeft;
            curr = next;
        }
        return root;
    }

    public static GenNode generateComplete(int n, int minVal, int maxVal, Random rng) {
        if (n <= 0) return null;
        GenNode root = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);
        int count = 1;
        while (count < n) {
            GenNode curr = q.poll();
            curr.left = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
            q.add(curr.left);
            count++;
            if (count >= n) break;

            curr.right = new GenNode(minVal + rng.nextInt(maxVal - minVal + 1));
            q.add(curr.right);
            count++;
        }
        return root;
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");

        if (!inDir.exists()) inDir.mkdirs();
        if (!outDir.exists()) outDir.mkdirs();

        Random rng = new Random(101010);

        for (int t = 0; t < 100; t++) {
            GenNode root = null;

            if (t == 0) {
                // Empty tree
                root = null;
            } else if (t == 1) {
                root = new GenNode(42);
            } else if (t == 2) {
                root = new GenNode(-1000);
            } else if (t == 3) {
                root = new GenNode(1000);
            } else if (t == 4) {
                // Two nodes: root + left child
                root = new GenNode(1);
                root.left = new GenNode(2);
            } else if (t == 5) {
                // Two nodes: root + right child
                root = new GenNode(1);
                root.right = new GenNode(3);
            } else if (t == 6) {
                // TUF Example 1
                root = buildFromLevelOrder("1 2 3 null 5 null 4");
            } else if (t == 7) {
                // TUF Example 2
                root = buildFromLevelOrder("1 2 3 6 5 8 4");
            } else if (t == 8) {
                // TUF Example 3: Left subtree extends deeper
                root = buildFromLevelOrder("5 1 2 8 null 4 5 null 6");
            } else if (t == 9) {
                // Left-skewed tree
                root = generateSkewed(6, true, -100, 100, rng);
            } else if (t == 10) {
                // Right-skewed tree
                root = generateSkewed(6, false, -100, 100, rng);
            } else if (t == 11) {
                // Zig-zag tree
                root = generateZigZag(7, -100, 100, rng);
            } else if (t == 12) {
                // Perfect tree 7 nodes
                root = generateComplete(7, -100, 100, rng);
            } else if (t == 13) {
                // Perfect tree 15 nodes
                root = generateComplete(15, -100, 100, rng);
            } else if (t == 14) {
                // All zeros
                root = generateComplete(31, 0, 0, rng);
            } else if (t == 15) {
                // All negative
                root = generateComplete(31, -1000, -500, rng);
            } else if (t == 16) {
                // Alternating values
                root = generateComplete(63, -1000, 1000, rng);
            } else if (t == 17) {
                // Deep left subtree, shallow right subtree
                root = new GenNode(10);
                root.left = generateSkewed(6, true, 1, 50, rng);
                root.right = new GenNode(20);
            } else if (t == 18) {
                // Right subtree with right branch only, left subtree full
                root = new GenNode(1);
                root.left = generateComplete(7, 2, 8, rng);
                root.right = generateSkewed(3, false, 9, 12, rng);
            } else if (t == 19) {
                // Unbalanced tree with null gaps
                root = buildFromLevelOrder("10 20 30 null 40 null 50 60 null null 70");
            } else if (t >= 20 && t < 40) {
                // Medium random trees (10 to 200 nodes)
                int n = 10 + (t - 20) * 10;
                root = generateRandomTree(n, rng, -1000, 1000);
            } else if (t >= 40 && t < 60) {
                // Trees with deeper left subtrees than right subtrees (300 to 1500 nodes)
                int n = 300 + (t - 40) * 60;
                root = new GenNode(rng.nextInt(2001) - 1000);
                int leftSize = (int) (n * 0.7);
                int rightSize = n - 1 - leftSize;
                root.left = generateRandomTree(leftSize, rng, -1000, 1000);
                root.right = generateRandomTree(rightSize, rng, -1000, 1000);
            } else if (t >= 60 && t < 80) {
                // Large trees (2000 to 5000 nodes)
                int n = 2000 + (t - 60) * 150;
                if (t % 3 == 0) {
                    root = generateComplete(n, -1000, 1000, rng);
                } else {
                    root = generateRandomTree(n, rng, -1000, 1000);
                }
            } else if (t == 80) {
                // Deep left-skewed 5000 nodes
                root = generateSkewed(5000, true, -1000, 1000, rng);
            } else if (t == 81) {
                // Deep right-skewed 5000 nodes
                root = generateSkewed(5000, false, -1000, 1000, rng);
            } else if (t == 82) {
                // Zig-zag bamboo 8000 nodes
                root = generateZigZag(8000, -1000, 1000, rng);
            } else if (t == 83) {
                // Inverted V shape: left 4000, right 4000
                root = new GenNode(0);
                root.left = generateSkewed(4000, true, -1000, 1000, rng);
                root.right = generateSkewed(4000, false, -1000, 1000, rng);
            } else if (t == 84) {
                // Complete tree 8191 nodes
                root = generateComplete(8191, -1000, 1000, rng);
            } else if (t == 85) {
                // Comb tree
                root = new GenNode(1);
                GenNode curr = root;
                for (int i = 0; i < 4000; i++) {
                    curr.left = new GenNode(rng.nextInt(2001) - 1000);
                    curr.right = new GenNode(rng.nextInt(2001) - 1000);
                    curr = curr.right;
                }
            } else if (t == 86) {
                // Left-heavy tree: left has 7500 nodes, right has 1000 nodes
                root = new GenNode(0);
                root.left = generateRandomTree(7500, rng, -1000, 1000);
                root.right = generateRandomTree(1000, rng, -1000, 1000);
            } else if (t == 87) {
                // Extreme values: only 1000 and -1000
                root = generateComplete(9000, 0, 1, rng);
                ArrayDeque<GenNode> q = new ArrayDeque<>();
                q.add(root);
                while (!q.isEmpty()) {
                    GenNode node = q.poll();
                    node.val = (node.val == 0) ? -1000 : 1000;
                    if (node.left != null) q.add(node.left);
                    if (node.right != null) q.add(node.right);
                }
            } else if (t == 88) {
                // Complete tree 9500 nodes
                root = generateComplete(9500, -1000, 1000, rng);
            } else if (t == 89) {
                // Dense random tree 9000 nodes
                root = generateRandomTree(9000, rng, -1000, 1000);
            } else if (t == 90) {
                // Max constraint N = 10000 complete tree
                root = generateComplete(10000, -1000, 1000, rng);
            } else if (t == 91) {
                // Max constraint N = 10000 random tree (seed 42)
                root = generateRandomTree(10000, new Random(42), -1000, 1000);
            } else if (t == 92) {
                // Max constraint N = 10000 random tree (seed 1337)
                root = generateRandomTree(10000, new Random(1337), -1000, 1000);
            } else if (t == 93) {
                // Max constraint N = 10000 left-skewed
                root = generateSkewed(10000, true, -1000, 1000, rng);
            } else if (t == 94) {
                // Max constraint N = 10000 right-skewed
                root = generateSkewed(10000, false, -1000, 1000, rng);
            } else if (t == 95) {
                // Max constraint N = 10000 zig-zag
                root = generateZigZag(10000, -1000, 1000, rng);
            } else if (t == 96) {
                // Max constraint N = 10000 left-dominant: left 9000 nodes, right 999 nodes
                root = new GenNode(0);
                root.left = generateRandomTree(9000, rng, -1000, 1000);
                root.right = generateRandomTree(999, rng, -1000, 1000);
            } else if (t == 97) {
                // Max constraint N = 10000 all -1000
                root = generateComplete(10000, -1000, -1000, rng);
            } else if (t == 98) {
                // Max constraint N = 10000 all 1000
                root = generateComplete(10000, 1000, 1000, rng);
            } else {
                // t == 99: Max constraint N = 10000 random tree (seed 999999)
                root = generateRandomTree(10000, new Random(999999), -1000, 1000);
            }

            String inContent = serialize(root);
            List<Integer> ans = solveRightSideView(root);
            String outContent = formatOutput(ans);

            String inFileName = String.format("input%02d.txt", t);
            String outFileName = String.format("output%02d.txt", t);

            File inFile = new File(inDir, inFileName);
            File outFile = new File(outDir, outFileName);

            try (PrintWriter pw = new PrintWriter(new FileWriter(inFile))) {
                pw.println(inContent);
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
                pw.println(outContent);
            }
        }

        System.out.println("Successfully generated 100 test cases in " + inDir.getAbsolutePath() + " and " + outDir.getAbsolutePath());
    }
}
