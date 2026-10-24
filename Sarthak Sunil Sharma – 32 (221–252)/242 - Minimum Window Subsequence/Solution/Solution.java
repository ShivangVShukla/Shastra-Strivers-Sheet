import java.io.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s1 = br.readLine();
        String s2 = br.readLine();

        int n = s1.length();
        int m = s2.length();

        int bestStart = -1;
        int bestLength = Integer.MAX_VALUE;

        int start = 0;

        while (start < n) {
            int i = start;
            int j = 0;

            while (i < n && j < m) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    j++;
                }
                i++;
            }

            if (j < m) {
                break;
            }

            int end = i - 1;

            j = m - 1;
            i = end;

            while (i >= start) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    j--;
                    if (j < 0) {
                        break;
                    }
                }
                i--;
            }

            int windowStart = i;
            int windowLength = end - windowStart + 1;

            if (windowLength < bestLength) {
                bestLength = windowLength;
                bestStart = windowStart;
            }

            start = windowStart + 1;
        }

        if (bestStart == -1) {
            System.out.println("-1");
        } else {
            System.out.println(s1.substring(bestStart, bestStart + bestLength));
        }
    }
}
