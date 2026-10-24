# Editorial — Task Scheduler

## Difficulty
Medium

## Prerequisites
- Greedy Algorithms
- Frequency Counting
- Priority Queues / Heaps
- Scheduling and Cooldown Constraints

## Problem Statement

Given uppercase-letter tasks and a cooldown `n`, each task takes one time unit, and two executions of the same task must have at least `n` units between them. Find the minimum total time, including any idle units.

## Explanation & Approach

Count how many times each task appears. Let:
- `maxFreq` = maximum frequency of any task.
- `numMax` = number of distinct task types with frequency `maxFreq`.
- `m` = total number of tasks.

Imagine placing the most frequent task in groups separated by the cooldown:
- There are `maxFreq - 1` gaps between its occurrences.
- Each gap needs `n` cooldown units plus one position for the next group start, giving a frame length of `(maxFreq - 1) * (n + 1)`.
- The `numMax` task types tied for the maximum frequency occupy the final group positions.

This gives the lower bound:

`(maxFreq - 1) * (n + 1) + numMax`

But if there are enough other tasks to fill the gaps, no idle time is needed. The schedule must at least contain all `m` tasks. Therefore, the answer is:

`max(m, (maxFreq - 1) * (n + 1) + numMax)`

This frequency formula is equivalent to the standard max-heap scheduling solution for this problem and runs in linear time.

## Example / Trace

Input:
```text
6 2
A A A B B B
```

Frequencies: `A = 3`, `B = 3`, so `maxFreq = 3` and `numMax = 2`.

```text
(maxFreq - 1) * (n + 1) + numMax
= (3 - 1) * (2 + 1) + 2
= 8
```

One valid schedule is:
```text
A B idle A B idle A B
```

Answer: `8`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        int[] freq = new int[26];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            char task = st.nextToken().charAt(0);
            freq[task - 'A']++;
        }

        int maxFreq = 0;
        for (int count : freq) maxFreq = Math.max(maxFreq, count);

        int numMax = 0;
        for (int count : freq) {
            if (count == maxFreq) numMax++;
        }

        long frameLength = (long) (maxFreq - 1) * (n + 1L) + numMax;
        System.out.println(Math.max((long) m, frameLength));
    }
}
```

## Complexity

- Time: `O(m + 26) = O(m)`.
- Space: `O(26) = O(1)`.

## Edge Cases

- `n = 0`: answer is the number of tasks.
- All tasks are the same: idle time may be necessary.
- All tasks are distinct: no idle time is needed.
- Several task types tie for maximum frequency.
- Large cooldown values.
- One task only.

## Why the Approach Works

The most frequent task determines the minimum frame size needed to respect cooldowns. The formula accounts for the gaps between its occurrences and all task types tied at the maximum frequency. If the number of tasks exceeds that frame size, other tasks can fill the gaps, so the answer is simply the task count. Taking the maximum of these two quantities gives the minimum possible schedule length.
