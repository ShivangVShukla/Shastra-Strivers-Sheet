import java.io.*;
import java.util.*;

public class Solution {
    static class MinHeap {
        private final ArrayList<Long> heap = new ArrayList<>();
        void add(long value) {
            heap.add(value);
            int i = heap.size() - 1;
            while (i > 0) {
                int p = (i - 1) / 2;
                if (heap.get(p) <= heap.get(i)) break;
                long t = heap.get(p); heap.set(p, heap.get(i)); heap.set(i, t);
                i = p;
            }
        }
        long removeMin() {
            long result = heap.get(0);
            long last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);
                int i = 0;
                while (true) {
                    int l = 2 * i + 1, r = 2 * i + 2, s = i;
                    if (l < heap.size() && heap.get(l) < heap.get(s)) s = l;
                    if (r < heap.size() && heap.get(r) < heap.get(s)) s = r;
                    if (s == i) break;
                    long t = heap.get(i); heap.set(i, heap.get(s)); heap.set(s, t);
                    i = s;
                }
            }
            return result;
        }
        long peek() { return heap.get(0); }
        int size() { return heap.size(); }
    }
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt(), k = fs.nextInt();
        MinHeap heap = new MinHeap();
        for (int i = 0; i < n; i++) {
            heap.add(fs.nextLong());
            if (heap.size() > k) heap.removeMin();
        }
        System.out.println(heap.peek());
    }
    static class FastScanner {
        private final InputStream in; private final byte[] b = new byte[1 << 16]; private int p, len;
        FastScanner(InputStream in) { this.in = in; }
        private int read() throws IOException {
            if (p >= len) { len = in.read(b); p = 0; if (len <= 0) return -1; }
            return b[p++];
        }
        long nextLong() throws IOException {
            int c; do c = read(); while (c <= ' ');
            boolean neg = false; if (c == '-') { neg = true; c = read(); }
            long v = 0; while (c > ' ') { v = v * 10 + c - '0'; c = read(); }
            return neg ? -v : v;
        }
        int nextInt() throws IOException { return (int) nextLong(); }
    }
}
