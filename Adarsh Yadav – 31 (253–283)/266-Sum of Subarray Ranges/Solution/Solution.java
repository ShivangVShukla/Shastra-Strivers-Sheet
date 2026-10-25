import java.io.*;
import java.util.*;

public class Solution {

    static long subArrayRanges(int[] nums) {
        int n = nums.length;
        int[] prevGreater = new int[n];
        int[] nextGreaterOrEqual = new int[n];
        int[] prevSmaller = new int[n];
        int[] nextSmallerOrEqual = new int[n];
        int[] stack = new int[n];
        int top;

        // Previous strictly greater element.
        top = 0;
        for (int i = 0; i < n; i++) {
            while (top > 0 && nums[stack[top - 1]] <= nums[i]) {
                top--;
            }
            prevGreater[i] = (top == 0) ? -1 : stack[top - 1];
            stack[top++] = i;
        }

        // Next greater-than-or-equal element.
        top = 0;
        for (int i = n - 1; i >= 0; i--) {
            while (top > 0 && nums[stack[top - 1]] < nums[i]) {
                top--;
            }
            nextGreaterOrEqual[i] = (top == 0) ? n : stack[top - 1];
            stack[top++] = i;
        }

        // Previous strictly smaller element.
        top = 0;
        for (int i = 0; i < n; i++) {
            while (top > 0 && nums[stack[top - 1]] >= nums[i]) {
                top--;
            }
            prevSmaller[i] = (top == 0) ? -1 : stack[top - 1];
            stack[top++] = i;
        }

        // Next smaller-than-or-equal element.
        top = 0;
        for (int i = n - 1; i >= 0; i--) {
            while (top > 0 && nums[stack[top - 1]] > nums[i]) {
                top--;
            }
            nextSmallerOrEqual[i] = (top == 0) ? n : stack[top - 1];
            stack[top++] = i;
        }

        long maximumTotal = 0L;
        long minimumTotal = 0L;

        for (int i = 0; i < n; i++) {
            long maxCount = (long) (i - prevGreater[i])
                    * (nextGreaterOrEqual[i] - i);
            long minCount = (long) (i - prevSmaller[i])
                    * (nextSmallerOrEqual[i] - i);

            maximumTotal += maxCount * nums[i];
            minimumTotal += minCount * nums[i];
        }

        return maximumTotal - minimumTotal;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        String firstLine = br.readLine();
        if (firstLine == null || firstLine.trim().isEmpty()) {
            return;
        }

        int t = Integer.parseInt(firstLine.trim());
        for (int tc = 0; tc < t; tc++) {
            int n = Integer.parseInt(br.readLine().trim());
            int[] nums = new int[n];

            int filled = 0;
            while (filled < n) {
                String line = br.readLine();
                if (line == null) {
                    throw new EOFException("Not enough array values in input.");
                }
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens() && filled < n) {
                    nums[filled++] = Integer.parseInt(st.nextToken());
                }
            }

            output.append(subArrayRanges(nums)).append('\n');
        }

        System.out.print(output);
    }
}
