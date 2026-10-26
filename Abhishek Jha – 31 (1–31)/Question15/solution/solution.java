package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            printRow(i);
        }
        for (int i = n - 1; i >= 1; i--) {
            printRow(i);
        }

        sc.close();
    }

    private static void printRow(int stars) {
        for (int i = 1; i <= stars; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
