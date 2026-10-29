Editorial: Even or Odd

Approach:
We are given an integer N. Our task is to determine whether the number is even or odd.

A number is even if it is divisible by 2 with a remainder of 0. Otherwise, it is odd.

Algorithm:
1. Read the integer N.
2. Check whether N % 2 == 0.
3. If true, print "Even".
4. Otherwise, print "Odd".

Java Code:
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        if (n % 2 == 0) {
            System.out.println("Even");
        } else {
            System.out.println("Odd");
        }

        sc.close();
    }
}

Example:
Input:
8

Output:
Even

Explanation:
The remainder when 8 is divided by 2 is 0. Therefore, 8 is even.

Complexity Analysis:
Time Complexity: O(1)
Space Complexity: O(1)