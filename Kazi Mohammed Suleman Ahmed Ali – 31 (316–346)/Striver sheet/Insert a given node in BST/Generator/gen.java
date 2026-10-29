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

    public static TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode curr = root;
        while (true) {
            if (val < curr.val) {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                }
                curr = curr.right;
            }
        }
        return root;
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
        int val;

        TestCase(TreeNode root, int val) {
            this.root = root;
            this.val = val;
        }
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "Insert a given node in BST/Testcases";
        File inDir = new File(baseDir + "/Input");
        File outDir = new File(baseDir + "/Output");
        inDir.mkdirs();
        outDir.mkdirs();

        List<TestCase> testCases = new ArrayList<>();
        Random rand = new Random(42);

        // 00 - 02: TUF Official Samples
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 7, 1, 3]"), 5));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[40, 20, 60, 10, 30, 50, 70]"), 25));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[10, 5, 23, 1, 8, 15, 25, null, 3]"), 2));

        // 03 - 09: Small Edge Cases
        testCases.add(new TestCase(new TreeNode(5), 3));     // insert left
        testCases.add(new TestCase(new TreeNode(5), 8));     // insert right
        testCases.add(new TestCase(new TreeNode(0), -100));  // insert negative
        {
            TreeNode r = new TreeNode(5);
            r.left = new TreeNode(2);
            testCases.add(new TestCase(r, 1)); // insert left of 2
        }
        {
            TreeNode r = new TreeNode(5);
            r.left = new TreeNode(2);
            testCases.add(new TestCase(r, 3)); // insert right of 2
        }
        {
            TreeNode r = new TreeNode(5);
            r.right = new TreeNode(8);
            testCases.add(new TestCase(r, 6)); // insert left of 8
        }
        {
            TreeNode r = new TreeNode(5);
            r.right = new TreeNode(8);
            testCases.add(new TestCase(r, 10)); // insert right of 8
        }

        // 10 - 19: 3 to 7 node trees exploring leaf / inner positions
        testCases.add(new TestCase(buildTreeFromLevelOrder("[2, 1, 3]"), 0)); // new absolute min
        testCases.add(new TestCase(buildTreeFromLevelOrder("[2, 1, 3]"), 4)); // new absolute max
        testCases.add(new TestCase(buildTreeFromLevelOrder("[3, 1, 4, null, 2]"), 0));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[3, 1, 5, null, null, 4]"), 6));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[3, 1, 4, null, 2]"), 5));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, null, 1, 3]"), 5));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, 1, 3, 5, 7]"), 0));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[4, 2, 6, 1, 3, 5, 7]"), 8));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[5, 3, 8, 2, 4, 7, 9, 1]"), 6));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[10, 5, 15, 3, 7, 12, 18]"), 11));

        // 20 - 29: Boundary Values and Sign Variations (-10^8 to 10^8)
        {
            TreeNode r = new TreeNode(0);
            r.left = new TreeNode(-100000000);
            r.right = new TreeNode(100000000);
            testCases.add(new TestCase(r, 50));
        }
        {
            TreeNode r = new TreeNode(50000000);
            r.left = new TreeNode(10000000);
            r.right = new TreeNode(99999999);
            testCases.add(new TestCase(r, 100000000)); // upper boundary
        }
        {
            TreeNode r = new TreeNode(-50000000);
            r.left = new TreeNode(-99999999);
            r.right = new TreeNode(-10000000);
            testCases.add(new TestCase(r, -100000000)); // lower boundary
        }
        {
            TreeNode r = buildTreeFromLevelOrder("[0, -5, 5, -10, -2, 2, 10]");
            testCases.add(new TestCase(r, 100000000));
        }
        {
            TreeNode r = buildTreeFromLevelOrder("[0, -5, 5, -10, -2, 2, 10]");
            testCases.add(new TestCase(r, -100000000));
        }
        testCases.add(new TestCase(buildTreeFromLevelOrder("[-50, -100, -20, -150, -80, -30, -10]"), -75));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[-50, -100, -20, -150, -80, -30, -10]"), 5));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[50, 20, 80, 10, 30, 60, 90]"), -5));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[0, -50, 50]"), -25));
        testCases.add(new TestCase(buildTreeFromLevelOrder("[0, -50, 50]"), 25));

        // 30 - 39: Skewed chains and zig-zags
        { // 30: Left-skewed chain length 5, insert 0
            TreeNode r = buildTreeFromLevelOrder("[5, 4, null, 3, null, 2, null, 1]");
            testCases.add(new TestCase(r, 0));
        }
        { // 31: Left-skewed chain length 5, insert 6
            TreeNode r = buildTreeFromLevelOrder("[5, 4, null, 3, null, 2, null, 1]");
            testCases.add(new TestCase(r, 6));
        }
        { // 32: Left-skewed chain length 10, insert middle value
            TreeNode r = new TreeNode(20);
            TreeNode c = r;
            for (int v = 18; v >= 2; v -= 2) {
                c.left = new TreeNode(v);
                c = c.left;
            }
            testCases.add(new TestCase(r, 11));
        }
        { // 33: Right-skewed chain length 5, insert 6
            TreeNode r = buildTreeFromLevelOrder("[1, null, 2, null, 3, null, 4, null, 5]");
            testCases.add(new TestCase(r, 6));
        }
        { // 34: Right-skewed chain length 5, insert 0
            TreeNode r = buildTreeFromLevelOrder("[1, null, 2, null, 3, null, 4, null, 5]");
            testCases.add(new TestCase(r, 0));
        }
        { // 35: Right-skewed chain length 10, insert middle value
            TreeNode r = new TreeNode(2);
            TreeNode c = r;
            for (int v = 4; v <= 20; v += 2) {
                c.right = new TreeNode(v);
                c = c.right;
            }
            testCases.add(new TestCase(r, 11));
        }
        { // 36: Zig-zag (left then right)
            TreeNode r = new TreeNode(50);
            r.left = new TreeNode(20);
            r.left.right = new TreeNode(35);
            r.left.right.left = new TreeNode(25);
            r.left.right.left.right = new TreeNode(30);
            testCases.add(new TestCase(r, 10));
        }
        { // 37: Zig-zag (right then left)
            TreeNode r = new TreeNode(10);
            r.right = new TreeNode(50);
            r.right.left = new TreeNode(30);
            r.right.left.right = new TreeNode(40);
            r.right.left.right.left = new TreeNode(35);
            testCases.add(new TestCase(r, 60));
        }
        { // 38: Left-skewed chain length 50
            TreeNode r = new TreeNode(100);
            TreeNode c = r;
            for (int i = 98; i >= 2; i -= 2) {
                c.left = new TreeNode(i);
                c = c.left;
            }
            testCases.add(new TestCase(r, 1));
        }
        { // 39: Right-skewed chain length 50
            TreeNode r = new TreeNode(2);
            TreeNode c = r;
            for (int i = 4; i <= 100; i += 2) {
                c.right = new TreeNode(i);
                c = c.right;
            }
            testCases.add(new TestCase(r, 101));
        }

        // 40 - 54: Moderate sized balanced & semi-balanced BSTs (N = 15 to 127)
        int[] moderateSizes = {15, 15, 20, 25, 31, 31, 40, 50, 63, 63, 75, 90, 100, 120, 127};
        for (int i = 0; i < moderateSizes.length; i++) {
            int sz = moderateSizes[i];
            int[] arr = new int[sz];
            int startVal = (i % 2 == 0) ? 10 : -sz * 5;
            for (int j = 0; j < sz; j++) {
                arr[j] = startVal + j * 10;
            }
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            int insertVal;
            if (i % 3 == 0) {
                insertVal = startVal - 5; // new minimum
            } else if (i % 3 == 1) {
                insertVal = startVal + sz * 10 + 5; // new maximum
            } else {
                insertVal = startVal + (sz / 2) * 10 + 5; // interior
            }
            testCases.add(new TestCase(root, insertVal));
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
            int insertVal;
            int type = i % 4;
            if (type == 0) insertVal = arr[0] - step;
            else if (type == 1) insertVal = arr[sz - 1] + step;
            else if (type == 2) insertVal = arr[sz / 2] + 1;
            else insertVal = arr[rand.nextInt(sz)] + 1;
            testCases.add(new TestCase(root, insertVal));
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
            int insertVal;
            int mode = i % 5;
            if (mode == 0) insertVal = -99999999;
            else if (mode == 1) insertVal = 99999999;
            else if (mode == 2) insertVal = arr[sz / 4] + 3;
            else if (mode == 3) insertVal = arr[3 * sz / 4] + 3;
            else insertVal = arr[sz / 2] + 1;
            testCases.add(new TestCase(root, insertVal));
        }

        // 90 - 99: Stress & Max Constraint Tests (N up to 10000)
        // 90: Balanced BST N = 10000, insert middle
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = j * 4;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, 20001));
        }
        // 91: Balanced BST N = 10000, insert lower bound -10^8
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = -50000000 + j * 10000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, -100000000));
        }
        // 92: Balanced BST N = 10000, insert upper bound 10^8
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = -50000000 + j * 10000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, 100000000));
        }
        // 93: Left-heavy deep BST N = 3000
        {
            int sz = 3000;
            TreeNode root = new TreeNode(sz * 2);
            TreeNode curr = root;
            for (int j = sz - 1; j >= 1; j--) {
                curr.left = new TreeNode(j * 2);
                curr = curr.left;
            }
            testCases.add(new TestCase(root, 1));
        }
        // 94: Right-heavy deep BST N = 3000
        {
            int sz = 3000;
            TreeNode root = new TreeNode(2);
            TreeNode curr = root;
            for (int j = 2; j <= sz; j++) {
                curr.right = new TreeNode(j * 2);
                curr = curr.right;
            }
            testCases.add(new TestCase(root, sz * 2 + 1));
        }
        // 95: Balanced BST N = 10000 with negatives and positives
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = -10000 + j * 2;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, 1));
        }
        // 96: Balanced BST N = 10000 spanning wide range [-90M, 90M]
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = -90000000 + j * 18000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, 42));
        }
        // 97: Balanced BST N = 9999
        {
            int sz = 9999;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = j * 3;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, 14998));
        }
        // 98: Balanced BST N = 10000 in negative domain
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = -90000000 + j * 8000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, -50000001));
        }
        // 99: Balanced BST N = 10000 in positive domain
        {
            int sz = 10000;
            int[] arr = new int[sz];
            for (int j = 0; j < sz; j++) arr[j] = 10000000 + j * 8000;
            TreeNode root = buildBalancedBST(arr, 0, sz - 1);
            testCases.add(new TestCase(root, 50000001));
        }

        System.out.println("Total test cases generated: " + testCases.size());

        for (int i = 0; i < testCases.size(); i++) {
            String filename = String.format("%02d", i);
            File inFile = new File(inDir, "input" + filename + ".txt");
            File outFile = new File(outDir, "output" + filename + ".txt");

            TestCase tc = testCases.get(i);
            String inputTreeSerialized = serializeTree(tc.root);

            // Write input
            try (PrintWriter pw = new PrintWriter(new FileWriter(inFile))) {
                pw.println("root = " + inputTreeSerialized);
                pw.println("val = " + tc.val);
            }

            // Clone and insert to produce output
            TreeNode copy = cloneTree(tc.root);
            TreeNode result = insertIntoBST(copy, tc.val);
            String outputSerialized = serializeTree(result);

            try (PrintWriter pw = new PrintWriter(new FileWriter(outFile))) {
                pw.println(outputSerialized);
            }
        }

        System.out.println("All test cases written successfully.");
    }
}
