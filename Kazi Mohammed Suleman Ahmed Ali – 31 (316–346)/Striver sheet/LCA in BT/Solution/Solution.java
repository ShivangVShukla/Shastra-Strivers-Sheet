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
     * Finds the Lowest Common Ancestor (LCA) of nodes p and q in the binary tree.
     * Implemented using an iterative parent-pointer BFS to guarantee 100% stack-safety
     * and O(N) runtime even for trees of depth up to 10^5.
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        Map<TreeNode, TreeNode> parent = new HashMap<>();
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        parent.put(root, null);
        queue.add(root);

        // BFS until both p and q are discovered
        while (!queue.isEmpty() && (!parent.containsKey(p) || !parent.containsKey(q))) {
            TreeNode curr = queue.poll();

            if (curr.left != null) {
                parent.put(curr.left, curr);
                queue.add(curr.left);
            }
            if (curr.right != null) {
                parent.put(curr.right, curr);
                queue.add(curr.right);
            }
        }

        // Build ancestor set for p
        Set<TreeNode> ancestors = new HashSet<>();
        TreeNode curr = p;
        while (curr != null) {
            ancestors.add(curr);
            curr = parent.get(curr);
        }

        // Walk up from q until finding the first common ancestor
        curr = q;
        while (curr != null && !ancestors.contains(curr)) {
            curr = parent.get(curr);
        }

        return curr;
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
                out.println("null");
                out.flush();
                return;
            }

            String treeStr = "";
            int pVal = 0, qVal = 0;

            if (lines.size() >= 3) {
                String[] tokens0 = lines.get(0).split("\\s+");
                String[] tokens1 = lines.get(1).split("\\s+");
                if (tokens0.length == 1 && tokens1.length == 1) {
                    // Line 1 is p, Line 2 is q, Line 3 is tree
                    pVal = Integer.parseInt(tokens0[0]);
                    qVal = Integer.parseInt(tokens1[0]);
                    treeStr = lines.get(2);
                } else {
                    // Line 1 is tree, Line 2 is p, Line 3 is q
                    treeStr = lines.get(0);
                    pVal = Integer.parseInt(tokens1[0]);
                    qVal = Integer.parseInt(lines.get(2).split("\\s+")[0]);
                }
            } else if (lines.size() == 2) {
                String[] tokens0 = lines.get(0).split("\\s+");
                String[] tokens1 = lines.get(1).split("\\s+");
                if (tokens0.length == 2) {
                    pVal = Integer.parseInt(tokens0[0]);
                    qVal = Integer.parseInt(tokens0[1]);
                    treeStr = lines.get(1);
                } else if (tokens1.length == 2) {
                    treeStr = lines.get(0);
                    pVal = Integer.parseInt(tokens1[0]);
                    qVal = Integer.parseInt(tokens1[1]);
                } else {
                    treeStr = lines.get(0);
                    pVal = Integer.parseInt(tokens1[0]);
                }
            } else {
                // Single line
                String[] allTokens = lines.get(0).split("\\s+");
                pVal = Integer.parseInt(allTokens[allTokens.length - 2]);
                qVal = Integer.parseInt(allTokens[allTokens.length - 1]);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < allTokens.length - 2; i++) {
                    sb.append(allTokens[i]).append(" ");
                }
                treeStr = sb.toString().trim();
            }

            // Deserialize tree
            String[] treeTokens = treeStr.split("\\s+");
            if (treeTokens.length == 0 || treeTokens[0].equals("null")) {
                out.println("null");
                out.flush();
                return;
            }

            Map<Integer, TreeNode> nodeMap = new HashMap<>();
            TreeNode root = new TreeNode(Integer.parseInt(treeTokens[0]));
            nodeMap.put(root.val, root);

            ArrayDeque<TreeNode> queue = new ArrayDeque<>();
            queue.add(root);
            int idx = 1;

            while (!queue.isEmpty() && idx < treeTokens.length) {
                TreeNode current = queue.poll();

                if (idx < treeTokens.length) {
                    if (!treeTokens[idx].equals("null")) {
                        current.left = new TreeNode(Integer.parseInt(treeTokens[idx]));
                        nodeMap.put(current.left.val, current.left);
                        queue.add(current.left);
                    }
                    idx++;
                }

                if (idx < treeTokens.length) {
                    if (!treeTokens[idx].equals("null")) {
                        current.right = new TreeNode(Integer.parseInt(treeTokens[idx]));
                        nodeMap.put(current.right.val, current.right);
                        queue.add(current.right);
                    }
                    idx++;
                }
            }

            TreeNode p = nodeMap.get(pVal);
            TreeNode q = nodeMap.get(qVal);

            TreeNode lca = lowestCommonAncestor(root, p, q);
            out.println(lca != null ? lca.val : "null");

        } catch (Exception e) {
            out.println("null");
        } finally {
            out.flush();
        }
    }
}
