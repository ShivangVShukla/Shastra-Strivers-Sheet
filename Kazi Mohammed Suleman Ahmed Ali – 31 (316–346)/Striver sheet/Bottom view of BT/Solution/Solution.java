import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class Solution {

    static class QueueNode {
        TreeNode node;
        int hd;

        QueueNode(TreeNode node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }

    /**
     * Computes the bottom view of a binary tree.
     * Nodes are ordered from leftmost horizontal distance to rightmost horizontal distance.
     * If multiple nodes share the same horizontal distance, the node at the deepest level
     * (or the one appearing later in level-order traversal if same depth) is selected.
     */
    public static List<Integer> bottomView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Map<Integer, Integer> hdToVal = new HashMap<>();
        ArrayDeque<QueueNode> queue = new ArrayDeque<>();
        queue.add(new QueueNode(root, 0));

        int minHd = 0;
        int maxHd = 0;

        while (!queue.isEmpty()) {
            QueueNode curr = queue.poll();
            TreeNode node = curr.node;
            int hd = curr.hd;

            // In BFS, a node visited later is at greater depth or further right at the same depth,
            // so unconditionally updating the entry achieves the exact bottom view.
            hdToVal.put(hd, node.val);

            if (hd < minHd) minHd = hd;
            if (hd > maxHd) maxHd = hd;

            if (node.left != null) {
                queue.add(new QueueNode(node.left, hd - 1));
            }
            if (node.right != null) {
                queue.add(new QueueNode(node.right, hd + 1));
            }
        }

        for (int hd = minHd; hd <= maxHd; hd++) {
            if (hdToVal.containsKey(hd)) {
                result.add(hdToVal.get(hd));
            }
        }

        return result;
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
                out.println("[]");
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

            List<Integer> ans = bottomView(root);

            out.print("[");
            for (int i = 0; i < ans.size(); i++) {
                out.print(ans.get(i));
                if (i + 1 < ans.size()) {
                    out.print(", ");
                }
            }
            out.println("]");

        } catch (Exception e) {
            out.println("[]");
        } finally {
            out.flush();
        }
    }
}
