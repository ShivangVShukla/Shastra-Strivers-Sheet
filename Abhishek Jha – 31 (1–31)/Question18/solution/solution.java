package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int value = 1;

        for (int row = 1; row <= n; row++) {
            for (int column = 1; column <= row; column++) {
                System.out.print(value);
                if (column < row) {
                    System.out.print(" ");
                }
                value++;
            }
            System.out.println();
        }

        sc.close();
    }
}
