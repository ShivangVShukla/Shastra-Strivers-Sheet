import java.io.*;
import java.util.*;

public class Solution {
    static long solve(int[] h) {
        Deque<Integer> st = new ArrayDeque<>();
        long ans = 0;
        for (int i = 0; i <= h.length; i++) {
            int cur = i == h.length ? 0 : h[i];
            while (!st.isEmpty() && h[st.peek()] >= cur) {
                int height = h[st.pop()];
                int left = st.isEmpty() ? -1 : st.peek();
                ans = Math.max(ans, (long) height * (i - left - 1));
            }
            if (i < h.length) st.push(i);
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] h = new int[n];
        int p = 0;
        while (p < n) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer s = new StringTokenizer(line);
            while (s.hasMoreTokens() && p < n) h[p++] = Integer.parseInt(s.nextToken());
        }
        System.out.println(solve(h));
    }
}
