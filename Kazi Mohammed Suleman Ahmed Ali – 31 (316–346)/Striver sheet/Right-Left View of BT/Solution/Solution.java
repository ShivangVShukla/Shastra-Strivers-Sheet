import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

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
     * Returns the values of the nodes visible when viewed from the right side of the binary tree,
     * arranged from top to bottom.
     * Uses iterative BFS to guarantee O(N) time and complete stack-safety on deep trees.
     */
    public static List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode curr = queue.poll();

                // The last element processed at this level is the rightmost node visible from the right
                if (i == levelSize - 1) {
                    result.add(curr.val);
                }

                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
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

            List<Integer> ans = rightSideView(root);

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
