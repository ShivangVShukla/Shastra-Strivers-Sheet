### Problem 305: Maximal Rectangle
**Difficulty:** Hard | **Tags:** Monotonic Stack, Dynamic Programming, Matrix

**Problem Statement**
Given a `R x C` binary matrix filled with `0`'s and `1`'s, find the largest rectangle containing only `1`'s and return its area.

**Input Format**
- The first line contains two integers `R` and `C`, the number of rows and columns.
- The next `R` lines each contain `C` space-separated integers (either `0` or `1`).

**Output Format**
- Print a single integer representing the maximal rectangle area.

**Constraints**
- `1 <= R, C <= 200`
- `matrix[i][j]` is `'0'` or `'1'`.

**Example**
**Input:**
```text
4 5
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
```
**Output:**
```text
6
```
