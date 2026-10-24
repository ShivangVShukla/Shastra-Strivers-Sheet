import java.util.*;

public class gen {
    static Random random = new Random(238);

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            int n;
            int k;

            if (tc == 0) {
                n = 1; k = 1;
            } else if (tc == 1) {
                n = 5; k = 1;
            } else if (tc == 2) {
                n = 5; k = 5;
            } else if (tc == 3) {
                n = 7; k = 3;
            } else {
                n = 2 + random.nextInt(99);
                k = 1 + random.nextInt(n);
            }

            System.out.println(n + " " + k);

            for (int i = 0; i < n; i++) {
                int value = 1 + random.nextInt(10000);
                System.out.print(value);
                if (i + 1 < n) System.out.print(" ");
            }
            System.out.println();
        }
    }
}
