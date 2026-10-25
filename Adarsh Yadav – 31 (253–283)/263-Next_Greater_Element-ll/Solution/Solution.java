import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        st.nextToken();
        int t = (int) st.nval;

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            st.nextToken();
            int n = (int) st.nval;

            long[] arr = new long[n];
            for (int i = 0; i < n; i++) {
                st.nextToken();
                arr[i] = (long) st.nval;
            }

            long[] res = new long[n];

            // Walk the array twice (indices 2n-1 down to 0) to simulate the circle.
            // The stack holds candidate values; from bottom to top they are
            // strictly decreasing.
            long[] stack = new long[n];
            int top = 0;

            for (int i = 2 * n - 1; i >= 0; i--) {
                long cur = arr[i % n];

                while (top > 0 && stack[top - 1] <= cur) {
                    top--;
                }

                if (i < n) {
                    res[i] = top == 0 ? -1 : stack[top - 1];
                }

                stack[top++] = cur;
            }

            for (int i = 0; i < n; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(res[i]);
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
