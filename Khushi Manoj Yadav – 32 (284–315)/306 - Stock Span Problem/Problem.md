### Problem 306: Stock Span Problem
**Difficulty:** Medium | **Tags:** Monotonic Stack, Array

**Problem Statement**
The stock span problem is a financial problem where we have a series of `N` daily price quotes for a stock. For each day, find the span of that day's price.
The span of the stock's price on a given day is the maximum number of consecutive days, ending on that day, for which the stock price was less than or equal to the current day's price.

**Input Format**
- The first line contains an integer `N`, the number of days.
- The second line contains `N` space-separated integers representing stock prices.

**Output Format**
- Print `N` space-separated integers representing the stock span for each day.

**Constraints**
- `1 <= N <= 10^5`
- `1 <= price[i] <= 10^9`

**Example**
**Input:**
```text
7
100 80 60 70 60 75 85
```
**Output:**
```text
1 1 1 2 1 4 6
```
