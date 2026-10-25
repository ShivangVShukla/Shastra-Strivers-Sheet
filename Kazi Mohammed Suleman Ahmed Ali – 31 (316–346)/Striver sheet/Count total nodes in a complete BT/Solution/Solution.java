import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayDeque;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class Solution {

    /**
     * Counts the total number of nodes in a complete binary tree.
     * Operates in optimal O((log N)^2) time by skipping complete perfect subtrees.
     */
    public static int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = getLeftHeight(root);
        int rightHeight = getRightHeight(root);

        // If the left height matches the right height, this is a perfect binary tree
        if (leftHeight == rightHeight) {
            return (1 << leftHeight) - 1;
        }

        // Otherwise, recursively count left and right subtrees
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private static int getLeftHeight(TreeNode node) {
        int height = 0;
        while (node != null) {
            height++;
            node = node.left;
        }
        return height;
    }

    private static int getRightHeight(TreeNode node) {
        int height = 0;
        while (node != null) {
            height++;
            node = node.right;
        }
        return height;
    }

    /**
     * High-performance custom scanner to parse large inputs efficiently.
     */
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[65536];
        private int head = 0;
        private int tail = 0;

        public FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (head >= tail) {
                head = 0;
                tail = in.read(buffer, 0, buffer.length);
                if (tail <= 0) return -1;
            }
            return buffer[head++];
        }

        public String nextToken() throws IOException {
            int c = read();
            while (c <= ' ' && c != -1) {
                c = read();
            }
            if (c == -1) return null;
            StringBuilder sb = new StringBuilder();
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        try {
            String rootToken = scanner.nextToken();
            if (rootToken == null || rootToken.equalsIgnoreCase("null") || rootToken.equalsIgnoreCase("N")) {
                out.println(0);
                out.flush();
                return;
            }

            TreeNode root = new TreeNode(Integer.parseInt(rootToken));
            ArrayDeque<TreeNode> treeQueue = new ArrayDeque<>();
            treeQueue.add(root);

            while (!treeQueue.isEmpty()) {
                TreeNode current = treeQueue.poll();

                String leftToken = scanner.nextToken();
                if (leftToken == null) break;
                if (!leftToken.equalsIgnoreCase("null") && !leftToken.equalsIgnoreCase("N")) {
                    current.left = new TreeNode(Integer.parseInt(leftToken));
                    treeQueue.add(current.left);
                }

                String rightToken = scanner.nextToken();
                if (rightToken == null) break;
                if (!rightToken.equalsIgnoreCase("null") && !rightToken.equalsIgnoreCase("N")) {
                    current.right = new TreeNode(Integer.parseInt(rightToken));
                    treeQueue.add(current.right);
                }
            }

            int ans = countNodes(root);
            out.println(ans);

        } catch (Exception e) {
            out.println(0);
        } finally {
            out.flush();
        }
    }
}
