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
            Arrays.fill(res, -1);

            // Stack of indices whose next greater element is not found yet.
            // Their values are non-increasing from bottom to top.
            int[] stack = new int[n];
            int top = 0;

            for (int i = 0; i < n; i++) {
                while (top > 0 && arr[stack[top - 1]] < arr[i]) {
                    res[stack[--top]] = arr[i];
                }
                stack[top++] = i;
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
