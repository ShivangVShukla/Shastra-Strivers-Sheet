import java.io.*;
import java.util.*;

public class Solution {
    static class Node {
        int key, value, freq;
        Node prev, next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    static class DLL {
        Node head = new Node(0, 0);
        Node tail = new Node(0, 0);
        int size = 0;

        DLL() {
            head.next = tail;
            tail.prev = head;
        }

        void addFirst(Node x) {
            x.next = head.next;
            x.prev = head;
            head.next.prev = x;
            head.next = x;
            size++;
        }

        void remove(Node x) {
            x.prev.next = x.next;
            x.next.prev = x.prev;
            x.prev = null;
            x.next = null;
            size--;
        }

        Node removeLast() {
            if (size == 0) return null;
            Node x = tail.prev;
            remove(x);
            return x;
        }

        boolean empty() {
            return size == 0;
        }
    }

    static class LFUCache {
        int capacity, size, minFreq;
        Map<Integer, Node> nodes = new HashMap<>();
        Map<Integer, DLL> lists = new HashMap<>();

        LFUCache(int capacity) {
            this.capacity = capacity;
        }

        int get(int key) {
            Node x = nodes.get(key);
            if (x == null) return -1;
            touch(x);
            return x.value;
        }

        void put(int key, int value) {
            if (capacity <= 0) return;

            Node x = nodes.get(key);
            if (x != null) {
                x.value = value;
                touch(x);
                return;
            }

            if (size == capacity) {
                DLL list = lists.get(minFreq);
                Node victim = list.removeLast();
                nodes.remove(victim.key);
                size--;

                if (list.empty()) lists.remove(minFreq);
            }

            Node fresh = new Node(key, value);
            nodes.put(key, fresh);
            lists.computeIfAbsent(1, k -> new DLL()).addFirst(fresh);
            minFreq = 1;
            size++;
        }

        void touch(Node x) {
            int old = x.freq;
            DLL oldList = lists.get(old);
            oldList.remove(x);

            if (oldList.empty()) {
                lists.remove(old);
                if (minFreq == old) minFreq++;
            }

            x.freq++;
            lists.computeIfAbsent(x.freq, k -> new DLL()).addFirst(x);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int capacity = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        LFUCache cache = new LFUCache(capacity);
        StringBuilder ans = new StringBuilder();
        boolean first = true;

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                int key = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                cache.put(key, value);
            } else {
                int key = Integer.parseInt(st.nextToken());
                if (!first) ans.append(' ');
                ans.append(cache.get(key));
                first = false;
            }
        }

        System.out.println(first ? "EMPTY" : ans);
    }
}
