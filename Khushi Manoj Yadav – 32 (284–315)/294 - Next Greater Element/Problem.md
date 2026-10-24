### Problem 294: Next Greater Element
**Difficulty:** Medium | **Tags:** Monotonic Stack, Array

**Problem Statement**
Given an array `arr` of size `N`, find the Next Greater Element (NGE) for each element in the array.
The Next Greater Element for an element `arr[i]` is the first element to the right of `arr[i]` that is strictly greater than `arr[i]`. If no such element exists, output `-1`.

**Input Format**
- The first line contains an integer `N`, the size of the array.
- The second line contains `N` space-separated integers representing `arr`.

**Output Format**
- Print `N` space-separated integers representing the Next Greater Element for each position.

**Constraints**
- `1 <= N <= 10^5`
- `-10^9 <= arr[i] <= 10^9`

**Example**
**Input:**
```text
4
1 3 2 4
```
**Output:**
```text
3 4 4 -1
```
