import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
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

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        TreeNode curr = root;
        while (true) {
            if (val < curr.val) {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                }
                curr = curr.right;
            }
        }

        return root;
    }

    public static TreeNode buildTree(String input) {
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
            if (!p.isEmpty()) {
                tokens.add(p);
            }
        }
        if (tokens.isEmpty() || tokens.get(0).equalsIgnoreCase("null")) {
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(tokens.get(0)));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int idx = 1;

        while (!queue.isEmpty() && idx < tokens.size()) {
            TreeNode current = queue.poll();

            // Left child
            if (idx < tokens.size()) {
                String leftToken = tokens.get(idx++);
                if (!leftToken.equalsIgnoreCase("null")) {
                    current.left = new TreeNode(Integer.parseInt(leftToken));
                    queue.offer(current.left);
                }
            }

            // Right child
            if (idx < tokens.size()) {
                String rightToken = tokens.get(idx++);
                if (!rightToken.equalsIgnoreCase("null")) {
                    current.right = new TreeNode(Integer.parseInt(rightToken));
                    queue.offer(current.right);
                }
            }
        }

        return root;
    }

    public static String serializeTree(TreeNode root) {
        if (root == null) return "[]";

        List<String> values = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                values.add("null");
            } else {
                values.add(String.valueOf(node.val));
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        // Trim trailing "null"s
        int lastNonNull = values.size() - 1;
        while (lastNonNull >= 0 && values.get(lastNonNull).equals("null")) {
            lastNonNull--;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i <= lastNonNull; i++) {
            sb.append(values.get(i));
            if (i < lastNonNull) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line1 = br.readLine();
        if (line1 == null) return;

        String line2 = br.readLine();
        if (line2 == null) {
            line2 = "";
        }

        // Parse val from line2 or line1
        String valStr = line2.trim();
        if (valStr.startsWith("val")) {
            int eqIdx = valStr.indexOf('=');
            if (eqIdx != -1) {
                valStr = valStr.substring(eqIdx + 1).trim();
            }
        }

        // Sometimes input might be in single line or line 2 might be empty
        int val = 0;
        if (!valStr.isEmpty()) {
            val = Integer.parseInt(valStr);
        }

        TreeNode root = buildTree(line1);

        Solution sol = new Solution();
        TreeNode updatedRoot = sol.insertIntoBST(root, val);

        System.out.println(serializeTree(updatedRoot));
    }
}
