import java.util.*;

public class gen {
    static Random random = new Random(239);

    static String randomString(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append((char)('a' + random.nextInt(26)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            int n;
            int k;
            String s;

            if (tc == 0) {
                s = "a";
                n = 1;
                k = 0;
            } else if (tc == 1) {
                s = "aaaaaa";
                n = 6;
                k = 1;
            } else if (tc == 2) {
                s = "eceba";
                n = 5;
                k = 2;
            } else if (tc == 3) {
                s = "abcaaa";
                n = 6;
                k = 1;
            } else if (tc == 4) {
                s = "abcdefghijklmnopqrstuvwxyz";
                n = 26;
                k = 26;
            } else {
                n = 1 + random.nextInt(1000);
                k = random.nextInt(27);
                s = randomString(n);
            }

            System.out.println(n + " " + k);
            System.out.println(s);
        }
    }
}
