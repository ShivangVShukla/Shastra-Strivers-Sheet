Editorial: Count the Digits

1. Prerequisites

- Variables and data types
- Taking input using Scanner
- While loop
- Integer division
- Conditional statements (if-else)

2. Approach

Read a non-negative integer N.

Initialize count to 0.

If N is 0, set count to 1 because zero contains one digit.

Otherwise, use a while loop that runs while N is greater than 0. In each iteration, increment count by 1 and divide N by 10 to remove its last digit.

Finally, print count.

3. Algorithm

1. Read N.
2. Initialize count = 0.
3. If N == 0, set count = 1.
4. Otherwise, repeat while N > 0:
   - Increment count by 1.
   - Update N = N / 10.
5. Print count.

4. Examples

Example 1:

Input:
472

Output:
3

Explanation:
472 contains three digits: 4, 7, and 2.

Example 2:

Input:
1050

Output:
4

Explanation:
1050 contains four digits. The trailing zero is also counted.

Example 3:

Input:
7

Output:
1

Explanation:
The number contains only one digit.

Example 4:

Input:
0

Output:
1

Explanation:
Zero itself is a single digit.

5. Dry Run

Input: 472

Initially, count = 0.

Iteration 1:
N = 472
count = 1
N = 47

Iteration 2:
N = 47
count = 2
N = 4

Iteration 3:
N = 4
count = 3
N = 0

The loop stops because N is no longer greater than 0.

Output:
3

6. Time and Space Complexity

Time Complexity: O(log10 N) for N > 0, because the number is divided by 10 in each iteration.

Space Complexity: O(1), because only a constant amount of extra space is used.

7. Key Takeaway

Integer division by 10 removes the last digit of a non-negative integer. Counting how many times this operation can be performed gives the total number of digits. Remember to handle 0 separately because it contains one digit.