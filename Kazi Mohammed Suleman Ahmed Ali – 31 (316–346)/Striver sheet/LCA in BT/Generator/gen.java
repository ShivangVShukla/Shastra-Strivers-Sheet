import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
     * Optimal solver for Lowest Common Ancestor.
     * Uses parent-pointer BFS to avoid recursion stack overflow on large trees.
     */
    public static int solveLCA(GenNode root, int pVal, int qVal) {
        if (root == null) return -1;
        if (root.val == pVal || root.val == qVal) return root.val;

        Map<Integer, Integer> parent = new HashMap<>();
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);
        parent.put(root.val, null);

        while (!q.isEmpty() && (!parent.containsKey(pVal) || !parent.containsKey(qVal))) {
            GenNode curr = q.poll();

            if (curr.left != null) {
                parent.put(curr.left.val, curr.val);
                q.add(curr.left);
            }
            if (curr.right != null) {
                parent.put(curr.right.val, curr.val);
                q.add(curr.right);
            }
        }

        Set<Integer> ancestors = new HashSet<>();
        Integer curr = pVal;
        while (curr != null) {
            ancestors.add(curr);
            curr = parent.get(curr);
        }

        curr = qVal;
        while (curr != null && !ancestors.contains(curr)) {
            curr = parent.get(curr);
        }

        return curr != null ? curr : -1;
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

    public static void collectNodeValues(GenNode root, List<Integer> list) {
        if (root == null) return;
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            GenNode curr = q.poll();
            list.add(curr.val);
            if (curr.left != null) q.add(curr.left);
            if (curr.right != null) q.add(curr.right);
        }
    }

    public static GenNode generateRandomTree(int n, int startVal) {
        if (n <= 0) return null;
        GenNode root = new GenNode(startVal);
        if (n == 1) return root;

        List<GenNode> nodes = new ArrayList<>();
        nodes.add(root);
        int created = 1;
        Random rng = new Random(startVal * 31L + n);

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

            GenNode child = new GenNode(startVal + created);
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

    public static GenNode generateSkewed(int n, boolean left, int startVal) {
        if (n <= 0) return null;
        GenNode root = new GenNode(startVal);
        GenNode curr = root;
        for (int i = 1; i < n; i++) {
            GenNode next = new GenNode(startVal + i);
            if (left) {
                curr.left = next;
            } else {
                curr.right = next;
            }
            curr = next;
        }
        return root;
    }

    public static GenNode generateZigZag(int n, int startVal) {
        if (n <= 0) return null;
        GenNode root = new GenNode(startVal);
        GenNode curr = root;
        boolean goLeft = true;
        for (int i = 1; i < n; i++) {
            GenNode next = new GenNode(startVal + i);
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

    public static GenNode generateComplete(int n, int startVal) {
        if (n <= 0) return null;
        GenNode root = new GenNode(startVal);
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);
        int count = 1;
        while (count < n) {
            GenNode curr = q.poll();
            curr.left = new GenNode(startVal + count);
            q.add(curr.left);
            count++;
            if (count >= n) break;

            curr.right = new GenNode(startVal + count);
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

        Random rng = new Random(888888);

        for (int t = 0; t < 100; t++) {
            GenNode root = null;
            int pVal = 0, qVal = 0;

            if (t == 0) {
                root = new GenNode(1);
                root.left = new GenNode(2);
                pVal = 1;
                qVal = 2;
            } else if (t == 1) {
                root = new GenNode(1);
                root.right = new GenNode(2);
                pVal = 2;
                qVal = 1;
            } else if (t == 2) {
                root = new GenNode(-1000000);
                root.left = new GenNode(1000000);
                pVal = -1000000;
                qVal = 1000000;
            } else if (t == 3) {
                root = new GenNode(1);
                root.left = new GenNode(2);
                root.right = new GenNode(3);
                pVal = 2;
                qVal = 3;
            } else if (t == 4) {
                root = new GenNode(1);
                root.left = new GenNode(2);
                root.right = new GenNode(3);
                pVal = 1;
                qVal = 3;
            } else if (t == 5) {
                root = new GenNode(1);
                root.left = new GenNode(2);
                root.right = new GenNode(3);
                pVal = 2;
                qVal = 1;
            } else if (t == 6) {
                // TUF Example 1
                root = buildFromLevelOrder("3 5 1 6 2 0 8 null null 7 4");
                pVal = 5;
                qVal = 1;
            } else if (t == 7) {
                // TUF Example 2
                root = buildFromLevelOrder("3 5 1 6 2 0 8 null null 7 4");
                pVal = 5;
                qVal = 4;
            } else if (t == 8) {
                // TUF Example 3
                root = buildFromLevelOrder("7 1 2 8 10 4 5 null 6");
                pVal = 6;
                qVal = 10;
            } else if (t == 9) {
                // TUF Sample Case 3
                root = buildFromLevelOrder("1 2 3 null 4 null 5");
                pVal = 4;
                qVal = 5;
            } else if (t == 10) {
                root = generateSkewed(5, true, 10);
                pVal = 10;
                qVal = 14;
            } else if (t == 11) {
                root = generateSkewed(5, true, 20);
                pVal = 22;
                qVal = 24;
            } else if (t == 12) {
                root = generateSkewed(5, false, 30);
                pVal = 30;
                qVal = 34;
            } else if (t == 13) {
                root = generateSkewed(5, false, 40);
                pVal = 42;
                qVal = 43;
            } else if (t == 14) {
                root = generateZigZag(7, 50);
                pVal = 53;
                qVal = 56;
            } else if (t == 15) {
                root = generateComplete(7, 1);
                pVal = 4;
                qVal = 5;
            } else if (t == 16) {
                root = generateComplete(15, 1);
                pVal = 8;
                qVal = 15;
            } else if (t == 17) {
                root = generateComplete(15, 1);
                pVal = 8;
                qVal = 9;
            } else if (t == 18) {
                root = generateComplete(15, 1);
                pVal = 4;
                qVal = 9;
            } else if (t == 19) {
                root = buildFromLevelOrder("10 20 30 null 40 50 null 60 null null 70");
                pVal = 60;
                qVal = 70;
            } else if (t >= 20 && t < 40) {
                int n = 10 + (t - 20) * 10;
                root = generateRandomTree(n, 1000 + t * 500);
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                int pIdx = rng.nextInt(vals.size());
                int qIdx = rng.nextInt(vals.size());
                while (qIdx == pIdx) qIdx = rng.nextInt(vals.size());
                pVal = vals.get(pIdx);
                qVal = vals.get(qIdx);
            } else if (t >= 40 && t < 60) {
                int n = 300 + (t - 40) * 100;
                if (t % 2 == 0) {
                    root = generateComplete(n, 10000 + t * 1000);
                } else {
                    root = generateRandomTree(n, 10000 + t * 1000);
                }
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                int pIdx = rng.nextInt(vals.size());
                int qIdx = rng.nextInt(vals.size());
                while (qIdx == pIdx) qIdx = rng.nextInt(vals.size());
                pVal = vals.get(pIdx);
                qVal = vals.get(qIdx);
            } else if (t >= 60 && t < 80) {
                int n = 2000 + (t - 60) * 500;
                root = generateComplete(n, 50000 + t * 1000);
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                int pIdx = rng.nextInt(vals.size());
                int qIdx = rng.nextInt(vals.size());
                while (qIdx == pIdx) qIdx = rng.nextInt(vals.size());
                pVal = vals.get(pIdx);
                qVal = vals.get(qIdx);
            } else if (t == 80) {
                root = generateSkewed(25000, true, 100000);
                pVal = 100000;
                qVal = 124999;
            } else if (t == 81) {
                root = generateSkewed(25000, false, 200000);
                pVal = 210000;
                qVal = 224999;
            } else if (t == 82) {
                root = generateZigZag(30000, 300000);
                pVal = 300005;
                qVal = 329999;
            } else if (t == 83) {
                // Inverted V
                root = new GenNode(0);
                root.left = generateSkewed(20000, true, 1);
                root.right = generateSkewed(20000, false, 20001);
                pVal = 19999;
                qVal = 40000;
            } else if (t == 84) {
                root = generateComplete(32767, 1);
                pVal = 16384;
                qVal = 32766;
            } else if (t == 85) {
                // Comb tree
                root = new GenNode(1);
                GenNode curr = root;
                for (int i = 0; i < 20000; i++) {
                    curr.left = new GenNode(2 + i * 2);
                    curr.right = new GenNode(3 + i * 2);
                    curr = curr.right;
                }
                pVal = 2;
                qVal = 40001;
            } else if (t == 86) {
                root = generateComplete(50000, -50000);
                pVal = -50000;
                qVal = -1;
            } else if (t == 87) {
                root = generateComplete(50000, -1000000);
                pVal = -1000000;
                qVal = -1000000 + 49999;
            } else if (t == 88) {
                root = generateComplete(60000, 1000);
                pVal = 2000;
                qVal = 55000;
            } else if (t == 89) {
                root = generateComplete(70000, 1);
                pVal = 35000;
                qVal = 70000;
            } else if (t == 90) {
                // Max constraint N = 100000 complete tree: opposite branches
                root = generateComplete(100000, 1);
                pVal = 2;
                qVal = 3;
            } else if (t == 91) {
                // Max constraint N = 100000 complete tree: root and leaf
                root = generateComplete(100000, 1);
                pVal = 1;
                qVal = 100000;
            } else if (t == 92) {
                // Max constraint N = 100000 complete tree: deep leaves
                root = generateComplete(100000, 1);
                pVal = 99998;
                qVal = 99999;
            } else if (t == 93) {
                // Max constraint N = 100000 left-skewed
                root = generateSkewed(100000, true, -50000);
                pVal = -50000;
                qVal = 49999;
            } else if (t == 94) {
                // Max constraint N = 100000 right-skewed
                root = generateSkewed(100000, false, 1);
                pVal = 50000;
                qVal = 100000;
            } else if (t == 95) {
                // Max constraint N = 100000 zig-zag
                root = generateZigZag(100000, 1);
                pVal = 1;
                qVal = 100000;
            } else if (t == 96) {
                // Max constraint N = 100000 Inverted V
                root = new GenNode(0);
                root.left = generateSkewed(50000, true, 1);
                root.right = generateSkewed(49999, false, 50001);
                pVal = 50000;
                qVal = 99999;
            } else if (t == 97) {
                // Comb tree N = 100000
                root = new GenNode(1);
                GenNode curr = root;
                for (int i = 0; i < 49999; i++) {
                    curr.left = new GenNode(2 + i * 2);
                    curr.right = new GenNode(3 + i * 2);
                    curr = curr.right;
                }
                pVal = 10;
                qVal = 99999;
            } else if (t == 98) {
                // Tree spanning [-10^6, 10^6]
                root = generateComplete(100000, -1000000);
                pVal = -1000000;
                qVal = -1000000 + 99999;
            } else {
                // t == 99: Complete tree N = 100000 with boundary values
                root = generateComplete(100000, 1000000 - 100000);
                pVal = 1000000 - 100000;
                qVal = 1000000 - 1;
            }

            String inContent = serialize(root) + "\n" + pVal + "\n" + qVal;
            int lcaVal = solveLCA(root, pVal, qVal);
            String outContent = String.valueOf(lcaVal);

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
