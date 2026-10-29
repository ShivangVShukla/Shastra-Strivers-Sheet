package generator;
import java.util.Random;

public class gen {
    public static void main(String[] args) {
        Random random = new Random();

        int age = random.nextInt(151);

        System.out.println(age);
    }
}
