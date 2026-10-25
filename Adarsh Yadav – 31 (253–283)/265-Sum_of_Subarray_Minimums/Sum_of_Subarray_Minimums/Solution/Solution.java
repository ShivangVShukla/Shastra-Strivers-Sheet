import java.io.*;
import java.util.*;

public class Solution {

    static final long MOD = 1_000_000_007L;

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

            // Each element is counted for the subarrays in which it is the
            // rightmost minimum:
            //   left  = index of the previous element STRICTLY smaller (or -1)
            //   right = index of the next element SMALLER OR EQUAL (or n)
            // The stack holds indices with strictly increasing values.
            int[] stack = new int[n];
            int top = 0;
            long ans = 0;

            for (int i = 0; i <= n; i++) {
                long cur = (i == n) ? 0 : arr[i]; // 0 acts as a sentinel (all values >= 1)

                while (top > 0 && arr[stack[top - 1]] >= cur) {
                    int mid = stack[--top];
                    int left = top == 0 ? -1 : stack[top - 1];
                    long count = (long) (mid - left) * (i - mid);
                    ans = (ans + (count % MOD) * arr[mid]) % MOD;
                }

                stack[top++] = i;
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
