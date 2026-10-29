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
     * Optimal solver for counting nodes in a complete binary tree.
     */
    public static int solveCount(GenNode root) {
        if (root == null) return 0;

        int lh = getLeftHeight(root);
        int rh = getRightHeight(root);

        if (lh == rh) {
            return (1 << lh) - 1;
        }

        return 1 + solveCount(root.left) + solveCount(root.right);
    }

    private static int getLeftHeight(GenNode node) {
        int h = 0;
        while (node != null) {
            h++;
            node = node.left;
        }
        return h;
    }

    private static int getRightHeight(GenNode node) {
        int h = 0;
        while (node != null) {
            h++;
            node = node.right;
        }
        return h;
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

        Random rng = new Random(987654);

        for (int t = 0; t < 100; t++) {
            GenNode root = null;

            if (t == 0) {
                // Empty tree
                root = null;
            } else if (t == 1) {
                root = new GenNode(42);
            } else if (t == 2) {
                root = new GenNode(-100000);
            } else if (t == 3) {
                root = new GenNode(100000);
            } else if (t == 4) {
                root = generateComplete(2, 1);
            } else if (t == 5) {
                root = generateComplete(3, 1);
            } else if (t == 6) {
                // TUF Example 1
                root = buildFromLevelOrder("1 2 3 4 5 6");
            } else if (t == 7) {
                // TUF Example 2
                root = buildFromLevelOrder("1 2 3 4 5 6 7 8 9");
            } else if (t == 8) {
                // TUF Example 3
                root = buildFromLevelOrder("1 2 3");
            } else if (t == 9) {
                root = generateComplete(4, 1);
            } else if (t == 10) {
                root = generateComplete(5, 1);
            } else if (t == 11) {
                root = generateComplete(7, 1);
            } else if (t == 12) {
                root = generateComplete(8, 1);
            } else if (t == 13) {
                root = generateComplete(12, 1);
            } else if (t == 14) {
                root = generateComplete(15, 1);
            } else if (t == 15) {
                root = generateComplete(16, 1);
            } else if (t == 16) {
                root = generateComplete(25, -25);
            } else if (t == 17) {
                root = generateComplete(31, 1);
            } else if (t == 18) {
                root = generateComplete(32, 1);
            } else if (t == 19) {
                root = generateComplete(50, 10);
            } else if (t >= 20 && t < 40) {
                int n = 60 + (t - 20) * 22;
                root = generateComplete(n, -500 + t * 20);
            } else if (t >= 40 && t < 60) {
                int n = 500 + (t - 40) * 125;
                root = generateComplete(n, -10000 + t * 100);
            } else if (t >= 60 && t < 80) {
                int n = 3000 + (t - 60) * 850;
                root = generateComplete(n, 1000);
            } else if (t == 80) {
                // 1023 (2^10 - 1)
                root = generateComplete(1023, 1);
            } else if (t == 81) {
                // 1024
                root = generateComplete(1024, 1);
            } else if (t == 82) {
                // 2047 (2^11 - 1)
                root = generateComplete(2047, 1);
            } else if (t == 83) {
                // 2048
                root = generateComplete(2048, 1);
            } else if (t == 84) {
                // 4095 (2^12 - 1)
                root = generateComplete(4095, 1);
            } else if (t == 85) {
                // 4096
                root = generateComplete(4096, 1);
            } else if (t == 86) {
                // 8191 (2^13 - 1)
                root = generateComplete(8191, 1);
            } else if (t == 87) {
                // 8192
                root = generateComplete(8192, 1);
            } else if (t == 88) {
                // 16383 (2^14 - 1)
                root = generateComplete(16383, 1);
            } else if (t == 89) {
                // 16384
                root = generateComplete(16384, 1);
            } else if (t == 90) {
                root = generateComplete(25000, 1);
            } else if (t == 91) {
                root = generateComplete(30000, 1);
            } else if (t == 92) {
                // 32767 (2^15 - 1)
                root = generateComplete(32767, 1);
            } else if (t == 93) {
                // 32768
                root = generateComplete(32768, 1);
            } else if (t == 94) {
                root = generateComplete(35000, 1);
            } else if (t == 95) {
                root = generateComplete(40000, 1);
            } else if (t == 96) {
                root = generateComplete(45000, 1);
            } else if (t == 97) {
                // Negative values
                root = generateComplete(48000, -100000);
            } else if (t == 98) {
                // Positive boundary
                root = generateComplete(49999, 50000);
            } else {
                // t == 99: Max constraint N = 50000
                root = generateComplete(50000, 1);
            }

            String inContent = serialize(root);
            int count = solveCount(root);
            String outContent = String.valueOf(count);

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
