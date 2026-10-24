import java.io.*;

public class Solution {
    static class FastScanner {
        private final InputStream in = System.in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr, len;
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        String next() throws IOException {
            StringBuilder s = new StringBuilder();
            int c;
            do { c = read(); } while (c <= 32 && c != -1);
            if (c == -1) return null;
            while (c > 32 && c != -1) { s.append((char)c); c = read(); }
            return s.toString();
        }
        int nextInt() throws IOException {
            int c;
            do { c = read(); } while (c <= 32 && c != -1);
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int value = 0;
            while (c > 32 && c != -1) { value = value * 10 + c - '0'; c = read(); }
            return value * sign;
        }
    }
    static class Node {
        int val;
        Node next;
        Node(int val) { this.val = val; }
    }
    static Node build(FastScanner fs, int n) throws IOException {
        Node head = null, tail = null;
        for (int i = 0; i < n; i++) {
            Node z = new Node(fs.nextInt());
            if (head == null) head = z;
            else tail.next = z;
            tail = z;
        }
        return head;
    }
    static String show(Node head) {
        if (head == null) return "EMPTY";
        StringBuilder out = new StringBuilder();
        for (Node p = head; p != null; p = p.next) {
            if (out.length() > 0) out.append(' ');
            out.append(p.val);
        }
        return out.toString();
    }
    static class SList {
        Node head, tail;
        int size;
        SList(FastScanner fs, int n) throws IOException {
            for (int i = 0; i < n; i++) append(fs.nextInt());
        }
        void append(int x) {
            Node z = new Node(x);
            if (tail == null) head = z;
            else tail.next = z;
            tail = z;
            size++;
        }
        void addHead(int x) {
            Node z = new Node(x);
            z.next = head;
            head = z;
            if (tail == null) tail = z;
            size++;
        }
        void insertAt(int k, int x) {
            if (k < 1 || k > size + 1) return;
            if (k == 1) { addHead(x); return; }
            if (k == size + 1) { append(x); return; }
            Node p = head;
            for (int i = 1; i < k - 1; i++) p = p.next;
            Node z = new Node(x);
            z.next = p.next;
            p.next = z;
            size++;
        }
        void deleteHead() {
            if (head == null) return;
            head = head.next;
            size--;
            if (head == null) tail = null;
        }
        void deleteTail() {
            if (tail == null) return;
            if (head == tail) { head = tail = null; size = 0; return; }
            Node p = head;
            while (p.next != tail) p = p.next;
            p.next = null;
            tail = p;
            size--;
        }
        void deleteAt(int k) {
            if (k < 1 || k > size) return;
            if (k == 1) { deleteHead(); return; }
            Node p = head;
            for (int i = 1; i < k - 1; i++) p = p.next;
            Node removed = p.next;
            p.next = removed.next;
            if (removed == tail) tail = p;
            size--;
        }
        void deleteValue(int x) {
            if (head == null) return;
            if (head.val == x) { deleteHead(); return; }
            Node p = head;
            while (p.next != null && p.next.val != x) p = p.next;
            if (p.next != null) {
                if (p.next == tail) tail = p;
                p.next = p.next.next;
                size--;
            }
        }
        void insertBeforeValue(int target, int x) {
            if (head == null) return;
            if (head.val == target) { addHead(x); return; }
            Node p = head;
            while (p.next != null && p.next.val != target) p = p.next;
            if (p.next != null) {
                Node z = new Node(x);
                z.next = p.next;
                p.next = z;
                size++;
            }
        }
        String forward() {
            if (head == null) return "EMPTY";
            StringBuilder out = new StringBuilder();
            for (Node p = head; p != null; p = p.next) {
                if (out.length() > 0) out.append(' ');
                out.append(p.val);
            }
            return out.toString();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        String token;
        StringBuilder out = new StringBuilder();
        while ((token = fs.next()) != null) {
            int n = Integer.parseInt(token);
            SList list = new SList(fs, n);
            int q = fs.nextInt();
            for (int i = 0; i < q; i++) {
                String op = fs.next();
                if (op.equals("H")) list.deleteHead();
                else if (op.equals("T")) list.deleteTail();
                else if (op.equals("K")) list.deleteAt(fs.nextInt());
                else if (op.equals("X")) list.deleteValue(fs.nextInt());
            }
            out.append(list.forward()).append('\n');
        }
        System.out.print(out);
    }
}
