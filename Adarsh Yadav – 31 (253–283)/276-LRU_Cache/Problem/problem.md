# LRU Cache

## Problem Statement

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement an LRU cache with a positive capacity:

- `get(key)`: Return the value associated with `key` if it exists; otherwise return `-1`. A successful `get` marks the key as recently used.
- `put(key, value)`: Update the value if the key exists, or insert the key-value pair if it does not. If the cache exceeds its capacity, evict the least recently used key.

Both operations must run in `O(1)` average time.

## Input Format

- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains two integers `capacity` and `Q`, the cache capacity and number of operations.
  - Each of the next `Q` lines contains either:
    - `1 key value` for `put(key, value)`, or
    - `2 key` for `get(key)`.

## Output Format

For each test case, print the result of each operation separated by spaces:
- Print `null` for every `put` operation.
- Print the returned integer for every `get` operation.

## Examples

### Example 1

Input:
```text
1
2 9
1 1 1
1 2 2
2 1
1 3 3
2 2
1 4 4
2 1
2 3
2 4
```
Output:
```text
null null 1 null -1 null -1 3 4
```

### Example 2

Input:
```text
1
1 7
1 1 1
1 2 2
2 1
1 3 3
2 2
1 4 4
2 3
```
Output:
```text
null null -1 null -1 null -1
```

### Example 3

Input:
```text
1
2 7
1 1 1
1 2 2
2 1
1 3 3
1 4 4
2 2
2 4
```
Output:
```text
null null 1 null null -1 4
```

## Constraints

- `1 <= capacity <= 1000`
- `0 <= key <= 10^4`
- `0 <= value <= 10^5`
- At most `10^5` operations per test case.
