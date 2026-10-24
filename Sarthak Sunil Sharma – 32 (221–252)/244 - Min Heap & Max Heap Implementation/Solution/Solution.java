import java.io.*;
import java.util.*;

public class Solution {
    static ArrayList<Long> minHeap = new ArrayList<>();
    static ArrayList<Long> maxHeap = new ArrayList<>();

    static void insertMin(long value) {
        minHeap.add(value);
        int i = minHeap.size() - 1;

        while (i > 0) {
            int parent = (i - 1) / 2;
            if (minHeap.get(parent) <= minHeap.get(i)) break;
            swap(minHeap, parent, i);
            i = parent;
        }
    }

    static void insertMax(long value) {
        maxHeap.add(value);
        int i = maxHeap.size() - 1;

        while (i > 0) {
            int parent = (i - 1) / 2;
            if (maxHeap.get(parent) >= maxHeap.get(i)) break;
            swap(maxHeap, parent, i);
            i = parent;
        }
    }

    static long removeMin() {
        long result = minHeap.get(0);
        int last = minHeap.size() - 1;

        if (last == 0) {
            minHeap.remove(last);
            return result;
        }

        minHeap.set(0, minHeap.get(last));
        minHeap.remove(last);
        heapifyDownMin(0);
        return result;
    }

    static long removeMax() {
        long result = maxHeap.get(0);
        int last = maxHeap.size() - 1;

        if (last == 0) {
            maxHeap.remove(last);
            return result;
        }

        maxHeap.set(0, maxHeap.get(last));
        maxHeap.remove(last);
        heapifyDownMax(0);
        return result;
    }

    static void heapifyDownMin(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < minHeap.size() &&
                minHeap.get(left) < minHeap.get(smallest)) {
                smallest = left;
            }

            if (right < minHeap.size() &&
                minHeap.get(right) < minHeap.get(smallest)) {
                smallest = right;
            }

            if (smallest == i) break;

            swap(minHeap, i, smallest);
            i = smallest;
        }
    }

    static void heapifyDownMax(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < maxHeap.size() &&
                maxHeap.get(left) > maxHeap.get(largest)) {
                largest = left;
            }

            if (right < maxHeap.size() &&
                maxHeap.get(right) > maxHeap.get(largest)) {
                largest = right;
            }

            if (largest == i) break;

            swap(maxHeap, i, largest);
            i = largest;
        }
    }

    static void swap(ArrayList<Long> heap, int i, int j) {
        long temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(br.readLine());
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < q; i++) {
            StringTokenizer st =
                new StringTokenizer(br.readLine());

            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                long value = Long.parseLong(st.nextToken());
                insertMin(value);
                insertMax(value);
            } else if (type == 2) {
                if (minHeap.isEmpty()) out.append("EMPTY\n");
                else out.append(removeMin()).append('\n');
            } else if (type == 3) {
                if (maxHeap.isEmpty()) out.append("EMPTY\n");
                else out.append(removeMax()).append('\n');
            } else if (type == 4) {
                if (minHeap.isEmpty()) out.append("EMPTY\n");
                else out.append(minHeap.get(0)).append('\n');
            } else if (type == 5) {
                if (maxHeap.isEmpty()) out.append("EMPTY\n");
                else out.append(maxHeap.get(0)).append('\n');
            } else if (type == 6) {
                out.append(minHeap.size())
                   .append(' ')
                   .append(maxHeap.size())
                   .append('\n');
            }
        }

        System.out.print(out);
    }
}
