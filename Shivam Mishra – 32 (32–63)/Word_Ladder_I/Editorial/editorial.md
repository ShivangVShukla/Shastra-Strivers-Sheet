
# Editorial: Word Ladder I

## Topic
BFS (Breadth-First Search) / Graph

## Problem Statement
Given two distinct words `startWord` and `targetWord`, along with a dictionary `wordList` containing unique words of equal length, find the length of the shortest transformation sequence from `startWord` to `targetWord`.

The following conditions must be satisfied:
- Only one character can be changed at a time.
- Every transformed word must exist in `wordList`, including `targetWord`.
- `startWord` may or may not be present in `wordList`.
- The sequence length includes both `startWord` and `targetWord`.
- If no valid transformation sequence exists, return `0`.

## Approach
We can solve this problem efficiently using Breadth-First Search (BFS).

Each word can be considered a node in a graph, and an edge exists between two words if they differ by exactly one character. The objective is to find the shortest path from `startWord` to `targetWord`.

Since every transformation has the same cost, BFS explores the graph level by level and guarantees that the first time we reach the target word, we have found the shortest transformation sequence.

### Algorithm
1. Insert all words from `wordList` into an unordered set for efficient lookup.
2. If `targetWord` is not present in the set, return `0`, because the target must belong to the dictionary.
3. Initialize a queue containing the pair `(startWord, 1)`. The second value represents the number of words in the current transformation sequence, including the starting word.
4. Create a visited set and mark `startWord` as visited to prevent repeated processing.
5. While the queue is not empty:
   - Remove the front element, obtaining the current word and its sequence length.
   - If the current word equals `targetWord`, return its sequence length.
   - Iterate through every character position in the current word.
   - Replace that character with each lowercase English letter from `'a'` to `'z'`, skipping the original character.
   - If the newly generated word exists in the dictionary and has not been visited:
     - Mark it as visited.
     - Add it to the queue with the sequence length increased by one.
   - Restore the original character before processing the next position.
6. If the queue becomes empty without reaching `targetWord`, return `0`.

### Example Walkthrough

**Input:**
```text
wordList = ["des", "der", "dfr", "dgt", "dfs"]
startWord = "der"
targetWord = "dfs"
```

**Step 1:** Initialize the queue.

```text
Queue: [(der, 1)]
```

**Step 2:** Process `der`.

Changing one character at a time generates valid dictionary words such as `des` and `dfr`.

```text
Queue: [(des, 2), (dfr, 2)]
```

**Step 3:** Process the next words.

- `des` cannot reach `dfs` in one transformation.
- `dfr` can reach `dfs` by changing the last character from `'r'` to `'s'`.

```text
Queue: [(dfr, 2), ...]
```

When `dfs` is discovered, it is added to the queue with a sequence length of `3`. When it is removed from the queue, the algorithm returns `3`.

**Shortest transformation sequence:**

```text
der -> dfr -> dfs
```

Therefore, the answer is `3`.

## Solution
```Cpp
class Solution {
public:
    int wordLadderLength(string startWord, string targetWord,
                         vector<string>& wordList) {
        unordered_set<string> words(wordList.begin(), wordList.end());

        if (!words.count(targetWord)) {
            return 0;
        }

        queue<pair<string, int>> q;
        q.push({startWord, 1});

        unordered_set<string> visited;
        visited.insert(startWord);

        while (!q.empty()) {
            string word = q.front().first;
            int steps = q.front().second;
            q.pop();

            if (word == targetWord) {
                return steps;
            }

            for (int i = 0; i < (int)word.size(); i++) {
                char original = word[i];

                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (ch == original) continue;

                    word[i] = ch;

                    if (words.count(word) && !visited.count(word)) {
                        visited.insert(word);
                        q.push({word, steps + 1});
                    }
                }

                word[i] = original;
            }
        }

        return 0;
    }
};
```

## Complexity

Let:
- `N` be the number of words in `wordList`.
- `M` be the length of each word.
- `26` be the number of lowercase English letters.

**Expected Time Complexity:** `O(N * M * 26)`, or equivalently `O(N * M)`, assuming average constant-time hash-set operations. Each word is processed at most once, and for each word, every character position is tested with 26 possible letters.

**Auxiliary Space:** `O(N * M)` for storing the dictionary words, visited words, and BFS queue. The queue and visited set contain at most `N + 1` distinct words, including a possible `startWord` outside the dictionary.

## Implementation
See `Solution/solution.cpp` for the complete C++ implementation, including the `Solution` class and `main()` function.

The implementation uses an unordered set for dictionary lookups and a queue for BFS traversal. The input format is documented in `Problem.md`.
