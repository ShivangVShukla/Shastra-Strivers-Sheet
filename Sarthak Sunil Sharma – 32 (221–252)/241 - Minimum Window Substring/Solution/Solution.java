import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        String t = br.readLine();

        int[] need = new int[128];
        int[] window = new int[128];

        for (char c : t.toCharArray()) {
            need[c]++;
        }

        int required = t.length();
        int formed = 0;
        int left = 0;

        int bestStart = -1;
        int bestLength = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            window[c]++;

            if (need[c] > 0 && window[c] <= need[c]) {
                formed++;
            }

            while (formed == required) {
                int currentLength = right - left + 1;

                if (currentLength < bestLength) {
                    bestLength = currentLength;
                    bestStart = left;
                }

                char leftChar = s.charAt(left);
                window[leftChar]--;

                if (need[leftChar] > 0 && window[leftChar] < need[leftChar]) {
                    formed--;
                }

                left++;
            }
        }

        if (bestStart == -1) {
            System.out.println("-1");
        } else {
            System.out.println(s.substring(bestStart, bestStart + bestLength));
        }
    }
}
