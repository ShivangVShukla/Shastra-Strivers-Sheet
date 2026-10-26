package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int digits = 0;
        int value = n;
        do {
            digits++;
            value /= 10;
        } while (value != 0);

        System.out.println(digits);
        sc.close();
    }
}
