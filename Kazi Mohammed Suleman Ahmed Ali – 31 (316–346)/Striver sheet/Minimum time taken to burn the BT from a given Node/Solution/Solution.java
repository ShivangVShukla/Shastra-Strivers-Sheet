import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
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
     * Finds the minimum time required to burn the entire binary tree from the target node.
     * Uses parent-pointer mapping followed by radial BFS simulation.
     */
    public static int timeToBurnTree(TreeNode root, int start) {
        if (root == null) {
            return 0;
        }

        // Step 1: Map parents and locate target node
        Map<TreeNode, TreeNode> parentTrack = new HashMap<>();
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        TreeNode target = null;

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            if (curr.val == start) {
                target = curr;
            }
            if (curr.left != null) {
                parentTrack.put(curr.left, curr);
                queue.add(curr.left);
            }
            if (curr.right != null) {
                parentTrack.put(curr.right, curr);
                queue.add(curr.right);
            }
        }

        if (target == null) {
            return 0;
        }

        // Step 2: Radial BFS fire spread simulation
        Set<TreeNode> visited = new HashSet<>();
        queue.add(target);
        visited.add(target);
        int time = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean burnedAny = false;

            for (int i = 0; i < size; i++) {
                TreeNode curr = queue.poll();

                // 1. Left child
                if (curr.left != null && !visited.contains(curr.left)) {
                    visited.add(curr.left);
                    queue.add(curr.left);
                    burnedAny = true;
                }

                // 2. Right child
                if (curr.right != null && !visited.contains(curr.right)) {
                    visited.add(curr.right);
                    queue.add(curr.right);
                    burnedAny = true;
                }

                // 3. Parent node
                TreeNode parent = parentTrack.get(curr);
                if (parent != null && !visited.contains(parent)) {
                    visited.add(parent);
                    queue.add(parent);
                    burnedAny = true;
                }
            }

            if (burnedAny) {
                time++;
            }
        }

        return time;
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
                out.println(0);
                out.flush();
                return;
            }

            String rootStr = "";
            int startVal = 0;

            if (lines.size() >= 2) {
                String[] tokens0 = lines.get(0).split("\\s+");
                String[] tokens1 = lines.get(1).split("\\s+");

                if (tokens0.length == 1 && (tokens1.length > 1 || tokens1[0].equalsIgnoreCase("N") || tokens1[0].equalsIgnoreCase("null"))) {
                    // Line 0 is target, Line 1 is root
                    startVal = Integer.parseInt(tokens0[0]);
                    rootStr = lines.get(1);
                } else {
                    // Line 0 is root, Line 1 is target
                    rootStr = lines.get(0);
                    startVal = Integer.parseInt(tokens1[0]);
                }
            } else {
                // Single line: root tokens followed by target
                String[] allTokens = lines.get(0).split("\\s+");
                startVal = Integer.parseInt(allTokens[allTokens.length - 1]);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < allTokens.length - 1; i++) {
                    sb.append(allTokens[i]).append(" ");
                }
                rootStr = sb.toString().trim();
            }

            // Deserialize tree
            String[] treeTokens = rootStr.split("\\s+");
            if (treeTokens.length == 0 || treeTokens[0].equalsIgnoreCase("null") || treeTokens[0].equalsIgnoreCase("N")) {
                out.println(0);
                out.flush();
                return;
            }

            TreeNode root = new TreeNode(Integer.parseInt(treeTokens[0]));
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
                        treeQueue.add(current.left);
                    }
                }

                // Right child
                if (idx < treeTokens.length) {
                    String token = treeTokens[idx++];
                    if (!token.equalsIgnoreCase("null") && !token.equalsIgnoreCase("N")) {
                        current.right = new TreeNode(Integer.parseInt(token));
                        treeQueue.add(current.right);
                    }
                }
            }

            int ans = timeToBurnTree(root, startVal);
            out.println(ans);

        } catch (Exception e) {
            out.println(0);
        } finally {
            out.flush();
        }
    }
}
