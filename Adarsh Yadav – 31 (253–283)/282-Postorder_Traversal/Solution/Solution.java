import java.io.*;
import java.util.*;

public class Solution {
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static TreeNode buildTree(String line) {
        line = line.trim();
        if (line.startsWith("[") && line.endsWith("]")) {
            line = line.substring(1, line.length() - 1).trim();
        }
        if (line.isEmpty()) return null;
        String[] tokens = line.split(",");
        String first = tokens[0].trim();
        if (first.equalsIgnoreCase("null")) return null;

        TreeNode root = new TreeNode(Integer.parseInt(first));
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty() && i < tokens.length) {
            TreeNode node = queue.poll();
            String left = tokens[i++].trim();
            if (!left.equalsIgnoreCase("null") && !left.isEmpty()) {
                node.left = new TreeNode(Integer.parseInt(left));
                queue.offer(node.left);
            }
            if (i < tokens.length) {
                String right = tokens[i++].trim();
                if (!right.equalsIgnoreCase("null") && !right.isEmpty()) {
                    node.right = new TreeNode(Integer.parseInt(right));
                    queue.offer(node.right);
                }
            }
        }
        return root;
    }

    static void postorder(TreeNode node, List<Integer> result) {
        if (node == null) return;
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node.val);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            TreeNode root = buildTree(line);
            List<Integer> result = new ArrayList<>();
            postorder(root, result);
            System.out.println(result);
        }
    }
}
