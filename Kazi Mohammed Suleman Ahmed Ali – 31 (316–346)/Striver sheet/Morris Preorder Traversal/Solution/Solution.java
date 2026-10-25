import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class TreeNode {
    int data;
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.data = val;
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class Solution {

    /**
     * Computes the Preorder Traversal of a Binary Tree using Morris Traversal.
     * Time Complexity: O(N)
     * Auxiliary Space Complexity: O(1)
     */
    public List<Integer> preorder(TreeNode root) {
        List<Integer> preorderList = new ArrayList<>();
        TreeNode curr = root;

        while (curr != null) {
            if (curr.left == null) {
                preorderList.add(curr.val);
                curr = curr.right;
            } else {
                // Find the inorder predecessor (rightmost node in left subtree)
                TreeNode prev = curr.left;
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }

                if (prev.right == null) {
                    // Record value upon first visit (Preorder)
                    preorderList.add(curr.val);
                    // Create thread
                    prev.right = curr;
                    curr = curr.left;
                } else {
                    // Thread already exists, cut it and move right
                    prev.right = null;
                    curr = curr.right;
                }
            }
        }

        return preorderList;
    }

    /**
     * Alias method for compatibility.
     */
    public List<Integer> getPreorder(TreeNode root) {
        return preorder(root);
    }

    /**
     * Parses level-order serialized string into a binary tree.
     */
    public static TreeNode deserialize(String input) {
        if (input == null) return null;
        String trimmed = input.trim();
        if (trimmed.startsWith("root")) {
            int eqIdx = trimmed.indexOf('=');
            if (eqIdx != -1) {
                trimmed = trimmed.substring(eqIdx + 1).trim();
            }
        }
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            trimmed = trimmed.substring(1, trimmed.length() - 1).trim();
        }
        if (trimmed.isEmpty()) return null;

        String[] parts = trimmed.split("[,\\s]+");
        List<String> tokens = new ArrayList<>();
        for (String p : parts) {
            String s = p.trim();
            if (!s.isEmpty()) {
                tokens.add(s);
            }
        }

        if (tokens.isEmpty() || tokens.get(0).equals("null")) return null;

        TreeNode root = new TreeNode(Integer.parseInt(tokens.get(0)));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int idx = 1;
        while (!queue.isEmpty() && idx < tokens.size()) {
            TreeNode parent = queue.poll();

            // Left child
            if (idx < tokens.size()) {
                String token = tokens.get(idx++);
                if (!token.equals("null") && !token.equalsIgnoreCase("none")) {
                    parent.left = new TreeNode(Integer.parseInt(token));
                    queue.add(parent.left);
                }
            }

            // Right child
            if (idx < tokens.size()) {
                String token = tokens.get(idx++);
                if (!token.equals("null") && !token.equalsIgnoreCase("none")) {
                    parent.right = new TreeNode(Integer.parseInt(token));
                    queue.add(parent.right);
                }
            }
        }

        return root;
    }

    /**
     * Formats integer list into bracketed representation: [val1, val2, ...]
     */
    public static String formatList(List<Integer> list) {
        if (list == null || list.isEmpty()) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(list.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(" ");
        }

        String input = sb.toString().trim();
        TreeNode root = deserialize(input);

        Solution solution = new Solution();
        List<Integer> result = solution.preorder(root);

        System.out.println(formatList(result));
    }
}
