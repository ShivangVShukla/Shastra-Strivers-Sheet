import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Optimal Stack-Safe Solution for "Top View of BT".
 *
 * Implements Breadth-First Search (BFS) level-order traversal tracking Horizontal Distance (HD):
 * - The root has HD = 0.
 * - Left child has HD - 1, Right child has HD + 1.
 * - The first node encountered at each HD is the topmost visible node.
 * - Uses a HashMap with minHd and maxHd tracking for linear O(N) time.
 * - 100% stack-safe for deep/skewed trees up to 10^4 nodes.
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
     * Queue entry for iterative BFS with horizontal distance.
     */
    static class QueueNode {
        TreeNode node;
        int hd;

        QueueNode(TreeNode node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }

    /**
     * Computes the top view of a binary tree.
     *
     * @param root The root of the binary tree.
     * @return List of top view node values ordered from leftmost to rightmost.
     */
    public static List<Integer> topView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Map<Integer, Integer> map = new HashMap<>();
        ArrayDeque<QueueNode> queue = new ArrayDeque<>();
        queue.add(new QueueNode(root, 0));

        int minHd = 0;
        int maxHd = 0;

        while (!queue.isEmpty()) {
            QueueNode qn = queue.poll();
            TreeNode curr = qn.node;
            int hd = qn.hd;

            minHd = Math.min(minHd, hd);
            maxHd = Math.max(maxHd, hd);

            // First node seen at this horizontal distance is the top view node
            if (!map.containsKey(hd)) {
                map.put(hd, curr.val);
            }

            if (curr.left != null) {
                queue.add(new QueueNode(curr.left, hd - 1));
            }
            if (curr.right != null) {
                queue.add(new QueueNode(curr.right, hd + 1));
            }
        }

        for (int hd = minHd; hd <= maxHd; hd++) {
            if (map.containsKey(hd)) {
                result.add(map.get(hd));
            }
        }

        return result;
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
     * Formats and prints the list to the output writer.
     */
    public static void printResult(List<Integer> result, PrintWriter out) {
        if (result == null || result.isEmpty()) {
            out.println("[]");
            return;
        }

        out.print("[");
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) {
                out.print(", ");
            }
            out.print(result.get(i));
        }
        out.println("]");
    }

    /**
     * Main entry point.
     */
    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        TreeNode root = buildTree(scanner);
        List<Integer> result = topView(root);

        printResult(result, out);
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
