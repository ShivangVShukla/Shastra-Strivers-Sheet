package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int value = n;
        int oddDigits = 0;

        do {
            int digit = value % 10;
            if (digit % 2 != 0) {
                oddDigits++;
            }
            value /= 10;
        } while (value != 0);

        System.out.println(oddDigits);
        sc.close();
    }
}
