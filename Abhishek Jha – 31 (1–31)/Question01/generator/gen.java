package generator;

import java.util.Random;

public class gen {
    public static void main(String[] args) {
        Random random = new Random();
        int n = random.nextInt(2000000001) - 1000000000;
        System.out.println(n);
    }
}
