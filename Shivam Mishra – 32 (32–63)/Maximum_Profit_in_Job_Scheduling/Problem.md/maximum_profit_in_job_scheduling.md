# Maximum Profit in Job Scheduling

## Difficulty

Hard

## Prerequisites

- Arrays
- Sorting
- Binary Search
- Dynamic Programming

## Problem Statement

You are given `n` jobs, where every job is represented by three arrays:

- `startTime[i]`: the start time of the job.
- `endTime[i]`: the end time of the job.
- `profit[i]`: the profit earned from completing the job.

You cannot take two jobs that overlap in time.

Return the maximum profit you can earn such that there are no two overlapping jobs in your selected subset.

**Note:** A job ending at time `X` is allowed to be followed by another job that starts exactly at time `X`.

## Example 1

**Input:**

```text
startTime = [1, 2, 3, 3]
endTime   = [3, 4, 5, 6]
profit    = [50, 10, 40, 70]
```

**Output:**

```text
120
```

**Explanation:**

Select jobs with intervals `[1, 3]` and `[3, 6]`.

Total profit = `50 + 70 = 120`.

## Example 2

**Input:**

```text
startTime = [1, 2, 3, 4, 6]
endTime   = [3, 5, 10, 6, 9]
profit    = [20, 20, 100, 70, 60]
```

**Output:**

```text
150
```

**Explanation:**

Select jobs with intervals `[1, 3]`, `[4, 6]`, and `[6, 9]`.

Total profit = `20 + 70 + 60 = 150`.

## Constraints

- `1 <= n <= 50000`
- `1 <= startTime[i] < endTime[i] <= 10^9`
- `1 <= profit[i] <= 10^4`

## Explanation and Approach

The objective is to choose a subset of jobs with the maximum total profit while ensuring that no two selected jobs overlap.

Trying every possible subset would be too slow for up to 50,000 jobs.

We can solve the problem efficiently using **sorting, binary search, and dynamic programming**.

### 1. Sort the jobs

Combine each job's start time, end time, and profit into a single record.

Sort the records by start time in ascending order. This allows us to search efficiently for the next compatible job.

### 2. Define the DP state

Let `dp[i]` represent the maximum profit obtainable by selecting jobs from index `i` onward.

The base case is:

```cpp
dp[n] = 0;
```

There are no jobs remaining after index `n - 1`, so the maximum additional profit is zero.

### 3. Consider two choices

For each job `i`, we have two options.

**Skip the job:**

```cpp
skip = dp[i + 1];
```

**Take the job:**

Find the first job whose start time is greater than or equal to the current job's end time.

Let its index be `next`.

```cpp
take = profit[i] + dp[next];
```

The binary search must use `startTime >= endTime[i]`, because jobs that meet exactly at an endpoint are compatible.

### 4. Calculate the maximum profit

The recurrence is:

```text
dp[i] = max(skip, take)
```

Process the jobs from right to left so that the required DP states have already been calculated.

The final answer is:

```cpp
dp[0]
```

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — sorting takes `O(n log n)`, and each job requires one binary search.
- **Auxiliary Space:** `O(n)` — the job records, sorted start times, and DP array require linear space.

## Input Format Used by solution.cpp

The standalone C++ program accepts multiple test cases.

```text
T
n
startTime[0] startTime[1] ... startTime[n-1]
endTime[0] endTime[1] ... endTime[n-1]
profit[0] profit[1] ... profit[n-1]
```

Repeat the four lines after `T` for each test case.

## Output Format

For every test case, print one integer representing the maximum achievable profit.

## Key Takeaway

This problem is a classic example of **weighted interval scheduling**. Sorting establishes the order, binary search finds the next compatible job, and dynamic programming compares taking a job against skipping it.