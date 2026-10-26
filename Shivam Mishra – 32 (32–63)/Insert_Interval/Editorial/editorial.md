
# Editorial: Insert Interval

## Topic
Intervals / Arrays / Sorting

## Problem Statement

Given a 2D array `Intervals`, where `Intervals[i] = [start[i], end[i]]` represents an interval, the intervals are sorted in ascending order of their starting points and do not overlap.

We are also given a `newInterval = [start, end]`. We need to insert this interval into the existing intervals while ensuring that the resulting intervals remain sorted and non-overlapping.

If the new interval overlaps with any existing intervals, those intervals must be merged into a single interval.

Return the final list of intervals after insertion and merging.

## Approach

We can solve this problem efficiently using a single traversal of the sorted intervals. Since the given intervals are already sorted and non-overlapping, there is no need to sort them again.

We divide the intervals into three categories:

1. **Intervals before the new interval:** These intervals end before the new interval starts. They cannot overlap with the new interval, so we add them directly to the answer.
2. **Overlapping intervals:** These intervals overlap with the new interval. We merge them by updating the start to the minimum of both starting points and the end to the maximum of both ending points.
3. **Intervals after the new interval:** Once all overlapping intervals have been processed, the merged interval is added to the answer. All remaining intervals can then be appended directly because they are already sorted and non-overlapping.

This approach processes every interval at most once and does not require any additional sorting.

## Algorithm

1. Initialize an empty result array `ans` and a pointer `i = 0`.
2. Traverse the intervals while the current interval ends before the new interval starts.
   - Add each such interval to `ans`.
   - Increment `i`.
3. Traverse the intervals that overlap with the new interval.
   - Update the start of `newInterval` using the minimum of the two starting points.
   - Update the end of `newInterval` using the maximum of the two ending points.
   - Increment `i`.
4. Add the final merged `newInterval` to `ans`.
5. Append all the remaining intervals to `ans`.
6. Return `ans`.

## C++ Implementation

```cpp
class Solution {
public:
    vector<vector<int>> insertNewInterval(
        vector<vector<int>>& Intervals,
        vector<int>& newInterval
    ) {
        vector<vector<int>> ans;
        int n = Intervals.size();
        int i = 0;

        // Step 1: Add intervals before the new interval.
        while (i < n && Intervals[i][1] < newInterval[0]) {
            ans.push_back(Intervals[i]);
            i++;
        }

        // Step 2: Merge all overlapping intervals.
        while (i < n && Intervals[i][0] <= newInterval[1]) {
            newInterval[0] = min(
                newInterval[0], Intervals[i][0]
            );

            newInterval[1] = max(
                newInterval[1], Intervals[i][1]
            );

            i++;
        }

        // Step 3: Add the merged interval.
        ans.push_back(newInterval);

        // Step 4: Add all remaining intervals.
        while (i < n) {
            ans.push_back(Intervals[i]);
            i++;
        }

        return ans;
    }
};
```

## Dry Run

**Input:**

```text
Intervals = [[1, 2], [3, 5], [6, 7], [8, 10]]
newInterval = [4, 8]
```

Initially:

- `ans = []`
- `newInterval = [4, 8]`
- `i = 0`

### Step 1: Process intervals before the new interval

The first interval is `[1, 2]`.

Since `2 < 4`, this interval ends before the new interval starts. Add it to the answer.

```text
ans = [[1, 2]]
i = 1
```

### Step 2: Merge overlapping intervals

The current interval is `[3, 5]`.

Since `3 <= 8`, it overlaps with the new interval `[4, 8]`.

Update:

```text
start = min(4, 3) = 3
end   = max(8, 5) = 8
newInterval = [3, 8]
```

Next, process `[6, 7]`.

Since `6 <= 8`, it overlaps with `[3, 8]`. The merged interval remains `[3, 8]`.

Next, process `[8, 10]`.

Since `8 <= 8`, it overlaps at the boundary. Merge it:

```text
start = min(3, 8) = 3
end   = max(8, 10) = 10
newInterval = [3, 10]
```

All overlapping intervals have now been processed.

### Step 3: Add the merged interval

```text
ans = [[1, 2], [3, 10]]
```

There are no remaining intervals.

**Final Output:**

```text
[[1, 2], [3, 10]]
```

## Complexity Analysis

Let `n` be the number of intervals in the input array.

- **Time Complexity: O(n)**

  Each interval is visited at most once across the three traversal phases. Therefore, the total running time is linear in the number of intervals.

- **Auxiliary Space: O(1)**

  The algorithm uses only a constant number of additional variables, excluding the result array. The result array requires **O(n)** space in the worst case.

  Therefore, the total space complexity, including the output, is **O(n)**.

## Edge Cases

1. **Empty interval array:** The new interval is returned as the only interval.
2. **No overlapping intervals:** The new interval is inserted at its correct sorted position.
3. **Overlap with one interval:** Only the overlapping interval and the new interval are merged.
4. **Overlap with multiple intervals:** All overlapping intervals are merged into one interval.
5. **New interval covers all intervals:** The result contains only the merged interval.
6. **New interval lies inside an existing interval:** The existing interval remains unchanged in the result.
7. **Boundary overlap:** Intervals sharing an endpoint, such as `[1, 3]` and `[3, 5]`, are merged by this implementation.

## Conclusion

The Insert Interval problem can be solved optimally in **O(n) time** by exploiting the sorted, non-overlapping property of the input.

Instead of inserting the new interval and sorting the entire array again, we process the intervals in three phases: add intervals before the new interval, merge overlapping intervals, and append the remaining intervals.

This approach is efficient, easy to implement, and avoids unnecessary sorting.
```
