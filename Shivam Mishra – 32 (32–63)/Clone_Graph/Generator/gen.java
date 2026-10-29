
import java.io.*;
import java.util.*;

public class gen {

    static final Random random = new Random(2026);

    public static void main(String[] args) throws IOException {
        File inputDir = new File("Testcases/input");
        File outputDir = new File("Testcases/output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        for (int tc = 1; tc <= 101; tc++) {
            int n = getNodeCount(tc);

            List<LinkedHashSet<Integer>> graph =
                    new ArrayList<>();

            for (int i = 0; i <= n; i++) {
                graph.add(new LinkedHashSet<>());
            }

            generateGraph(graph, n, tc);

            StringBuilder input = new StringBuilder();
            StringBuilder output = new StringBuilder();

            input.append(n).append('\n');

            for (int i = 1; i <= n; i++) {
                input.append(graph.get(i).size());

                for (int neighbor : graph.get(i)) {
                    input.append(' ').append(neighbor);
                }

                input.append('\n');

                output.append(graph.get(i).size());

                for (int neighbor : graph.get(i)) {
                    output.append(' ').append(neighbor);
                }

                output.append('\n');
            }

            writeFile(
                new File(inputDir, "input" + tc + ".txt"),
                input.toString()
            );

            writeFile(
                new File(outputDir, "output" + tc + ".txt"),
                n == 0 ? "[]\n" : output.toString()
            );
        }

        System.out.println("Generated 101 test cases successfully.");
        System.out.println("Input files: Testcases/input/");
        System.out.println("Output files: Testcases/output/");
    }

    static int getNodeCount(int tc) {
        if (tc == 1) return 1;
        if (tc == 2) return 0;
        if (tc <= 10) return tc - 1;

        return 5 + random.nextInt(96);
    }

    static void addEdge(
            List<LinkedHashSet<Integer>> graph,
            int u,
            int v) {

        if (u == v) return;

        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    static void generateGraph(
            List<LinkedHashSet<Integer>> graph,
            int n,
            int tc) {

        if (n <= 1) return;

        // Test 3: path graph.
        if (tc == 3) {
            for (int i = 1; i < n; i++) {
                addEdge(graph, i, i + 1);
            }
            return;
        }

        // Test 4: star graph.
        if (tc == 4) {
            for (int i = 2; i <= n; i++) {
                addEdge(graph, 1, i);
            }
            return;
        }

        // Test 5: cycle graph.
        if (tc == 5) {
            for (int i = 1; i < n; i++) {
                addEdge(graph, i, i + 1);
            }
            addEdge(graph, n, 1);
            return;
        }

        // Generate a connected graph using a random tree.
        for (int i = 2; i <= n; i++) {
            int parent = 1 + random.nextInt(i - 1);
            addEdge(graph, parent, i);
        }

        // Add extra edges for variety.
        int extraEdges;

        if (tc % 10 == 0) {
            extraEdges = n * 2;
        } else {
            extraEdges = random.nextInt(n + 1);
        }

        for (int i = 0; i < extraEdges; i++) {
            int u = 1 + random.nextInt(n);
            int v = 1 + random.nextInt(n);

            addEdge(graph, u, v);
        }
    }

    static void writeFile(File file, String content)
            throws IOException {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {
            writer.write(content);
        }
    }
}
