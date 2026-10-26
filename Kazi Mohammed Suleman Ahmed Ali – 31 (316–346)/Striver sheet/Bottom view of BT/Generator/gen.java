import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    static class QueueNode {
        GenNode node;
        int hd;

        QueueNode(GenNode node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }

    /**
     * Computes the bottom view of the tree.
     */
    public static List<Integer> solveBottomView(GenNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Map<Integer, Integer> hdToVal = new HashMap<>();
        ArrayDeque<QueueNode> queue = new ArrayDeque<>();
        queue.add(new QueueNode(root, 0));

        int minHd = 0;
        int maxHd = 0;

        while (!queue.isEmpty()) {
            QueueNode curr = queue.poll();
            GenNode node = curr.node;
            int hd = curr.hd;

            hdToVal.put(hd, node.val);

            if (hd < minHd) minHd = hd;
            if (hd > maxHd) maxHd = hd;

            if (node.left != null) {
                queue.add(new QueueNode(node.left, hd - 1));
            }
            if (node.right != null) {
                queue.add(new QueueNode(node.right, hd + 1));
            }
        }

        for (int hd = minHd; hd <= maxHd; hd++) {
            if (hdToVal.containsKey(hd)) {
                result.add(hdToVal.get(hd));
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

    // Tree generation helpers
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

    public static GenNode generateCollidingTree(int n, Random rng) {
        // Deliberately creates overlapping nodes at horizontal distance 0 and nearby HDs
        if (n <= 0) return null;
        GenNode root = new GenNode(rng.nextInt(2001) - 1000);
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);
        int count = 1;

        while (count < n && !q.isEmpty()) {
            GenNode curr = q.poll();
            if (count < n) {
                curr.left = new GenNode(rng.nextInt(2001) - 1000);
                q.add(curr.left);
                count++;
            }
            if (count < n) {
                curr.right = new GenNode(rng.nextInt(2001) - 1000);
                q.add(curr.right);
                count++;
            }
        }
        return root;
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");

        if (!inDir.exists()) inDir.mkdirs();
        if (!outDir.exists()) outDir.mkdirs();

        Random rng = new Random(424242);

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
                root = buildFromLevelOrder("20 8 22 5 3 null 25 null null 10 14");
            } else if (t == 7) {
                // TUF Example 2 (Overlapping nodes)
                root = buildFromLevelOrder("20 8 22 5 3 4 25 null null 10 14");
            } else if (t == 8) {
                root = generateSkewed(5, true, -50, 50, rng);
            } else if (t == 9) {
                root = generateSkewed(5, false, -50, 50, rng);
            } else if (t == 10) {
                root = generateZigZag(7, -50, 50, rng);
            } else if (t == 11) {
                root = generateComplete(7, -100, 100, rng);
            } else if (t == 12) {
                root = generateComplete(15, -100, 100, rng);
            } else if (t == 13) {
                // Symmetrical values
                root = buildFromLevelOrder("1 2 2 3 4 4 3 5 null null 6 6 null null 5");
            } else if (t == 14) {
                // All zeros
                root = generateComplete(31, 0, 0, rng);
            } else if (t == 15) {
                // All negative
                root = generateComplete(31, -1000, -500, rng);
            } else if (t == 16) {
                // Alternating positive / negative
                root = generateComplete(63, -1000, 1000, rng);
            } else if (t == 17) {
                // Diamond cross overlapping at HD=0
                root = buildFromLevelOrder("1 2 3 null 4 5 null null 6 7 null");
            } else if (t == 18) {
                // Perfect tree 3 levels
                root = generateComplete(31, -100, 100, rng);
            } else if (t == 19) {
                // Highly unbalanced small tree
                root = buildFromLevelOrder("10 20 30 null 40 null 50 60 null 70");
            } else if (t >= 20 && t < 40) {
                // Medium trees 10 to 200 nodes
                int n = 10 + (t - 20) * 10;
                root = generateRandomTree(n, rng, -1000, 1000);
            } else if (t >= 40 && t < 60) {
                // Heavy collision trees 300 to 1500 nodes
                int n = 300 + (t - 40) * 60;
                root = generateCollidingTree(n, rng);
            } else if (t >= 60 && t < 80) {
                // Large trees 2000 to 5000 nodes
                int n = 2000 + (t - 60) * 150;
                if (t % 3 == 0) {
                    root = generateComplete(n, -1000, 1000, rng);
                } else {
                    root = generateRandomTree(n, rng, -1000, 1000);
                }
            } else if (t == 80) {
                // Deep left skewed 5000 nodes
                root = generateSkewed(5000, true, -1000, 1000, rng);
            } else if (t == 81) {
                // Deep right skewed 5000 nodes
                root = generateSkewed(5000, false, -1000, 1000, rng);
            } else if (t == 82) {
                // Zig-zag bamboo 8000 nodes
                root = generateZigZag(8000, -1000, 1000, rng);
            } else if (t == 83) {
                // Inverted V shape: left branch 4000, right branch 4000
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
                // Tree with deep collision lines
                root = generateCollidingTree(8500, rng);
            } else if (t == 87) {
                // Extreme values: only 1000 and -1000
                root = generateComplete(9000, 0, 1, rng);
                // remap to -1000 and 1000
                ArrayDeque<GenNode> q = new ArrayDeque<>();
                q.add(root);
                while (!q.isEmpty()) {
                    GenNode node = q.poll();
                    node.val = (node.val == 0) ? -1000 : 1000;
                    if (node.left != null) q.add(node.left);
                    if (node.right != null) q.add(node.right);
                }
            } else if (t == 88) {
                // Alternating pairs
                root = generateRandomTree(9500, rng, -1000, 1000);
            } else if (t == 89) {
                // Complete tree 9000 nodes
                root = generateComplete(9000, -1000, 1000, rng);
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
                // Max constraint N = 10000 collision tree
                root = generateCollidingTree(10000, rng);
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
            List<Integer> ans = solveBottomView(root);
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
