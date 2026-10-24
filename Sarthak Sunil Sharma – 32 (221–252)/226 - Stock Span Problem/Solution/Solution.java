import java.io.*;
import java.util.*;

public class Solution {
    static int[] calculateSpan(int[] price) {
        int n = price.length;
        int[] span = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && price[stack.peek()] <= price[i]) {
                stack.pop();
            }

            span[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }

        return span;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();

        int[] price = new int[n];
        for (int i = 0; i < n; i++) price[i] = fs.nextInt();

        int[] span = calculateSpan(price);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(span[i]);
        }

        System.out.println(sb);
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
