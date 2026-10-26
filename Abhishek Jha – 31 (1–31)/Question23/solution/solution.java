package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int row = 0; row < n; row++) {
            for (int column = n - row - 1; column < n; column++) {
                System.out.print((char) ('A' + column));
            }
            System.out.println();
        }

        sc.close();
    }
}
