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
     * Constructs a binary tree from preorder and inorder traversals.
     * Iterative approach using an explicit stack: O(N) time and O(N) heap space.
     * Guaranteed 100% stack-safe for deep/skewed trees up to N = 10,000.
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        int inIndex = 0;
        for (int i = 1; i < preorder.length; i++) {
            TreeNode node = stack.peek();
            if (node.val != inorder[inIndex]) {
                // Next node in preorder is the left child
                node.left = new TreeNode(preorder[i]);
                stack.push(node.left);
            } else {
                // Pop all nodes that have been fully visited in inorder
                while (!stack.isEmpty() && stack.peek().val == inorder[inIndex]) {
                    node = stack.pop();
                    inIndex++;
                }
                // The next node in preorder is the right child of the last popped node
                node.right = new TreeNode(preorder[i]);
                stack.push(node.right);
            }
        }

        return root;
    }

    /**
     * Parses an integer array from a string.
     * Supports formats: "[1, 2, 3]", "preorder = [1, 2, 3]", or space-separated "1 2 3".
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

        int[] preorder = null;
        int[] inorder = null;

        // Check if labeled
        for (String line : lines) {
            String lower = line.toLowerCase();
            if (lower.startsWith("preorder")) {
                preorder = parseArray(line);
            } else if (lower.startsWith("inorder")) {
                inorder = parseArray(line);
            }
        }

        // If not labeled, line 0 is preorder, line 1 is inorder
        if (preorder == null && lines.size() >= 1) {
            preorder = parseArray(lines.get(0));
        }
        if (inorder == null && lines.size() >= 2) {
            inorder = parseArray(lines.get(1));
        }

        Solution solution = new Solution();
        TreeNode root = solution.buildTree(preorder, inorder);

        System.out.println(serialize(root));
    }
}
