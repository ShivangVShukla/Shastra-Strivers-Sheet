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
     * Finds the minimum and maximum values in a Binary Search Tree (BST).
     * Minimum is located at the leftmost node.
     * Maximum is located at the rightmost node.
     * Time Complexity: O(H) where H is the height of the BST.
     * Auxiliary Space Complexity: O(1) iterative, 100% stack-safe.
     */
    public int[] findMinMax(TreeNode root) {
        if (root == null) return new int[0];

        // Walk to the leftmost node for minimum
        TreeNode curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        int minVal = curr.val;

        // Walk to the rightmost node for maximum
        curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        int maxVal = curr.val;

        return new int[]{minVal, maxVal};
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
        int[] result = solution.findMinMax(root);

        if (result.length == 0) {
            System.out.println("[]");
        } else {
            System.out.println("[" + result[0] + ", " + result[1] + "]");
        }
    }
}
