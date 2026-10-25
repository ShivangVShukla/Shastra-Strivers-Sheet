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
     * Returns all root-to-leaf paths in the binary tree in left-to-right order.
     * Uses an iterative stack-based DFS traversal with backtracking to ensure
     * 100% stack-safety even on deep degenerately skewed trees.
     */
    public static List<List<Integer>> allRootToLeaf(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        List<Integer> currPath = new ArrayList<>();
        TreeNode curr = root;
        TreeNode lastVisited = null;

        while (curr != null || !stack.isEmpty()) {
            if (curr != null) {
                stack.push(curr);
                currPath.add(curr.val);
                curr = curr.left;
            } else {
                TreeNode peek = stack.peek();
                if (peek.right != null && lastVisited != peek.right) {
                    curr = peek.right;
                } else {
                    // Check if peek is a leaf node
                    if (peek.left == null && peek.right == null) {
                        result.add(new ArrayList<>(currPath));
                    }
                    lastVisited = stack.pop();
                    currPath.remove(currPath.size() - 1);
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

            List<List<Integer>> ans = allRootToLeaf(root);

            if (ans.isEmpty()) {
                out.println("[]");
            } else {
                out.print("[ ");
                for (int i = 0; i < ans.size(); i++) {
                    List<Integer> path = ans.get(i);
                    out.print("[");
                    for (int j = 0; j < path.size(); j++) {
                        out.print(path.get(j));
                        if (j + 1 < path.size()) {
                            out.print(", ");
                        }
                    }
                    out.print("]");
                    if (i + 1 < ans.size()) {
                        out.print(" , ");
                    }
                }
                out.println(" ]");
            }

        } catch (Exception e) {
            out.println("[]");
        } finally {
            out.flush();
        }
    }
}
