# Editorial: Word Search

## Approach

The problem can be solved using **Depth-First Search (DFS) with Backtracking**.

The objective is to determine whether a given word can be formed by visiting adjacent cells in a character grid. Movement is allowed only in four directions: up, down, left, and right. A cell cannot be used more than once in the same path.

### Algorithm

1. **Traverse the grid:** Iterate through every cell and identify cells matching the first character of the target word.
2. **Start DFS:** For each matching cell, recursively search for the remaining characters of the word.
3. **Check boundaries and characters:** Reject a cell if it is outside the grid, has already been visited, or does not match the current character.
4. **Mark visited cells:** Temporarily mark the current cell so it cannot be reused in the same search path.
5. **Explore four directions:** Recursively visit the neighboring cells—up, down, left, and right—to find the next character.
6. **Backtrack:** Restore the original character when returning from the recursive call, allowing the cell to be considered in other possible paths.
7. **Return the result:** If every character is matched consecutively, return `true`. If no starting cell produces a valid path, return `false`.

An important optimization is to stop searching when the word length exceeds the total number of cells, since revisiting cells is not allowed.

## Complexity Analysis

Let:
- \(R\) = number of rows
- \(C\) = number of columns
- \(L\) = length of the target word

**Time Complexity:** \(O(RC \cdot 4 \cdot 3^{L-1})\), which is commonly simplified to \(O(RC \cdot 3^L)\).

There are \(R \times C\) possible starting cells. From each starting cell, DFS explores up to four directions initially. At subsequent steps, it can explore at most three new directions because it cannot return immediately to the previously visited cell. This gives the stated worst-case upper bound.

**Auxiliary Space Complexity:** \(O(L)\) for the recursive call stack, since the search path cannot contain more than \(L\) cells. The implementation modifies the board temporarily instead of allocating a separate visited matrix.
