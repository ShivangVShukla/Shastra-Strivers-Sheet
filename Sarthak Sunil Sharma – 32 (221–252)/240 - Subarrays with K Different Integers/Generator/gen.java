import java.util.*;

public class gen {
    static Random random = new Random(240);

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            int n;
            int k;

            if (tc == 0) {
                n = 1;
                k = 1;
            } else if (tc == 1) {
                n = 5;
                k = 1;
            } else if (tc == 2) {
                n = 5;
                k = 3;
            } else if (tc == 3) {
                n = 5;
                k = 2;
            } else {
                n = 1 + random.nextInt(100);
                k = 1 + random.nextInt(n);
            }

            System.out.println(n + " " + k);

            for (int i = 0; i < n; i++) {
                int value = 1 + random.nextInt(Math.min(20, n));
                System.out.print(value);
                if (i + 1 < n) System.out.print(" ");
            }
            System.out.println();
        }
    }
}
