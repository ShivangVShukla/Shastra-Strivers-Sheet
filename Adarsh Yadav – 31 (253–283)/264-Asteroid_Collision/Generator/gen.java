import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";
        if (!new File(baseDir).exists() && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "input");
        File outputDir = new File(baseDir, "output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        // Each test file holds exactly one test case (T = 1)
        List<int[]> testCases = new ArrayList<>();
        addExamples(testCases);       // 2 files
        addHandcrafted(testCases);    // 18 files
        addSmallRandom(testCases);    // 45 files
        addMediumRandom(testCases);   // 33 files
        addLarge(testCases);          // 12 files

        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%03d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            int[] a = testCases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inWriter.println(1);
            inWriter.println(a.length);
            inWriter.println(join(a));
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outWriter.println(join(solve(a)));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully! Total: " + testCases.size());
    }

    static String join(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(a[i]);
        }
        return sb.toString();
    }

    // Reference solution: linked-list simulation with a work queue (different
    // technique from the stack in Solution.java). Only an adjacent pair
    // "moving right, moving left" can collide. Such pairs are disjoint, so the
    // final state does not depend on the order in which they are resolved.
    static int[] solve(int[] a) {
        int n = a.length;
        int[] prv = new int[n];
        int[] nxt = new int[n];
        boolean[] alive = new boolean[n];
        for (int i = 0; i < n; i++) {
            prv[i] = i - 1;
            nxt[i] = i + 1;
            alive[i] = true;
        }

        ArrayDeque<Integer> work = new ArrayDeque<>();
        for (int i = 0; i + 1 < n; i++) {
            if (a[i] > 0 && a[i + 1] < 0) {
                work.add(i);
            }
        }

        while (!work.isEmpty()) {
            int i = work.poll();
            if (!alive[i]) {
                continue;
            }
            int j = nxt[i];
            if (j >= n || !(a[i] > 0 && a[j] < 0)) {
                continue;
            }

            int big = a[i];
            int small = -a[j];
            if (big > small) {
                remove(j, prv, nxt, alive, n);
                int q = nxt[i];
                if (q < n && a[q] < 0) {
                    work.add(i);
                }
            } else if (big < small) {
                int p = prv[i];
                remove(i, prv, nxt, alive, n);
                if (p >= 0 && a[p] > 0) {
                    work.add(p);
                }
            } else {
                int p = prv[i];
                int q = nxt[j];
                remove(i, prv, nxt, alive, n);
                remove(j, prv, nxt, alive, n);
                if (p >= 0 && q < n && a[p] > 0 && a[q] < 0) {
                    work.add(p);
                }
            }
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            if (alive[i]) {
                count++;
            }
        }
        int[] res = new int[count];
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (alive[i]) {
                res[k++] = a[i];
            }
        }

        if (n <= 3000) {
            check(a, res);
        }
        return res;
    }

    static void remove(int i, int[] prv, int[] nxt, boolean[] alive, int n) {
        alive[i] = false;
        int p = prv[i];
        int q = nxt[i];
        if (p >= 0) {
            nxt[p] = q;
        }
        if (q < n) {
            prv[q] = p;
        }
    }

    // O(n^2) brute-force cross-check, used for small arrays only: repeatedly
    // resolve the leftmost adjacent (right-moving, left-moving) pair
    static void check(int[] a, int[] res) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int v : a) {
            list.add(v);
        }
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i + 1 < list.size(); i++) {
                int x = list.get(i);
                int y = list.get(i + 1);
                if (x > 0 && y < 0) {
                    if (x > -y) {
                        list.remove(i + 1);
                    } else if (x < -y) {
                        list.remove(i);
                    } else {
                        list.remove(i + 1);
                        list.remove(i);
                    }
                    changed = true;
                    break;
                }
            }
        }
        if (list.size() != res.length) {
            throw new IllegalStateException("Reference mismatch (size)");
        }
        for (int i = 0; i < res.length; i++) {
            if (list.get(i) != res[i]) {
                throw new IllegalStateException("Reference mismatch at " + i);
            }
        }
    }

    static int[] arr(int... v) {
        return v;
    }

    // Examples from the problem statement
    static void addExamples(List<int[]> out) {
        out.add(arr(1, 2, 3, -4, -2));
        out.add(arr(5, 10, -5, -10, 8, -8, -3, 12));
    }

    // Edge cases: everything explodes, nothing collides, ties, cascades,
    // boundary values
    static void addHandcrafted(List<int[]> out) {
        out.add(arr(10, 2, -5));
        out.add(arr(5, -5));
        out.add(arr(-5, 5));
        out.add(arr(1, -1, 1, -1));
        out.add(arr(5, 10, -5));
        out.add(arr(-2, -1, 1, 2));
        out.add(arr(1, 2, 3, 4));
        out.add(arr(-4, -3, -2, -1));
        out.add(arr(1000000, -1000000));
        out.add(arr(1000000, -999999));
        out.add(arr(-1000000, 999999));
        out.add(arr(3, 3, -3));
        out.add(arr(3, -3, -3));
        out.add(arr(1, 1, 1, -2));
        out.add(arr(2, 2, -2, -2));
        out.add(arr(5, 4, 3, 2, 1, -6));
        out.add(arr(7, -3, -4, -5));
        out.add(arr(1, -2, 2, -1));
    }

    // 45 small arrays (n = 2..20) with different magnitude ranges and direction bias
    static void addSmallRandom(List<int[]> out) {
        Random rand = new Random(2024);
        int[] maxMag = {3, 10, 1000000};
        double[] pRight = {0.5, 0.3, 0.7};
        for (int k = 0; k < 45; k++) {
            int n = 2 + (k % 19);
            out.add(randomArray(n, maxMag[k % 3], pRight[(k / 3) % 3], rand));
        }
    }

    // 33 medium arrays (n = 50..3000) with different shapes
    static void addMediumRandom(List<int[]> out) {
        Random rand = new Random(99);
        int[] sizes = {50, 100, 250, 500, 1000, 2000, 3000};
        int[] maxMag = {5, 100, 1000000};
        for (int k = 0; k < 33; k++) {
            int n = sizes[k % sizes.length];
            int mag = maxMag[k % 3];
            int shape = k % 5;
            int[] a;
            if (shape == 0) {
                a = randomArray(n, mag, 0.5, rand);   // balanced random
            } else if (shape == 1) {
                a = randomArray(n, mag, 0.8, rand);   // mostly right-moving
            } else if (shape == 2) {
                a = randomArray(n, mag, 0.2, rand);   // mostly left-moving
            } else if (shape == 3) {
                // all right-moving first, then all left-moving (long cascades)
                a = new int[n];
                for (int i = 0; i < n; i++) {
                    int m = 1 + rand.nextInt(mag);
                    a[i] = i < n / 2 ? m : -m;
                }
            } else {
                // strictly alternating directions
                a = new int[n];
                for (int i = 0; i < n; i++) {
                    int m = 1 + rand.nextInt(mag);
                    a[i] = i % 2 == 0 ? m : -m;
                }
            }
            out.add(a);
        }
    }

    // 12 large arrays (n up to 10^5)
    static void addLarge(List<int[]> out) {
        Random rand = new Random(7);
        int n = 100000;
        int half = n / 2;

        // Random signs and sizes over the full range
        out.add(randomArray(n, 1000000, 0.5, rand));

        // All right-moving: nothing collides
        out.add(randomArray(n, 1000000, 1.0, rand));

        // All left-moving: nothing collides
        out.add(randomArray(n, 1000000, 0.0, rand));

        // 1, 2, ..., 99999 moving right, then one huge left-moving asteroid
        int[] oneKiller = new int[n];
        for (int i = 0; i < n - 1; i++) {
            oneKiller[i] = i + 1;
        }
        oneKiller[n - 1] = -1000000;
        out.add(oneKiller);

        // 1..50000 right, then -50000..-1 left: everything annihilates pairwise
        int[] mirror = new int[n];
        for (int i = 0; i < half; i++) {
            mirror[i] = i + 1;
            mirror[n - 1 - i] = -(i + 1);
        }
        out.add(mirror);

        // Alternating +x, -x with equal magnitude: every pair explodes
        int[] pairs = new int[n];
        for (int i = 0; i < n; i += 2) {
            int m = 1 + rand.nextInt(1000000);
            pairs[i] = m;
            pairs[i + 1] = -m;
        }
        out.add(pairs);

        // Big right-movers, small left-movers alternating: right-movers all survive
        int[] bigSmall = new int[n];
        for (int i = 0; i < n; i++) {
            bigSmall[i] = i % 2 == 0 ? 500000 + rand.nextInt(500001) : -(1 + rand.nextInt(1000));
        }
        out.add(bigSmall);

        // Only magnitude 1 with random directions
        out.add(randomArray(n, 1, 0.5, rand));

        // All left-moving first, then all right-moving: nothing collides
        int[] separated = new int[n];
        for (int i = 0; i < n; i++) {
            int m = 1 + rand.nextInt(1000000);
            separated[i] = i < half ? -m : m;
        }
        out.add(separated);

        // 50000..1 right, then -1..-50000 left: everything annihilates pairwise
        int[] mirror2 = new int[n];
        for (int i = 0; i < half; i++) {
            mirror2[i] = half - i;
            mirror2[half + i] = -(i + 1);
        }
        out.add(mirror2);

        // 1..50000 right, then -1..-50000 left (small ones die, the equal pair explodes)
        int[] cascade = new int[n];
        for (int i = 0; i < half; i++) {
            cascade[i] = i + 1;
            cascade[half + i] = -(i + 1);
        }
        out.add(cascade);

        // Magnitudes 1..3, random directions (many ties)
        out.add(randomArray(n, 3, 0.5, rand));
    }

    // n asteroids with sizes in [1, maxMag]; each moves right with probability pRight
    static int[] randomArray(int n, int maxMag, double pRight, Random rand) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            int m = 1 + rand.nextInt(maxMag);
            a[i] = rand.nextDouble() < pRight ? m : -m;
        }
        return a;
    }
}
