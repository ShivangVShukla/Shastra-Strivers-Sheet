
import java.io.*;
import java.util.*;

public class gen {

    static class TestCase {
        String startWord;
        String targetWord;
        List<String> wordList;

        TestCase(String start, String target, List<String> words) {
            startWord = start;
            targetWord = target;
            wordList = words;
        }
    }

    static int solve(TestCase tc) {
        Set<String> words = new HashSet<>(tc.wordList);

        if (!words.contains(tc.targetWord)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distance = new HashMap<>();

        queue.offer(tc.startWord);
        distance.put(tc.startWord, 1);

        while (!queue.isEmpty()) {
            String word = queue.poll();
            int steps = distance.get(word);

            if (word.equals(tc.targetWord)) {
                return steps;
            }

            char[] chars = word.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                char original = chars[i];

                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (ch == original) {
                        continue;
                    }

                    chars[i] = ch;
                    String next = new String(chars);

                    if (words.contains(next)
                            && !distance.containsKey(next)) {
                        distance.put(next, steps + 1);
                        queue.offer(next);
                    }
                }

                chars[i] = original;
            }
        }

        return 0;
    }

    static List<String> buildWords(String start, String target,
                                   int extraCount, Random random) {
        LinkedHashSet<String> words = new LinkedHashSet<>();

        // Create a guaranteed transformation chain.
        char[] current = start.toCharArray();

        for (int i = 0; i < current.length; i++) {
            if (current[i] != target.charAt(i)) {
                current[i] = target.charAt(i);
                words.add(new String(current));
            }
        }

        // Add random words of the same length.
        while (words.size() < extraCount) {
            char[] chars = new char[start.length()];

            for (int i = 0; i < chars.length; i++) {
                chars[i] = (char) ('a' + random.nextInt(26));
            }

            String word = new String(chars);

            if (!word.equals(start)) {
                words.add(word);
            }
        }

        return new ArrayList<>(words);
    }

    static TestCase createCase(int index, Random random) {
        int length = 2 + index % 5;
        char[] startChars = new char[length];
        char[] targetChars = new char[length];

        for (int i = 0; i < length; i++) {
            startChars[i] = (char) ('a' + random.nextInt(26));
            targetChars[i] = startChars[i];
        }

        // Ensure start and target are distinct.
        int changes = 1 + index % length;

        for (int i = 0; i < changes; i++) {
            targetChars[i] = (char) ('a' +
                    (targetChars[i] - 'a' + 1 + random.nextInt(25)) % 26);
        }

        String start = new String(startChars);
        String target = new String(targetChars);

        int extraCount = 5 + index % 20;
        List<String> words =
                buildWords(start, target, extraCount, random);

        // Include unreachable cases as well.
        if (index % 5 == 0) {
            words.remove(target);
        }

        return new TestCase(start, target, words);
    }

    static void writeCase(PrintWriter writer, TestCase tc) {
        writer.println(tc.wordList.size());

        for (String word : tc.wordList) {
            writer.println(word);
        }

        writer.println(tc.startWord);
        writer.println(tc.targetWord);
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";

        if (!new File(baseDir).exists()
                && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "input");
        File outputDir = new File(baseDir, "output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        Random random = new Random(101);

        for (int i = 1; i <= 101; i++) {
            TestCase tc = createCase(i, random);

            try (PrintWriter in = new PrintWriter(
                        new File(inputDir, i + ".txt"));
                 PrintWriter out = new PrintWriter(
                        new File(outputDir, i + ".txt"))) {

                writeCase(in, tc);
                out.println(solve(tc));
            }
        }

        System.out.println("Generated 101 test cases successfully.");
        System.out.println("Input directory: " + inputDir.getPath());
        System.out.println("Output directory: " + outputDir.getPath());
    }
}
