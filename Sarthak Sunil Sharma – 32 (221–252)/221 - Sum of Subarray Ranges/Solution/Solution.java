import java.io.*;
import java.util.*;

public class Solution {
    static long subArrayRanges(int[] nums) {
        int n = nums.length;
        long answer = 0;
        int[] stack = new int[n];
        int top;

        // Maximum contribution.
        top = -1;
        for (int i = 0; i <= n; i++) {
            while (top >= 0 && (i == n || nums[stack[top]] <= nums[i])) {
                int mid = stack[top--];
                int left = top >= 0 ? stack[top] : -1;
                int right = i;
                answer += (long) nums[mid] * (mid - left) * (right - mid);
            }
            if (i < n) stack[++top] = i;
        }

        // Minimum contribution.
        top = -1;
        for (int i = 0; i <= n; i++) {
            while (top >= 0 && (i == n || nums[stack[top]] >= nums[i])) {
                int mid = stack[top--];
                int left = top >= 0 ? stack[top] : -1;
                int right = i;
                answer -= (long) nums[mid] * (mid - left) * (right - mid);
            }
            if (i < n) stack[++top] = i;
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] nums = new int[n];
        int index = 0;
        while (index < n) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && index < n) {
                nums[index++] = Integer.parseInt(st.nextToken());
            }
        }
        System.out.println(subArrayRanges(nums));
    }
}
