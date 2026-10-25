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

    static class Pair {
        GenNode node;
        long idx;

        Pair(GenNode node, long idx) {
            this.node = node;
            this.idx = idx;
        }
    }

    /**
     * Optimal solver for Maximum Width of Binary Tree.
     */
    public static int solveWidth(GenNode root) {
        if (root == null) return 0;

        int maxWidth = 0;
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(root, 0L));

        while (!queue.isEmpty()) {
            int size = queue.size();
            long minIdx = queue.peek().idx;
            long first = 0, last = 0;

            for (int i = 0; i < size; i++) {
                Pair curr = queue.poll();
                long curIdx = curr.idx - minIdx;

                if (i == 0) first = curIdx;
                if (i == size - 1) last = curIdx;

                if (curr.node.left != null) {
                    queue.add(new Pair(curr.node.left, 2 * curIdx + 1));
                }
                if (curr.node.right != null) {
                    queue.add(new Pair(curr.node.right, 2 * curIdx + 2));
                }
            }

            maxWidth = Math.max(maxWidth, (int) (last - first + 1));
        }

        return maxWidth;
    }

    /**
     * Serializes tree to level-order string.
     */
    public static String serialize(GenNode root) {
        if (root == null) return "null";

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

        Random rng = new Random(654321);

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
                root = new GenNode(1);
                root.left = new GenNode(2);
            } else if (t == 5) {
                root = new GenNode(1);
                root.right = new GenNode(3);
            } else if (t == 6) {
                // TUF Example 1
                root = buildFromLevelOrder("1 3 2 5 3 null 9");
            } else if (t == 7) {
                // TUF Example 2
                root = buildFromLevelOrder("1 3 2 5 null null 9 6 null 7");
            } else if (t == 8) {
                // TUF Example 3 / Sample 3
                root = buildFromLevelOrder("1 2 3 null 4 5");
            } else if (t == 9) {
                // TUF Gamification
                root = buildFromLevelOrder("5 1 2 8 null 4 5 null 6");
            } else if (t == 10) {
                root = generateSkewed(6, true, -100, 100, rng);
            } else if (t == 11) {
                root = generateSkewed(6, false, -100, 100, rng);
            } else if (t == 12) {
                root = generateZigZag(7, -100, 100, rng);
            } else if (t == 13) {
                root = generateComplete(7, -100, 100, rng);
            } else if (t == 14) {
                root = generateComplete(15, -100, 100, rng);
            } else if (t == 15) {
                // All zeros
                root = generateComplete(31, 0, 0, rng);
            } else if (t == 16) {
                // All negative
                root = generateComplete(31, -1000, -500, rng);
            } else if (t == 17) {
                // Alternating values
                root = generateComplete(63, -1000, 1000, rng);
            } else if (t == 18) {
                // Maximum width at bottom level with large gap
                root = new GenNode(1);
                root.left = new GenNode(2);
                root.right = new GenNode(3);
                root.left.left = new GenNode(4);
                root.right.right = new GenNode(5);
            } else if (t == 19) {
                // Unbalanced tree with holes
                root = buildFromLevelOrder("10 20 30 null 40 50 null 60 null null 70");
            } else if (t >= 20 && t < 40) {
                // Medium random trees (10 to 150 nodes)
                int n = 10 + (t - 20) * 7;
                root = generateRandomTree(n, rng, -1000, 1000);
            } else if (t >= 40 && t < 60) {
                // Wide sparse trees (200 to 1000 nodes)
                int n = 200 + (t - 40) * 40;
                if (t % 2 == 0) {
                    root = generateComplete(n, -1000, 1000, rng);
                } else {
                    root = generateRandomTree(n, rng, -1000, 1000);
                }
            } else if (t >= 60 && t < 80) {
                // Large trees (1000 to 2000 nodes)
                int n = 1000 + (t - 60) * 50;
                root = generateRandomTree(n, rng, -1000, 1000);
            } else if (t == 80) {
                // Deep left-skewed 2500 nodes
                root = generateSkewed(2500, true, -1000, 1000, rng);
            } else if (t == 81) {
                // Deep right-skewed 2500 nodes
                root = generateSkewed(2500, false, -1000, 1000, rng);
            } else if (t == 82) {
                // Zig-zag bamboo 2800 nodes
                root = generateZigZag(2800, -1000, 1000, rng);
            } else if (t == 83) {
                // Inverted V shape: 1400 left, 1400 right
                root = new GenNode(0);
                root.left = generateSkewed(1400, true, -1000, 1000, rng);
                root.right = generateSkewed(1400, false, -1000, 1000, rng);
            } else if (t == 84) {
                // Complete tree 2047 nodes
                root = generateComplete(2047, -1000, 1000, rng);
            } else if (t == 85) {
                // Comb tree
                root = new GenNode(1);
                GenNode curr = root;
                for (int i = 0; i < 1400; i++) {
                    curr.left = new GenNode(rng.nextInt(2001) - 1000);
                    curr.right = new GenNode(rng.nextInt(2001) - 1000);
                    curr = curr.right;
                }
            } else if (t == 86) {
                // Sparse tree with deep branches
                root = generateRandomTree(2600, rng, -1000, 1000);
            } else if (t == 87) {
                // Extreme values: only 1000 and -1000
                root = generateComplete(2700, 0, 1, rng);
                ArrayDeque<GenNode> q = new ArrayDeque<>();
                q.add(root);
                while (!q.isEmpty()) {
                    GenNode node = q.poll();
                    node.val = (node.val == 0) ? -1000 : 1000;
                    if (node.left != null) q.add(node.left);
                    if (node.right != null) q.add(node.right);
                }
            } else if (t == 88) {
                // Complete tree 2800 nodes
                root = generateComplete(2800, -1000, 1000, rng);
            } else if (t == 89) {
                // Heavy asymmetric tree
                root = new GenNode(0);
                root.left = generateRandomTree(2200, rng, -1000, 1000);
                root.right = generateRandomTree(700, rng, -1000, 1000);
            } else if (t == 90) {
                // Max constraint N = 3000 complete tree
                root = generateComplete(3000, -1000, 1000, rng);
            } else if (t == 91) {
                // Max constraint N = 3000 random tree (seed 42)
                root = generateRandomTree(3000, new Random(42), -1000, 1000);
            } else if (t == 92) {
                // Max constraint N = 3000 random tree (seed 1337)
                root = generateRandomTree(3000, new Random(1337), -1000, 1000);
            } else if (t == 93) {
                // Max constraint N = 3000 left-skewed
                root = generateSkewed(3000, true, -1000, 1000, rng);
            } else if (t == 94) {
                // Max constraint N = 3000 right-skewed
                root = generateSkewed(3000, false, -1000, 1000, rng);
            } else if (t == 95) {
                // Max constraint N = 3000 zig-zag
                root = generateZigZag(3000, -1000, 1000, rng);
            } else if (t == 96) {
                // Max constraint N = 3000 wide dense tree
                root = generateComplete(3000, -1000, 1000, new Random(96));
            } else if (t == 97) {
                // Max constraint N = 3000 all -1000
                root = generateComplete(3000, -1000, -1000, rng);
            } else if (t == 98) {
                // Max constraint N = 3000 all 1000
                root = generateComplete(3000, 1000, 1000, rng);
            } else {
                // t == 99: Max constraint N = 3000 random tree (seed 999999)
                root = generateRandomTree(3000, new Random(999999), -1000, 1000);
            }

            String inContent = serialize(root);
            int width = solveWidth(root);
            String outContent = String.valueOf(width);

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
