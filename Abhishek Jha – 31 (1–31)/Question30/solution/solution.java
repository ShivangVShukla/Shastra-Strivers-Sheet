package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int value = n;
        int reversed = 0;

        while (value != 0) {
            reversed = reversed * 10 + value % 10;
            value /= 10;
        }

        System.out.println(reversed);
        sc.close();
    }
}
