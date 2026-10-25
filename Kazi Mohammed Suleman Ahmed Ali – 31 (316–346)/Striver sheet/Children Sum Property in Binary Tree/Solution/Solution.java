import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;

/**
 * Optimal Stack-Safe Solution for "Children Sum Property in Binary Tree".
 *
 * Implements an iterative Breadth-First Search (BFS) level-order traversal
 * using an explicit FIFO queue allocated on the JVM heap:
 * - Visits each node and computes the sum of its immediate children.
 * - Leaf nodes (nodes where both left and right children are null) trivially satisfy the property.
 * - For any internal node, checks: node.val == (left != null ? left.val : 0) + (right != null ? right.val : 0).
 * - Short-circuits immediately upon the first violation.
 * - 100% stack-safe for trees of arbitrary depth up to 10^4 nodes.
 *
 * Employs custom byte-buffer FastScanner and PrintWriter for ultra-fast I/O.
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
     * Stack-safe iterative BFS verification of Children Sum Property.
     *
     * @param root The root of the binary tree.
     * @return true if every node satisfies the property, false otherwise.
     */
    public static boolean checkChildrenSum(TreeNode root) {
        if (root == null) {
            return true;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();

            // Leaf nodes trivially satisfy the property
            if (curr.left == null && curr.right == null) {
                continue;
            }

            int childSum = (curr.left != null ? curr.left.val : 0) +
                           (curr.right != null ? curr.right.val : 0);

            if (curr.val != childSum) {
                return false;
            }

            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }

        return true;
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
        boolean result = checkChildrenSum(root);

        out.println(result ? "true" : "false");
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
