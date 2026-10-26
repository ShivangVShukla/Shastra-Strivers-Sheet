package generator;
import java.util.Random;

public class gen {
    public static void main(String[] args) {
        Random random = new Random();

        int a = random.nextInt(2000000001) - 1000000000;
        int b = random.nextInt(2000000001) - 1000000000;

        System.out.println(a + " " + b);
    }
}
