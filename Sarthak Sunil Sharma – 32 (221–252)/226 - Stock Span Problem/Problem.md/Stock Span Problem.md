### Problem 226: Stock Span Problem
**Difficulty:** Medium | **Tags:** Stack, Monotonic Stack, Array

**Problem Statement**
The stock span problem is a financial problem where we have a series of `n` daily price quotes for a stock. For each day, find the span of that day's price.

The span of a stock's price on a given day is the maximum number of consecutive days, ending on that day, for which the stock price was less than or equal to the current day's price.

**Input Format**
* The first line contains an integer `N`, the number of days.
* The second line contains `N` space-separated integers representing the stock prices.

**Output Format**
* Print `N` space-separated integers representing the stock span for every day.

**Constraints**
* 1 ≤ N ≤ 100000
* 1 ≤ price[i] ≤ 100000

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

**Explanation:**
For price `75`, the consecutive previous prices less than or equal to it are `60, 70, 60, 75`, so its span is `4`. For price `85`, all six previous prices are less than or equal to `85`, so its span is `6` including the current day.

**Expected Complexity**
* Time: O(N)
* Space: O(N)
