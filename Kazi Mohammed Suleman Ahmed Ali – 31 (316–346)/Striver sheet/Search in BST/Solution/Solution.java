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
     * Searches for a node with value 'val' in a Binary Search Tree (BST).
     * Returns the subtree rooted at that node, or null if not found.
     * Iterative implementation: O(H) time and O(1) auxiliary space.
     * 100% stack-safe for deep/skewed BSTs up to N = 5,000.
     */
    public TreeNode searchBST(TreeNode root, int val) {
        TreeNode curr = root;
        while (curr != null && curr.val != val) {
            if (val < curr.val) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return curr;
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
     * Serializes binary tree into level-order bracketed format.
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

        // Trim trailing nulls
        int lastNonNull = list.size() - 1;
        while (lastNonNull >= 0 && list.get(lastNonNull).equals("null")) {
            lastNonNull--;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i <= lastNonNull; i++) {
            if (i > 0) sb.append(", ");
            sb.append(list.get(i));
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        String l;
        while ((l = br.readLine()) != null) {
            String trimmed = l.trim();
            if (!trimmed.isEmpty()) {
                lines.add(trimmed);
            }
        }

        if (lines.isEmpty()) {
            System.out.println("[]");
            return;
        }

        String treeLine = null;
        Integer val = null;

        for (String line : lines) {
            String lower = line.toLowerCase();
            if (lower.startsWith("val")) {
                int eqIdx = line.indexOf('=');
                if (eqIdx != -1) {
                    val = Integer.parseInt(line.substring(eqIdx + 1).trim());
                } else {
                    String[] parts = line.split("\\s+");
                    val = Integer.parseInt(parts[parts.length - 1]);
                }
            } else if (lower.startsWith("root")) {
                treeLine = line;
            }
        }

        if (treeLine == null && val == null) {
            if (lines.size() >= 1) treeLine = lines.get(0);
            if (lines.size() >= 2) val = Integer.parseInt(lines.get(1).trim());
        } else if (treeLine == null) {
            // Find line that is not the val line
            for (String line : lines) {
                if (!line.toLowerCase().startsWith("val")) {
                    treeLine = line;
                    break;
                }
            }
        } else if (val == null) {
            for (String line : lines) {
                if (!line.toLowerCase().startsWith("root")) {
                    val = Integer.parseInt(line.trim());
                    break;
                }
            }
        }

        TreeNode root = deserialize(treeLine);
        int targetVal = (val != null) ? val : 0;

        Solution solution = new Solution();
        TreeNode result = solution.searchBST(root, targetVal);

        System.out.println(serialize(result));
    }
}
