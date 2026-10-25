import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class gen {

    static class Node {
        int val;
        Node left, right;
        Node(int val) {
            this.val = val;
        }
    }

    // Iterative find min and max in BST
    public static int[] getMinMax(Node root) {
        if (root == null) return new int[0];
        Node curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        int min = curr.val;

        curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        int max = curr.val;

        return new int[]{min, max};
    }

    // Level-order serialization
    public static String serialize(Node root) {
        if (root == null) return "[]";

        List<String> list = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node == null) {
                list.add("null");
            } else {
                list.add(String.valueOf(node.val));
                queue.add(node.left);
                queue.add(node.right);
            }
        }

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

    // Generate N unique values
    static int[] generateUniqueValues(int n, int minVal, int maxVal, Random rnd) {
        long range = (long) maxVal - minVal + 1;
        int[] arr = new int[n];
        if (range <= n * 2) {
            List<Integer> list = new ArrayList<>();
            for (int i = minVal; i <= maxVal; i++) list.add(i);
            Collections.shuffle(list, rnd);
            for (int i = 0; i < n; i++) arr[i] = list.get(i);
        } else {
            Set<Integer> set = new HashSet<>();
            while (set.size() < n) {
                int v = minVal + (int)(rnd.nextDouble() * range);
                set.add(v);
            }
            int idx = 0;
            for (int v : set) arr[idx++] = v;
        }
        Arrays.sort(arr);
        return arr;
    }

    // Build balanced BST from sorted array
    static Node buildBalanced(int[] arr, int left, int right) {
        if (left > right) return null;
        int mid = (left + right) / 2;
        Node root = new Node(arr[mid]);
        root.left = buildBalanced(arr, left, mid - 1);
        root.right = buildBalanced(arr, mid + 1, right);
        return root;
    }

    // Build left-skewed BST (iterative, 100% stack-safe)
    static Node buildLeftSkewed(int[] sortedArr) {
        int n = sortedArr.length;
        if (n == 0) return null;
        Node root = new Node(sortedArr[n - 1]);
        Node curr = root;
        for (int i = n - 2; i >= 0; i--) {
            curr.left = new Node(sortedArr[i]);
            curr = curr.left;
        }
        return root;
    }

    // Build right-skewed BST (iterative, 100% stack-safe)
    static Node buildRightSkewed(int[] sortedArr) {
        int n = sortedArr.length;
        if (n == 0) return null;
        Node root = new Node(sortedArr[0]);
        Node curr = root;
        for (int i = 1; i < n; i++) {
            curr.right = new Node(sortedArr[i]);
            curr = curr.right;
        }
        return root;
    }

    // Insert into BST iteratively
    static void insertBST(Node root, int val) {
        Node curr = root;
        while (true) {
            if (val < curr.val) {
                if (curr.left == null) {
                    curr.left = new Node(val);
                    break;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new Node(val);
                    break;
                }
                curr = curr.right;
            }
        }
    }

    // Build random BST by inserting shuffled values
    static Node buildRandomBST(int[] sortedArr, Random rnd) {
        int n = sortedArr.length;
        if (n == 0) return null;
        List<Integer> list = new ArrayList<>(n);
        for (int v : sortedArr) list.add(v);
        Collections.shuffle(list, rnd);

        Node root = new Node(list.get(0));
        for (int i = 1; i < n; i++) {
            insertBST(root, list.get(i));
        }
        return root;
    }

    // Build tree directly from level-order tokens
    static Node fromTokens(String[] tokens) {
        if (tokens.length == 0 || tokens[0].equals("null")) return null;
        Node root = new Node(Integer.parseInt(tokens[0]));
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < tokens.length) {
            Node curr = q.poll();
            if (i < tokens.length) {
                if (!tokens[i].equals("null")) {
                    curr.left = new Node(Integer.parseInt(tokens[i]));
                    q.add(curr.left);
                }
                i++;
            }
            if (i < tokens.length) {
                if (!tokens[i].equals("null")) {
                    curr.right = new Node(Integer.parseInt(tokens[i]));
                    q.add(curr.right);
                }
                i++;
            }
        }
        return root;
    }

    public static void main(String[] args) throws Exception {
        File baseDir = new File("Testcases");
        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        Random rnd = new Random(4242);
        List<Node> testcases = new ArrayList<>();

        // 0..3: Sample Cases from TUF
        testcases.add(fromTokens(new String[]{"8", "3", "12", "1", "6", "10", "14"}));
        testcases.add(fromTokens(new String[]{"20", "10", "30", "5", "15", "25", "40", "null", "7"}));
        testcases.add(fromTokens(new String[]{"15", "8", "22", "4", "11", "18", "27", "null", "6"}));
        testcases.add(fromTokens(new String[]{"6", "4", "8", "3", "null", "7", "9"}));

        // 4..12: Boundary & small trees
        testcases.add(new Node(0)); // single node 0
        testcases.add(new Node(-1000000000)); // single node min constraint
        testcases.add(new Node(1000000000));  // single node max constraint
        testcases.add(fromTokens(new String[]{"5", "2"})); // 2 nodes left
        testcases.add(fromTokens(new String[]{"2", "null", "5"})); // 2 nodes right
        testcases.add(fromTokens(new String[]{"0", "-1000000000", "1000000000"})); // extreme boundary values
        testcases.add(fromTokens(new String[]{"10", "5", "null", "1"})); // left-skewed 3
        testcases.add(fromTokens(new String[]{"1", "null", "5", "null", "10"})); // right-skewed 3
        testcases.add(fromTokens(new String[]{"0", "-100", "100", "-200", "null", "null", "200"}));

        // 13..25: Small balanced/random BSTs (4 to 20 nodes)
        for (int i = 13; i < 26; i++) {
            int n = 4 + (i % 17);
            int[] vals = generateUniqueValues(n, -5000, 5000, rnd);
            Node root = (i % 2 == 0) ? buildBalanced(vals, 0, n - 1) : buildRandomBST(vals, rnd);
            testcases.add(root);
        }

        // 26..39: Left-skewed BSTs of various sizes (5 to 10000)
        int[] leftSizes = {5, 10, 20, 50, 100, 250, 500, 1000, 2000, 4000, 6000, 8000, 10000, 15000};
        for (int i = 0; i < leftSizes.length; i++) {
            int n = leftSizes[i];
            int[] vals = generateUniqueValues(n, -1000000, 1000000, rnd);
            testcases.add(buildLeftSkewed(vals));
        }

        // 40..53: Right-skewed BSTs of various sizes (5 to 10000)
        int[] rightSizes = {5, 10, 20, 50, 100, 250, 500, 1000, 2000, 4000, 6000, 8000, 10000, 15000};
        for (int i = 0; i < rightSizes.length; i++) {
            int n = rightSizes[i];
            int[] vals = generateUniqueValues(n, -1000000, 1000000, rnd);
            testcases.add(buildRightSkewed(vals));
        }

        // 54..74: Balanced BSTs of various sizes (31 to 8191 nodes)
        int[] balancedSizes = {31, 63, 100, 127, 200, 255, 350, 511, 750, 1023, 1500, 2047, 3000, 4095, 5000, 6000, 7000, 8191, 10000, 12000, 15000};
        for (int i = 0; i < balancedSizes.length; i++) {
            int n = balancedSizes[i];
            int[] vals = generateUniqueValues(n, -1000000000, 1000000000, rnd);
            testcases.add(buildBalanced(vals, 0, n - 1));
        }

        // 75..94: Medium to large random BSTs (100 to 10000 nodes)
        for (int i = 75; i < 95; i++) {
            int n = 100 + rnd.nextInt(9900);
            int[] vals = generateUniqueValues(n, -500000000, 500000000, rnd);
            testcases.add(buildRandomBST(vals, rnd));
        }

        // 95..99: Maximum scale boundary stress tests
        // 95: Balanced BST (30,000)
        int[] b30k = generateUniqueValues(30000, -1000000000, 1000000000, rnd);
        testcases.add(buildBalanced(b30k, 0, 29999));

        // 96: Left-skewed BST (20,000)
        int[] l20k = generateUniqueValues(20000, -1000000000, 1000000000, rnd);
        testcases.add(buildLeftSkewed(l20k));

        // 97: Right-skewed BST (20,000)
        int[] r20k = generateUniqueValues(20000, -1000000000, 1000000000, rnd);
        testcases.add(buildRightSkewed(r20k));

        // 98: Balanced BST (50,000)
        int[] b50k = generateUniqueValues(50000, -1000000000, 1000000000, rnd);
        testcases.add(buildBalanced(b50k, 0, 49999));

        // 99: Full scale balanced BST (100,000 nodes, exact maximum constraint N = 10^5)
        int[] b100k = generateUniqueValues(100000, -1000000000, 1000000000, rnd);
        testcases.add(buildBalanced(b100k, 0, 99999));

        System.out.println("Total testcases created: " + testcases.size());

        for (int i = 0; i < 100; i++) {
            Node root = testcases.get(i);
            String treeStr = serialize(root);
            int[] minMax = getMinMax(root);
            String outputStr = "[" + minMax[0] + ", " + minMax[1] + "]";

            String inFilename = String.format("input%02d.txt", i);
            String outFilename = String.format("output%02d.txt", i);

            try (PrintWriter pwIn = new PrintWriter(new File(inputDir, inFilename))) {
                pwIn.println(treeStr);
            }

            try (PrintWriter pwOut = new PrintWriter(new File(outputDir, outFilename))) {
                pwOut.println(outputStr);
            }
        }

        System.out.println("All 100 input and output testcases generated successfully!");
    }
}
