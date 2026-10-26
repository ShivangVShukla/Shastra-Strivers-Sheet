
# Insert Interval

Given a 2D array `Intervals`, where `Intervals[i] = [start[i], end[i]]` represents the start and end of the ith interval, the array represents non-overlapping intervals sorted in ascending order by `start[i]`.

Given another array `newInterval`, where `newInterval = [start, end]` represents the start and end of another interval, merge `newInterval` into `Intervals` such that `Intervals` remain non-overlapping and sorted in ascending order by `start[i]`.

Return **Intervals** after the insertion of `newInterval`.

### Example 1

**Input:**
```text
Intervals = [[1, 3], [6, 9]]
newInterval = [2, 5]
```

**Output:**
```text
[[1, 5], [6, 9]]
```

**Explanation:**

After inserting the `newInterval`, the `Intervals` array becomes `[[1, 3], [2, 5], [6, 9]]`.

To make the intervals non-overlapping, we merge `[1, 3]` and `[2, 5]`.

Therefore, the final array is `[[1, 5], [6, 9]]`.

### Example 2

**Input:**
```text
Intervals = [[1, 2], [3, 5], [6, 7], [8, 10]]
newInterval = [4, 8]
```

**Output:**
```text
[[1, 2], [3, 10]]
```

**Explanation:**

After inserting the `newInterval`, the `Intervals` array becomes `[[1, 2], [3, 5], [4, 8], [6, 7], [8, 10]]`.

We merge the overlapping intervals to make the intervals non-overlapping and sorted.

Therefore, the final array is `[[1, 2], [3, 10]]`.
