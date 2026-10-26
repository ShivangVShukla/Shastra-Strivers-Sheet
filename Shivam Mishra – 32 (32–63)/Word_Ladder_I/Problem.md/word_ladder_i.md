
# Word Ladder I

## Problem Statement

Given are two distinct words `startWord` and `targetWord`, and a list of size `N`, denoting `wordList` of **unique words** of equal size `M`. Find the length of the shortest transformation sequence from `startWord` to `targetWord`.

Keep the following conditions in mind:

- A word can only consist of lowercase characters.
- Only one letter can be changed in each transformation.
- Each transformed word must exist in the `wordList`, including the `targetWord`.
- `startWord` may or may not be part of the `wordList`.

**Note:** If there is no possible way to transform the sequence from `startWord` to `targetWord`, return `0`.

## Example 1

**Input:**
```text
5
des der dfr dgt dfs
der dfs
```

**Output:**
```text
3
```

**Explanation:**

The length of the shortest transformation sequence from `der` to `dfs` is `3`.

The sequence is:

`der` -> `dfr` -> `dfs`

Each word in the sequence, except the starting word, belongs to `wordList`.

## Example 2

**Input:**
```text
2
geek gefk
gedk geek
```

**Output:**
```text
2
```

**Explanation:**

The length of the shortest transformation sequence from `gedk` to `geek` is `2`.

The sequence is:

`gedk` -> `geek`

Only one letter changes, and the target word exists in `wordList`.

## Constraints

- `1 <= N <= 10^4`
- `1 <= M <= 10`
- `startWord.length() == targetWord.length() == M`
- All words consist of lowercase English letters.
- All words in `wordList` are unique.

## Expected Complexity

- **Time Complexity:** `O(N * M * 26)` using BFS with character substitutions.
- **Auxiliary Space:** `O(N * M)` for the word set, visited information, and BFS queue.
