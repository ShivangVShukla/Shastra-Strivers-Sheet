package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int original = n;
        int reversed = 0;

        while (n != 0) {
            reversed = reversed * 10 + n % 10;
            n /= 10;
        }

        System.out.println(original == reversed);
        sc.close();
    }
}
