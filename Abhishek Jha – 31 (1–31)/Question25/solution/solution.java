package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int stars = 1; stars <= n; stars++) {
            printRow(n, stars);
        }
        for (int stars = n - 1; stars >= 1; stars--) {
            printRow(n, stars);
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
