import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;

/**
 * Optimal Stack-Safe Solution for "Check for symmetrical BTs".
 *
 * Implements simultaneous iterative BFS traversal comparing mirror branches
 * using explicit queues on JVM heap memory:
 * - Compares outer pair (n1.left, n2.right) and inner pair (n1.right, n2.left).
 * - Early exits immediately upon detecting any value or structural asymmetry.
 * - 100% stack-safe for deep / skewed trees up to 10^4 nodes.
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
     * Stack-safe iterative BFS check for tree symmetry.
     *
     * @param root The root of the binary tree.
     * @return true if the tree is symmetric around its vertical axis, false otherwise.
     */
    public static boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null && root.right == null) {
            return true;
        }
        if (root.left == null || root.right == null) {
            return false;
        }

        ArrayDeque<TreeNode> q1 = new ArrayDeque<>();
        ArrayDeque<TreeNode> q2 = new ArrayDeque<>();
        q1.add(root.left);
        q2.add(root.right);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode n1 = q1.poll();
            TreeNode n2 = q2.poll();

            // Value comparison
            if (n1.val != n2.val) {
                return false;
            }

            // Outer pair: n1.left and n2.right
            if (n1.left != null && n2.right != null) {
                q1.add(n1.left);
                q2.add(n2.right);
            } else if (n1.left != null || n2.right != null) {
                return false; // Structural asymmetry
            }

            // Inner pair: n1.right and n2.left
            if (n1.right != null && n2.left != null) {
                q1.add(n1.right);
                q2.add(n2.left);
            } else if (n1.right != null || n2.left != null) {
                return false; // Structural asymmetry
            }
        }

        return q1.isEmpty() && q2.isEmpty();
    }

    /**
     * Checks if a token indicates an absent child node.
     */
    private static boolean isNullToken(String token) {
        if (token == null) {
            return true;
        }
        String lower = token.toLowerCase();
        return lower.equals("null") || lower.equals("none") || lower.equals("#");
    }

    /**
     * Reconstructs the binary tree from level-order tokens.
     */
    public static TreeNode buildTree(FastScanner scanner) {
        if (!scanner.hasNext()) {
            return null;
        }

        String rootToken = scanner.next();
        if (rootToken == null || isNullToken(rootToken)) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(rootToken));
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty() && scanner.hasNext()) {
            TreeNode current = queue.poll();

            // Left child
            if (scanner.hasNext()) {
                String leftToken = scanner.next();
                if (leftToken != null && !isNullToken(leftToken)) {
                    current.left = new TreeNode(Integer.parseInt(leftToken));
                    queue.add(current.left);
                }
            }

            // Right child
            if (scanner.hasNext()) {
                String rightToken = scanner.next();
                if (rightToken != null && !isNullToken(rightToken)) {
                    current.right = new TreeNode(Integer.parseInt(rightToken));
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

        TreeNode root = buildTree(scanner);
        boolean symmetric = isSymmetric(root);

        out.println(symmetric ? "true" : "false");
        out.flush();
    }

    /**
     * Custom byte-buffer FastScanner for ultra-fast I/O.
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

        private void skipWhitespace() {
            while (true) {
                int b = readByte();
                if (b == -1) {
                    return;
                }
                if (b > 32) {
                    head--; // put back character
                    return;
                }
            }
        }

        public boolean hasNext() {
            skipWhitespace();
            if (head < tail) {
                return true;
            }
            int b = readByte();
            if (b == -1) {
                return false;
            }
            head--;
            return true;
        }

        public String next() {
            skipWhitespace();
            int b = readByte();
            if (b == -1) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            while (b > 32) {
                char c = (char) b;
                // Strip bracket and comma formatting if present
                if (c != '[' && c != ']' && c != ',') {
                    sb.append(c);
                }
                b = readByte();
            }
            String token = sb.toString().trim();
            if (token.isEmpty()) {
                return hasNext() ? next() : null;
            }
            return token;
        }
    }
}
