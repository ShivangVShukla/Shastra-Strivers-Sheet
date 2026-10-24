### Problem 227: The Celebrity Problem
**Difficulty:** Medium | **Tags:** Stack, Two Pointers, Matrix, Greedy

**Problem Statement**
A celebrity is a person at a party who is known by everyone but does not know anyone at the party.

You are given an `N x N` matrix `M` where `M[i][j] = 1` means person `i` knows person `j`, and `M[i][j] = 0` means person `i` does not know person `j`.

Find the celebrity, if one exists. If there is no celebrity, return `-1`.

**Input Format**
* The first line contains an integer `N`.
* The next `N` lines each contain `N` space-separated integers representing the matrix.

**Output Format**
* Print the zero-based index of the celebrity if one exists.
* Otherwise print `-1`.

**Constraints**
* 1 ≤ N ≤ 1000
* `M[i][j]` is either `0` or `1`.
* `M[i][i]` is `0`.

**Example**
**Input:**
```text
3
0 1 0
0 0 0
0 1 0
```

**Output:**
```text
1
```

**Explanation:**
Person `1` is known by persons `0` and `2`, and person `1` does not know either of them. Therefore, person `1` is the celebrity.

**Expected Complexity**
* Time: O(N)
* Auxiliary Space: O(1) after the matrix is given.
