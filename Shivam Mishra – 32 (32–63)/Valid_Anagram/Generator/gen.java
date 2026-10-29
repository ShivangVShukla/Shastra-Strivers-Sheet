
import java.io.*;
import java.util.*;

public class gen {
    static final Random random = new Random(20261026);
    static final int TOTAL = 101;

    static class TestCase {
        String s, t;

        TestCase(String s, String t) {
            this.s = s;
            this.t = t;
        }

        String key() {
            return s + "|" + t;
        }
    }

    static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }

        return sb.toString();
    }

    static String shuffle(String s) {
        char[] chars = s.toCharArray();

        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }

    static TestCase makeCase(int length, boolean anagram) {
        String s = randomString(length);
        String t = shuffle(s);

        if (!anagram) {
            int pos = random.nextInt(t.length());
            char oldChar = t.charAt(pos);

            char newChar = (char) ('a' + random.nextInt(26));

            while (newChar == oldChar) {
                newChar = (char) ('a' + random.nextInt(26));
            }

            t = t.substring(0, pos) + newChar
                    + t.substring(pos + 1);
        }

        return new TestCase(s, t);
    }

    static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        for (char c : t.toCharArray()) {
            freq[c - 'a']--;

            if (freq[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        Set<String> used = new HashSet<>();
        List<TestCase> cases = new ArrayList<>();

        // Fixed test cases
        cases.add(new TestCase("anagram", "nagaram"));
        cases.add(new TestCase("dog", "cat"));
        cases.add(new TestCase("a", "a"));
        cases.add(new TestCase("a", "b"));
        cases.add(new TestCase("listen", "silent"));
        cases.add(new TestCase("rat", "car"));
        cases.add(new TestCase("aabb", "bbaa"));
        cases.add(new TestCase("aabb", "abbb"));

        // Large test cases
        cases.add(makeCase(50000, true));
        cases.add(makeCase(50000, false));

        for (TestCase tc : cases) {
            used.add(tc.key());
        }

        // Generate the remaining unique test cases
        while (cases.size() < TOTAL) {
            int length;

            if (cases.size() == TOTAL - 1) {
                length = 50000;
            } else {
                length = 1 + random.nextInt(200);
            }

            boolean anagram = random.nextBoolean();
            TestCase tc = makeCase(length, anagram);

            if (used.add(tc.key())) {
                cases.add(tc);
            }
        }

        // Create separate input and output directories
        File inputDir = new File("input");
        File outputDir = new File("output");

        if (!inputDir.exists() && !inputDir.mkdirs()) {
            throw new IOException("Could not create input directory");
        }

        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IOException("Could not create output directory");
        }

        // Write all 101 input and output files
        for (int i = 0; i < TOTAL; i++) {
            TestCase tc = cases.get(i);
            int number = i + 1;

            File inputFile = new File(
                    inputDir, "inp" + number + ".txt");

            File outputFile = new File(
                    outputDir, "out" + number + ".txt");

            try (PrintWriter in = new PrintWriter(inputFile)) {
                in.println(tc.s);
                in.println(tc.t);
            }

            try (PrintWriter out = new PrintWriter(outputFile)) {
                out.println(isAnagram(tc.s, tc.t)
                        ? "true" : "false");
            }
        }

        System.out.println("Generated " + TOTAL + " test cases.");
        System.out.println("Input files:  "
                + inputDir.getAbsolutePath());
        System.out.println("Output files: "
                + outputDir.getAbsolutePath());
    }
}
