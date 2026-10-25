import java.io.*;
import java.util.*;

public class Solution {

    static String removeKDigits(String s, int k) {
        int n = s.length();
        char[] stack = new char[n];
        int top = 0;
        int remaining = k;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            // A bigger digit followed by a smaller one should be removed
            while (remaining > 0 && top > 0 && stack[top - 1] > c) {
                top--;
                remaining--;
            }
            stack[top++] = c;
        }

        // If removals are left, the digits are non-decreasing: drop from the end
        top -= remaining;

        // Strip leading zeros
        int start = 0;
        while (start < top && stack[start] == '0') {
            start++;
        }

        return start == top ? "0" : new String(stack, start, top - start);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            int k = Integer.parseInt(st.nextToken());
            sb.append(removeKDigits(s, k)).append('\n');
        }

        System.out.print(sb);
    }
}
