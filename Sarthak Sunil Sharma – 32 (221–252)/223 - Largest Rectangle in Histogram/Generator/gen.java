import java.io.*;
import java.util.*;

public class gen {
    static class TestCase {
        int[] h;
        TestCase(int[] h) { this.h = h; }
    }

    static TestCase make(int id, Random r) {
        int n;
        int[] h;
        switch (id) {
            case 0: return new TestCase(new int[]{1});
            case 1: return new TestCase(new int[]{0});
            case 2: return new TestCase(new int[]{2, 2, 2, 2});
            case 3: return new TestCase(new int[]{1, 2, 3, 4, 5});
            case 4: return new TestCase(new int[]{5, 4, 3, 2, 1});
            case 5: return new TestCase(new int[]{2, 1, 5, 6, 2, 3});
            case 6: return new TestCase(new int[]{1, 1, 1, 1, 1, 1, 1});
            case 7: return new TestCase(new int[]{0, 0, 0, 0, 0});
            case 8: return new TestCase(new int[]{1, 0, 1, 0, 1, 0, 1});
            case 9: return new TestCase(new int[]{2, 1, 2, 3, 1, 2});
            case 10: return new TestCase(new int[]{6, 2, 5, 4, 5, 1, 6});
            case 11: return new TestCase(new int[]{3, 3, 3, 2, 2, 2, 3, 3});
            case 12: return new TestCase(new int[]{1, 3, 2, 1, 2, 3, 1});
            case 13: return new TestCase(new int[]{10000, 10000, 9999, 10000});
            case 14: return new TestCase(new int[]{10000, 1, 10000, 1, 10000});
            case 15: return new TestCase(new int[]{1, 2, 1, 2, 1, 2, 1, 2});
            case 16: n = 50; h = new int[n]; Arrays.fill(h, 7); return new TestCase(h);
            case 17: n = 100; h = new int[n]; for (int i=0;i<n;i++) h[i]=i+1; return new TestCase(h);
            case 18: n = 100; h = new int[n]; for (int i=0;i<n;i++) h[i]=n-i; return new TestCase(h);
            case 19: n = 1000; h = new int[n]; for (int i=0;i<n;i++) h[i]=(i%10==0)?1:100; return new TestCase(h);
            case 20: n = 1000; h = new int[n]; for (int i=0;i<n;i++) h[i]=(i%2==0)?10000:0; return new TestCase(h);
            case 21: n = 1000; h = new int[n]; for (int i=0;i<n;i++) h[i]=1+(i%100); return new TestCase(h);
            case 22: n = 10000; h = new int[n]; for (int i=0;i<n;i++) h[i]=1+r.nextInt(10000); return new TestCase(h);
            case 23: n = 10000; h = new int[n]; for (int i=0;i<n;i++) h[i]=r.nextInt(2); return new TestCase(h);
            case 24: n = 10000; h = new int[n]; for (int i=0;i<n;i++) h[i]=r.nextInt(10001); return new TestCase(h);
            case 25: n = 100000; h = new int[n]; for (int i=0;i<n;i++) h[i]=1+r.nextInt(10000); return new TestCase(h);
            case 26: n = 100000; h = new int[n]; for (int i=0;i<n;i++) h[i]=i<50000?10000:1; return new TestCase(h);
            case 27: n = 100000; h = new int[n]; for (int i=0;i<n;i++) h[i]=i/10; return new TestCase(h);
            default:
                n = 20 + r.nextInt(1981);
                h = new int[n];
                for (int i=0;i<n;i++) h[i]=r.nextInt(10001);
                return new TestCase(h);
        }
    }

    public static void main(String[] args) throws Exception {
        Random r = new Random(2232026L);
        for (int id=0; id<100; id++) {
            TestCase tc = make(id, r);
            String suffix = String.format("%02d", id);
            File in = new File("Testcases/Input/input" + suffix + ".txt");
            try (PrintWriter out = new PrintWriter(in)) {
                out.println(tc.h.length);
                for (int i=0;i<tc.h.length;i++) {
                    if (i>0) out.print(' ');
                    out.print(tc.h[i]);
                }
                out.println();
            }
        }
    }
}
