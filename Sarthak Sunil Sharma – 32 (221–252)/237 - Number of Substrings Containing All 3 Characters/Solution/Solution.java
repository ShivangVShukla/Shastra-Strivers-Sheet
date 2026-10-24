import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        int[] last = {-1, -1, -1};
        long answer = 0;

        for (int right = 0; right < s.length(); right++) {
            last[s.charAt(right) - 'a'] = right;
            int minLast = Math.min(last[0], Math.min(last[1], last[2]));

            if (minLast >= 0) {
                answer += minLast + 1L;
            }
        }

        System.out.println(answer);
    }
}
