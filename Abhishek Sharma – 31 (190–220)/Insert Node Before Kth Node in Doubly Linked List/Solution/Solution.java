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
        Node next, prev;
        Node(int val) { this.val = val; }
    }
    static class DList {
        Node head, tail;
        int size;
        DList(FastScanner fs, int n) throws IOException {
            for (int i = 0; i < n; i++) append(fs.nextInt());
        }
        void append(int x) {
            Node z = new Node(x);
            z.prev = tail;
            if (tail == null) head = z;
            else tail.next = z;
            tail = z;
            size++;
        }
        void addHead(int x) {
            Node z = new Node(x);
            z.next = head;
            if (head == null) tail = z;
            else head.prev = z;
            head = z;
            size++;
        }
        Node at(int k) {
            if (k < 1 || k > size) return null;
            if (k - 1 <= size - k) {
                Node p = head;
                for (int i = 1; i < k; i++) p = p.next;
                return p;
            }
            Node p = tail;
            for (int i = size; i > k; i--) p = p.prev;
            return p;
        }
        void insertBefore(Node p, int x) {
            if (p == null) return;
            Node z = new Node(x);
            z.prev = p.prev;
            z.next = p;
            if (p.prev == null) head = z;
            else p.prev.next = z;
            p.prev = z;
            size++;
        }
        void insertAt(int k, int x) {
            if (k < 1 || k > size + 1) return;
            if (k == size + 1) append(x);
            else insertBefore(at(k), x);
        }
        void remove(Node p) {
            if (p == null) return;
            if (p.prev == null) head = p.next;
            else p.prev.next = p.next;
            if (p.next == null) tail = p.prev;
            else p.next.prev = p.prev;
            p.next = p.prev = null;
            size--;
        }
        void deleteHead() { remove(head); }
        void deleteTail() { remove(tail); }
        void deleteAt(int k) { remove(at(k)); }
        void deleteValue(int x) {
            for (Node p = head; p != null; p = p.next) {
                if (p.val == x) { remove(p); return; }
            }
        }
        void insertBeforeValue(int target, int x) {
            for (Node p = head; p != null; p = p.next) {
                if (p.val == target) { insertBefore(p, x); return; }
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
        String backward() {
            if (tail == null) return "EMPTY";
            StringBuilder out = new StringBuilder();
            for (Node p = tail; p != null; p = p.prev) {
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
            DList list = new DList(fs, n);
            int k = fs.nextInt(), x = fs.nextInt();
            if (list.size == 0 && k == 1) list.insertAt(1, x);
            else list.insertBefore(list.at(k), x);
            out.append(list.forward()).append('\n').append(list.backward()).append('\n');
        }
        System.out.print(out);
    }
}
