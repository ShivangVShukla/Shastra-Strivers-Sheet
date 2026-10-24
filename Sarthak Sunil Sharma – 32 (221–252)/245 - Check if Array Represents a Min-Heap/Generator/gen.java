import java.util.*;

public class gen {
    static Random random = new Random(245);

    static long[] generateMinHeap(int n) {
        long[] heap = new long[n];

        for (int i = 0; i < n; i++) {
            heap[i] = random.nextInt(100000);
        }

        // Build a valid min heap using bottom-up heapify.
        for (int i = n / 2 - 1; i >= 0; i--) {
            int current = i;

            while (true) {
                int left = 2 * current + 1;
                int right = 2 * current + 2;
                int smallest = current;

                if (left < n && heap[left] < heap[smallest]) {
                    smallest = left;
                }

                if (right < n && heap[right] < heap[smallest]) {
                    smallest = right;
                }

                if (smallest == current) {
                    break;
                }

                long temp = heap[current];
                heap[current] = heap[smallest];
                heap[smallest] = temp;

                current = smallest;
            }
        }

        return heap;
    }

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            int n = 1 + random.nextInt(100);

            long[] arr;

            if (tc % 2 == 0) {
                arr = generateMinHeap(n);
            } else {
                arr = new long[n];

                for (int i = 0; i < n; i++) {
                    arr[i] = random.nextInt(100000);
                }
            }

            System.out.println(n);

            for (int i = 0; i < n; i++) {
                System.out.print(arr[i]);
                if (i + 1 < n) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }
}
