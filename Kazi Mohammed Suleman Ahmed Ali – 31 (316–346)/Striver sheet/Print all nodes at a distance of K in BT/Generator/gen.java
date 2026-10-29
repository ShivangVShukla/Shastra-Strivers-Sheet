import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
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
     * Optimal solver for distance K.
     */
    public static List<Integer> solveDistanceK(GenNode root, int targetVal, int k) {
        List<Integer> result = new ArrayList<>();
        if (root == null || k < 0) return result;

        Map<GenNode, GenNode> parentTrack = new HashMap<>();
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);
        GenNode target = null;

        while (!q.isEmpty()) {
            GenNode curr = q.poll();
            if (curr.val == targetVal) target = curr;
            if (curr.left != null) {
                parentTrack.put(curr.left, curr);
                q.add(curr.left);
            }
            if (curr.right != null) {
                parentTrack.put(curr.right, curr);
                q.add(curr.right);
            }
        }

        if (target == null) return result;

        Set<GenNode> visited = new HashSet<>();
        q.add(target);
        visited.add(target);
        int currentDistance = 0;

        while (!q.isEmpty()) {
            if (currentDistance == k) break;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                GenNode curr = q.poll();
                if (curr.left != null && !visited.contains(curr.left)) {
                    visited.add(curr.left);
                    q.add(curr.left);
                }
                if (curr.right != null && !visited.contains(curr.right)) {
                    visited.add(curr.right);
                    q.add(curr.right);
                }
                GenNode parent = parentTrack.get(curr);
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent);
                    q.add(parent);
                }
            }
            currentDistance++;
        }

        while (!q.isEmpty()) {
            result.add(q.poll().val);
        }
        Collections.sort(result);
        return result;
    }

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
        if (s == null || s.trim().isEmpty() || s.trim().equalsIgnoreCase("null") || s.trim().equalsIgnoreCase("N")) {
            return null;
        }
        String[] parts = s.trim().split("\\s+");
        if (parts.length == 0 || parts[0].equalsIgnoreCase("null") || parts[0].equalsIgnoreCase("N")) {
            return null;
        }

        GenNode root = new GenNode(Integer.parseInt(parts[0]));
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);

        int idx = 1;
        while (!q.isEmpty() && idx < parts.length) {
            GenNode curr = q.poll();

            if (idx < parts.length) {
                String tk = parts[idx++];
                if (!tk.equalsIgnoreCase("null") && !tk.equalsIgnoreCase("N")) {
                    curr.left = new GenNode(Integer.parseInt(tk));
                    q.add(curr.left);
                }
            }

            if (idx < parts.length) {
                String tk = parts[idx++];
                if (!tk.equalsIgnoreCase("null") && !tk.equalsIgnoreCase("N")) {
                    curr.right = new GenNode(Integer.parseInt(tk));
                    q.add(curr.right);
                }
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

        Random rng = new Random(123456);

        for (int t = 0; t < 100; t++) {
            GenNode root = null;
            int targetVal = 0;
            int k = 0;

            if (t == 0) {
                // Empty tree
                root = null;
                targetVal = 1;
                k = 1;
            } else if (t == 1) {
                root = new GenNode(42);
                targetVal = 42;
                k = 0;
            } else if (t == 2) {
                root = new GenNode(42);
                targetVal = 42;
                k = 1;
            } else if (t == 3) {
                root = new GenNode(1);
                root.left = new GenNode(2);
                targetVal = 1;
                k = 1;
            } else if (t == 4) {
                root = new GenNode(1);
                root.left = new GenNode(2);
                targetVal = 2;
                k = 1;
            } else if (t == 5) {
                root = new GenNode(1);
                root.right = new GenNode(2);
                targetVal = 2;
                k = 2;
            } else if (t == 6) {
                // TUF Example 1
                root = buildFromLevelOrder("3 5 1 6 2 0 8 N N 7 4");
                targetVal = 5;
                k = 2;
            } else if (t == 7) {
                // TUF Example 2
                root = buildFromLevelOrder("3 5 1 6 2 0 8 N N 7 4");
                targetVal = 5;
                k = 3;
            } else if (t == 8) {
                // TUF Example 3
                root = buildFromLevelOrder("1 2 3 4 N 5 6");
                targetVal = 6;
                k = 2;
            } else if (t == 9) {
                // Negative values
                root = buildFromLevelOrder("-10 20 -30 40 -50");
                targetVal = 20;
                k = 1;
            } else if (t == 10) {
                root = generateSkewed(5, true, 10);
                targetVal = 14;
                k = 2;
            } else if (t == 11) {
                root = generateSkewed(5, true, 20);
                targetVal = 20;
                k = 3;
            } else if (t == 12) {
                root = generateSkewed(5, false, 30);
                targetVal = 32;
                k = 1;
            } else if (t == 13) {
                root = generateZigZag(7, 40);
                targetVal = 46;
                k = 3;
            } else if (t == 14) {
                root = generateComplete(7, 1);
                targetVal = 1;
                k = 2;
            } else if (t == 15) {
                root = generateComplete(15, 1);
                targetVal = 1;
                k = 3;
            } else if (t == 16) {
                root = generateComplete(15, 1);
                targetVal = 15;
                k = 0;
            } else if (t == 17) {
                root = generateComplete(15, 1);
                targetVal = 1;
                k = 100;
            } else if (t == 18) {
                root = generateComplete(31, -15);
                targetVal = 0;
                k = 2;
            } else if (t == 19) {
                root = buildFromLevelOrder("10 20 30 N 40 50 N 60 N N 70");
                targetVal = 40;
                k = 2;
            } else if (t >= 20 && t < 40) {
                int n = 10 + (t - 20) * 5;
                root = generateRandomTree(n, 100 + t * 50);
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                targetVal = vals.get(rng.nextInt(vals.size()));
                k = 1 + rng.nextInt(5);
            } else if (t >= 40 && t < 60) {
                int n = 150 + (t - 40) * 12;
                if (t % 2 == 0) {
                    root = generateComplete(n, 1000 + t * 100);
                } else {
                    root = generateRandomTree(n, 1000 + t * 100);
                }
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                targetVal = vals.get(rng.nextInt(vals.size()));
                k = 1 + rng.nextInt(8);
            } else if (t >= 60 && t < 80) {
                int n = 500 + (t - 60) * 15;
                root = generateRandomTree(n, -4000 + t * 50);
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                targetVal = vals.get(rng.nextInt(vals.size()));
                k = 2 + rng.nextInt(10);
            } else if (t == 80) {
                root = generateSkewed(900, true, 1);
                targetVal = 900;
                k = 450;
            } else if (t == 81) {
                root = generateSkewed(900, false, 1);
                targetVal = 450;
                k = 200;
            } else if (t == 82) {
                root = generateZigZag(950, 1);
                targetVal = 1;
                k = 500;
            } else if (t == 83) {
                // Inverted V
                root = new GenNode(0);
                root.left = generateSkewed(475, true, 1);
                root.right = generateSkewed(474, false, 476);
                targetVal = 10;
                k = 15;
            } else if (t == 84) {
                root = generateComplete(1023, 1);
                targetVal = 1;
                k = 4;
            } else if (t == 85) {
                // Comb tree
                root = new GenNode(1);
                GenNode curr = root;
                for (int i = 0; i < 474; i++) {
                    curr.left = new GenNode(2 + i * 2);
                    curr.right = new GenNode(3 + i * 2);
                    curr = curr.right;
                }
                targetVal = 2;
                k = 5;
            } else if (t == 86) {
                root = generateRandomTree(950, 5000);
                targetVal = 5000;
                k = 4;
            } else if (t == 87) {
                // Extreme values spanning [-10000, 10000]
                root = generateComplete(900, -10000);
                targetVal = -10000;
                k = 5;
            } else if (t == 88) {
                root = generateComplete(950, 1);
                targetVal = 500;
                k = 1;
            } else if (t == 89) {
                root = generateRandomTree(980, 1);
                targetVal = 490;
                k = 3;
            } else if (t == 90) {
                // Max constraint N = 1000 complete tree, target at root
                root = generateComplete(1000, 1);
                targetVal = 1;
                k = 3;
            } else if (t == 91) {
                // Max constraint N = 1000 complete tree, target at leaf
                root = generateComplete(1000, 1);
                targetVal = 1000;
                k = 5;
            } else if (t == 92) {
                root = generateRandomTree(1000, 1);
                targetVal = 1;
                k = 10;
            } else if (t == 93) {
                root = generateRandomTree(1000, 2000);
                targetVal = 2500;
                k = 6;
            } else if (t == 94) {
                // Max constraint N = 1000 left-skewed, k = 1000 (out of bounds -> [])
                root = generateSkewed(1000, true, 1);
                targetVal = 1;
                k = 1000;
            } else if (t == 95) {
                // Max constraint N = 1000 right-skewed, target at bottom
                root = generateSkewed(1000, false, 1);
                targetVal = 1000;
                k = 500;
            } else if (t == 96) {
                // Max constraint N = 1000 zig-zag
                root = generateZigZag(1000, 1);
                targetVal = 500;
                k = 200;
            } else if (t == 97) {
                // Comb tree N = 1000
                root = new GenNode(1);
                GenNode curr = root;
                for (int i = 0; i < 499; i++) {
                    curr.left = new GenNode(2 + i * 2);
                    curr.right = new GenNode(3 + i * 2);
                    curr = curr.right;
                }
                targetVal = 998;
                k = 2;
            } else if (t == 98) {
                // Extreme values tree [-10000, 10000] with N = 1000
                root = generateComplete(1000, -10000);
                targetVal = -9999;
                k = 4;
            } else {
                // t == 99: Max constraint N = 1000 random tree (seed 999999)
                root = generateRandomTree(1000, 8000);
                targetVal = 8000;
                k = 5;
            }

            String inContent = serialize(root) + "\n" + targetVal + "\n" + k;
            List<Integer> ans = solveDistanceK(root, targetVal, k);
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
