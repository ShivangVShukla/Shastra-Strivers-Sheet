### Problem 223: Largest Rectangle in Histogram
**Difficulty:** Hard | **Tags:** Stack and Queues, Monotonic Stack
**Problem Statement**
You are given an array `heights` where `heights[i]` represents the height of a histogram bar and every bar has width `1`. Return the area of the largest rectangle that can be formed using one or more contiguous bars.

A rectangle must use consecutive bars, and its height is limited by the shortest bar in the chosen range.

**Input Format**
* The first line contains one integer `N`, the number of histogram bars.
* The second line contains `N` space-separated integers representing `heights`.

**Output Format**
* Print one integer: the maximum rectangular area.

**Constraints**
* `1 <= N <= 100000`
* `0 <= heights[i] <= 10000`

**Example**
**Input:**
```text
6
2 1 5 6 2 3
```

**Output:**
```text
10
```

**Explanation:**
The largest rectangle uses the bars with heights `5` and `6`. Its limiting height is `5`, its width is `2`, and its area is `5 * 2 = 10`.
