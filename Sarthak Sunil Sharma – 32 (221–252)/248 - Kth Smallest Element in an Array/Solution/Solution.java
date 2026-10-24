import java.io.*;
import java.util.*;

public class Solution {
    static class MaxHeap {
        ArrayList<Integer> heap = new ArrayList<>();

        void add(int x) {
            heap.add(x);
            int i = heap.size() - 1;
            while (i > 0) {
                int p = (i - 1) / 2;
                if (heap.get(p) >= heap.get(i)) break;
                swap(p, i);
                i = p;
            }
        }

        int removeMax() {
            int ans = heap.get(0);
            int last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }
            return ans;
        }

        int peek() {
            return heap.get(0);
        }

        int size() {
            return heap.size();
        }

        void heapifyDown(int i) {
            while (true) {
                int largest = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;

                if (left < heap.size() && heap.get(left) > heap.get(largest))
                    largest = left;
                if (right < heap.size() && heap.get(right) > heap.get(largest))
                    largest = right;

                if (largest == i) break;
                swap(i, largest);
                i = largest;
            }
        }

        void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        MaxHeap heap = new MaxHeap();

        for (int i = 0; i < n; i++) {
            heap.add(Integer.parseInt(st.nextToken()));
            if (heap.size() > k) heap.removeMax();
        }

        System.out.println(heap.peek());
    }
}
