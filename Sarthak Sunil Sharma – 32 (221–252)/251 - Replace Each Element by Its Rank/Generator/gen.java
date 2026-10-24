import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws Exception {
        Random random = new Random(251);
        File dir = new File("../Testcases/Input");
        dir.mkdirs();

        for (int tc = 0; tc < 100; tc++) {
            int n = 1 + random.nextInt(100);
            try (PrintWriter out = new PrintWriter(new File(dir, String.format("input%02d.txt", tc)))) {
                out.println(n);
                for (int i = 0; i < n; i++) {
                    if (i > 0) out.print(" ");
                    out.print(random.nextInt(201) - 100);
                }
                out.println();
            }
        }
    }
}
