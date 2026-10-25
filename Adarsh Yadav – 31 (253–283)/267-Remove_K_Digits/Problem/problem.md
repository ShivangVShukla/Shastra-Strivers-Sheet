### Problem: Remove K Digits

**Difficulty:** Medium
**Tags:** Stack, Greedy, String

**Problem Statement**
Given a string `nums` representing a non-negative integer, and an integer `k`, find the smallest possible integer after removing `k` digits from `nums`.

Note: If removing `k` digits deletes all digits, return `"0"`. The result must be a valid non-negative integer without leading zeros.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each of the next `T` lines contains a string `nums` and an integer `k`, separated by a single space.

**Output Format**
* For each test case, print the smallest possible integer (as a string, without leading zeros) on a new line.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ k ≤ |nums| ≤ 10^4
* `nums` consists of only digits.
* `nums` does not have any leading zeros except for the zero itself.

**Example 1**

**Input:**
1
541892 2

**Output:**
1892

**Explanation:**
Removing the two digits 5 and 4 yields the smallest number, 1892.

**Example 2**

**Input:**
1
1002991 3

**Output:**
21

**Explanation:**
Remove the three digits 1 (leading one), 9, and 9 to form the new number 21. Note that the output must not contain leading zeroes. 21 is the smallest possible result.
