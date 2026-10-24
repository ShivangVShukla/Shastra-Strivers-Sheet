import java.io.*;
import java.util.*;

public class Solution {
    static class Node {
        int key, value;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    static class LRUCache {
        private final int capacity;
        private final Map<Integer, Node> map = new HashMap<>();
        private final Node head = new Node(0, 0);
        private final Node tail = new Node(0, 0);

        LRUCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addLast(Node node) {
            node.prev = tail.prev;
            node.next = tail;
            tail.prev.next = node;
            tail.prev = node;
        }

        private void moveToLast(Node node) {
            remove(node);
            addLast(node);
        }

        int get(int key) {
            Node node = map.get(key);
            if (node == null) return -1;
            moveToLast(node);
            return node.value;
        }

        void put(int key, int value) {
            Node node = map.get(key);

            if (node != null) {
                node.value = value;
                moveToLast(node);
                return;
            }

            node = new Node(key, value);
            map.put(key, node);
            addLast(node);

            if (map.size() > capacity) {
                Node lru = head.next;
                remove(lru);
                map.remove(lru.key);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int capacity = fs.nextInt();
        int q = fs.nextInt();

        LRUCache cache = new LRUCache(capacity);
        StringBuilder out = new StringBuilder();
        boolean first = true;

        for (int i = 0; i < q; i++) {
            int type = fs.nextInt();

            if (type == 1) {
                cache.put(fs.nextInt(), fs.nextInt());
            } else {
                int value = cache.get(fs.nextInt());
                if (!first) out.append(' ');
                out.append(value);
                first = false;
            }
        }

        System.out.println(first ? "EMPTY" : out);
    }

    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        FastScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
