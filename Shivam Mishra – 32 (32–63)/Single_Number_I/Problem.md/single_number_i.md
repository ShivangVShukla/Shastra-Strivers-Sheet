
# Single Number - I

Given an array `nums` of `n` integers, every integer in the array appears twice except for one integer. Find the number that appears exactly once.

### Example 1

**Input:**
```text
7
1 2 2 4 3 1 4
```

**Output:**
```text
3
```

**Explanation:** The integer `3` appears only once in the array.

### Example 2

**Input:**
```text
1
5
```

**Output:**
```text
5
```

**Explanation:** The integer `5` appears only once in the array.

### Constraints

- `1 <= n <= 10^5`
- `-10^5 <= nums[i] <= 10^5`
- Every integer appears exactly twice except one integer, which appears once.

### Expected Complexity

- **Time Complexity:** `O(n)`
- **Auxiliary Space Complexity:** `O(1)`

### Input Format

- The first line contains an integer `n`, representing the size of the array.
- The second line contains `n` space-separated integers representing `nums`.

### Output Format

Print the integer that appears exactly once in the array.
