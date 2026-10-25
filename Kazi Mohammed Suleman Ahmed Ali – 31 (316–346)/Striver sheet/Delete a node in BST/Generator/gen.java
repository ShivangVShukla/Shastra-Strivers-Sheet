import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class gen {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    public static TreeNode cloneTree(TreeNode root) {
        if (root == null) return null;
        TreeNode copy = new TreeNode(root.val);
        copy.left = cloneTree(root.left);
        copy.right = cloneTree(root.right);
        return copy;
    }

    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (root.val == key) {
            return helper(root);
        }
        TreeNode curr = root;
        while (curr != null) {
            if (key < curr.val) {
                if (curr.left != null && curr.left.val == key) {
                    curr.left = helper(curr.left);
                    break;
                } else {
                    curr = curr.left;
                }
            } else {
                if (curr.right != null && curr.right.val == key) {
                    curr.right = helper(curr.right);
                    break;
                } else {
                    curr = curr.right;
                }
            }
        }
        return root;
    }

    private static TreeNode helper(TreeNode node) {
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }
        TreeNode rightChild = node.right;
        TreeNode leftmost = findLastLeft(rightChild);
        leftmost.left = node.left;
        return rightChild;
    }

    private static TreeNode findLastLeft(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
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

    public static TreeNode buildTreeFromLevelOrder(String input) {
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
            if (!p.isEmpty()) tokens.add(p);
        }
        if (tokens.isEmpty() || tokens.get(0).equalsIgnoreCase("null")) return null;

        TreeNode root = new TreeNode(Integer.parseInt(tokens.get(0)));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int idx = 1;

        while (!queue.isEmpty() && idx < tokens.size()) {
            TreeNode curr = queue.poll();
            if (idx < tokens.size()) {
                String leftToken = tokens.get(idx++);
                if (!leftToken.equalsIgnoreCase("null")) {
                    curr.left = new TreeNode(Integer.parseInt(leftToken));
                    queue.offer(curr.left);
                }
            }
            if (idx < tokens.size()) {
                String rightToken = tokens.get(idx++);
                if (!rightToken.equalsIgnoreCase("null")) {
                    curr.right = new TreeNode(Integer.parseInt(rightToken));
                    queue.offer(curr.right);
                }
            }
        }
        return root;
    }

    public static TreeNode buildBalancedBST(int[] sortedArr, int left, int right) {
        if (left > right) return null;
        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(sortedArr[mid]);
        node.left = buildBalancedBST(sortedArr, left, mid - 1);
        node.right = buildBalancedBST(sortedArr, mid + 1, right);
        return node;
    }

    static class TestCase {
        TreeNode root;
        int key;

        TestCase(TreeNode root, int key) {
            this.root = root;
            this.key = key;
        }
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "Delete a node in BST/Testcases";
        File inDir = new File(baseDir + "/Input");
        File outDir = new File(baseDir + "/Output");
        inDir.mkdirs();
        outDir.mkdirs();

        List<TestCase> testCases = new ArrayList<>();
        Random rand = new Random(101);

        // 00 - 02: TUF Official Samples
        testCases.add(new TestCase(buildTreeFromLevelOrder("[5, 3, 6, 2, 4, null, 7]"), 3));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[5, 3, 6, 2, 4, null, 7]"), 0));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[5, 3, 6, 2, 4, null, 7]"), 5));

        // 03 - 09: Edge Cases (empty, single node, 2 nodes)
        testCases.add(new TestCase(null, 1)); // empty tree
        testCases.add(new TestCase(new TreeNode(10), 10)); // single node delete
        testCases.add(new TestCase(new TreeNode(10), 5));  // single node absent
        {
            TreeNode r = new TreeNode(10);
            r.left = new TreeNode(5);
            testCases.add(new TestCase(r, 10)); // delete root
        }
        {
            TreeNode r = new TreeNode(10);
            r.left = new TreeNode(5);
            testCases.add(new TestCase(r, 5)); // delete leaf
        }
        {
            TreeNode r = new TreeNode(10);
            r.right = new TreeNode(15);
            testCases.add(new TestCase(r, 10)); // delete root
        }
        {
            TreeNode r = new TreeNode(10);
            r.right = new TreeNode(15);
            testCases.add(new TestCase(r, 15)); // delete leaf
        }

        // 10 - 19: 3 to 7 node trees exploring all deletion positions
        testCases.add(new TestCase(buildTreeFromLevelOrder("[2, 1, 3]"), 2)); // delete root
        testCases.add(new TestCase(buildTreeFromLevelOrder("[2, 1, 3]"), 1)); // delete left leaf
        testCases.add(new TestCase(buildTreeFromLevelOrder("[2, 1, 3]"), 3)); // delete right leaf
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, 1, 3, 5, 7]"), 1)); // leaf
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, 1, 3, 5, 7]"), 2)); // 2 children internal
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, 1, 3, 5, 7]"), 6)); // 2 children internal
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, 1, 3, 5, 7]"), 4)); // root
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, 1, null, 5, 7]"), 2)); // 1 child
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, null, 3, 5, null]"), 2)); // 1 child
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, 1, 3, 5, 7]"), 99)); // absent

        // 20 - 29: Extreme Values and Signs (-10^8 to 10^8)
        {
            TreeNode r = new TreeNode(0);
            r.left = new TreeNode(-100000000);
            r.right = new TreeNode(100000000);
            testCases.add(new TestCase(r, 0)); // delete 0
        }
        {
            TreeNode r = new TreeNode(0);
            r.left = new TreeNode(-100000000);
            r.right = new TreeNode(100000000);
            testCases.add(new TestCase(r, -100000000)); // delete min
        }
        {
            TreeNode r = new TreeNode(0);
            r.left = new TreeNode(-100000000);
            r.right = new TreeNode(100000000);
            testCases.add(new TestCase(r, 100000000)); // delete max
        }
        {
            TreeNode r = buildTreeFromLevelOrder("[-50000000, -90000000, -10000000, -100000000, -70000000]");
            testCases.add(new TestCase(r, -100000000));
        }
        {
            TreeNode r = buildTreeFromLevelOrder("[50000000, 10000000, 90000000, null, null, 70000000, 100000000]");
            testCases.add(new TestCase(r, 100000000));
        }
        testCases.add(new TestCase(buildTreeFromLevelOrder("[-50, -100, -20, -150, -80, -30, -10]"), -100));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[-50, -100, -20, -150, -80, -30, -10]"), 5)); // absent
        testCases.add(new TestCase(buildTreeFromLevelOrder("[50, 20, 80, 10, 30, 60, 90]"), -5)); // absent
        testCases.add(new TestCase(buildTreeFromLevelOrder("[0, -50, 50, -75, -25, 25, 75]"), 0));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[0, -50, 50, -75, -25, 25, 75]"), 50));

        // 30 - 39: Skewed chains and zig-zags
        { // 30: Left-skewed chain length 6, delete bottom leaf
            TreeNode r = buildTreeFromLevelOrder("[6, 5, null, 4, null, 3, null, 2, null, 1]");
            testCases.add(new TestCase(r, 1));
        }
        { // 31: Left-skewed chain length 6, delete root
            TreeNode r = buildTreeFromLevelOrder("[6, 5, null, 4, null, 3, null, 2, null, 1]");
            testCases.add(new TestCase(r, 6));
        }
        { // 32: Left-skewed chain length 6, delete middle
            TreeNode r = buildTreeFromLevelOrder("[6, 5, null, 4, null, 3, null, 2, null, 1]");
            testCases.add(new TestCase(r, 3));
        }
        { // 33: Right-skewed chain length 6, delete bottom leaf
            TreeNode r = buildTreeFromLevelOrder("[1, null, 2, null, 3, null, 4, null, 5, null, 6]");
            testCases.add(new TestCase(r, 6));
        }
        { // 34: Right-skewed chain length 6, delete root
            TreeNode r = buildTreeFromLevelOrder("[1, null, 2, null, 3, null, 4, null, 5, null, 6]");
            testCases.add(new TestCase(r, 1));
        }
        { // 35: Right-skewed chain length 6, delete middle
            TreeNode r = buildTreeFromLevelOrder("[1, null, 2, null, 3, null, 4, null, 5, null, 6]");
            testCases.add(new TestCase(r, 4));
        }
        { // 36: Zig-zag (left then right)
            TreeNode r = new TreeNode(50);
            r.left = new TreeNode(20);
            r.left.right = new TreeNode(35);
            r.left.right.left = new TreeNode(25);
            r.left.right.left.right = new TreeNode(30);
            testCases.add(new TestCase(r, 20));
        }
        { // 37: Zig-zag (right then left)
            TreeNode r = new TreeNode(10);
            r.right = new TreeNode(50);
            r.right.left = new TreeNode(30);
            r.right.left.right = new TreeNode(40);
            r.right.left.right.left = new TreeNode(35);
            testCases.add(new TestCase(r, 50));
        }
        { // 38: Left-skewed chain length 80, delete node
            TreeNode r = new TreeNode(100);
            TreeNode c = r;
            for (int i = 99; i >= 21; i--) {
                c.left = new TreeNode(i);
                c = c.left;
            }
            testCases.add(new TestCase(r, 50));
        }
        { // 39: Right-skewed chain length 80, delete node
            TreeNode r = new TreeNode(1);
            TreeNode c = r;
            for (int i = 2; i <= 80; i++) {
                c.right = new TreeNode(i);
                c = c.right;
            }
            testCases.add(new TestCase(r, 40));
        }

        // 40 - 54: Moderate sized balanced & irregular BSTs (N = 15 to 127)
        int[] moderateSizes = {15, 15, 20, 25, 31, 31, 40, 50, 63, 63, 75, 90, 100, 120, 127};
        for (int i = 0; i < moderateSizes.length; i++) {
            int sz = moderateSizes[i];
            int[] arr = new int[sz];
            int startVal = (i % 2 == 0) ? 10 : -sz * 5;
            for (int j = 0; j < sz; j++) {
                arr[j] = startVal + j * 10;
            }
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            int key;
            if (i % 5 == 0) key = arr[0]; // min
            else if (i % 5 == 1) key = arr[sz - 1]; // max
            else if (i % 5 == 2) key = arr[sz / 2]; // root
            else if (i % 5 == 3) key = arr[sz / 4]; // internal
            else key = startVal - 999; // absent
            testCases.add(new TestCase(root, key));
        }

        // 55 - 74: Medium to large sized BSTs (N = 200 to 2000)
        int[] mediumSizes = {
            200, 255, 300, 400, 500, 511, 600, 750, 800, 900,
            1000, 1023, 1100, 1250, 1400, 1500, 1600, 1750, 1900, 2000
        };
        for (int i = 0; i < mediumSizes.length; i++) {
            int sz = mediumSizes[i];
            int[] arr = new int[sz];
            int step = 2 + rand.nextInt(5);
            int startVal = - (sz / 2) * step;
            for (int j = 0; j < sz; j++) {
                arr[j] = startVal + j * step * 2;
            }
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            int key;
            int type = i % 5;
            if (type == 0) key = arr[0];
            else if (type == 1) key = arr[sz - 1];
            else if (type == 2) key = arr[sz / 2];
            else if (type == 3) key = arr[rand.nextInt(sz)];
            else key = 99999999; // absent
            testCases.add(new TestCase(root, key));
        }

        // 75 - 89: Large BSTs (N = 2500 to 8000)
        int[] largeSizes = {
            2500, 3000, 3500, 4000, 4095, 4500, 5000, 5500,
            6000, 6500, 7000, 7500, 7800, 8000, 8191
        };
        for (int i = 0; i < largeSizes.length; i++) {
            int sz = largeSizes[i];
            int[] arr = new int[sz];
            int step = 10;
            int startVal = - (sz / 2) * step;
            for (int j = 0; j < sz; j++) {
                arr[j] = startVal + j * step;
            }
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            int key;
            int mode = i % 5;
            if (mode == 0) key = arr[0];
            else if (mode == 1) key = arr[sz - 1];
            else if (mode == 2) key = arr[sz / 2];
            else if (mode == 3) key = arr[sz / 3];
            else key = -99999999; // absent
            testCases.add(new TestCase(root, key));
        }

        // 90 - 99: Stress & Max Constraint Tests (N up to 10000)
        // 90: Balanced BST N = 10000, delete root
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = j * 4;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, arr[sz / 2]));
        }
        // 91: Balanced BST N = 10000, delete min -10^8
        {
            int sz = 10000;
            int[] arr = new int[sz];
            arr[0] = -100000000;
            for (int j = 1; j < sz; j++) arr[j] = -50000000 + j * 10000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, -100000000));
        }
        // 92: Balanced BST N = 10000, delete max 10^8
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz - 1; j++) arr[j] = -50000000 + j * 10000;
            arr[sz - 1] = 100000000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, 100000000));
        }
        // 93: Deep left-heavy chain N = 3000, delete middle
        {
            int sz = 3000;
            TreeNode root = new TreeNode(sz * 2);
            TreeNode curr = root;
            for (int j = sz - 1; j >= 1; j--) {
                curr.left = new TreeNode(j * 2);
                curr = curr.left;
            }
            testCases.add(new TestCase(root, 1500 * 2));
        }
        // 94: Deep right-heavy chain N = 3000, delete root
        {
            int sz = 3000;
            TreeNode root = new TreeNode(2);
            TreeNode curr = root;
            for (int j = 2; j <= sz; j++) {
                curr.right = new TreeNode(j * 2);
                curr = curr.right;
            }
            testCases.add(new TestCase(root, 2));
        }
        // 95: Balanced BST N = 10000, delete internal node with 2 children
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = -10000 + j * 2;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, arr[2500]));
        }
        // 96: Balanced BST N = 10000, key absent
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = -90000000 + j * 18000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, 1234567));
        }
        // 97: Balanced BST N = 9999, delete leaf
        {
            int sz = 9999;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = j * 3;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, arr[0]));
        }
        // 98: Balanced BST N = 10000 in negative domain
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = -90000000 + j * 8000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, arr[5000]));
        }
        // 99: Balanced BST N = 10000 in positive domain
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = 10000000 + j * 8000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, arr[7500]));
        }

        System.out.println("Total test cases: " + testCases.size());

        for (int i = 0; i < testCases.size(); i++) {
            String filename = String.format("%02d", i);
            File inFile = new File(inDir, "input" + filename + ".txt");
            File outFile = new File(outDir, "output" + filename + ".txt");

            TestCase tc = testCases.get(i);
            String inputTreeSerialized = serializeTree(tc.root);

            try (PrintWriter pw = new PrintWriter(new FileWriter(inFile))) {
                pw.println("root = " + inputTreeSerialized);
                pw.println("key = " + tc.key);
            }

            TreeNode copy = cloneTree(tc.root);
            TreeNode result = deleteNode(copy, tc.key);
            String outputSerialized = serializeTree(result);

            try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
                pw.println(outputSerialized);
            }
        }

        System.out.println("All 100 test cases generated successfully.");
    }
}
