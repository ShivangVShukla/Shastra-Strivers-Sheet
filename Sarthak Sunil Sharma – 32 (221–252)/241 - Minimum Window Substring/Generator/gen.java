import java.util.*;

public class gen {
    static Random random = new Random(241);

    static String randomString(int n) {
        String alphabet = "abcABC";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            String s;
            String t;

            if (tc == 0) {
                s = "ADOBECODEBANC";
                t = "ABC";
            } else if (tc == 1) {
                s = "a";
                t = "aa";
            } else if (tc == 2) {
                s = "a";
                t = "a";
            } else if (tc == 3) {
                s = "AAABBC";
                t = "AABC";
            } else if (tc == 4) {
                s = "abc";
                t = "abc";
            } else {
                int n = 3 + random.nextInt(100);
                int m = 1 + random.nextInt(Math.min(10, n));
                s = randomString(n);
                t = randomString(m);
            }

            System.out.println(s);
            System.out.println(t);
        }
    }
}
