import java.io.*;
import java.util.*;

public class gen {
    static Random random = new Random(20261025L);

    static class Node {
        int value;
        Node left, right;
        Node(int value) { this.value = value; }
    }

    static String treeToLevelOrder(Node root) {
        if (root == null) return "[]";
        List<String> values = new ArrayList<>();
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node n = queue.removeFirst();
            if (n == null) {
                values.add("null");
            } else {
                values.add(Integer.toString(n.value));
                queue.addLast(n.left);
                queue.addLast(n.right);
            }
        }
        int end = values.size();
        while (end > 0 && values.get(end - 1).equals("null")) end--;
        return "[" + String.join(", ", values.subList(0, end)) + "]";
    }

    static int randomValue(int mode) {
        if (mode == 1) return random.nextInt(5) - 2;
        if (mode == 2) return 0;
        return random.nextInt(201) - 100;
    }

    static Node randomTree(int n, int mode) {
        if (n <= 0) return null;
        Node root = new Node(randomValue(mode));
        List<Node> open = new ArrayList<>();
        open.add(root);
        int count = 1;
        while (count < n) {
            int idx = random.nextInt(open.size());
            Node parent = open.get(idx);
            boolean placed = false;
            if (random.nextBoolean()) {
                if (parent.left == null) {
                    parent.left = new Node(randomValue(mode));
                    open.add(parent.left); count++; placed = true;
                } else if (parent.right == null) {
                    parent.right = new Node(randomValue(mode));
                    open.add(parent.right); count++; placed = true;
                }
            } else {
                if (parent.right == null) {
                    parent.right = new Node(randomValue(mode));
                    open.add(parent.right); count++; placed = true;
                } else if (parent.left == null) {
                    parent.left = new Node(randomValue(mode));
                    open.add(parent.left); count++; placed = true;
                }
            }
            if (parent.left != null && parent.right != null) open.remove(idx);
            if (!placed && idx < open.size()) open.remove(idx);
        }
        return root;
    }

    static Node chain(int n, boolean left, int mode) {
        if (n <= 0) return null;
        Node root = new Node(randomValue(mode));
        Node cur = root;
        for (int i = 1; i < n; i++) {
            Node next = new Node(randomValue(mode));
            if (left) cur.left = next;
            else cur.right = next;
            cur = next;
        }
        return root;
    }

    static Node sampleTree() {
        Node r = new Node(5);
        r.left = new Node(1);
        r.right = new Node(2);
        r.left.left = new Node(8);
        r.right.left = new Node(4);
        r.right.right = new Node(5);
        r.left.left.right = new Node(6);
        return r;
    }

    static Node exampleOne() {
        Node r = new Node(1);
        r.left = new Node(4);
        r.left.left = new Node(4);
        r.left.right = new Node(2);
        return r;
    }

    static void preorder(Node n, List<Integer> out) {
        if (n == null) return;
        out.add(n.value);
        preorder(n.left, out);
        preorder(n.right, out);
    }

    public static void main(String[] args) throws Exception {
        File inDir = new File("Testcases/input");
        File outDir = new File("Testcases/output");
        inDir.mkdirs(); outDir.mkdirs();

        List<Node> cases = new ArrayList<>();
        cases.add(sampleTree());
        cases.add(exampleOne());
        cases.add(new Node(1));
        cases.add(new Node(0));
        cases.add(chain(100, true, 0));
        cases.add(chain(100, false, 0));
        cases.add(chain(100, true, 1));
        cases.add(chain(100, false, 2));

        for (int i = 0; i < 112; i++) {
            int n;
            if (i % 10 == 0) n = 100;
            else if (i % 10 == 1) n = 1 + random.nextInt(8);
            else n = 1 + random.nextInt(100);
            int mode = i % 7 == 0 ? 2 : (i % 5 == 0 ? 1 : 0);
            Node tree = (i % 13 == 0) ? chain(n, i % 2 == 0, mode) : randomTree(n, mode);
            cases.add(tree);
        }

        for (int i = 0; i < cases.size(); i++) {
            String input = treeToLevelOrder(cases.get(i));
            List<Integer> traversal = new ArrayList<>();
            preorder(cases.get(i), traversal);
            try (PrintWriter pw = new PrintWriter(new File(inDir, String.format("input%02d.txt", i)))) {
                pw.println(input);
            }
            try (PrintWriter pw = new PrintWriter(new File(outDir, String.format("output%02d.txt", i)))) {
                pw.println(traversal);
            }
        }
        System.out.println("Generated " + cases.size() + " testcases.");
    }
}
