import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Optimal Stack-Safe Solution for "Boundary Traversal".
 *
 * Implements anti-clockwise boundary traversal:
 * 1. Root node (if not a leaf).
 * 2. Left boundary (descending down left perimeter, excluding leaves).
 * 3. All leaves (strictly left-to-right using iterative DFS on explicit heap stack).
 * 4. Right boundary in reverse (ascending up right perimeter, excluding leaves).
 *
 * Guaranteed 100% stack-safe: zero recursive calls, runs safely on trees up to 10^4 depth.
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
     * Checks if a node is a leaf (both children null).
     */
    private static boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }

    /**
     * Computes the anti-clockwise boundary traversal of a binary tree.
     *
     * @param root The root of the binary tree.
     * @return List of boundary node values.
     */
    public static List<Integer> boundary(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // 1. Root node (if not a leaf; leaves are handled by step 3)
        if (!isLeaf(root)) {
            result.add(root.val);
        }

        // 2. Left boundary (excluding leaves)
        TreeNode curr = root.left;
        while (curr != null) {
            if (!isLeaf(curr)) {
                result.add(curr.val);
            }
            if (curr.left != null) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        // 3. Leaves (left-to-right) via iterative explicit-stack DFS
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (isLeaf(node)) {
                result.add(node.val);
            } else {
                // Push right first so left child is popped and processed first
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
        }

        // 4. Right boundary in reverse (excluding leaves)
        ArrayList<Integer> rightBoundary = new ArrayList<>();
        curr = root.right;
        while (curr != null) {
            if (!isLeaf(curr)) {
                rightBoundary.add(curr.val);
            }
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        for (int i = rightBoundary.size() - 1; i >= 0; i--) {
            result.add(rightBoundary.get(i));
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
        List<Integer> result = boundary(root);

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
