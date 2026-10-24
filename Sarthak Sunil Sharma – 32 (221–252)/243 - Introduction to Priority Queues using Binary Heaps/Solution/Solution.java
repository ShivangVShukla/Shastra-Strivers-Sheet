import java.io.*;
import java.util.*;

public class Solution {
    static ArrayList<Long> heap = new ArrayList<>();

    static void insert(long value) {
        heap.add(value);
        int i = heap.size() - 1;

        while (i > 0) {
            int parent = (i - 1) / 2;

            if (heap.get(parent) <= heap.get(i)) {
                break;
            }

            swap(parent, i);
            i = parent;
        }
    }

    static long removeMin() {
        long result = heap.get(0);
        int last = heap.size() - 1;

        if (last == 0) {
            heap.remove(last);
            return result;
        }

        heap.set(0, heap.get(last));
        heap.remove(last);

        int i = 0;

        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }

            if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest == i) {
                break;
            }

            swap(i, smallest);
            i = smallest;
        }

        return result;
    }

    static long peek() {
        return heap.get(0);
    }

    static void swap(int i, int j) {
        long temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(br.readLine());
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                insert(Long.parseLong(st.nextToken()));
            } else if (type == 2) {
                if (heap.isEmpty()) {
                    out.append("EMPTY\n");
                } else {
                    out.append(removeMin()).append('\n');
                }
            } else if (type == 3) {
                if (heap.isEmpty()) {
                    out.append("EMPTY\n");
                } else {
                    out.append(peek()).append('\n');
                }
            } else if (type == 4) {
                out.append(heap.size()).append('\n');
            }
        }

        System.out.print(out);
    }
}
