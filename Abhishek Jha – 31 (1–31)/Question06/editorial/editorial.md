Editorial: Square Star Pattern

Problem Understanding:
Given an integer n, print a square pattern of stars (*) containing n rows and n columns.

Example:

Input:
n = 3

Output:
***
***
***

Approach:
We use two nested for loops to print the pattern.

1. The outer loop controls the number of rows.
2. The inner loop prints n stars in each row.
3. System.out.print("*") prints a star on the same line.
4. System.out.println() moves to the next line after each row.

Algorithm:
1. Read the integer n.
2. Run the outer loop from i = 0 to i < n.
3. Inside the outer loop, run the inner loop from j = 0 to j < n.
4. Print one star (*) during each inner loop iteration.
5. After the inner loop finishes, move to the next line.
6. Repeat until all n rows are printed.

Dry Run:
For n = 2:

First iteration:
The inner loop prints ** and moves to the next line.

Second iteration:
The inner loop prints ** and moves to the next line.

Final Output:
**
**

Time Complexity:
O(n^2), because the outer loop runs n times and the inner loop runs n times for each row.

Space Complexity:
O(1), because only loop variables are used.

Key Takeaway:
The outer loop controls rows, and the inner loop controls columns. Nested loops are commonly used to print patterns.