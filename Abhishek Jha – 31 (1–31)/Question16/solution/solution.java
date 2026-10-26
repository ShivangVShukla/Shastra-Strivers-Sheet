package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            int value = i % 2 == 1 ? 1 : 0;
            for (int j = 1; j <= i; j++) {
                System.out.print(value);
                if (j < i) {
                    System.out.print(" ");
                }
                value = 1 - value;
            }
            System.out.println();
        }

        sc.close();
    }
}
