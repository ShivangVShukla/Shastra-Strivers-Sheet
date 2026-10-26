
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static class TestCase {
        String s;
        List<String> dict;

        TestCase(String s, List<String> dict) {
            this.s = s;
            this.dict = dict;
        }
    }

    static boolean wordBreak(String s, List<String> words) {
        Set<String> dict = new HashSet<>(words);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[s.length()];
    }

    static TestCase createRandomTest(Random random, int index) {
        List<String> dict = new ArrayList<>();
        Set<String> unique = new HashSet<>();

        int wordCount = 2 + random.nextInt(8);

        while (dict.size() < wordCount) {
            int len = 1 + random.nextInt(5);
            StringBuilder word = new StringBuilder();

            for (int i = 0; i < len; i++) {
                word.append((char) ('a' + random.nextInt(4)));
            }

            if (unique.add(word.toString())) {
                dict.add(word.toString());
            }
        }

        StringBuilder s = new StringBuilder();

        if (index % 2 == 0) {
            // Construct a string guaranteed to be segmentable.
            int pieces = 2 + random.nextInt(5);

            for (int i = 0; i < pieces; i++) {
                s.append(dict.get(random.nextInt(dict.size())));
            }
        } else {
            // Generate a random string; its answer is checked separately.
            int len = 5 + random.nextInt(16);

            for (int i = 0; i < len; i++) {
                s.append((char) ('a' + random.nextInt(4)));
            }
        }

        return new TestCase(s.toString(), dict);
    }

    static List<TestCase> generateTests() {
        List<TestCase> tests = new ArrayList<>();

        tests.add(new TestCase(
            "takeuforward",
            Arrays.asList("take", "forward", "you", "u")
        ));

        tests.add(new TestCase(
            "applepineapple",
            Arrays.asList("apple")
        ));

        tests.add(new TestCase(
            "catsanddogs",
            Arrays.asList("and", "dogs", "cats", "animals")
        ));

        tests.add(new TestCase(
            "leetcode",
            Arrays.asList("leet", "code")
        ));

        tests.add(new TestCase(
            "applepenapple",
            Arrays.asList("apple", "pen")
        ));

        tests.add(new TestCase(
            "catsandog",
            Arrays.asList("cats", "dog", "sand", "and", "cat")
        ));

        tests.add(new TestCase(
            "aaaaaaa",
            Arrays.asList("aaaa", "aaa")
        ));

        tests.add(new TestCase(
            "abcd",
            Arrays.asList("a", "abc", "b", "cd")
        ));

        tests.add(new TestCase(
            "aaaaab",
            Arrays.asList("a", "aa", "aaa")
        ));

        tests.add(new TestCase(
            "pineapplepenapple",
            Arrays.asList("apple", "pen", "applepen", "pine", "pineapple")
        ));

        Random random = new Random(20261025L);

        while (tests.size() < 101) {
            tests.add(createRandomTest(random, tests.size()));
        }

        return tests;
    }

    static void writeTest(
        TestCase test,
        Path inputPath,
        Path outputPath
    ) throws IOException {

        StringBuilder input = new StringBuilder();
        input.append(test.s).append('\n');
        input.append(test.dict.size()).append('\n');

        for (String word : test.dict) {
            input.append(word).append('\n');
        }

        boolean answer = wordBreak(test.s, test.dict);

        Files.writeString(inputPath, input.toString());
        Files.writeString(
            outputPath,
            (answer ? "true" : "false") + "\n"
        );
    }

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("Testcases", "input");
        Path outputDir = Paths.get("Testcases", "output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        List<TestCase> tests = generateTests();

        for (int i = 0; i < tests.size(); i++) {
            String fileName = (i + 1) + ".txt";

            writeTest(
                tests.get(i),
                inputDir.resolve(fileName),
                outputDir.resolve(fileName)
            );
        }

        System.out.println(
            "Generated " + tests.size() + " test cases successfully."
        );
        System.out.println("Input folder: " + inputDir);
        System.out.println("Output folder: " + outputDir);
    }
}
