import java.util.*;

public class gen {
    static Random random = new Random(243);

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            int q = 10 + random.nextInt(91);

            if (tc == 0) {
                q = 8;
                System.out.println(q);
                System.out.println("1 5");
                System.out.println("1 2");
                System.out.println("1 8");
                System.out.println("3");
                System.out.println("2");
                System.out.println("3");
                System.out.println("4");
                System.out.println("2");
                continue;
            }

            System.out.println(q);

            int currentSize = 0;

            for (int i = 0; i < q; i++) {
                int type;

                if (currentSize == 0) {
                    type = random.nextBoolean() ? 1 : 4;
                } else {
                    type = 1 + random.nextInt(4);
                }

                if (type == 1) {
                    long value = -1000000000L + random.nextInt(2000000001);
                    System.out.println("1 " + value);
                    currentSize++;
                } else if (type == 2) {
                    System.out.println("2");
                    if (currentSize > 0) currentSize--;
                } else if (type == 3) {
                    System.out.println("3");
                } else {
                    System.out.println("4");
                }
            }
        }
    }
}
