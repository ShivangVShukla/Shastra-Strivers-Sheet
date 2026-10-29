Editorial: Number Triangle Pattern
Prerequisites
Basic knowledge of Java
For loops (nested loops)
System.out.print() and System.out.println()
Variables and integers
Approach
Read the integer n.
Use an outer loop from 1 to n to control the rows.
Use an inner loop from 1 to the current row number to print the numbers.
Print a space between consecutive numbers.
After each row, move to the next line.
Java Solution
java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j);
                if (j < i) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        sc.close();
    }
}

Example

Input:

text
4


Output:

text
1
1 2
1 2 3
1 2 3 4

Complexity Analysis
Time Complexity: O(n²), because the total number of printed numbers is 1 + 2 + ... + n.
Space Complexity: O(1), because the program uses a constant amount of extra space.