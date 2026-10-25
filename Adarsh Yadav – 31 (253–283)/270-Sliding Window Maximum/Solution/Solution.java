import java.io.*;
import java.util.*;

public class Solution {

    static int[] maxSlidingWindow(int[] arr, int k) {
        int n = arr.length;
        int[] result = new int[n - k + 1];
        int[] deque = new int[n];
        int head = 0, tail = 0;
        int out = 0;

        for (int i = 0; i < n; i++) {
            // Remove indices that are outside the current window.
            while (head < tail && deque[head] <= i - k) {
                head++;
            }

            // Keep values in decreasing order from front to back.
            while (head < tail && arr[deque[tail - 1]] <= arr[i]) {
                tail--;
            }

            deque[tail++] = i;

            if (i >= k - 1) {
                result[out++] = arr[deque[head]];
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String first = br.readLine();
        if (first == null || first.trim().isEmpty()) return;

        int t = Integer.parseInt(first.trim());
        StringBuilder out = new StringBuilder();

        for (int tc = 0; tc < t; tc++) {
            StringTokenizer header = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(header.nextToken());
            int k = Integer.parseInt(header.nextToken());

            int[] arr = new int[n];
            int filled = 0;
            while (filled < n) {
                String line = br.readLine();
                if (line == null) throw new EOFException("Not enough array values in input.");
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens() && filled < n) {
                    arr[filled++] = Integer.parseInt(st.nextToken());
                }
            }

            int[] result = maxSlidingWindow(arr, k);
            for (int i = 0; i < result.length; i++) {
                if (i > 0) out.append(' ');
                out.append(result[i]);
            }
            out.append('\n');
        }

        System.out.print(out);
    }
}
