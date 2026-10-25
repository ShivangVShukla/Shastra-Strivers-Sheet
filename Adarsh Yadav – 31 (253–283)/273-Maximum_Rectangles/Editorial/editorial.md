# Editorial — Maximum Rectangles

## Approach: Row Histograms + Monotonic Stack

Treat each row as the bottom of a histogram. Maintain a `heights` array where `heights[c]` is the number of consecutive `1`s ending at the current row in column `c`.

1. Initialize all histogram heights to zero and the maximum area to zero.
2. Process each row from top to bottom:
   - If the cell is `1`, increment that column's height.
   - If the cell is `0`, reset that column's height to zero.
3. Compute the largest rectangle in the current histogram with a monotonic increasing stack.
4. Update the maximum matrix rectangle area with the histogram's maximum area.
5. Return the maximum found over all rows.

Any all-ones rectangle has a bottom row. At that row, its columns form a valid rectangle in the corresponding histogram, so checking every row finds the global maximum.

## Complexity

- **Time:** `O(M * N)` — each row's histogram is processed in linear time.
- **Auxiliary space:** `O(N)` for the histogram and stack.
