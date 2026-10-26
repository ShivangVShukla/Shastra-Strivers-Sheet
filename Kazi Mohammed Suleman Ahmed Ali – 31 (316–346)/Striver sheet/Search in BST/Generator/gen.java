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

    // Iterative search in BST
    public static Node searchBST(Node root, int val) {
        Node curr = root;
        while (curr != null && curr.val != val) {
            if (val < curr.val) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return curr;
    }

    // Level-order serialization of a tree
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

    // Generate N unique values in [1, maxVal]
    static int[] generateUniqueValues(int n, int maxVal, Random rnd) {
        Set<Integer> set = new HashSet<>();
        while (set.size() < n) {
            int v = 1 + rnd.nextInt(maxVal);
            set.add(v);
        }
        int[] arr = new int[n];
        int idx = 0;
        for (int v : set) {
            arr[idx++] = v;
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
        // descending values
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
        // ascending values
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

    static class TestCase {
        Node root;
        int val;
        TestCase(Node root, int val) {
            this.root = root;
            this.val = val;
        }
    }

    public static void main(String[] args) throws Exception {
        File baseDir = new File("Testcases");
        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        Random rnd = new Random(1337);
        List<TestCase> testcases = new ArrayList<>();

        // 0..3: Sample Cases from TUF
        testcases.add(new TestCase(fromTokens(new String[]{"4", "2", "7", "1", "3"}), 2));
        testcases.add(new TestCase(fromTokens(new String[]{"4", "2", "7", "1", "3"}), 5));
        testcases.add(new TestCase(fromTokens(new String[]{"10", "2", "12", "1", "4", "null", "null", "null", "null", "3"}), 2));
        testcases.add(new TestCase(fromTokens(new String[]{"6", "4", "7", "null", "null", "null", "10"}), 4));

        // 4..11: Boundary & small trees
        testcases.add(new TestCase(new Node(1), 1)); // single node match
        testcases.add(new TestCase(new Node(1), 2)); // single node no match
        testcases.add(new TestCase(new Node(10000000), 10000000)); // max boundary match
        testcases.add(new TestCase(new Node(10000000), 9999999));  // max boundary no match
        testcases.add(new TestCase(fromTokens(new String[]{"2", "1"}), 1)); // 2 nodes left child
        testcases.add(new TestCase(fromTokens(new String[]{"2", "1"}), 2)); // 2 nodes root
        testcases.add(new TestCase(fromTokens(new String[]{"1", "null", "2"}), 2)); // 2 nodes right child
        testcases.add(new TestCase(fromTokens(new String[]{"1", "null", "2"}), 3)); // 2 nodes no match

        // 12..25: Small balanced/random BSTs (3 to 15 nodes)
        for (int i = 12; i < 26; i++) {
            int n = 3 + (i % 12);
            int[] vals = generateUniqueValues(n, 1000, rnd);
            Node root = buildBalanced(vals, 0, n - 1);
            int target;
            if (i % 3 == 0) {
                target = vals[rnd.nextInt(n)]; // guaranteed match
            } else if (i % 3 == 1) {
                target = vals[0]; // min element
            } else {
                target = 2000; // not found
            }
            testcases.add(new TestCase(root, target));
        }

        // 26..39: Left-skewed BSTs of various sizes
        int[] leftSizes = {5, 10, 25, 50, 100, 200, 400, 800, 1500, 2500, 3500, 4500, 5000, 5000};
        for (int i = 0; i < leftSizes.length; i++) {
            int n = leftSizes[i];
            int[] vals = generateUniqueValues(n, 1000000, rnd);
            Node root = buildLeftSkewed(vals);
            int target = (i % 2 == 0) ? vals[rnd.nextInt(n)] : 9999999;
            testcases.add(new TestCase(root, target));
        }

        // 40..53: Right-skewed BSTs of various sizes
        int[] rightSizes = {5, 10, 25, 50, 100, 200, 400, 800, 1500, 2500, 3500, 4500, 5000, 5000};
        for (int i = 0; i < rightSizes.length; i++) {
            int n = rightSizes[i];
            int[] vals = generateUniqueValues(n, 1000000, rnd);
            Node root = buildRightSkewed(vals);
            int target = (i % 2 == 0) ? vals[rnd.nextInt(n)] : 9999999;
            testcases.add(new TestCase(root, target));
        }

        // 54..74: Balanced BSTs of various sizes (100 to 4000 nodes)
        for (int i = 54; i < 75; i++) {
            int n = 100 + rnd.nextInt(3900);
            int[] vals = generateUniqueValues(n, 10000000, rnd);
            Node root = buildBalanced(vals, 0, n - 1);
            int target = (i % 2 == 0) ? vals[rnd.nextInt(n)] : (vals[vals.length - 1] + 1);
            testcases.add(new TestCase(root, target));
        }

        // 75..94: Random BSTs (100 to 3000 nodes)
        for (int i = 75; i < 95; i++) {
            int n = 100 + rnd.nextInt(2900);
            int[] vals = generateUniqueValues(n, 10000000, rnd);
            Node root = buildRandomBST(vals, rnd);
            int target = (i % 2 == 0) ? vals[rnd.nextInt(n)] : 10000000;
            testcases.add(new TestCase(root, target));
        }

        // 95..99: Maximum scale boundary stress tests (N = 5000 nodes)
        // 95: Balanced BST (5000), target found
        int[] bVals = generateUniqueValues(5000, 10000000, rnd);
        testcases.add(new TestCase(buildBalanced(bVals, 0, 4999), bVals[2500]));

        // 96: Balanced BST (5000), target not found
        testcases.add(new TestCase(buildBalanced(bVals, 0, 4999), bVals[bVals.length - 1] + 1));

        // 97: Left-skewed BST (5000), target near bottom
        int[] lVals = generateUniqueValues(5000, 10000000, rnd);
        testcases.add(new TestCase(buildLeftSkewed(lVals), lVals[5]));

        // 98: Right-skewed BST (5000), target near bottom
        int[] rVals = generateUniqueValues(5000, 10000000, rnd);
        testcases.add(new TestCase(buildRightSkewed(rVals), rVals[4995]));

        // 99: Large random BST (5000), target at root
        int[] randVals = generateUniqueValues(5000, 10000000, rnd);
        Node rRoot = buildRandomBST(randVals, rnd);
        testcases.add(new TestCase(rRoot, rRoot.val));

        System.out.println("Total testcases created: " + testcases.size());

        for (int i = 0; i < 100; i++) {
            TestCase tc = testcases.get(i);
            String treeStr = serialize(tc.root);
            Node resultNode = searchBST(tc.root, tc.val);
            String outputStr = serialize(resultNode);

            String inFilename = String.format("input%02d.txt", i);
            String outFilename = String.format("output%02d.txt", i);

            try (PrintWriter pwIn = new PrintWriter(new File(inputDir, inFilename))) {
                pwIn.println("root = " + treeStr);
                pwIn.println("val = " + tc.val);
            }

            try (PrintWriter pwOut = new PrintWriter(new File(outputDir, outFilename))) {
                pwOut.println(outputStr);
            }
        }

        System.out.println("All 100 input and output testcases generated successfully!");
    }
}
