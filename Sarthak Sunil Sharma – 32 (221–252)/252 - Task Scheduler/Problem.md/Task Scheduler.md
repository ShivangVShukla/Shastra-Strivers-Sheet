### Problem 252: Task Scheduler
**Difficulty:** Medium | **Tags:** Heap, Greedy, Priority Queue, Hashing

**Problem Statement**

You are given a list of tasks represented by uppercase letters and a non-negative integer `n`. Each task takes exactly one unit of time. The same task must have at least `n` units of time between two consecutive executions. In each unit of time, you may either execute an available task or remain idle. Return the minimum number of units required to execute all tasks.

**Input Format**

- The first line contains two integers `m` and `n`, where `m` is the number of tasks.
- The second line contains `m` uppercase English letters separated by spaces.

**Output Format**

Print one integer: the minimum total time needed to finish all tasks.

**Constraints**

- `1 ≤ m ≤ 10^5`
- `0 ≤ n ≤ 10^5`
- Each task is an uppercase English letter from `A` to `Z`.

**Example 1**

**Input:**
```text
6 2
A A A B B B
```

**Output:**
```text
8
```

**Explanation:** One optimal schedule is `A B idle A B idle A B`, which takes 8 units.

**Example 2**

**Input:**
```text
6 0
A A A B B B
```

**Output:**
```text
6
```

**Explanation:** When `n = 0`, there is no cooldown restriction, so all tasks can execute consecutively.

**Approach:**

Count the frequency of each task. Let `maxFreq` be the highest frequency and `numMax` be the number of tasks having that frequency. The minimum time is `max(m, (maxFreq - 1) * (n + 1) + numMax)`.

**Complexity:**

- Time: `O(m + 26)`, effectively `O(m)`.
- Space: `O(26)`, effectively `O(1)`.
