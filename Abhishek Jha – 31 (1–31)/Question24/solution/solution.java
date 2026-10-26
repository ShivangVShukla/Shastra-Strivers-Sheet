package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int row = n; row >= 1; row--) {
            printRow(n, row);
        }
        for (int row = 1; row <= n; row++) {
            printRow(n, row);
        }

        sc.close();
    }

    private static void printRow(int n, int stars) {
        for (int i = 0; i < stars; i++) {
            System.out.print("*");
        }
        for (int i = 0; i < 2 * (n - stars); i++) {
            System.out.print(" ");
        }
        for (int i = 0; i < stars; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
