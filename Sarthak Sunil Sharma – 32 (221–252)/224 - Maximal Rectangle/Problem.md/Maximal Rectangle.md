### Problem 224: Maximal Rectangle
**Difficulty:** Hard | **Tags:** Stack, Matrix, Dynamic Programming, Monotonic Stack

**Problem Statement**
Given a binary matrix containing only `0` and `1`, find the area of the largest axis-aligned rectangle that contains only `1`s.

**Input Format**
* The first line contains two integers `R` and `C`, representing the number of rows and columns.
* Each of the next `R` lines contains a binary string of length `C`. Each character is either `0` or `1`.

**Output Format**
* Print one integer: the maximum area of a rectangle containing only `1`s.

**Constraints**
* 1 ≤ R, C ≤ 200
* Every matrix entry is either 0 or 1.

**Example**
**Input:**
```text
4 5
10100
10111
11111
10010
```

**Output:**
```text
6
```

**Explanation:**
The largest all-1 rectangle has area 6. It can be formed by a block of 3 columns across 2 rows.

**Expected Complexity**
* Time: O(R × C)
* Space: O(C)
