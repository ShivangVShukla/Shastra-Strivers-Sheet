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
     * Encodes a tree to a single string using BFS level-order traversal.
     * Guaranteed O(N) time and 100% stack-safe for deep/skewed trees up to N = 10,000.
     */
    public String serialize(TreeNode root) {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("#,");
            } else {
                sb.append(node.val).append(",");
                queue.add(node.left);
                queue.add(node.right);
            }
        }

        return sb.toString();
    }

    /**
     * Decodes encoded data to binary tree.
     * Guaranteed O(N) time and 100% stack-safe.
     */
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] tokens = data.split(",");
        if (tokens.length == 0 || tokens[0].equals("#")) return null;

        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;
        while (!queue.isEmpty() && i < tokens.length) {
            TreeNode parent = queue.poll();

            // Left child
            if (i < tokens.length) {
                if (!tokens[i].equals("#")) {
                    parent.left = new TreeNode(Integer.parseInt(tokens[i]));
                    queue.add(parent.left);
                }
                i++;
            }

            // Right child
            if (i < tokens.length) {
                if (!tokens[i].equals("#")) {
                    parent.right = new TreeNode(Integer.parseInt(tokens[i]));
                    queue.add(parent.right);
                }
                i++;
            }
        }

        return root;
    }

    /**
     * Helper to parse initial input into a binary tree.
     */
    public static TreeNode parseInputTree(String input) {
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
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);

        int idx = 1;
        while (!q.isEmpty() && idx < tokens.size()) {
            TreeNode parent = q.poll();

            // Left child
            if (idx < tokens.size()) {
                String t = tokens.get(idx++);
                if (!t.equals("null") && !t.equalsIgnoreCase("none")) {
                    parent.left = new TreeNode(Integer.parseInt(t));
                    q.add(parent.left);
                }
            }

            // Right child
            if (idx < tokens.size()) {
                String t = tokens.get(idx++);
                if (!t.equals("null") && !t.equalsIgnoreCase("none")) {
                    parent.right = new TreeNode(Integer.parseInt(t));
                    q.add(parent.right);
                }
            }
        }

        return root;
    }

    /**
     * Serializes binary tree to standard level-order format for test output.
     */
    public static String formatLevelOrder(TreeNode root) {
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
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append(" ");
        }

        String input = sb.toString().trim();
        TreeNode root = parseInputTree(input);

        Solution codec = new Solution();
        String serialized = codec.serialize(root);
        TreeNode reconstructed = codec.deserialize(serialized);

        System.out.println(formatLevelOrder(reconstructed));
    }
}
