import java.io.*;
import java.util.*;

public class Solution {
    private static long largestHistogram(int[] heights) {
        int n = heights.length;
        int[] stack = new int[n + 1];
        int top = -1;
        long best = 0;

        for (int i = 0; i <= n; i++) {
            int current = (i == n) ? 0 : heights[i];
            while (top >= 0 && heights[stack[top]] > current) {
                int h = heights[stack[top--]];
                int width = (top < 0) ? i : i - stack[top] - 1;
                best = Math.max(best, (long) h * width);
            }
            if (i < n) stack[++top] = i;
        }
        return best;
    }

    private static long maximalRectangle(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] heights = new int[n];
        long best = 0;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                heights[c] = (matrix[r][c] == 1) ? heights[c] + 1 : 0;
            }
            best = Math.max(best, largestHistogram(heights));
        }
        return best;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String first = fs.next();
        if (first == null) return;
        int t = Integer.parseInt(first);
        StringBuilder out = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            int m = fs.nextInt();
            int n = fs.nextInt();
            int[][] matrix = new int[m][n];
            for (int r = 0; r < m; r++) {
                for (int c = 0; c < n; c++) matrix[r][c] = fs.nextInt();
            }
            out.append(maximalRectangle(matrix)).append('\n');
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
