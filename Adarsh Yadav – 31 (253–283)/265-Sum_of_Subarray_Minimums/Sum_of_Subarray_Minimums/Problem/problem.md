### Problem: Sum of Subarray Minimums

**Difficulty:** Medium
**Tags:** Stack, Monotonic Stack, Array

**Problem Statement**
Given an array of integers `arr` of size `n`, calculate the sum of the minimum value in each (contiguous) subarray of `arr`. Since the result may be large, return the answer modulo `10^9 + 7`.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each test case consists of two lines:
  * The first line contains a single integer `n`, the size of the array.
  * The second line contains `n` space-separated integers, the elements of `arr`.

**Output Format**
* For each test case, print a single integer on a new line: the sum of the minimums of all subarrays, modulo `10^9 + 7`.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ n ≤ 10^5
* 1 ≤ arr[i] ≤ 10^6

**Example 1**

**Input:**
1
4
3 1 2 5

**Output:**
18

**Explanation:**
The minimums of the subarrays `[3]`, `[1]`, `[2]`, `[5]`, `[3, 1]`, `[1, 2]`, `[2, 5]`, `[3, 1, 2]`, `[1, 2, 5]`, `[3, 1, 2, 5]` are 3, 1, 2, 5, 1, 1, 2, 1, 1, 1 respectively, and their sum is 18.

**Example 2**

**Input:**
1
3
2 3 1

**Output:**
10

**Explanation:**
The minimums of the subarrays `[2]`, `[3]`, `[1]`, `[2, 3]`, `[3, 1]`, `[2, 3, 1]` are 2, 3, 1, 2, 1, 1 respectively, and their sum is 10.
