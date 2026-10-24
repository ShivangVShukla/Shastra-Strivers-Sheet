import java.io.*;
import java.util.*;

public class gen {
    static Random random = new Random(237);

    static String randomString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append((char)('a' + random.nextInt(3)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            int n;
            if (tc < 20) n = 3 + tc;
            else if (tc < 50) n = 20 + random.nextInt(81);
            else if (tc < 80) n = 100 + random.nextInt(901);
            else n = 5000 + random.nextInt(5001);

            String s;
            if (tc == 0) s = "abc";
            else if (tc == 1) s = "aaa";
            else if (tc == 2) s = "bbbccc";
            else if (tc == 3) s = "aaacb";
            else if (tc == 4) s = "abcabc";
            else if (tc == 5) s = "a".repeat(n);
            else if (tc == 6) s = "abc".repeat((n + 2) / 3).substring(0, n);
            else s = randomString(n);

            System.out.println(s);
        }
    }
}
