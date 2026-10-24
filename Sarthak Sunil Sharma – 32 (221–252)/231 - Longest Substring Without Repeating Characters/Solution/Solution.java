import java.io.*;
import java.util.*;

public class Solution {
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int left = 0;
        int answer = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);

            if (lastSeen.containsKey(ch)) {
                left = Math.max(left, lastSeen.get(ch) + 1);
            }

            lastSeen.put(ch, right);
            answer = Math.max(answer, right - left + 1);
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        if (s == null) {
            s = "";
        }

        System.out.println(lengthOfLongestSubstring(s));
    }
}
