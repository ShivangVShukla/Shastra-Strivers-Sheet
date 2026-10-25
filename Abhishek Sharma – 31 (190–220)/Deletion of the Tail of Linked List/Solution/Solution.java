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

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        String token;
        StringBuilder out = new StringBuilder();
        while ((token = fs.next()) != null) {
            int n = Integer.parseInt(token);
            Node head = build(fs, n);
            if (head != null) {
                if (head.next == null) head = null;
                else {
                    Node p = head;
                    while (p.next.next != null) p = p.next;
                    p.next = null;
                }
            }
            out.append(show(head)).append('\n');
        }
        System.out.print(out);
    }
}
