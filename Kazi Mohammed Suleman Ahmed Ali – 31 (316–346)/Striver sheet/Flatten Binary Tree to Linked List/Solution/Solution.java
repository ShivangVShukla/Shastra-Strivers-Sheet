import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class Solution {

    /**
     * Flattens the binary tree into a linked list in-place
     * following the preorder traversal sequence.
     * Optimal Morris-style traversal: O(N) time and O(1) auxiliary space.
     */
    public void flatten(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                // Find the rightmost node of the left subtree
                TreeNode pred = curr.left;
                while (pred.right != null) {
                    pred = pred.right;
                }

                // Splice current node's right subtree to predecessor's right
                pred.right = curr.right;

                // Move left subtree to right
                curr.right = curr.left;
                curr.left = null;
            }
            // Move to the next node in the preorder chain
            curr = curr.right;
        }
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
        if (trimmed.isEmpty()) {
            return null;
        }

        String[] parts = trimmed.split("[,\\s]+");
        List<String> tokens = new ArrayList<>();
        for (String p : parts) {
            String s = p.trim();
            if (!s.isEmpty()) {
                tokens.add(s);
            }
        }

        if (tokens.isEmpty() || tokens.get(0).equals("null")) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(tokens.get(0)));
        Queue<TreeNode> queue = new ArrayDeque<>();
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
     * Serializes binary tree to level-order format without trailing nulls.
     */
    public static String serialize(TreeNode root) {
        if (root == null) return "[]";

        List<String> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                list.add("null");
            } else {
                list.add(String.valueOf(node.val));
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        // Trim trailing "null"s
        int lastNonNull = list.size() - 1;
        while (lastNonNull >= 0 && list.get(lastNonNull).equals("null")) {
            lastNonNull--;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i <= lastNonNull; i++) {
            if (i > 0) sb.append(",");
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
        solution.flatten(root);

        System.out.println(serialize(root));
    }
}
