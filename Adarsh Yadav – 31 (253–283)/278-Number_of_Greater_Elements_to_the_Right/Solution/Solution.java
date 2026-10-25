import java.io.*;

public class Solution {
    private static int[] countGreaterToRight(int[] arr, int[] indices) {
        int[] answers = new int[indices.length];

        for (int q = 0; q < indices.length; q++) {
            int index = indices[q];
            int count = 0;
            for (int j = index + 1; j < arr.length; j++) {
                if (arr[j] > arr[index]) {
                    count++;
                }
            }
            answers[q] = count;
        }
        return answers;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        String first = fs.next();
        if (first == null) return;
        int t = Integer.parseInt(first);
        StringBuilder out = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            int n = fs.nextInt();
            int q = fs.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = fs.nextInt();
            int[] indices = new int[q];
            for (int i = 0; i < q; i++) indices[i] = fs.nextInt();

            int[] answers = countGreaterToRight(arr, indices);
            for (int i = 0; i < answers.length; i++) {
                if (i > 0) out.append(' ');
                out.append(answers[i]);
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
