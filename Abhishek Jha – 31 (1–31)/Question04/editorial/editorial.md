Editorial: Student Result With Subject-Wise Rule

1. Prerequisites

- Variables and data types
- Taking input using Scanner
- Conditional statements (if-else)
- Logical operators (&&, ||)
- Arithmetic operations

2. Approach

First, read the marks of three subjects: m1, m2, and m3.

Check whether any mark is less than 0 or greater than 100. If any mark is invalid, print "Invalid marks".

If all marks are valid, calculate the average of the three subjects.

Then check whether all three subjects have marks greater than or equal to 40. If yes, print "Pass"; otherwise, print "Fail".

3. Algorithm

1. Read m1, m2, and m3.
2. Check whether any mark is outside the range 0 to 100.
3. If any mark is invalid, display "Invalid marks".
4. Otherwise, calculate the average.
5. Display the average.
6. If all three marks are at least 40, display "Pass".
7. Otherwise, display "Fail".

4. Examples

Example 1:

Input:
70 80 90

Output:
Average: 80
Pass

Explanation:
All marks are valid, and each subject has at least 40 marks.

Example 2:

Input:
90 90 30

Output:
Average: 70
Fail

Explanation:
The marks are valid, but the third subject has fewer than 40 marks.

Example 3:

Input:
90 105 80

Output:
Invalid marks

Explanation:
The second subject's marks exceed 100, so the average is not calculated.

5. Time and Space Complexity

Time Complexity: O(1), because a fixed number of marks and conditions are processed.

Space Complexity: O(1), because only a fixed number of variables are used.

6. Key Takeaway

Always validate the input before performing calculations. A student passes only when every subject has at least 40 marks.