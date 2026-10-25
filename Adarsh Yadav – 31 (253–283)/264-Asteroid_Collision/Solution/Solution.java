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

            // Stack of surviving asteroids
            int[] stack = new int[n];
            int top = 0;

            for (int i = 0; i < n; i++) {
                st.nextToken();
                int cur = (int) st.nval;
                boolean alive = true;

                // A collision happens only when the top moves right and cur moves left
                while (alive && cur < 0 && top > 0 && stack[top - 1] > 0) {
                    int right = stack[top - 1];
                    if (right < -cur) {
                        top--;              // top explodes, cur keeps moving
                    } else if (right == -cur) {
                        top--;              // both explode
                        alive = false;
                    } else {
                        alive = false;      // cur explodes
                    }
                }

                if (alive) {
                    stack[top++] = cur;
                }
            }

            for (int i = 0; i < top; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(stack[i]);
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
