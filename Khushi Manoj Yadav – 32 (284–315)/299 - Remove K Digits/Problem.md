### Problem 299: Remove K Digits
**Difficulty:** Medium | **Tags:** Monotonic Stack, Greedy, String

**Problem Statement**
Given string `num` representing a non-negative integer and an integer `k`, return the smallest possible integer after removing `k` digits from `num`.
The result must not contain leading zeroes, unless the result is `"0"`.

**Input Format**
- The first line contains the string `num`.
- The second line contains the integer `k`.

**Output Format**
- Print the smallest possible integer string after removing `k` digits.

**Constraints**
- `1 <= k <= num.length <= 10^5`
- `num` consists of only digits.
- `num` does not have any leading zero not counted as a digit.

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
