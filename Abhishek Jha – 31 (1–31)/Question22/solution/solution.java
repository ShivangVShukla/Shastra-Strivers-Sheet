package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int row = 0; row < n; row++) {
            for (int space = 0; space < n - row - 1; space++) {
                System.out.print(" ");
            }
            for (int column = 0; column <= row; column++) {
                System.out.print((char) ('A' + column));
            }
            for (int column = row - 1; column >= 0; column--) {
                System.out.print((char) ('A' + column));
            }
            System.out.println();
        }

        sc.close();
    }
}
