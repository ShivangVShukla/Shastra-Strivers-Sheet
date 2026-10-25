import java.io.*;
import java.util.*;

public class Solution {
    private static class Node {
        int key;
        int value;
        int frequency = 1;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class DoublyLinkedList {
        Node head = new Node(-1, -1);
        Node tail = new Node(-1, -1);
        int size = 0;

        DoublyLinkedList() {
            head.next = tail;
            tail.prev = head;
        }

        void addFirst(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        Node removeLast() {
            if (size == 0) return null;
            Node node = tail.prev;
            remove(node);
            return node;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    private static class LFUCache {
        private final int capacity;
        private int minFrequency = 0;
        private final Map<Integer, Node> keyToNode = new HashMap<>();
        private final Map<Integer, DoublyLinkedList> frequencyToList = new HashMap<>();

        LFUCache(int capacity) {
            this.capacity = capacity;
        }

        int get(int key) {
            Node node = keyToNode.get(key);
            if (node == null) return -1;
            promote(node);
            return node.value;
        }

        void put(int key, int value) {
            Node existing = keyToNode.get(key);
            if (existing != null) {
                existing.value = value;
                promote(existing);
                return;
            }

            if (keyToNode.size() == capacity) {
                DoublyLinkedList list = frequencyToList.get(minFrequency);
                Node evicted = list.removeLast();
                keyToNode.remove(evicted.key);
            }

            Node node = new Node(key, value);
            keyToNode.put(key, node);
            frequencyToList.computeIfAbsent(1, ignored -> new DoublyLinkedList()).addFirst(node);
            minFrequency = 1;
        }

        private void promote(Node node) {
            int oldFrequency = node.frequency;
            DoublyLinkedList oldList = frequencyToList.get(oldFrequency);
            oldList.remove(node);

            if (oldFrequency == minFrequency && oldList.isEmpty()) {
                minFrequency++;
            }

            node.frequency++;
            frequencyToList.computeIfAbsent(node.frequency, ignored -> new DoublyLinkedList()).addFirst(node);
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
            LFUCache cache = new LFUCache(capacity);

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
