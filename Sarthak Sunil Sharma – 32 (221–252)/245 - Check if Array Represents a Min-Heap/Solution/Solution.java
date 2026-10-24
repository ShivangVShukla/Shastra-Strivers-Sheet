import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        StringTokenizer st =
            new StringTokenizer(br.readLine());

        long[] arr = new long[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        for (int i = 0; i < n / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && arr[i] > arr[left]) {
                System.out.println("NO");
                return;
            }

            if (right < n && arr[i] > arr[right]) {
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");
    }
}
