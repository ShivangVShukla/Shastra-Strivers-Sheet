import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
     * Constructs a binary tree from inorder and postorder traversals.
     * Iterative approach using an explicit stack: O(N) time and O(N) heap space.
     * Completely stack-safe for deep/skewed trees up to N = 3,000.
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || inorder.length == 0 || postorder.length == 0) {
            return null;
        }

        int n = postorder.length;
        TreeNode root = new TreeNode(postorder[n - 1]);
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        int inIndex = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            TreeNode node = stack.peek();
            if (node.val != inorder[inIndex]) {
                // Next node in reverse postorder is the right child
                node.right = new TreeNode(postorder[i]);
                stack.push(node.right);
            } else {
                // Pop all nodes that have been fully visited in reverse inorder
                while (!stack.isEmpty() && stack.peek().val == inorder[inIndex]) {
                    node = stack.pop();
                    inIndex--;
                }
                // The next node in reverse postorder is the left child of the last popped node
                node.left = new TreeNode(postorder[i]);
                stack.push(node.left);
            }
        }

        return root;
    }

    /**
     * Parses an integer array from a string.
     * Supports formats: "[1, 2, 3]", "inorder = [1, 2, 3]", or space-separated "1 2 3".
     */
    public static int[] parseArray(String line) {
        if (line == null) return new int[0];
        String trimmed = line.trim();
        int eqIdx = trimmed.indexOf('=');
        if (eqIdx != -1) {
            trimmed = trimmed.substring(eqIdx + 1).trim();
        }
        if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
            trimmed = trimmed.substring(1, trimmed.length() - 1).trim();
        }
        if (trimmed.isEmpty()) {
            return new int[0];
        }

        String[] parts = trimmed.split("[,\\s]+");
        List<Integer> list = new ArrayList<>();
        for (String p : parts) {
            String s = p.trim();
            if (!s.isEmpty()) {
                list.add(Integer.parseInt(s));
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
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

        int[] inorder = null;
        int[] postorder = null;

        // Check if labeled
        for (String line : lines) {
            String lower = line.toLowerCase();
            if (lower.startsWith("inorder")) {
                inorder = parseArray(line);
            } else if (lower.startsWith("postorder")) {
                postorder = parseArray(line);
            }
        }

        // If not labeled, line 0 is inorder, line 1 is postorder
        if (inorder == null && lines.size() >= 1) {
            inorder = parseArray(lines.get(0));
        }
        if (postorder == null && lines.size() >= 2) {
            postorder = parseArray(lines.get(1));
        }

        Solution solution = new Solution();
        TreeNode root = solution.buildTree(inorder, postorder);

        System.out.println(serialize(root));
    }
}
