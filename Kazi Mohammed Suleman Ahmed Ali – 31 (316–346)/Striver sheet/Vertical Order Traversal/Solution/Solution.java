import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Optimal Stack-Safe Solution for "Vertical Order Traversal".
 *
 * Implements Breadth-First Search (BFS) with 3-tier tuple sorting:
 * 1. Primary Sort Key: Column index (leftmost to rightmost).
 * 2. Secondary Sort Key: Row index (top to bottom).
 * 3. Tertiary Sort Key: Node value in ascending order for overlapping coordinates.
 *
 * 100% stack-safe for deep/skewed trees up to 10^4 nodes.
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
     * Flat tuple storing node coordinates and value.
     */
    static class Item implements Comparable<Item> {
        int col;
        int row;
        int val;

        Item(int col, int row, int val) {
            this.col = col;
            this.row = row;
            this.val = val;
        }

        @Override
        public int compareTo(Item other) {
            if (this.col != other.col) {
                return Integer.compare(this.col, other.col);
            }
            if (this.row != other.row) {
                return Integer.compare(this.row, other.row);
            }
            return Integer.compare(this.val, other.val);
        }
    }

    /**
     * Queue entry for iterative BFS.
     */
    static class QueueNode {
        TreeNode node;
        int col;
        int row;

        QueueNode(TreeNode node, int col, int row) {
            this.node = node;
            this.col = col;
            this.row = row;
        }
    }

    /**
     * Computes the vertical order traversal of a binary tree.
     *
     * @param root The root of the binary tree.
     * @return 2D list of node values grouped by vertical columns.
     */
    public static List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        List<Item> items = new ArrayList<>();
        ArrayDeque<QueueNode> queue = new ArrayDeque<>();
        queue.add(new QueueNode(root, 0, 0));

        while (!queue.isEmpty()) {
            QueueNode qn = queue.poll();
            items.add(new Item(qn.col, qn.row, qn.node.val));

            if (qn.node.left != null) {
                queue.add(new QueueNode(qn.node.left, qn.col - 1, qn.row + 1));
            }
            if (qn.node.right != null) {
                queue.add(new QueueNode(qn.node.right, qn.col + 1, qn.row + 1));
            }
        }

        Collections.sort(items);

        int prevCol = Integer.MIN_VALUE;
        List<Integer> currentCol = null;

        for (Item item : items) {
            if (currentCol == null || item.col != prevCol) {
                currentCol = new ArrayList<>();
                result.add(currentCol);
                prevCol = item.col;
            }
            currentCol.add(item.val);
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
     * Formats and prints the 2D list to the output writer.
     */
    public static void printResult(List<List<Integer>> result, PrintWriter out) {
        if (result == null || result.isEmpty()) {
            out.println("[]");
            return;
        }

        out.print("[");
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) {
                out.print(", ");
            }
            out.print("[");
            List<Integer> col = result.get(i);
            for (int j = 0; j < col.size(); j++) {
                if (j > 0) {
                    out.print(", ");
                }
                out.print(col.get(j));
            }
            out.print("]");
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
        List<List<Integer>> result = verticalTraversal(root);

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
