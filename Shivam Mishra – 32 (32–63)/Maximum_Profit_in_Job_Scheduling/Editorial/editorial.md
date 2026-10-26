# Editorial: Maximum Profit in Job Scheduling

## Approach

The problem can be solved efficiently using **sorting, binary search, and dynamic programming**. The objective is to select a subset of non-overlapping jobs such that their total profit is maximized.

Each job is represented by its start time, end time, and profit. Since the jobs may be provided in any order, we first sort them by their start times in ascending order.

### 1. Sort the Jobs

Combine the three arrays into job records containing `(startTime, endTime, profit)` and sort these records by start time.

Sorting allows us to find the next compatible job efficiently using binary search.

### 2. Define the DP State

Let `dp[i]` denote the maximum profit obtainable by considering jobs from index `i` to `n - 1`.

The base case is:

```cpp
dp[n] = 0;
```

When no jobs remain, the maximum additional profit is zero.

### 3. Consider Both Choices

Process the sorted jobs from right to left. For each job `i`, there are two possible choices.

**Option 1: Skip the current job**

If we do not select job `i`, the maximum profit remains the best profit obtainable from the next job onward:

```cpp
int skip = dp[i + 1];
```

**Option 2: Select the current job**

If we select job `i`, we earn its profit and must continue with the first job that starts at or after the current job's end time.

Use binary search to find this job:

```cpp
int next = lower_bound(
    starts.begin() + i + 1,
    starts.end(),
    jobs[i][1]
) - starts.begin();
```

Here, `starts` contains the sorted start times, and `jobs[i][1]` is the current job's end time.

The binary search finds the first compatible job because a job starting exactly when the current job ends is allowed.

The profit obtained by selecting the current job is:

```cpp
int take = jobs[i][2] + dp[next];
```

### 4. Transition

Choose the option that produces the greater profit:

```cpp
dp[i] = max(take, skip);
```

After processing all jobs, `dp[0]` contains the maximum achievable profit.

## Complexity Analysis

- **Time Complexity:** `O(n log n)`. Sorting takes `O(n log n)`, and binary search takes `O(log n)` for each of the `n` jobs.
- **Space Complexity:** `O(n)` for the job records, sorted start times, and dynamic programming array.

## Implementation

The reference C++ implementation is available in `Solution/solution.cpp`.

The program uses a `Solution` class with a `jobScheduling()` method and a `main()` function to handle multiple test cases. It reads the number of test cases, followed by the number of jobs and the three arrays for each case, and prints the maximum profit for each test case.

## Key Observation

The essential optimization is to avoid checking every possible subset of jobs. Sorting organizes the jobs, binary search finds the next compatible job, and dynamic programming reuses previously computed results. Together, these techniques reduce the time complexity from an exponential brute-force approach to `O(n log n)`.