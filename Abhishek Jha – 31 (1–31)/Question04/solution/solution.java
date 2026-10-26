package solution;

import java.util.Scanner;

public class solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m1 = sc.nextInt();
        int m2 = sc.nextInt();
        int m3 = sc.nextInt();

        if (m1 < 0 || m1 > 100 ||
            m2 < 0 || m2 > 100 ||
            m3 < 0 || m3 > 100) {
            System.out.println("Invalid marks");
        } else {
            int average = (m1 + m2 + m3) / 3;

            System.out.println("Average: " + average);

            if (m1 >= 40 && m2 >= 40 && m3 >= 40) {
                System.out.println("Pass");
            } else {
                System.out.println("Fail");
            }
        }

        sc.close();
    }
    
}
