### Problem 222: Remove K Digits
**Difficulty:** Medium | **Tags:** Stack and Queues, Monotonic Stack
**Problem Statement**
Given a non-negative integer represented as a string `num`, remove exactly `k` digits from the number so that the resulting number is the smallest possible number. Return the resulting number as a string.

The result should not contain leading zeros. If all digits are removed, return `0`.

**Input Format**
* The first line contains the non-negative integer `num` as a string.
* The second line contains one integer `k`, the number of digits to remove.

**Output Format**
* Print the smallest possible number after removing exactly `k` digits, without leading zeros. If no digits remain, print `0`.

**Constraints**
* `1 <= num.length <= 100000`
* `num` consists of digits (`0`-`9`).
* `1 <= k <= num.length`
* `num` represents a non-negative integer.

**Example**
**Input:**
```text
1432219
3
```

**Output:**
```text
1219
```

**Explanation:**
Remove the digits `4`, `3`, and `2` to obtain `1219`, which is the smallest possible number.
