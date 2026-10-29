
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {

    static final int TOTAL = 101;
    static final Random random = new Random(20261026L);

    static class TestCase {
        int n;
        List<int[]> edges;

        TestCase(int n, List<int[]> edges) {
            this.n = n;
            this.edges = edges;
        }
    }

    // Edge [a, b] means b must come before a.
    static List<Integer> findOrder(TestCase tc) {
        List<List<Integer>> adj = new ArrayList<>();
        int[] indegree = new int[tc.n];

        for (int i = 0; i < tc.n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int[] edge : tc.edges) {
            int a = edge[0];
            int b = edge[1];

            adj.get(b).add(a);
            indegree[a]++;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < tc.n; i++) {
            if (indegree[i] == 0) {
                pq.offer(i);
            }
        }

        List<Integer> order = new ArrayList<>();

        while (!pq.isEmpty()) {
            int course = pq.poll();
            order.add(course);

            for (int next : adj.get(course)) {
                indegree[next]--;

                if (indegree[next] == 0) {
                    pq.offer(next);
                }
            }
        }

        if (order.size() != tc.n) {
            return Collections.emptyList();
        }

        return order;
    }

    static TestCase createCase(int n, List<int[]> edges) {
        return new TestCase(n, edges);
    }

    static List<int[]> randomEdges(int n, int maxEdges) {
        List<int[]> edges = new ArrayList<>();
        Set<String> used = new HashSet<>();

        int count = random.nextInt(maxEdges + 1);

        while (edges.size() < count) {
            int a = random.nextInt(n);
            int b = random.nextInt(n);

            String key = a + "," + b;

            if (used.add(key)) {
                edges.add(new int[]{a, b});
            }
        }

        return edges;
    }

    static TestCase generate(int index) {
        // Example 1: a valid chain.
        if (index == 1) {
            return createCase(4, Arrays.asList(
                new int[]{1, 0},
                new int[]{2, 1},
                new int[]{3, 2}
            ));
        }

        // Example 2: contains a cycle.
        if (index == 2) {
            return createCase(4, Arrays.asList(
                new int[]{0, 1},
                new int[]{3, 2},
                new int[]{1, 3},
                new int[]{3, 0}
            ));
        }

        // Empty prerequisite list.
        if (index == 3) {
            return createCase(5, new ArrayList<>());
        }

        // Self-loop: impossible to complete.
        if (index == 4) {
            return createCase(3, Arrays.asList(
                new int[]{1, 0},
                new int[]{2, 2}
            ));
        }

        // Duplicate prerequisites are valid input.
        if (index == 5) {
            return createCase(4, Arrays.asList(
                new int[]{1, 0},
                new int[]{1, 0},
                new int[]{2, 1}
            ));
        }

        int n = 1 + random.nextInt(20);
        int maxEdges = Math.min(n * n, 60);

        return createCase(n, randomEdges(n, maxEdges));
    }

    static void writeCase(
        Path inputDir,
        Path outputDir,
        int index,
        TestCase tc
    ) throws IOException {

        String filename = String.format(
            Locale.ROOT, "testcase_%03d.txt", index
        );

        Path inputFile = inputDir.resolve(filename);
        Path outputFile = outputDir.resolve(filename);

        // Input format: N M, followed by M pairs a b.
        try (BufferedWriter writer = Files.newBufferedWriter(inputFile)) {
            writer.write(tc.n + " " + tc.edges.size());
            writer.newLine();

            for (int[] edge : tc.edges) {
                writer.write(edge[0] + " " + edge[1]);
                writer.newLine();
            }
        }

        List<Integer> answer = findOrder(tc);

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            if (answer.isEmpty()) {
                writer.write("[]");
            } else {
                for (int i = 0; i < answer.size(); i++) {
                    if (i > 0) writer.write(" ");
                    writer.write(String.valueOf(answer.get(i)));
                }
            }
            writer.newLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Path inputDir = Paths.get("inp");
        Path outputDir = Paths.get("out");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        for (int i = 1; i <= TOTAL; i++) {
            TestCase tc = generate(i);
            writeCase(inputDir, outputDir, i, tc);
        }

        System.out.println(
            "Generated " + TOTAL + " test cases in inp/ and out/."
        );
    }
}
