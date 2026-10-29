package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int size = 2 * n - 1;

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                int distanceFromBorder = Math.min(
                    Math.min(row, column),
                    Math.min(size - 1 - row, size - 1 - column)
                );
                int value = n - distanceFromBorder;
                System.out.print(value);
                if (column < size - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        sc.close();
    }
}
