import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        String s = br.readLine();

        int[] freq = new int[26];
        int left = 0;
        int distinct = 0;
        int answer = 0;

        for (int right = 0; right < n; right++) {
            int idx = s.charAt(right) - 'a';

            if (freq[idx] == 0) {
                distinct++;
            }
            freq[idx]++;

            while (distinct > k) {
                int leftIdx = s.charAt(left) - 'a';
                freq[leftIdx]--;

                if (freq[leftIdx] == 0) {
                    distinct--;
                }
                left++;
            }

            answer = Math.max(answer, right - left + 1);
        }

        System.out.println(answer);
    }
}
