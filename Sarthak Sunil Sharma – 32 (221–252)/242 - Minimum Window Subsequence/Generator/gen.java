import java.util.*;

public class gen {
    static Random random = new Random(242);

    static String randomString(int n) {
        String alphabet = "abc";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            String s1;
            String s2;

            if (tc == 0) {
                s1 = "abcdebdde";
                s2 = "bde";
            } else if (tc == 1) {
                s1 = "abc";
                s2 = "d";
            } else if (tc == 2) {
                s1 = "abc";
                s2 = "abc";
            } else if (tc == 3) {
                s1 = "aaabbbccc";
                s2 = "abc";
            } else if (tc == 4) {
                s1 = "abcabc";
                s2 = "ac";
            } else {
                int n = 3 + random.nextInt(100);
                int m = 1 + random.nextInt(Math.min(8, n));
                s1 = randomString(n);
                s2 = randomString(m);
            }

            System.out.println(s1);
            System.out.println(s2);
        }
    }
}
