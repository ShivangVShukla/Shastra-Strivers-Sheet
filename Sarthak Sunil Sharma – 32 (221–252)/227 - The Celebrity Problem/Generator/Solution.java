import java.io.*;
import java.util.*;

public class Solution {
    static int findCelebrity(int[][] matrix) {
        int n = matrix.length;
        int candidate = 0;

        for (int i = 1; i < n; i++) {
            if (matrix[candidate][i] == 1) {
                candidate = i;
            }
        }

        for (int i = 0; i < n; i++) {
            if (i == candidate) continue;

            if (matrix[candidate][i] == 1 ||
                matrix[i][candidate] == 0) {
                return -1;
            }
        }

        return candidate;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();

        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = fs.nextInt();
            }
        }

        System.out.println(findCelebrity(matrix));
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
