import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws Exception {
        Random random = new Random(252);
        File dir = new File("../Testcases/Input");
        dir.mkdirs();

        for (int tc = 0; tc < 100; tc++) {
            int m = 1 + random.nextInt(100);
            int cooldown = random.nextInt(21);
            try (PrintWriter out = new PrintWriter(new File(dir, String.format("input%02d.txt", tc)))) {
                out.println(m + " " + cooldown);
                for (int i = 0; i < m; i++) {
                    if (i > 0) out.print(" ");
                    out.print((char)('A' + random.nextInt(6)));
                }
                out.println();
            }
        }
    }
}
