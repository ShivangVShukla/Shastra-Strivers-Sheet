import java.io.*;
import java.util.*;

public class Solution {
    static class Node {
        int value, listIndex, elementIndex;
        Node(int value, int listIndex, int elementIndex) {
            this.value = value;
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int m = Integer.parseInt(br.readLine().trim());

        int[][] lists = new int[m][];
        PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b) -> {
            int cmp = Integer.compare(a.value, b.value);
            if (cmp != 0) return cmp;
            cmp = Integer.compare(a.listIndex, b.listIndex);
            if (cmp != 0) return cmp;
            return Integer.compare(a.elementIndex, b.elementIndex);
        });

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int len = Integer.parseInt(st.nextToken());
            lists[i] = new int[len];
            for (int j = 0; j < len; j++) lists[i][j] = Integer.parseInt(st.nextToken());
            if (len > 0) minHeap.offer(new Node(lists[i][0], i, 0));
        }

        StringBuilder answer = new StringBuilder();
        while (!minHeap.isEmpty()) {
            Node cur = minHeap.poll();
            if (answer.length() > 0) answer.append(' ');
            answer.append(cur.value);

            int nextIndex = cur.elementIndex + 1;
            if (nextIndex < lists[cur.listIndex].length) {
                minHeap.offer(new Node(lists[cur.listIndex][nextIndex], cur.listIndex, nextIndex));
            }
        }
        System.out.println(answer);
    }
}
