import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Self-contained test generator. Embeds the optimal solver.
 * Usage: java gen [testcasesDir]   (default: ../Testcases relative to cwd)
 *
 * NOTE: the constraints are 1 <= a, b <= 3, so only 9 distinct inputs exist.
 * Cases 00-08 enumerate all of them exactly once; the remaining 91 cases
 * are seeded-random repeats (deterministic).
 */
public class gen {

    private static final int TOTAL = 100;
    private static final long SEED = 885L;

    /** Embedded optimal solver: 1 = Preorder, 2 = Inorder, 3 = Postorder. */
    static boolean uniqueBinaryTree(int a, int b) {
        return (a == 2 && b != 2) || (b == 2 && a != 2);
    }

    private static void write(File f, String content) throws IOException {
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.print(content);
        }
    }

    public static void main(String[] args) throws IOException {
        File base = new File(args.length > 0 ? args[0] : "../Testcases");
        File inDir = new File(base, "Input");
        File outDir = new File(base, "Output");
        if (!inDir.exists() && !inDir.mkdirs()) {
            throw new IOException("Cannot create " + inDir);
        }
        if (!outDir.exists() && !outDir.mkdirs()) {
            throw new IOException("Cannot create " + outDir);
        }

        Random rnd = new Random(SEED);
        for (int t = 0; t < TOTAL; t++) {
            int a;
            int b;
            if (t < 9) {
                // Edge / base cases: exhaustive enumeration of the whole input space.
                a = t / 3 + 1;
                b = t % 3 + 1;
            } else if (t < 40) {
                // Targeted: cases involving inorder (the only way to get "true").
                a = rnd.nextBoolean() ? 2 : 1 + rnd.nextInt(3);
                b = (a == 2) ? 1 + rnd.nextInt(3) : 2;
            } else if (t < 70) {
                // Targeted: pairs without inorder (always "false"), incl. 1 1, 3 3, 1 3, 3 1.
                a = rnd.nextBoolean() ? 1 : 3;
                b = rnd.nextBoolean() ? 1 : 3;
            } else {
                // Uniform random over the full domain.
                a = 1 + rnd.nextInt(3);
                b = 1 + rnd.nextInt(3);
            }
            String name = String.format("%02d.txt", t);
            write(new File(inDir, "input" + name), a + " " + b + "\n");
            write(new File(outDir, "output" + name), (uniqueBinaryTree(a, b) ? "true" : "false") + "\n");
        }
        System.out.println("Generated " + TOTAL + " test cases in " + base.getPath());
    }
}
