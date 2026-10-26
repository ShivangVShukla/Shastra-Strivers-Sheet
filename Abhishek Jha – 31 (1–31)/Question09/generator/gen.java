package generator;

import java.util.Random;

public class gen {
      public static void main(String[] args) {
        Random random = new Random();

        int n = random.nextInt(100) + 1;

        System.out.println(n);
    }
    
}
