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
     * Optimal solver for Burning Tree time.
     */
    public static int solveBurnTime(GenNode root, int startVal) {
        if (root == null) return 0;

        Map<GenNode, GenNode> parentTrack = new HashMap<>();
        ArrayDeque<GenNode> q = new ArrayDeque<>();
        q.add(root);
        GenNode target = null;

        while (!q.isEmpty()) {
            GenNode curr = q.poll();
            if (curr.val == startVal) target = curr;
            if (curr.left != null) {
                parentTrack.put(curr.left, curr);
                q.add(curr.left);
            }
            if (curr.right != null) {
                parentTrack.put(curr.right, curr);
                q.add(curr.right);
            }
        }

        if (target == null) return 0;

        Set<GenNode> visited = new HashSet<>();
        q.add(target);
        visited.add(target);
        int time = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            boolean burnedAny = false;

            for (int i = 0; i < size; i++) {
                GenNode curr = q.poll();

                if (curr.left != null && !visited.contains(curr.left)) {
                    visited.add(curr.left);
                    q.add(curr.left);
                    burnedAny = true;
                }
                if (curr.right != null && !visited.contains(curr.right)) {
                    visited.add(curr.right);
                    q.add(curr.right);
                    burnedAny = true;
                }
                GenNode parent = parentTrack.get(curr);
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent);
                    q.add(parent);
                    burnedAny = true;
                }
            }

            if (burnedAny) {
                time++;
            }
        }

        return time;
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

        Random rng = new Random(456789);

        for (int t = 0; t < 100; t++) {
            GenNode root = null;
            int startVal = 0;

            if (t == 0) {
                // Empty tree
                root = null;
                startVal = 1;
            } else if (t == 1) {
                root = new GenNode(42);
                startVal = 42;
            } else if (t == 2) {
                root = new GenNode(-100000);
                startVal = -100000;
            } else if (t == 3) {
                root = new GenNode(100000);
                startVal = 100000;
            } else if (t == 4) {
                root = new GenNode(1);
                root.left = new GenNode(2);
                startVal = 1;
            } else if (t == 5) {
                root = new GenNode(1);
                root.left = new GenNode(2);
                startVal = 2;
            } else if (t == 6) {
                // TUF Example 1
                root = buildFromLevelOrder("1 2 3 4 null 5 6 null 7");
                startVal = 1;
            } else if (t == 7) {
                // TUF Example 2
                root = buildFromLevelOrder("1 2 3 null 5 null 4");
                startVal = 4;
            } else if (t == 8) {
                // TUF Example 3
                root = buildFromLevelOrder("1 2 3 4 5 6");
                startVal = 6;
            } else if (t == 9) {
                root = buildFromLevelOrder("-10 20 -30 40 -50");
                startVal = 20;
            } else if (t == 10) {
                root = generateSkewed(5, true, 10);
                startVal = 14;
            } else if (t == 11) {
                root = generateSkewed(5, true, 20);
                startVal = 20;
            } else if (t == 12) {
                root = generateSkewed(5, false, 30);
                startVal = 32;
            } else if (t == 13) {
                root = generateZigZag(7, 40);
                startVal = 46;
            } else if (t == 14) {
                root = generateComplete(7, 1);
                startVal = 1;
            } else if (t == 15) {
                root = generateComplete(7, 1);
                startVal = 4;
            } else if (t == 16) {
                root = generateComplete(15, 1);
                startVal = 1;
            } else if (t == 17) {
                root = generateComplete(15, 1);
                startVal = 15;
            } else if (t == 18) {
                root = generateComplete(31, -15);
                startVal = 0;
            } else if (t == 19) {
                root = buildFromLevelOrder("10 20 30 N 40 50 N 60 N N 70");
                startVal = 40;
            } else if (t >= 20 && t < 40) {
                int n = 10 + (t - 20) * 10;
                root = generateRandomTree(n, 100 + t * 50);
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                startVal = vals.get(rng.nextInt(vals.size()));
            } else if (t >= 40 && t < 60) {
                int n = 300 + (t - 40) * 60;
                if (t % 2 == 0) {
                    root = generateComplete(n, 1000 + t * 100);
                } else {
                    root = generateRandomTree(n, 1000 + t * 100);
                }
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                startVal = vals.get(rng.nextInt(vals.size()));
            } else if (t >= 60 && t < 80) {
                int n = 2000 + (t - 60) * 150;
                root = generateRandomTree(n, -4000 + t * 50);
                List<Integer> vals = new ArrayList<>();
                collectNodeValues(root, vals);
                startVal = vals.get(rng.nextInt(vals.size()));
            } else if (t == 80) {
                root = generateSkewed(6000, true, 1);
                startVal = 6000;
            } else if (t == 81) {
                root = generateSkewed(6000, false, 1);
                startVal = 3000;
            } else if (t == 82) {
                root = generateZigZag(7500, 1);
                startVal = 1;
            } else if (t == 83) {
                // Inverted V
                root = new GenNode(0);
                root.left = generateSkewed(4000, true, 1);
                root.right = generateSkewed(3999, false, 4001);
                startVal = 1;
            } else if (t == 84) {
                root = generateComplete(8191, 1);
                startVal = 8191;
            } else if (t == 85) {
                // Comb tree
                root = new GenNode(1);
                GenNode curr = root;
                for (int i = 0; i < 3999; i++) {
                    curr.left = new GenNode(2 + i * 2);
                    curr.right = new GenNode(3 + i * 2);
                    curr = curr.right;
                }
                startVal = 2;
            } else if (t == 86) {
                root = generateRandomTree(8500, 5000);
                startVal = 5000;
            } else if (t == 87) {
                // Extreme values spanning [-100000, 100000]
                root = generateComplete(8000, -100000);
                startVal = -100000;
            } else if (t == 88) {
                root = generateComplete(9000, 1);
                startVal = 4500;
            } else if (t == 89) {
                root = generateRandomTree(9500, 1);
                startVal = 4750;
            } else if (t == 90) {
                // Max constraint N = 10000 complete tree, target at root
                root = generateComplete(10000, 1);
                startVal = 1;
            } else if (t == 91) {
                // Max constraint N = 10000 complete tree, target at leaf
                root = generateComplete(10000, 1);
                startVal = 10000;
            } else if (t == 92) {
                root = generateRandomTree(10000, 1);
                startVal = 1;
            } else if (t == 93) {
                root = generateRandomTree(10000, 20000);
                startVal = 25000;
            } else if (t == 94) {
                // Max constraint N = 10000 left-skewed, target at bottom -> 9999
                root = generateSkewed(10000, true, 1);
                startVal = 10000;
            } else if (t == 95) {
                // Max constraint N = 10000 right-skewed, target at middle -> 5000
                root = generateSkewed(10000, false, 1);
                startVal = 5000;
            } else if (t == 96) {
                // Max constraint N = 10000 zig-zag
                root = generateZigZag(10000, 1);
                startVal = 5000;
            } else if (t == 97) {
                // Comb tree N = 10000
                root = new GenNode(1);
                GenNode curr = root;
                for (int i = 0; i < 4999; i++) {
                    curr.left = new GenNode(2 + i * 2);
                    curr.right = new GenNode(3 + i * 2);
                    curr = curr.right;
                }
                startVal = 9998;
            } else if (t == 98) {
                // Extreme values tree [-100000, 100000] with N = 10000
                root = generateComplete(10000, -100000);
                startVal = -99999;
            } else {
                // t == 99: Max constraint N = 10000 random tree (seed 999999)
                root = generateRandomTree(10000, 80000);
                startVal = 80000;
            }

            String inContent = serialize(root) + "\n" + startVal;
            int burnTime = solveBurnTime(root, startVal);
            String outContent = String.valueOf(burnTime);

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
