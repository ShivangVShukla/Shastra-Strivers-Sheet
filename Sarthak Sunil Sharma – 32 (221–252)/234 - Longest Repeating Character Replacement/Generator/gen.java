import java.io.*;
import java.util.*;

public class gen {
    static class TestCase {
        String s;
        int k;
        TestCase(String s, int k) { this.s = s; this.k = k; }
    }

    static final Random random = new Random(2342026L);

    static String randomString(int n, int alphabet) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append((char)('A' + random.nextInt(alphabet)));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        File inputDir = new File("../Testcases/Input");
        File outputDir = new File("../Testcases/Output");
        inputDir.mkdirs();
        outputDir.mkdirs();
        for (int i = 0; i < 100; i++) {
            int n = 1 + random.nextInt(100);
            int k = random.nextInt(n + 1);
            String s = randomString(n, 1 + random.nextInt(8));
            try (PrintWriter pw = new PrintWriter(new File(inputDir, String.format("input%02d.txt", i)))) {
                pw.println(s);
                pw.println(k);
            }
        }
    }
}
