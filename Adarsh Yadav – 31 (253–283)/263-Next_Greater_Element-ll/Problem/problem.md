### Problem: Next Greater Element - 2

**Difficulty:** Medium
**Tags:** Stack, Monotonic Stack, Array, Circular Array

**Problem Statement**
Given a circular integer array `arr`, return the next greater element for every element in `arr`.

The next greater element for an element `x` is the first element **strictly greater** than `x` that we come across while traversing the array in a clockwise manner (moving to the right and wrapping around from the last index to the first). If it doesn't exist, return `-1` for that element.

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
11
3 10 4 2 1 2 6 1 7 2 9

**Output:**
10 -1 6 6 2 6 7 7 9 9 10

**Explanation:**
For the first element, 3, the closest greater element while traversing is 10, so 10 is placed at index 0 of the result. For the second element, 10, there is no greater number anywhere in the circular array, so its next greater element is -1. The last element, 9, has no greater element to its right, but wrapping around the array we reach 10, so its answer is 10. Similarly, the next greater elements are found for all other elements.

**Example 2**

**Input:**
1
6
5 7 1 7 6 0

**Output:**
7 -1 7 -1 7 5

**Explanation:**
For the first element, 5, the closest greater element is 7. For the second element, 7, there is no greater number, hence -1. The last element, 0, has nothing to its right, but wrapping around, the first element 5 is greater, so its answer is 5. Similarly, the next greater elements are found for all other elements.
