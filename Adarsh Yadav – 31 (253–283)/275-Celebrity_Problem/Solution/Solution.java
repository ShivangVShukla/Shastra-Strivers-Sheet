import java.io.*;
import java.util.*;

public class Solution {
    private static int findCelebrity(int[][] matrix) {
        int n = matrix.length;
        int candidate = 0;

        for (int person = 1; person < n; person++) {
            if (matrix[candidate][person] == 1) {
                candidate = person;
            }
        }

        for (int person = 0; person < n; person++) {
            if (person == candidate) continue;
            if (matrix[candidate][person] == 1 || matrix[person][candidate] == 0) {
                return -1;
            }
        }
        return candidate;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String first = fs.next();
        if (first == null) return;
        int t = Integer.parseInt(first);
        StringBuilder out = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            int n = fs.nextInt();
            int[][] matrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) matrix[i][j] = fs.nextInt();
            }
            out.append(findCelebrity(matrix)).append('\n');
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
