
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    private static final Random random = new Random(42);

    private static final String[] BASE_WORDS = {
        "apple", "app", "application", "apply",
        "banana", "band", "bandit", "bat",
        "cat", "car", "care", "careful",
        "dog", "dove", "door", "dot",
        "mango", "man", "many", "map",
        "abc", "abcd", "ab", "a",
        "xyz", "xy", "x", "zebra"
    };

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("Testcases/input");
        Path outputDir = Paths.get("Testcases/output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int tc = 1; tc <= 101; tc++) {
            List<String> inputLines = new ArrayList<>();
            List<String> outputLines = new ArrayList<>();

            Map<String, Integer> words = new HashMap<>();
            List<String> operations = new ArrayList<>();

            int q = 30 + random.nextInt(51);

            // Guarantee that every case exercises duplicates,
            // exact-word counting, prefix counting, and erasure.
            operations.add("insert apple");
            operations.add("insert apple");
            operations.add("insert app");
            operations.add("countWordsEqualTo apple");
            operations.add("countWordsStartingWith app");
            operations.add("erase apple");
            operations.add("countWordsEqualTo apple");
            operations.add("countWordsStartingWith app");

            for (int i = operations.size(); i < q; i++) {
                int choice = random.nextInt(100);

                if (choice < 35) {
                    String word = randomWord();
                    operations.add("insert " + word);
                } else if (choice < 55) {
                    String word = randomWord();
                    operations.add("countWordsEqualTo " + word);
                } else if (choice < 75) {
                    String prefix = randomPrefix(words);
                    operations.add("countWordsStartingWith " + prefix);
                } else if (choice < 90 && !words.isEmpty()) {
                    String word = randomExistingWord(words);
                    operations.add("erase " + word);
                } else {
                    String word = randomWord();
                    operations.add("insert " + word);
                }
            }

            inputLines.add(String.valueOf(q));

            for (String operationLine : operations) {
                inputLines.add(operationLine);

                String[] parts = operationLine.split(" ");
                String operation = parts[0];
                String word = parts[1];

                if (operation.equals("insert")) {
                    words.put(word, words.getOrDefault(word, 0) + 1);
                } else if (operation.equals("erase")) {
                    int count = words.getOrDefault(word, 0);

                    if (count > 0) {
                        if (count == 1) {
                            words.remove(word);
                        } else {
                            words.put(word, count - 1);
                        }
                    }
                } else if (operation.equals("countWordsEqualTo")) {
                    outputLines.add(
                        String.valueOf(words.getOrDefault(word, 0))
                    );
                } else if (operation.equals("countWordsStartingWith")) {
                    int count = 0;

                    for (Map.Entry<String, Integer> entry
                            : words.entrySet()) {
                        if (entry.getKey().startsWith(word)) {
                            count += entry.getValue();
                        }
                    }

                    outputLines.add(String.valueOf(count));
                }
            }

            Files.write(
                inputDir.resolve(tc + ".txt"),
                inputLines
            );

            Files.write(
                outputDir.resolve(tc + ".txt"),
                outputLines
            );
        }

        System.out.println("Generated 101 test cases successfully.");
        System.out.println("Input directory: Testcases/input");
        System.out.println("Output directory: Testcases/output");
    }

    private static String randomWord() {
        if (random.nextInt(100) < 70) {
            return BASE_WORDS[random.nextInt(BASE_WORDS.length)];
        }

        int length = 1 + random.nextInt(10);
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < length; i++) {
            word.append((char) ('a' + random.nextInt(26)));
        }

        return word.toString();
    }

    private static String randomPrefix(Map<String, Integer> words) {
        if (!words.isEmpty() && random.nextInt(100) < 70) {
            String word = randomExistingWord(words);
            int length = 1 + random.nextInt(word.length());
            return word.substring(0, length);
        }

        String word = randomWord();
        int length = 1 + random.nextInt(word.length());
        return word.substring(0, length);
    }

    private static String randomExistingWord(
            Map<String, Integer> words) {
        List<String> existing = new ArrayList<>(words.keySet());
        return existing.get(random.nextInt(existing.size()));
    }
}
