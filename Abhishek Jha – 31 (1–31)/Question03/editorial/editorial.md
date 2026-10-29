Editorial: Check Eligibility by Age

Prerequisites:
1. Basic knowledge of Java.
2. Understanding of variables and data types.
3. Knowledge of if-else statements.
4. Understanding of comparison operators (>=).

Approach:
We are given an integer age. Our task is to determine whether a person is eligible based on their age.

A person is eligible if their age is greater than or equal to 18. Otherwise, they are not eligible.

Algorithm:
1. Read the age.
2. Check whether age >= 18.
3. If the condition is true, print "Eligible".
4. Otherwise, print "Not Eligible".

Example 1:
Input:
20

Output:
Eligible

Explanation:
Since 20 is greater than 18, the person is eligible.

Example 2:
Input:
16

Output:
Not Eligible

Explanation:
Since 16 is less than 18, the person is not eligible.

Example 3:
Input:
18

Output:
Eligible

Explanation:
Since the age is exactly 18 and the condition allows ages greater than or equal to 18, the person is eligible.

Complexity Analysis:
Time Complexity: O(1), because only one condition is checked.
Space Complexity: O(1), because only a constant amount of extra space is used.