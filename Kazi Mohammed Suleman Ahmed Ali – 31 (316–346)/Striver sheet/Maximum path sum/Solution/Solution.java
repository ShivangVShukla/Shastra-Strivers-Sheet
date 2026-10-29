import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * Optimal Stack-Safe Solution for "Maximum path sum".
 *
 * Implements an iterative post-order traversal using an explicit stack
 * on JVM heap memory:
 * - Computes maximum upward branch gains bottom-up.
 * - Tracks the global maximum path sum turning at any node.
 * - Truncates negative branch contributions using greedy max(0, gain).
 * - 100% stack-safe for deep / skewed trees up to 3 * 10^4 nodes.
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
     * Stack-safe iterative post-order computation of the maximum path sum.
     *
     * @param root The root of the binary tree.
     * @return The maximum path sum among all non-empty paths.
     */
    public static int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        Map<TreeNode, Integer> maxGains = new HashMap<>();
        int maxSum = Integer.MIN_VALUE;

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode peek = stack.peek();
                if (peek.right != null && lastVisited != peek.right) {
                    current = peek.right;
                } else {
                    stack.pop();
                    int leftGain = Math.max(0, peek.left == null ? 0 : maxGains.getOrDefault(peek.left, 0));
                    int rightGain = Math.max(0, peek.right == null ? 0 : maxGains.getOrDefault(peek.right, 0));

                    // Maximum path turning at current node
                    int currentTurnSum = peek.val + leftGain + rightGain;
                    maxSum = Math.max(maxSum, currentTurnSum);

                    // Upward branch gain contributed to parent
                    int maxGainUpward = peek.val + Math.max(leftGain, rightGain);
                    maxGains.put(peek, maxGainUpward);

                    // Prune finished child gains to maintain O(H) auxiliary memory
                    if (peek.left != null) {
                        maxGains.remove(peek.left);
                    }
                    if (peek.right != null) {
                        maxGains.remove(peek.right);
                    }

                    lastVisited = peek;
                }
            }
        }

        return maxSum;
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
        int result = maxPathSum(root);

        out.println(result);
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
