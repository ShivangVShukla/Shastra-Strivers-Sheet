import java.io.*;
import java.util.*;

public class gen {
    static int solve(String s) {
        Map<Character, Integer> last = new HashMap<>();
        int left = 0, ans = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);
            if (last.containsKey(ch)) {
                left = Math.max(left, last.get(ch) + 1);
            }
            last.put(ch, right);
            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }

    static void writeCase(int idx, String s) throws Exception {
        String in = String.format("../Testcases/Input/input%02d.txt", idx);
        String out = String.format("../Testcases/Output/output%02d.txt", idx);

        try (PrintWriter pw = new PrintWriter(new FileWriter(in))) {
            pw.println(s);
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println(solve(s));
        }
    }

    static String randomString(Random rnd, int len, String alphabet) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Random rnd = new Random(2312026L);
        List<String> cases = new ArrayList<>();

        cases.add("");
        cases.add("a");
        cases.add("abcabcbb");
        cases.add("bbbbb");
        cases.add("pwwkew");
        cases.add(" ");
        cases.add("abcdef");
        cases.add("aaaaaa");
        cases.add("abba");
        cases.add("tmmzuxt");
        cases.add("dvdf");
        cases.add("anviaj");
        cases.add("1234567890");
        cases.add("AaBbCcAa");
        cases.add("a b c a");
        cases.add("!@#$%^&*()!");
        cases.add("abcdefghijklmnopqrstuvwxyz");
        cases.add("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");

        while (cases.size() < 100) {
            int idx = cases.size();
            int len;

            if (idx < 45) {
                len = rnd.nextInt(80);
            } else if (idx < 85) {
                len = 100 + rnd.nextInt(900);
            } else {
                len = 5000 + rnd.nextInt(15001);
            }

            String alphabet;
            if (idx % 6 == 0) {
                alphabet = "abcdefghijklmnopqrstuvwxyz";
            } else if (idx % 6 == 1) {
                alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            } else if (idx % 6 == 2) {
                alphabet = "0123456789";
            } else if (idx % 6 == 3) {
                alphabet = "abcde12345!@#$ ";
            } else if (idx % 6 == 4) {
                alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
            } else {
                alphabet = "aabccddeeffgghhiijjkkllmmnn";
            }

            cases.add(randomString(rnd, len, alphabet));
        }

        for (int i = 0; i < 100; i++) {
            writeCase(i, cases.get(i));
        }
    }
}
