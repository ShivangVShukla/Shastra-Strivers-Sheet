import java.io.*;
import java.util.*;

public class Solution {

    static long atMostK(int[] nums, int k) {
        if (k <= 0) {
            return 0;
        }

        HashMap<Integer, Integer> freq = new HashMap<>();
        int left = 0;
        int distinct = 0;
        long count = 0;

        for (int right = 0; right < nums.length; right++) {
            int value = nums[right];

            if (!freq.containsKey(value) || freq.get(value) == 0) {
                distinct++;
            }

            freq.put(value, freq.getOrDefault(value, 0) + 1);

            while (distinct > k) {
                int leftValue = nums[left++];
                int newFreq = freq.get(leftValue) - 1;

                if (newFreq == 0) {
                    freq.remove(leftValue);
                    distinct--;
                } else {
                    freq.put(leftValue, newFreq);
                }
            }

            count += right - left + 1L;
        }

        return count;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        long answer = atMostK(nums, k) - atMostK(nums, k - 1);
        System.out.println(answer);
    }
}
