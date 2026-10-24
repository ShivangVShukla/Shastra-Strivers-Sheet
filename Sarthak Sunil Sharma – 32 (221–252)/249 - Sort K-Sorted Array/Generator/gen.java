import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws Exception {
        Random random = new Random(249);
        File dir = new File("../Testcases/Input");
        dir.mkdirs();

        for (int tc = 0; tc < 100; tc++) {
            int n = 1 + random.nextInt(100);
            int k = random.nextInt(n);
            int[] sorted = new int[n];
            for (int i = 0; i < n; i++) sorted[i] = random.nextInt(401) - 200;
            Arrays.sort(sorted);

            int[] arr = sorted.clone();
            // Shuffling only within blocks of size k+1 keeps each element
            // within at most k positions of its sorted position.
            int start = 0;
            while (start < n) {
                int end = Math.min(n, start + k + 1);
                for (int i = end - 1; i > start; i--) {
                    int j = start + random.nextInt(i - start + 1);
                    int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
                }
                start = end;
            }

            try (PrintWriter out = new PrintWriter(new File(dir, String.format("input%02d.txt", tc)))) {
                out.println(n + " " + k);
                for (int i = 0; i < n; i++) {
                    if (i > 0) out.print(" ");
                    out.print(arr[i]);
                }
                out.println();
            }
        }
    }
}
