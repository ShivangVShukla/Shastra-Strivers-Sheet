import java.io.*;
import java.util.*;

public class Solution {
    private static int[] calculateSpan(long[] prices) {
        int n = prices.length;
        int[] span = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            while (top >= 0 && prices[stack[top]] <= prices[i]) {
                top--;
            }

            span[i] = (top < 0) ? i + 1 : i - stack[top];
            stack[++top] = i;
        }
        return span;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String first = fs.next();
        if (first == null) return;
        int t = Integer.parseInt(first);
        StringBuilder out = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            int n = fs.nextInt();
            long[] prices = new long[n];
            for (int i = 0; i < n; i++) prices[i] = fs.nextLong();

            int[] span = calculateSpan(prices);
            for (int i = 0; i < n; i++) {
                if (i > 0) out.append(' ');
                out.append(span[i]);
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
        long nextLong() throws IOException { return Long.parseLong(next()); }
    }
}
