# Trie Implementation and Advanced Operations

## Problem Statement

Implement a **Trie** data structure from scratch that supports insertion, counting exact words, counting words with a given prefix, and deleting one occurrence of a word.

A Trie, also known as a Prefix Tree, stores strings character by character. Words that share common prefixes can share the same path in the data structure.

Implement the following functions:

- **`Trie()`**: Initialize an empty Trie data structure.

- **`insert(string word)`**: Insert the string `word` into the Trie. If the same word is inserted multiple times, each insertion must be counted separately.

- **`countWordsEqualTo(string word)`**: Return the number of times the complete string `word` has been inserted into the Trie.

- **`countWordsStartingWith(string prefix)`**: Return the number of inserted word occurrences that start with the given `prefix`.

- **`erase(string word)`**: Delete exactly one occurrence of `word` from the Trie, if it exists. If the word has been inserted multiple times, only one occurrence should be removed.

## Example 1

**Input:**

```text
["Trie", "insert", "countWordsEqualTo", "insert",
 "countWordsStartingWith", "erase",
 "countWordsStartingWith"]

[[], ["apple"], ["apple"], ["app"],
 ["app"], ["apple"], ["app"]]
```

**Output:**

```text
[null, null, 1, null, 2, null, 1]
```

**Explanation:**

```cpp
Trie trie = Trie();

trie.insert("apple");

trie.countWordsEqualTo("apple");       // Returns 1

trie.insert("app");

trie.countWordsStartingWith("app");    // Returns 2

trie.erase("apple");

trie.countWordsStartingWith("app");    // Returns 1
```

Initially, the Trie is empty. After inserting `"apple"`, its exact-word count is `1`. Inserting `"app"` adds another word beginning with the prefix `"app"`, making the prefix count `2`. Erasing `"apple"` removes one occurrence, so only `"app"` remains and the prefix count becomes `1`.

## Example 2

**Input:**

```text
["Trie", "insert", "countWordsEqualTo", "insert",
 "erase", "countWordsStartingWith"]

[[], ["mango"], ["apple"], ["app"],
 ["app"], ["mango"]]
```

**Output:**

```text
[null, null, 0, null, null, 1]
```

**Explanation:**

```cpp
Trie trie = Trie();

trie.insert("mango");

trie.countWordsEqualTo("apple");       // Returns 0

trie.insert("app");

trie.erase("app");

trie.countWordsStartingWith("mango");  // Returns 1
```

The word `"apple"` has not been inserted, so its exact-word count is `0`. After inserting and erasing `"app"`, the word `"mango"` remains in the Trie. Therefore, the prefix count for `"mango"` is `1`.

## Input Format

For the custom input/output implementation, use the following format:

- The first line contains an integer `Q`, representing the number of operations.
- Each of the next `Q` lines contains an operation name and a lowercase string.

Supported operations:

- `insert word`
- `countWordsEqualTo word`
- `countWordsStartingWith prefix`
- `erase word`

## Output Format

For every `countWordsEqualTo` and `countWordsStartingWith` operation, print the returned integer on a separate line.

Insertion and erasure operations do not produce output.

## Constraints

- \(1 \leq Q \leq 10^5\)
- Each word or prefix contains lowercase English letters (`a`–`z`).
- Each word or prefix has a length between \(1\) and \(10^3\).
- Duplicate insertions are allowed and must be counted separately.
- Each `erase` operation removes at most one occurrence of the specified word.
- Erasing a word that is not present must not change the Trie.

## Expected Complexity

Let \(L\) be the length of the word or prefix being processed.

- **Insertion:** \(O(L)\)
- **Count words equal to:** \(O(L)\)
- **Count words starting with:** \(O(L)\)
- **Erase:** \(O(L)\)

The space complexity is \(O(S)\), where \(S\) is the total number of characters represented by the Trie, with common prefixes shared between words.

## Notes

- A prefix does not need to be a complete word in the Trie.
- Exact-word counting and prefix counting are different operations.
- Prefix counts must include duplicate insertions.
- Erasing one occurrence must update the word count and all relevant prefix counts correctly.
- The implementation should use classes to represent the Trie and its nodes.