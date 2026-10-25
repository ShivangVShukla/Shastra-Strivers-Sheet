import java.io.*;
import java.util.*;

public class Solution {
    private static class Node {
        int key, value;
        Node prev, next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class LRUCache {
        private final int capacity;
        private final Map<Integer, Node> map = new HashMap<>();
        private final Node head = new Node(-1, -1);
        private final Node tail = new Node(-1, -1);

        LRUCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        int get(int key) {
            Node node = map.get(key);
            if (node == null) return -1;
            remove(node);
            addToFront(node);
            return node.value;
        }

        void put(int key, int value) {
            Node node = map.get(key);
            if (node != null) {
                node.value = value;
                remove(node);
                addToFront(node);
                return;
            }

            node = new Node(key, value);
            map.put(key, node);
            addToFront(node);

            if (map.size() > capacity) {
                Node lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToFront(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String first = fs.next();
        if (first == null) return;
        int t = Integer.parseInt(first);
        StringBuilder out = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            int capacity = fs.nextInt();
            int q = fs.nextInt();
            LRUCache cache = new LRUCache(capacity);
            for (int i = 0; i < q; i++) {
                int operation = fs.nextInt();
                if (operation == 1) {
                    int key = fs.nextInt();
                    int value = fs.nextInt();
                    cache.put(key, value);
                    out.append("null");
                } else if (operation == 2) {
                    int key = fs.nextInt();
                    out.append(cache.get(key));
                } else {
                    throw new IllegalArgumentException("Unknown operation: " + operation);
                }
                if (i + 1 < q) out.append(' ');
            }
            out.append('\n');
        }
        System.out.print(out);
    }

    private static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream in) { this.in = in; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = read();
                if (c == -1) return null;
            } while (c <= ' ');
            while (c > ' ') {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
