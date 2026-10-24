import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws Exception {
        Random random = new Random(250);
        File dir = new File("../Testcases/Input");
        dir.mkdirs();

        for (int tc = 0; tc < 100; tc++) {
            int m = 1 + random.nextInt(12);
            try (PrintWriter out = new PrintWriter(new File(dir, String.format("input%02d.txt", tc)))) {
                out.println(m);
                for (int i = 0; i < m; i++) {
                    int len = random.nextInt(11);
                    int[] arr = new int[len];
                    for (int j = 0; j < len; j++) arr[j] = random.nextInt(101) - 50;
                    Arrays.sort(arr);
                    out.print(len);
                    for (int x : arr) out.print(" " + x);
                    out.println();
                }
            }
        }
    }
}
