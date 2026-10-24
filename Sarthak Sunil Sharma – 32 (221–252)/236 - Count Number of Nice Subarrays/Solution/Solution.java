import java.io.*;
import java.util.*;

public class Solution {
    static long atMost(int[] nums, int k) {
        if (k < 0) return 0;
        int left = 0, odd = 0;
        long count = 0;
        for (int right = 0; right < nums.length; right++) {
            if ((nums[right] & 1) == 1) odd++;
            while (odd > k) {
                if ((nums[left] & 1) == 1) odd--;
                left++;
            }
            count += right - left + 1L;
        }
        return count;
    }
    static long solve(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] nums = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) nums[i] = Integer.parseInt(st.nextToken());
        System.out.println(solve(nums, k));
    }
}
