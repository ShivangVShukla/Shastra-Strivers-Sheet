import java.io.*;
import java.util.*;

public class Solution {
    static int longestRepeatingCharacterReplacement(String s, int k) {
        int[] freq = new int[26];
        int left = 0, maxFreq = 0, ans = 0;

        for (int right = 0; right < s.length(); right++) {
            int idx = s.charAt(right) - 'A';
            freq[idx]++;
            maxFreq = Math.max(maxFreq, freq[idx]);

            while (right - left + 1 - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        String kLine = br.readLine();
        if (s == null) s = "";
        int k = Integer.parseInt(kLine.trim());
        System.out.println(longestRepeatingCharacterReplacement(s, k));
    }
}
