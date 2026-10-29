package generator;
import java.util.Random;

public class gen {
    public static void main(String[] args) {
        Random random = new Random();

        int m1 = random.nextInt(2001) - 1000;
        int m2 = random.nextInt(2001) - 1000;
        int m3 = random.nextInt(2001) - 1000;

        System.out.println(m1 + " " + m2 + " " + m3);
    }
}
