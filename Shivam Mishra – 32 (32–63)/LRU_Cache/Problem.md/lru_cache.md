# 51. LRU Cache

## Problem Statement

Implement an LRU cache with positive capacity. Operation 1 key value means put(key,value); operation 2 key means get(key). A get returns the value if present or -1 otherwise. A put updates an existing key or inserts a new key; if capacity is exceeded, evict the least recently used key. Both operations must run in O(1) average time.

## Input Format

The first line contains `capacity operationCount`. Each operation follows as `1 key value` (put) or `2 key` (get). The complete test input may be on one line.

## Output Format

Print one token per operation in original order: `null` for put operations and the returned integer for get operations. Separate tokens with spaces.

## Constraints

- 1 <= capacity <= 1000; 0 <= key <= 10000; 0 <= value <= 100000; at most 100000 operations.

## Examples

Example 1
Input: 2 9 1 1 1 1 2 2 2 1 1 3 3 2 2 1 4 4 2 1 2 3 2 4
Output: null null 1 null -1 null -1 3 4

Each operation is encoded as `1 key value` for put or `2 key` for get. The first two integers are capacity and operation count.

