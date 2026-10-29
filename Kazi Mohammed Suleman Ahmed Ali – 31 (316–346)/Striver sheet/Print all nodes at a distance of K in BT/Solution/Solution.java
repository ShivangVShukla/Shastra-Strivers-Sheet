import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class Solution {

    /**
     * Finds and returns all node values at distance k from the target node.
     * Uses parent-tracking graph conversion followed by radial BFS.
     */
    public static List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> result = new ArrayList<>();
        if (root == null || target == null || k < 0) {
            return result;
        }

        // Step 1: Map child nodes to their parents
        Map<TreeNode, TreeNode> parentTrack = new HashMap<>();
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.left != null) {
                parentTrack.put(curr.left, curr);
                queue.add(curr.left);
            }
            if (curr.right != null) {
                parentTrack.put(curr.right, curr);
                queue.add(curr.right);
            }
        }

        // Step 2: Radial BFS starting from target node
        Set<TreeNode> visited = new HashSet<>();
        queue.add(target);
        visited.add(target);
        int currentDistance = 0;

        while (!queue.isEmpty()) {
            if (currentDistance == k) {
                break;
            }

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();

                // 1. Left child
                if (curr.left != null && !visited.contains(curr.left)) {
                    visited.add(curr.left);
                    queue.add(curr.left);
                }

                // 2. Right child
                if (curr.right != null && !visited.contains(curr.right)) {
                    visited.add(curr.right);
                    queue.add(curr.right);
                }

                // 3. Parent node
                TreeNode parent = parentTrack.get(curr);
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent);
                    queue.add(parent);
                }
            }
            currentDistance++;
        }

        // Step 3: Collect all nodes remaining in the queue
        while (!queue.isEmpty()) {
            result.add(queue.poll().val);
        }

        Collections.sort(result);
        return result;
    }

    public static void main(String[] args) {
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }

            if (lines.isEmpty()) {
                out.println("[]");
                out.flush();
                return;
            }

            String rootStr = "";
            int targetVal = 0;
            int k = 0;

            if (lines.size() >= 3) {
                String[] tokens0 = lines.get(0).split("\\s+");
                String[] tokens1 = lines.get(1).split("\\s+");

                if (tokens1.length > 1 || tokens1[0].equalsIgnoreCase("N") || tokens1[0].equalsIgnoreCase("null")) {
                    // Line 0 is k, Line 1 is root, Line 2 is target (TUF sample case order)
                    k = Integer.parseInt(tokens0[0]);
                    rootStr = lines.get(1);
                    targetVal = Integer.parseInt(lines.get(2).split("\\s+")[0]);
                } else {
                    // Line 0 is root, Line 1 is target, Line 2 is k
                    rootStr = lines.get(0);
                    targetVal = Integer.parseInt(tokens1[0]);
                    k = Integer.parseInt(lines.get(2).split("\\s+")[0]);
                }
            } else if (lines.size() == 2) {
                rootStr = lines.get(0);
                String[] tk = lines.get(1).split("\\s+");
                targetVal = Integer.parseInt(tk[0]);
                k = Integer.parseInt(tk[1]);
            } else {
                String[] allTokens = lines.get(0).split("\\s+");
                targetVal = Integer.parseInt(allTokens[allTokens.length - 2]);
                k = Integer.parseInt(allTokens[allTokens.length - 1]);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < allTokens.length - 2; i++) {
                    sb.append(allTokens[i]).append(" ");
                }
                rootStr = sb.toString().trim();
            }

            // Deserialize tree
            String[] treeTokens = rootStr.split("\\s+");
            if (treeTokens.length == 0 || treeTokens[0].equalsIgnoreCase("null") || treeTokens[0].equalsIgnoreCase("N")) {
                out.println("[]");
                out.flush();
                return;
            }

            Map<Integer, TreeNode> nodeMap = new HashMap<>();
            TreeNode root = new TreeNode(Integer.parseInt(treeTokens[0]));
            nodeMap.put(root.val, root);

            ArrayDeque<TreeNode> treeQueue = new ArrayDeque<>();
            treeQueue.add(root);
            int idx = 1;

            while (!treeQueue.isEmpty() && idx < treeTokens.length) {
                TreeNode current = treeQueue.poll();

                // Left child
                if (idx < treeTokens.length) {
                    String token = treeTokens[idx++];
                    if (!token.equalsIgnoreCase("null") && !token.equalsIgnoreCase("N")) {
                        current.left = new TreeNode(Integer.parseInt(token));
                        nodeMap.put(current.left.val, current.left);
                        treeQueue.add(current.left);
                    }
                }

                // Right child
                if (idx < treeTokens.length) {
                    String token = treeTokens[idx++];
                    if (!token.equalsIgnoreCase("null") && !token.equalsIgnoreCase("N")) {
                        current.right = new TreeNode(Integer.parseInt(token));
                        nodeMap.put(current.right.val, current.right);
                        treeQueue.add(current.right);
                    }
                }
            }

            TreeNode targetNode = nodeMap.get(targetVal);
            List<Integer> ans = distanceK(root, targetNode, k);

            out.print("[");
            for (int i = 0; i < ans.size(); i++) {
                out.print(ans.get(i));
                if (i + 1 < ans.size()) {
                    out.print(", ");
                }
            }
            out.println("]");

        } catch (Exception e) {
            out.println("[]");
        } finally {
            out.flush();
        }
    }
}
