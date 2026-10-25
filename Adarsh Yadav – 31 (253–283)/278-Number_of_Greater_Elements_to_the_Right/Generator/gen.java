import java.io.*;
import java.nio.file.*;
import java.util.*;

public class gen {
    private static final Random RNG = new Random(20261025L);

    public static void main(String[] args) throws Exception {
        Path root = Paths.get(args.length > 0 ? args[0] : ".");
        Path inDir = root.resolve("Testcases/input");
        Path outDir = root.resolve("Testcases/output");
        Files.createDirectories(inDir);
        Files.createDirectories(outDir);

        for (int tc = 0; tc < 120; tc++) {
            TestCase test = createCase(tc);
            String input = serialize(test);
            String output = reference(test);
            String suffix = String.format("%02d", tc);
            Files.writeString(inDir.resolve("input" + suffix + ".txt"), input);
            Files.writeString(outDir.resolve("output" + suffix + ".txt"), output);
        }
        System.out.println("Generated 120 input/output testcase pairs.");
    }

    private static class TestCase {
        int[] arr;
        int[] indices;
        TestCase(int[] arr, int[] indices) { this.arr = arr; this.indices = indices; }
    }

    private static TestCase createCase(int tc) {
        if (tc == 0) return new TestCase(new int[]{3,4,2,7,5,8,10,6}, new int[]{0,5});
        if (tc == 1) return new TestCase(new int[]{1,2,3,4,1}, new int[]{0,3});
        if (tc == 2) return new TestCase(new int[]{5,5,5,5}, new int[]{0,2});
        if (tc == 3) return new TestCase(new int[]{1}, new int[]{0});
        if (tc == 4) return new TestCase(new int[]{100000,1,100000,1}, new int[]{0,1,2,3});
        if (tc == 5) return new TestCase(new int[]{1,2,3,4,5,6}, new int[]{0,1,2,3,4,5});
        if (tc == 6) return new TestCase(new int[]{6,5,4,3,2,1}, new int[]{0,1,2,3,4,5});
        if (tc == 7) return new TestCase(new int[]{2,2,3,2,3,3}, new int[]{0,1,2,3,4,5});
        if (tc == 8) return new TestCase(new int[]{9,1,8,2,7,3,6,4,5}, new int[]{0,1,2,3,4,5,6,7,8});
        if (tc == 9) return new TestCase(new int[]{1,1,1,1,1,1}, new int[]{0,2,5});

        int n = (tc % 10 == 0) ? 10000 : 1 + RNG.nextInt(10000);
        int q = 1 + RNG.nextInt(100);
        int[] arr = new int[n];
        int style = tc % 6;
        for (int i = 0; i < n; i++) {
            switch (style) {
                case 0: arr[i] = 1 + RNG.nextInt(100000); break;
                case 1: arr[i] = 1 + (i % 100000); break;
                case 2: arr[i] = Math.max(1, 100000 - (i % 100000)); break;
                case 3: arr[i] = (i % 2 == 0) ? 100000 : 1; break;
                case 4: arr[i] = 1 + RNG.nextInt(10); break;
                default: arr[i] = 1 + RNG.nextInt(100000); break;
            }
        }
        int[] indices = new int[q];
        for (int i = 0; i < q; i++) indices[i] = RNG.nextInt(n);
        return new TestCase(arr, indices);
    }

    private static String serialize(TestCase tc) {
        StringBuilder sb = new StringBuilder();
        sb.append("1\n").append(tc.arr.length).append(' ').append(tc.indices.length).append('\n');
        appendArray(sb, tc.arr);
        appendArray(sb, tc.indices);
        return sb.toString();
    }

    private static void appendArray(StringBuilder sb, int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(a[i]);
        }
        sb.append('\n');
    }

    private static String reference(TestCase tc) {
        StringBuilder sb = new StringBuilder();
        for (int qi = 0; qi < tc.indices.length; qi++) {
            int index = tc.indices[qi];
            int count = 0;
            for (int j = index + 1; j < tc.arr.length; j++) {
                if (tc.arr[j] > tc.arr[index]) count++;
            }
            if (qi > 0) sb.append(' ');
            sb.append(count);
        }
        sb.append('\n');
        return sb.toString();
    }
}
