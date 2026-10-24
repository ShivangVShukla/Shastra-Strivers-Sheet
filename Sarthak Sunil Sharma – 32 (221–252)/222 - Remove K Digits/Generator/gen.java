import java.io.*;
import java.util.*;

public class gen {
    static final String BASE = "Testcases";

    static class TestCase {
        String num;
        int k;
        TestCase(String num, int k) { this.num = num; this.k = k; }
    }

    public static void main(String[] args) throws Exception {
        File inputDir = new File(BASE, "Input");
        File outputDir = new File(BASE, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<TestCase> cases = new ArrayList<>();
        cases.add(new TestCase("10", 1));
        cases.add(new TestCase("1432219", 3));
        cases.add(new TestCase("10200", 1));
        cases.add(new TestCase("10200", 2));
        cases.add(new TestCase("10", 2));
        cases.add(new TestCase("123456789", 3));
        cases.add(new TestCase("987654321", 4));
        cases.add(new TestCase("111111111", 5));
        cases.add(new TestCase("100000000", 1));
        cases.add(new TestCase("100000000", 8));
        cases.add(new TestCase("2222222222", 1));
        cases.add(new TestCase("54321", 2));
        cases.add(new TestCase("112", 1));
        cases.add(new TestCase("1203", 1));
        cases.add(new TestCase("100200300", 3));

        Random random = new Random(2222026L);

        while (cases.size() < 40) {
            int n = 2 + random.nextInt(48);
            StringBuilder s = new StringBuilder();
            s.append((char)('1' + random.nextInt(9)));
            for (int i = 1; i < n; i++) s.append((char)('0' + random.nextInt(10)));
            int k = 1 + random.nextInt(n);
            cases.add(new TestCase(s.toString(), k));
        }

        while (cases.size() < 70) {
            int n = 50 + random.nextInt(451);
            StringBuilder s = new StringBuilder(n);
            s.append((char)('1' + random.nextInt(9)));
            for (int i = 1; i < n; i++) s.append((char)('0' + random.nextInt(10)));
            int k = 1 + random.nextInt(n);
            cases.add(new TestCase(s.toString(), k));
        }

        while (cases.size() < 100) {
            int n = 5000 + random.nextInt(95001);
            StringBuilder s = new StringBuilder(n);
            int type = cases.size() % 6;
            for (int i = 0; i < n; i++) {
                int d;
                if (i == 0) d = 1 + random.nextInt(9);
                else if (type == 0) d = 9;
                else if (type == 1) d = 0;
                else if (type == 2) d = i % 10;
                else if (type == 3) d = 9 - (i % 10);
                else if (type == 4) d = random.nextInt(3);
                else d = random.nextInt(10);
                s.append((char)('0' + d));
            }
            int k;
            if (type == 0) k = n - 1;
            else if (type == 1) k = 1;
            else if (type == 2) k = n / 2;
            else k = 1 + random.nextInt(n);
            cases.add(new TestCase(s.toString(), k));
        }

        for (int i = 0; i < 100; i++) {
            String id = String.format("%02d", i);
            TestCase tc = cases.get(i);
            try (PrintWriter in = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + id + ".txt"))))) {
                in.println(tc.num);
                in.println(tc.k);
            }
            String ans = removeKDigits(tc.num, tc.k);
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + id + ".txt"))))) {
                out.println(ans);
            }
        }
        System.out.println("Generated 100 test cases successfully.");
    }

    static String removeKDigits(String num, int k) {
        char[] stack = new char[num.length()];
        int top = 0;
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            while (top > 0 && k > 0 && stack[top - 1] > c) {
                top--;
                k--;
            }
            stack[top++] = c;
        }
        top -= k;
        int start = 0;
        while (start < top && stack[start] == '0') start++;
        return start == top ? "0" : new String(stack, start, top - start);
    }
}
