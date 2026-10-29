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
     * Finds the Floor and Ceil values for a given key in a BST.
     * Floor: Maximum value in BST that is <= key (or -1 if none).
     * Ceil: Minimum value in BST that is >= key (or -1 if none).
     * Single-pass iterative traversal: O(H) time and O(1) auxiliary space.
     * 100% stack-safe for deep/skewed BSTs up to N = 5,000.
     */
    public List<Integer> floorCeilOfBST(TreeNode root, int key) {
        int floor = -1;
        int ceil = -1;

        TreeNode curr = root;
        while (curr != null) {
            if (curr.val == key) {
                floor = curr.val;
                ceil = curr.val;
                break;
            }

            if (curr.val < key) {
                floor = curr.val;
                curr = curr.right;
            } else {
                ceil = curr.val;
                curr = curr.left;
            }
        }

        List<Integer> result = new ArrayList<>(2);
        result.add(floor);
        result.add(ceil);
        return result;
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
        List<String> lines = new ArrayList<>();
        String l;
        while ((l = br.readLine()) != null) {
            String trimmed = l.trim();
            if (!trimmed.isEmpty()) {
                lines.add(trimmed);
            }
        }

        if (lines.isEmpty()) {
            System.out.println("[-1, -1]");
            return;
        }

        String treeLine = null;
        Integer key = null;

        for (String line : lines) {
            String lower = line.toLowerCase();
            if (lower.startsWith("key")) {
                int eqIdx = line.indexOf('=');
                if (eqIdx != -1) {
                    key = Integer.parseInt(line.substring(eqIdx + 1).trim());
                } else {
                    String[] parts = line.split("\\s+");
                    key = Integer.parseInt(parts[parts.length - 1]);
                }
            } else if (lower.startsWith("root")) {
                treeLine = line;
            }
        }

        if (treeLine == null && key == null) {
            if (lines.size() >= 1) treeLine = lines.get(0);
            if (lines.size() >= 2) key = Integer.parseInt(lines.get(1).trim());
        } else if (treeLine == null) {
            for (String line : lines) {
                if (!line.toLowerCase().startsWith("key")) {
                    treeLine = line;
                    break;
                }
            }
        } else if (key == null) {
            for (String line : lines) {
                if (!line.toLowerCase().startsWith("root")) {
                    key = Integer.parseInt(line.trim());
                    break;
                }
            }
        }

        TreeNode root = deserialize(treeLine);
        int searchKey = (key != null) ? key : 0;

        Solution solution = new Solution();
        List<Integer> result = solution.floorCeilOfBST(root, searchKey);

        System.out.println("[" + result.get(0) + ", " + result.get(1) + "]");
    }
}
