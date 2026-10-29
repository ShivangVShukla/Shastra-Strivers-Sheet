# Editorial: 54. Task Scheduler

## Difficulty

Medium

## Prerequisites

- Greedy
- frequency counting
- priority queue / scheduling

## Problem Understanding

Tasks are uppercase English letters. Every task requires one CPU interval, and identical tasks must have at least n intervals between executions. During those intervals the CPU may execute other tasks or remain idle. Return the minimum total intervals needed to execute all tasks.

## Approach

Count the frequency of each task. Let `F` be the maximum frequency and `K` be the number of task types that occur exactly `F` times. The most frequent tasks force at least `(F - 1) * (cooldown + 1) + K` intervals: the first `F - 1` occurrences create cooldown-separated frames, and the final frame contains the `K` most frequent task types. If other tasks are numerous enough to fill all idle positions, the schedule is simply the number of tasks. Therefore the answer is `max(tasks.length, (F - 1) * (cooldown + 1) + K)`.

This formula handles zero cooldown, ties for maximum frequency, and cases with no idle time without explicitly simulating a schedule.

## Step-by-Step Algorithm

1. Count the occurrences of each of the 26 task letters.
2. Find the largest frequency `F`.
3. Count how many task types occur `F` times; call this `K`.
4. Compute the forced schedule length `(F - 1) * (cooldown + 1) + K`.
5. Return the larger of this value and the number of tasks.

## Worked Example

For `AAA BBB` with cooldown 2, one optimal schedule is `A B idle A B idle A B`, requiring 8 intervals. The frequency formula gives `F=3`, `K=2`, so `max(6, (3-1)*(2+1)+2)=8`.

## Correctness Argument

The most frequent task type determines the minimum spacing structure. Its F occurrences require F-1 gaps of cooldown+1 intervals, with K equally frequent final tasks occupying the last frame. This gives a lower bound of `(F-1)*(cooldown+1)+K`; if there are enough other tasks to fill the gaps, the number of tasks is the lower bound instead. The maximum of these quantities is achievable and minimal.

## Complexity Analysis

**Time:** O(n + 26), effectively O(n), to count task frequencies. **Auxiliary space:** O(1), since there are only 26 task types.

## Conclusion

Task frequencies reveal the cooldown-imposed lower bound, allowing the minimum interval count to be calculated directly.
