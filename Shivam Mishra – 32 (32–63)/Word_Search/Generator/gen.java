
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static final Random random = new Random(20261026);
    static final String CHARS = "ABCD";

    static class TestCase {
        char[][] board;
        String word;

        TestCase(char[][] board, String word) {
            this.board = board;
            this.word = word;
        }
    }

    static TestCase generateTest(int id) {
        int n, m;

        if (id == 1) {
            return new TestCase(
                new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
                },
                "ABCCED"
            );
        }

        if (id == 2) {
            return new TestCase(
                new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
                },
                "SEE"
            );
        }

        if (id == 3) {
            return new TestCase(
                new char[][]{
                    {'A', 'B', 'C'},
                    {'D', 'E', 'F'}
                },
                "ABCB"
            );
        }

        n = 1 + random.nextInt(6);
        m = 1 + random.nextInt(6);

        char[][] board = new char[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] = CHARS.charAt(
                    random.nextInt(CHARS.length())
                );
            }
        }

        // Guarantee a valid path for selected test cases.
        if (id % 2 == 0) {
            List<int[]> path = new ArrayList<>();
            boolean[][] visited = new boolean[n][m];

            int x = random.nextInt(n);
            int y = random.nextInt(m);

            path.add(new int[]{x, y});
            visited[x][y] = true;

            int targetLength = 1 + random.nextInt(
                Math.min(10, n * m)
            );

            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};

            while (path.size() < targetLength) {
                int[] last = path.get(path.size() - 1);
                List<int[]> choices = new ArrayList<>();

                for (int k = 0; k < 4; k++) {
                    int nx = last[0] + dx[k];
                    int ny = last[1] + dy[k];

                    if (nx >= 0 && nx < n &&
                        ny >= 0 && ny < m &&
                        !visited[nx][ny]) {
                        choices.add(new int[]{nx, ny});
                    }
                }

                if (choices.isEmpty()) break;

                int[] next = choices.get(
                    random.nextInt(choices.size())
                );

                visited[next[0]][next[1]] = true;
                path.add(next);
            }

            StringBuilder word = new StringBuilder();

            for (int[] cell : path) {
                char c = CHARS.charAt(
                    random.nextInt(CHARS.length())
                );

                board[cell[0]][cell[1]] = c;
                word.append(c);
            }

            return new TestCase(board, word.toString());
        }

        // Random word: may or may not exist.
        int len = 1 + random.nextInt(
            Math.min(10, n * m)
        );

        StringBuilder word = new StringBuilder();

        for (int i = 0; i < len; i++) {
            word.append(
                CHARS.charAt(random.nextInt(CHARS.length()))
            );
        }

        return new TestCase(board, word.toString());
    }

    static boolean dfs(char[][] board, String word,
                       int x, int y, int idx,
                       boolean[][] visited) {

        if (idx == word.length()) return true;

        int n = board.length;
        int m = board[0].length;

        if (x < 0 || x >= n || y < 0 || y >= m) {
            return false;
        }

        if (visited[x][y] ||
            board[x][y] != word.charAt(idx)) {
            return false;
        }

        visited[x][y] = true;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int k = 0; k < 4; k++) {
            if (dfs(board, word,
                    x + dx[k], y + dy[k],
                    idx + 1, visited)) {
                visited[x][y] = false;
                return true;
            }
        }

        visited[x][y] = false;
        return false;
    }

    static boolean solve(TestCase tc) {
        char[][] board = tc.board;
        String word = tc.word;

        if (word.isEmpty()) return true;

        int n = board.length;
        int m = board[0].length;

        if (word.length() > n * m) return false;

        boolean[][] visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == word.charAt(0) &&
                    dfs(board, word, i, j, 0, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    static void writeInput(Path file, TestCase tc)
            throws IOException {

        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            int n = tc.board.length;
            int m = tc.board[0].length;

            bw.write(n + " " + m);
            bw.newLine();

            for (int i = 0; i < n; i++) {
                bw.write(new String(tc.board[i]));
                bw.newLine();
            }

            bw.write(tc.word);
            bw.newLine();
        }
    }

    static void writeOutput(Path file, boolean answer)
            throws IOException {

        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            bw.write(answer ? "true" : "false");
            bw.newLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("inp");
        Path outputDir = Paths.get("out");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int i = 1; i <= 101; i++) {
            TestCase tc = generateTest(i);

            String name = String.format(
                "testcase_%03d.txt", i
            );

            Path inputFile = inputDir.resolve(name);
            Path outputFile = outputDir.resolve(name);

            writeInput(inputFile, tc);
            writeOutput(outputFile, solve(tc));
        }

        System.out.println(
            "Generated 101 Word Search test cases."
        );
        System.out.println("Input directory: inp/");
        System.out.println("Output directory: out/");
    }
}
