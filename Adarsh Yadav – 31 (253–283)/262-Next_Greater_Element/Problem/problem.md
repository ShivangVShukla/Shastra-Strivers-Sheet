### Problem: Next Greater Element

**Difficulty:** Medium
**Tags:** Stack, Monotonic Stack, Array

**Problem Statement**
Given an array `arr` of size `n` containing elements, find the next greater element for each element in the array in the order of their appearance.

The next greater element of an element in the array is the nearest element on the right that is **strictly greater** than the current element. If there does not exist a next greater element for the current element, then the next greater element for that element is `-1`.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each test case consists of two lines:
  * The first line contains a single integer `n`, the size of the array.
  * The second line contains `n` space-separated integers, the elements of `arr`.

**Output Format**
* For each test case, print `n` space-separated integers on a single line: the next greater element of each array element, in order.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ n ≤ 10^5
* 0 ≤ arr[i] ≤ 10^9

**Example 1**

**Input:**
1
4
1 3 2 4

**Output:**
3 4 4 -1

**Explanation:**
In the array, the next larger element to 1 is 3, to 3 is 4, to 2 is 4, and for 4 it is -1, since it does not exist.

**Example 2**

**Input:**
1
5
6 8 0 1 3

**Output:**
8 -1 1 3 -1

**Explanation:**
For 6 the next larger element is 8. For 8 there is no larger element, hence -1. For 0 it is 1, for 1 it is 3, and for 3 there is no larger element on the right, hence -1.
