# Word Search

Given a grid of \(n \times m\) dimensions called `board` and a string `word`, determine whether the word exists in the grid.

The word can be created by assembling letters from successively adjacent cells. Cells are considered adjacent if they are next to each other **vertically or horizontally**.

It is forbidden to use the same cell more than once in a single word path.

Return `true` if the word exists in the grid; otherwise, return `false`.

## Example 1

**Input:**
```text
3 4
ABCE
SFCS
ADEE
ABCCED
```

**Output:**
```text
true
```

**Explanation:** The word `ABCCED` can be formed by traversing adjacent cells without reusing any cell.

## Example 2

**Input:**
```text
3 4
ABCE
SFCS
ADEE
SEE
```

**Output:**
```text
true
```

**Explanation:** The word `SEE` exists in the grid using vertically and horizontally adjacent cells.

## Example 3

**Input:**
```text
2 3
ABC
DEF
ABCB
```

**Output:**
```text
false
```

**Explanation:** Forming `ABCB` would require reusing a cell, which is forbidden.

## Input Format

- The first line contains two integers, `n` and `m`, representing the number of rows and columns.
- The next `n` lines each contain a string of `m` uppercase characters representing a row of the board.
- The final line contains the string `word` to search for.

## Output Format

Print `true` if the word exists in the board; otherwise, print `false`.

## Constraints

- \(1 \le n,m \le 6\) for the generated test cases.
- \(1 \le |word| \le 10\) for the generated test cases.
- The board contains uppercase English letters.
- Only vertical and horizontal movements are allowed.
- A cell cannot be visited more than once in a single path.

## Approach

Use **Depth-First Search (DFS) with backtracking**.

1. Traverse every cell in the grid.
2. If a cell matches the first character of the word, begin DFS from that cell.
3. Explore its four neighbors: up, down, left, and right.
4. Temporarily mark each visited cell to prevent reuse.
5. Backtrack by restoring the cell after exploring its neighbors.
6. If all characters match in sequence, return `true`. If no path matches, return `false`.

## Complexity Analysis

Let \(n\) and \(m\) be the board dimensions and \(L\) be the length of the word.

**Time Complexity:** \(O(nm \cdot 4 \cdot 3^{L-1})\) as an upper bound for DFS exploration.

**Auxiliary Space Complexity:** \(O(L)\) for the recursive call stack.

