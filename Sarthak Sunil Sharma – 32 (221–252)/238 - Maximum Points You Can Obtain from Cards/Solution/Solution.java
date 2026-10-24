import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int[] cards = new int[n];
        long total = 0;

        for (int i = 0; i < n; i++) {
            cards[i] = Integer.parseInt(st.nextToken());
            total += cards[i];
        }

        int windowSize = n - k;

        if (windowSize == 0) {
            System.out.println(total);
            return;
        }

        long windowSum = 0;

        for (int i = 0; i < windowSize; i++) {
            windowSum += cards[i];
        }

        long minWindow = windowSum;

        for (int right = windowSize; right < n; right++) {
            windowSum += cards[right];
            windowSum -= cards[right - windowSize];
            minWindow = Math.min(minWindow, windowSum);
        }

        System.out.println(total - minWindow);
    }
}
