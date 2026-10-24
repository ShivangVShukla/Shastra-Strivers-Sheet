# Editorial: Bubble Sort

## Difficulty
Easy

## Algorithm
1. Repeatedly step through the list, comparing adjacent pairs and swapping them if out of order.
2. In each pass $i$ from $0$ to $N - 1$, the largest unsorted element bubbles up to index $N - 1 - i$.
3. Track swaps using a boolean flag `swapped`. If no swaps occur during a pass, the array is already sorted and we break early.

## Complexity Analysis
- **Time Complexity:** $O(N)$ best case (already sorted), $O(N^2)$ worst and average case.
- **Space Complexity:** $O(1)$ in-place.
