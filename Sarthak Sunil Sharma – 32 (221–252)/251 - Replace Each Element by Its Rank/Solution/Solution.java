import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        int[] sorted = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            sorted[i] = arr[i];
        }

        Arrays.sort(sorted);
        HashMap<Integer, Integer> rank = new HashMap<>();
        int nextRank = 1;
        for (int value : sorted) {
            if (!rank.containsKey(value)) rank.put(value, nextRank++);
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) answer.append(' ');
            answer.append(rank.get(arr[i]));
        }
        System.out.println(answer);
    }
}
