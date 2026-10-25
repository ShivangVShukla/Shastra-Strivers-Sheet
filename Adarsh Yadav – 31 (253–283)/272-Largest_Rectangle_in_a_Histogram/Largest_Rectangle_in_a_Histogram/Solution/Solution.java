import java.io.*;
import java.util.*;

public class Solution {
    private static long largestRectangle(int[] heights) {
        int n = heights.length;
        int[] stack = new int[n + 1];
        int top = -1;
        long maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            while (top >= 0 && heights[stack[top]] > currentHeight) {
                int height = heights[stack[top--]];
                int width = (top < 0) ? i : i - stack[top] - 1;
                maxArea = Math.max(maxArea, (long) height * width);
            }

            if (i < n) {
                stack[++top] = i;
            }
        }
        return maxArea;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String first = fs.next();
        if (first == null) return;
        int t = Integer.parseInt(first);
        StringBuilder out = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            int n = fs.nextInt();
            int[] heights = new int[n];
            for (int i = 0; i < n; i++) heights[i] = fs.nextInt();
            out.append(largestRectangle(heights)).append('\n');
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
