import java.util.*;

public class gen {
    static Random random = new Random(244);

    public static void main(String[] args) {
        for (int tc = 0; tc < 100; tc++) {
            int q = 10 + random.nextInt(91);

            System.out.println(q);

            int size = 0;

            for (int i = 0; i < q; i++) {
                int type;

                if (size == 0) {
                    type = random.nextInt(3) + 1;
                } else {
                    type = 1 + random.nextInt(6);
                }

                if (type == 1) {
                    long value = -1000000000L +
                                 random.nextInt(2000000001);
                    System.out.println("1 " + value);
                    size++;
                } else {
                    System.out.println(type);

                    if ((type == 2 || type == 3) && size > 0) {
                        size--;
                    }
                }
            }
        }
    }
}
