### Problem 300: Next Smaller Element
**Difficulty:** Medium | **Tags:** Monotonic Stack, Array

**Problem Statement**
Given an array `arr` of size `N`, find the Next Smaller Element (NSE) for each element in the array.
The Next Smaller Element for an element `arr[i]` is the first element to the right of `arr[i]` that is strictly smaller than `arr[i]`. If no such element exists, output `-1`.

**Input Format**
- The first line contains an integer `N`, the size of the array.
- The second line contains `N` space-separated integers representing `arr`.

**Output Format**
- Print `N` space-separated integers representing the Next Smaller Element for each position.

**Constraints**
- `1 <= N <= 10^5`
- `-10^9 <= arr[i] <= 10^9`

**Example**
**Input:**
```text
5
4 5 2 10 8
```
**Output:**
```text
2 2 -1 8 -1
```
