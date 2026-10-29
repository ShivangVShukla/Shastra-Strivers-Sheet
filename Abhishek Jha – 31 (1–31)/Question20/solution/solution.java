package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int row = n; row >= 1; row--) {
            for (int column = 0; column < row; column++) {
                System.out.print((char) ('A' + column));
            }
            System.out.println();
        }

        sc.close();
    }
}
