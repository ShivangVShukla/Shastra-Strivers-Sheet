### Problem 313: Fruit Into Baskets
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Array

**Problem Statement**
You are visiting a farm that has a single row of fruit trees arranged from left to right. The trees are represented by an integer array `fruits` where `fruits[i]` is the type of fruit the `i`-th tree produces.
You have two baskets, and each basket can only hold a single type of fruit. There is no limit on the amount of fruit each basket can hold.
Starting from any tree of your choice, you must pick exactly one fruit from every tree while moving to the right. You must stop when you reach a tree with fruit that cannot fit in your baskets.
Return the maximum number of fruits you can pick. (Equivalent to finding the length of the longest contiguous subarray with at most 2 distinct integers).

**Input Format**
- The first line contains an integer `N`, the number of trees.
- The second line contains `N` space-separated integers representing `fruits`.

**Output Format**
- Print a single integer representing the maximum number of fruits you can pick.

**Constraints**
- `1 <= N <= 10^5`
- `0 <= fruits[i] <= 10^9`

**Example**
**Input:**
```text
5
1 2 3 2 2
```
**Output:**
```text
4
```
