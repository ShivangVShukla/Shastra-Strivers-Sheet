Prerequisites
Basic knowledge of Java programming.
Understanding of for loops.
Knowledge of nested loops.
Understanding of System.out.print() and System.out.println().
Editorial: Right-Angled Triangle Star Pattern
Approach
Read an integer n, representing the number of rows.
Use an outer for loop to control the rows from 1 to n.
Use an inner for loop to print stars in each row.
Print i stars in the ith row.
Use System.out.println() to move to the next line after printing each row.
Example

Input

text
4


Output

text
*
**
***
****

Complexity Analysis
Time Complexity: O(n²), because the total number of stars printed is n(n + 1) / 2.
Space Complexity: O(1), because the program uses a constant amount of extra space.