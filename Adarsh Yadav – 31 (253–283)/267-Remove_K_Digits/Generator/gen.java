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
        List<Case> testCases = new ArrayList<>();
        addExamples(testCases);       // 2 files
        addHandcrafted(testCases);    // 18 files
        addSmallRandom(testCases);    // 45 files
        addMediumRandom(testCases);   // 33 files
        addLarge(testCases);          // 12 files

        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%03d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            Case c = testCases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inWriter.println(1);
            inWriter.println(c.s + " " + c.k);
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outWriter.println(solve(c.s, c.k));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully! Total: " + testCases.size());
    }

    static class Case {
        String s;
        int k;

        Case(String s, int k) {
            this.s = s;
            this.k = k;
        }
    }

    // Reference solution: build the answer digit by digit (different technique
    // from the stack in Solution.java). The result keeps m = n - k digits. For the
    // j-th kept digit, the window of allowed positions ends at n - (m - j) so that
    // enough digits remain; pick the smallest digit in the window (earliest
    // occurrence). nextPos[i][d] is the first position >= i holding digit d.
    static String solve(String s, int k) {
        int n = s.length();
        int m = n - k;
        if (m == 0) {
            return "0";
        }

        int[] nextPos = new int[(n + 1) * 10];
        for (int d = 0; d < 10; d++) {
            nextPos[n * 10 + d] = n;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int d = 0; d < 10; d++) {
                nextPos[i * 10 + d] = (s.charAt(i) - '0' == d) ? i : nextPos[(i + 1) * 10 + d];
            }
        }

        StringBuilder kept = new StringBuilder();
        int start = 0;
        for (int j = 0; j < m; j++) {
            int limit = n - (m - j);
            for (int d = 0; d < 10; d++) {
                int p = nextPos[start * 10 + d];
                if (p <= limit) {
                    kept.append((char) ('0' + d));
                    start = p + 1;
                    break;
                }
            }
        }

        int z = 0;
        while (z < kept.length() && kept.charAt(z) == '0') {
            z++;
        }
        String res = z == kept.length() ? "0" : kept.substring(z);

        if (n <= 14) {
            check(s, k, res);
        }
        return res;
    }

    // Brute-force cross-check for short strings: try every way to remove k digits
    static void check(String s, int k, String res) {
        int n = s.length();
        long best = Long.MAX_VALUE;
        for (int mask = 0; mask < (1 << n); mask++) {
            if (Integer.bitCount(mask) != k) {
                continue;
            }
            long v = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) == 0) {
                    v = v * 10 + (s.charAt(i) - '0');
                }
            }
            best = Math.min(best, v);
        }
        if (!String.valueOf(best).equals(res)) {
            throw new IllegalStateException("Reference mismatch for " + s + " " + k);
        }
    }

    // Examples from the problem statement
    static void addExamples(List<Case> out) {
        out.add(new Case("541892", 2));
        out.add(new Case("1002991", 3));
    }

    // Edge cases: removing everything, zeros, leading zeros after removal,
    // sorted digits, repeated digits
    static void addHandcrafted(List<Case> out) {
        out.add(new Case("10", 2));
        out.add(new Case("10", 1));
        out.add(new Case("9", 1));
        out.add(new Case("0", 1));
        out.add(new Case("112", 1));
        out.add(new Case("1432219", 3));
        out.add(new Case("10200", 1));
        out.add(new Case("100", 1));
        out.add(new Case("12345", 2));
        out.add(new Case("54321", 2));
        out.add(new Case("10001", 4));
        out.add(new Case("10001", 1));
        out.add(new Case("1111111", 3));
        out.add(new Case("9999", 2));
        out.add(new Case("123456789", 9));
        out.add(new Case("100200300", 1));
        out.add(new Case("2000", 1));
        out.add(new Case("1234567890", 9));
    }

    // 45 short strings (length 2..14) with different digit alphabets
    static void addSmallRandom(List<Case> out) {
        Random rand = new Random(2024);
        int[] alphabets = {2, 3, 10};
        for (int k = 0; k < 45; k++) {
            int n = 2 + (k % 13);
            int alpha = alphabets[k % 3];
            String s = randomDigits(n, alpha, rand);
            int remove = 1 + rand.nextInt(n);
            out.add(new Case(s, remove));
        }
    }

    // 33 medium strings (length 50..5000) with different shapes and k values
    static void addMediumRandom(List<Case> out) {
        Random rand = new Random(99);
        int[] sizes = {50, 100, 250, 500, 1000, 2000, 3000, 5000};
        for (int idx = 0; idx < 33; idx++) {
            int n = sizes[idx % sizes.length];
            int shape = idx % 6;
            String s;
            if (shape == 0) {
                s = randomDigits(n, 10, rand);                     // random digits
            } else if (shape == 1) {
                s = mostlyZeros(n, 0.8, rand);                     // many zeros
            } else if (shape == 2) {
                s = cyclic("123456789", n);                        // increasing runs
            } else if (shape == 3) {
                s = cyclic("9876543210", n);                       // decreasing runs
            } else if (shape == 4) {
                s = repeatChar((char) ('1' + rand.nextInt(9)), n); // all the same digit
            } else {
                s = randomDigits(n, 2, rand);                      // only 0 and 1
            }
            int k;
            switch (idx % 5) {
                case 0:
                    k = 1;
                    break;
                case 1:
                    k = n / 2;
                    break;
                case 2:
                    k = n - 1;
                    break;
                case 3:
                    k = n;
                    break;
                default:
                    k = 1 + rand.nextInt(n);
            }
            out.add(new Case(s, k));
        }
    }

    // 12 large strings (length 10^4)
    static void addLarge(List<Case> out) {
        Random rand = new Random(7);
        int n = 10000;

        out.add(new Case(randomDigits(n, 10, rand), 5000));
        out.add(new Case(randomDigits(n, 10, rand), 1));
        out.add(new Case(randomDigits(n, 10, rand), 9999));
        out.add(new Case(randomDigits(n, 10, rand), 10000));
        out.add(new Case(cyclic("123456789", n), 5000));
        out.add(new Case(cyclic("987654321", n), 5000));
        out.add(new Case(repeatChar('9', n), 5000));
        out.add(new Case(repeatChar('1', n), 1234));
        out.add(new Case(mostlyZeros(n, 0.9, rand), 3000));
        out.add(new Case("1" + repeatChar('0', n - 1), 1));

        // Non-decreasing digits: 1..9 in blocks
        StringBuilder sorted = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sorted.append((char) ('1' + Math.min(8, i * 9 / n)));
        }
        out.add(new Case(sorted.toString(), 5000));

        out.add(new Case(cyclic("91", n), 2500));
    }

    // Random digits drawn from 0..alpha-1 (first digit is never 0 unless n == 1)
    static String randomDigits(int n, int alpha, Random rand) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int d = rand.nextInt(alpha);
            if (i == 0 && n > 1 && d == 0) {
                d = 1;
            }
            sb.append((char) ('0' + d));
        }
        return sb.toString();
    }

    // Each digit after the first is 0 with probability pZero, otherwise 1..9
    static String mostlyZeros(int n, double pZero, Random rand) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) ('1' + rand.nextInt(9)));
        for (int i = 1; i < n; i++) {
            if (rand.nextDouble() < pZero) {
                sb.append('0');
            } else {
                sb.append((char) ('1' + rand.nextInt(9)));
            }
        }
        return sb.toString();
    }

    // The pattern repeated and cut to length n (pattern must not start with 0)
    static String cyclic(String pattern, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(pattern.charAt(i % pattern.length()));
        }
        return sb.toString();
    }

    static String repeatChar(char c, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
