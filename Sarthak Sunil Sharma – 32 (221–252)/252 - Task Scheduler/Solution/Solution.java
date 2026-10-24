import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[] freq = new int[26];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            char task = st.nextToken().charAt(0);
            freq[task - 'A']++;
        }

        int maxFreq = 0;
        for (int count : freq) maxFreq = Math.max(maxFreq, count);

        int numMax = 0;
        for (int count : freq) {
            if (count == maxFreq) numMax++;
        }

        long frameLength = (long) (maxFreq - 1) * (n + 1L) + numMax;
        System.out.println(Math.max((long) m, frameLength));
    }
}
