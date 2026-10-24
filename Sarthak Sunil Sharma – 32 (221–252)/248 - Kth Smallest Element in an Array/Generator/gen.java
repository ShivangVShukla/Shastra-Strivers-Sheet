import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws Exception {
        Random random = new Random(248);
        for (int tc = 0; tc < 100; tc++) {
            int n = 1 + random.nextInt(100);
            int k = 1 + random.nextInt(n);

            StringBuilder sb = new StringBuilder();
            sb.append(n).append(' ').append(k).append('\n');

            for (int i = 0; i < n; i++) {
                int value = random.nextInt(201) - 100;
                if (i > 0) sb.append(' ');
                sb.append(value);
            }
            sb.append('\n');

            File dir = new File("../Testcases/Input");
            dir.mkdirs();
            try (PrintWriter out = new PrintWriter(new File(dir, String.format("input%02d.txt", tc)))) {
                out.print(sb);
            }
        }
    }
}
