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
        return random.nextInt(3001) - 1000;
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
            if (random.nextBoolean()) {
                if (parent.left == null) {
                    parent.left = new Node(randomValue(mode));
                    open.add(parent.left);
                    count++;
                } else if (parent.right == null) {
                    parent.right = new Node(randomValue(mode));
                    open.add(parent.right);
                    count++;
                }
            } else {
                if (parent.right == null) {
                    parent.right = new Node(randomValue(mode));
                    open.add(parent.right);
                    count++;
                } else if (parent.left == null) {
                    parent.left = new Node(randomValue(mode));
                    open.add(parent.left);
                    count++;
                }
            }
            if (parent.left != null && parent.right != null) open.remove(idx);
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

    static Node exampleOne() {
        Node r = new Node(3);
        r.left = new Node(9);
        r.right = new Node(20);
        r.right.left = new Node(15);
        r.right.right = new Node(7);
        return r;
    }

    static Node exampleTwo() {
        Node r = new Node(1);
        r.left = new Node(4);
        r.left.left = new Node(4);
        r.left.right = new Node(2);
        return r;
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

    static List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<Node> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node n = q.poll();
                level.add(n.value);
                if (n.left != null) q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
            result.add(level);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        File inDir = new File("Testcases/input");
        File outDir = new File("Testcases/output");
        inDir.mkdirs();
        outDir.mkdirs();

        List<Node> cases = new ArrayList<>();
        cases.add(sampleTree());
        cases.add(exampleOne());
        cases.add(exampleTwo());
        cases.add(new Node(1));
        cases.add(null);
        cases.add(chain(2000, true, 0));
        cases.add(chain(2000, false, 0));
        cases.add(chain(2000, true, 1));
        cases.add(randomTree(2000, 2));

        for (int i = 0; i < 111; i++) {
            int n;
            if (i % 10 == 0) n = 2000;
            else if (i % 10 == 1) n = 1 + random.nextInt(10);
            else n = 1 + random.nextInt(2000);
            int mode = i % 7 == 0 ? 2 : (i % 5 == 0 ? 1 : 0);
            Node tree = i % 13 == 0 ? chain(n, i % 2 == 0, mode) : randomTree(n, mode);
            cases.add(tree);
        }

        for (int i = 0; i < cases.size(); i++) {
            String input = treeToLevelOrder(cases.get(i));
            List<List<Integer>> traversal = levelOrder(cases.get(i));
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
