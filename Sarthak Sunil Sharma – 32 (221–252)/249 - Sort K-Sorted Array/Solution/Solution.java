import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        StringBuilder answer = new StringBuilder();

        int initial = Math.min(n, k + 1);
        for (int i = 0; i < initial; i++) minHeap.offer(Integer.parseInt(st.nextToken()));

        for (int i = initial; i < n; i++) {
            answer.append(minHeap.poll()).append(' ');
            minHeap.offer(Integer.parseInt(st.nextToken()));
        }

        while (!minHeap.isEmpty()) {
            answer.append(minHeap.poll());
            if (!minHeap.isEmpty()) answer.append(' ');
        }
        System.out.println(answer);
    }
}
