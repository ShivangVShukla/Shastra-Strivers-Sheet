import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;

/**
 * Optimal Stack-Safe Solution for "Check if two trees are identical or not".
 *
 * Implements simultaneous iterative BFS traversal of both trees:
 * - Compares structural symmetry and node values in lockstep.
 * - Early exits immediately upon detecting any mismatch.
 * - Stack-safe and immune to JVM thread call stack exhaustion.
 *
 * Uses custom byte-buffer FastScanner and PrintWriter for high-performance I/O.
 */
public class Solution {

    /**
     * Binary Tree Node definition.
     */
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
     * Stack-safe iterative BFS comparison of two binary trees.
     *
     * @param p Root of the first binary tree.
     * @param q Root of the second binary tree.
     * @return true if both trees are structurally identical with equal values, false otherwise.
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }

        ArrayDeque<TreeNode> queueP = new ArrayDeque<>();
        ArrayDeque<TreeNode> queueQ = new ArrayDeque<>();
        queueP.add(p);
        queueQ.add(q);

        while (!queueP.isEmpty() && !queueQ.isEmpty()) {
            TreeNode nodeP = queueP.poll();
            TreeNode nodeQ = queueQ.poll();

            // Value comparison
            if (nodeP.val != nodeQ.val) {
                return false;
            }

            // Left child structural and existence check
            if (nodeP.left != null && nodeQ.left != null) {
                queueP.add(nodeP.left);
                queueQ.add(nodeQ.left);
            } else if (nodeP.left != null || nodeQ.left != null) {
                return false; // Structural divergence
            }

            // Right child structural and existence check
            if (nodeP.right != null && nodeQ.right != null) {
                queueP.add(nodeP.right);
                queueQ.add(nodeQ.right);
            } else if (nodeP.right != null || nodeQ.right != null) {
                return false; // Structural divergence
            }
        }

        return queueP.isEmpty() && queueQ.isEmpty();
    }

    /**
     * Checks if a token indicates an absent child.
     */
    private static boolean isNullToken(String token) {
        if (token == null) {
            return true;
        }
        String lower = token.toLowerCase();
        return lower.equals("null") || lower.equals("none") || lower.equals("#");
    }

    /**
     * Reconstructs a binary tree from a single serialized level-order string.
     */
    public static TreeNode buildTreeFromLine(String line) {
        if (line == null) {
            return null;
        }

        String cleaned = line.replace('[', ' ').replace(']', ' ').replace(',', ' ').trim();
        if (cleaned.isEmpty() || isNullToken(cleaned)) {
            return null;
        }

        String[] tokens = cleaned.split("\\s+");
        if (tokens.length == 0 || isNullToken(tokens[0])) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int i = 1;

        while (!queue.isEmpty() && i < tokens.length) {
            TreeNode current = queue.poll();

            // Left child
            if (i < tokens.length) {
                String token = tokens[i++];
                if (!isNullToken(token)) {
                    current.left = new TreeNode(Integer.parseInt(token));
                    queue.add(current.left);
                }
            }

            // Right child
            if (i < tokens.length) {
                String token = tokens[i++];
                if (!isNullToken(token)) {
                    current.right = new TreeNode(Integer.parseInt(token));
                    queue.add(current.right);
                }
            }
        }

        return root;
    }

    /**
     * Main entry point.
     */
    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        String lineP = scanner.readLine();
        String lineQ = scanner.readLine();

        TreeNode p = buildTreeFromLine(lineP);
        TreeNode q = buildTreeFromLine(lineQ);

        boolean result = isSameTree(p, q);
        out.println(result ? "true" : "false");
        out.flush();
    }

    /**
     * Custom byte-buffer FastScanner for ultra-fast line and token I/O.
     */
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[65536];
        private int head = 0;
        private int tail = 0;

        public FastScanner(InputStream in) {
            this.in = in;
        }

        private int readByte() {
            if (head >= tail) {
                head = 0;
                try {
                    tail = in.read(buffer, 0, buffer.length);
                } catch (IOException e) {
                    tail = -1;
                }
                if (tail <= 0) {
                    return -1;
                }
            }
            return buffer[head++];
        }

        public String readLine() {
            int b = readByte();
            if (b == -1) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            while (b != -1 && b != '\n') {
                if (b != '\r') {
                    sb.append((char) b);
                }
                b = readByte();
            }
            return sb.toString();
        }
    }
}
