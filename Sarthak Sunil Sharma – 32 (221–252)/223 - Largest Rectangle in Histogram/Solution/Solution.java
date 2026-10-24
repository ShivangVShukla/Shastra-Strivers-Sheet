import java.io.*;
import java.util.*;

public class Solution {
    public static long largestRectangleArea(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        long best = 0;
        for (int i = 0; i <= n; i++) {
            int current = (i == n) ? 0 : heights[i];
            while (!stack.isEmpty() && heights[stack.peek()] >= current) {
                int height = heights[stack.pop()];
                int left = stack.isEmpty() ? -1 : stack.peek();
                long width = i - left - 1L;
                best = Math.max(best, height * width);
            }
            if (i < n) stack.push(i);
        }
        return best;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] heights = new int[n];
        int idx = 0;
        while (idx < n) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && idx < n) heights[idx++] = Integer.parseInt(st.nextToken());
        }
        System.out.println(largestRectangleArea(heights));
    }
}
