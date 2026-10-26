package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int row = 0; row < n; row++) {
            char letter = (char) ('A' + row);
            for (int column = 0; column <= row; column++) {
                System.out.print(letter);
            }
            System.out.println();
        }

        sc.close();
    }
}
