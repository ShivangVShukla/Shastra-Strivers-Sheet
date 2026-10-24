# 305. Maximal Rectangle - Editorial

## Approach
Convert the 2D problem into 1D Largest Rectangle in Histogram for each row:
1. Maintain an array `heights` of size `C`, initialized to 0.
2. For each row `r`:
   - For each column `c`:
     - If `matrix[r][c] == 1`, `heights[c] += 1`.
     - Else, `heights[c] = 0`.
   - Run the Largest Rectangle in Histogram algorithm on `heights`.
   - Update global maximum rectangle area.

## Complexity
- Time Complexity: O(R * C)
- Space Complexity: O(C)
