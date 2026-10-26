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

    static class Pair {
        TreeNode node;
        long idx;

        Pair(TreeNode node, long idx) {
            this.node = node;
            this.idx = idx;
        }
    }

    /**
     * Calculates the maximum width of a binary tree across all levels.
     * Uses level-wise index normalization to prevent 64-bit integer overflow
     * on deeply skewed or unbalanced trees.
     */
    public static int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int maxWidth = 0;
        ArrayDeque<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(root, 0L));

        while (!queue.isEmpty()) {
            int size = queue.size();
            long minIdx = queue.peek().idx;
            long first = 0, last = 0;

            for (int i = 0; i < size; i++) {
                Pair curr = queue.poll();
                long curIdx = curr.idx - minIdx;

                if (i == 0) first = curIdx;
                if (i == size - 1) last = curIdx;

                if (curr.node.left != null) {
                    queue.add(new Pair(curr.node.left, 2 * curIdx + 1));
                }
                if (curr.node.right != null) {
                    queue.add(new Pair(curr.node.right, 2 * curIdx + 2));
                }
            }

            maxWidth = Math.max(maxWidth, (int) (last - first + 1));
        }

        return maxWidth;
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
            if (rootToken == null || rootToken.equals("null")) {
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
                if (!leftToken.equals("null")) {
                    current.left = new TreeNode(Integer.parseInt(leftToken));
                    treeQueue.add(current.left);
                }

                String rightToken = scanner.nextToken();
                if (rightToken == null) break;
                if (!rightToken.equals("null")) {
                    current.right = new TreeNode(Integer.parseInt(rightToken));
                    treeQueue.add(current.right);
                }
            }

            int ans = widthOfBinaryTree(root);
            out.println(ans);

        } catch (Exception e) {
            out.println(0);
        } finally {
            out.flush();
        }
    }
}
