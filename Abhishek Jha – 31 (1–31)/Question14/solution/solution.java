package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            printRow(n, i);
        }
        for (int i = n; i >= 1; i--) {
            printRow(n, i);
        }

        sc.close();
    }

    private static void printRow(int n, int row) {
        for (int j = 1; j <= n - row; j++) {
            System.out.print(" ");
        }
        for (int j = 1; j <= 2 * row - 1; j++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
