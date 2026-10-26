package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int row = 1; row <= n; row++) {
            for (int value = 1; value <= row; value++) {
                System.out.print(value);
            }
            for (int space = 1; space <= 2 * (n - row); space++) {
                System.out.print(" ");
            }
            for (int value = row; value >= 1; value--) {
                System.out.print(value);
            }
            System.out.println();
        }

        sc.close();
    }
}
