# Editorial: Trie Implementation and Advanced Operations

## Topic
Trie (Prefix Tree), String Data Structure

## Problem Statement
Implement a Trie, also known as a Prefix Tree, that supports insertion of words, searching for an exact word, checking whether any inserted word starts with a given prefix, and counting the number of inserted words that begin with a specified prefix.

The Trie stores strings character by character, allowing words with common prefixes to share the same path in the data structure.

The implementation should support the following operations:

- **Insert:** Insert a word into the Trie.
- **Search:** Check whether a complete word exists in the Trie.
- **Starts With:** Check whether at least one inserted word begins with a given prefix.
- **Count Words Equal To:** Return the number of inserted occurrences of a particular word.
- **Count Words Starting With:** Return the number of inserted words that begin with a given prefix.
- **Erase:** Remove one occurrence of a word from the Trie, if it exists.

Duplicate insertions must be handled consistently with the problem's requirements. If duplicates are counted separately, each insertion increases the corresponding word and prefix counts. If duplicates must count only once, inserting an already existing word should not increase those counts.

## Approach

### 1. Trie Structure

A Trie represents each word as a sequence of character edges. Each node contains references to its children and information indicating whether a complete word ends at that node.

For a Trie containing lowercase English letters, each node can maintain an array of 26 child pointers, where each index represents a letter from `'a'` to `'z'`.

For example, inserting the words `app`, `apple`, and `apt` creates shared paths for their common prefix `ap`. The common characters are stored only once, reducing redundant storage.

Each node can maintain the following information:

- **Children:** References to the next character nodes.
- **Word count:** Number of inserted occurrences of the complete word ending at that node.
- **Prefix count:** Number of inserted word occurrences passing through that node.

A terminal flag can be used instead of a word count when duplicate insertions are not counted separately.

### 2. Insertion

To insert a word, begin at the root and process its characters from left to right.

For every character:

1. Calculate its corresponding child index.
2. Create a new node if the required child does not exist.
3. Move to the child node.
4. Update the prefix count when occurrence-based counting is required.

After processing the final character, update the word count or mark the node as terminal.

This ensures that the complete word and all its prefixes are represented in the Trie.

### 3. Exact-Word Search

To determine whether a complete word exists, start at the root and follow the edges corresponding to each character.

If a required child does not exist at any point, the word is absent.

If every character is found, check whether the final node marks the end of a complete word. Reaching a node alone is insufficient because the path may represent only a prefix of a longer word.

For example, if `apple` is inserted but `app` is not, the path for `app` exists, but an exact search for `app` should return false.

### 4. Prefix Search

Prefix search follows the same traversal process as exact-word search but does not require the final node to mark the end of a complete word.

If all characters of the prefix can be traversed successfully, the prefix exists. Otherwise, it does not.

For example, after inserting `apple`, searching for the prefix `app` returns true even if `app` was never inserted as an independent word.

### 5. Count Words Equal To

To count occurrences of an exact word, traverse the Trie using all its characters.

If any required child is missing, return zero. Otherwise, return the word count stored at the final node.

This operation distinguishes complete words from their prefixes and supports duplicate insertions when counts are maintained.

### 6. Count Words Starting With

To count words beginning with a given prefix, traverse the Trie until the complete prefix has been processed.

If the prefix path does not exist, return zero. Otherwise, return the prefix count stored at the final node.

The prefix count represents the number of inserted word occurrences that pass through that node. Therefore, the count can be obtained without traversing every descendant node.

For example, if `app`, `apple`, and `application` are inserted, the prefix `app` has a count of three when each inserted word is counted separately.

### 7. Erase a Word

To erase a word, first traverse the Trie and verify that the complete word exists.

If the word is absent, no changes are required. Otherwise, decrease its word count and the prefix counts of the nodes along its path by one.

If duplicates are not counted separately, erasing an existing word should remove its terminal status instead.

Nodes do not necessarily need to be physically deleted immediately. They can remain in the Trie as long as their counts and terminal information correctly indicate which words are still present.

## Complexity Analysis

Let \(L\) be the length of the word or prefix being processed, and let \(\Sigma\) be the alphabet size. For lowercase English letters, \(\Sigma = 26\).

- **Insertion:** \(O(L)\) time.
- **Exact-word search:** \(O(L)\) time.
- **Prefix search:** \(O(L)\) time.
- **Count words equal to:** \(O(L)\) time.
- **Count words starting with:** \(O(L)\) time.
- **Erase:** \(O(L)\) time, excluding optional node reclamation.
- **Auxiliary space:** \(O(NL)\) in the worst case for \(N\) inserted words of maximum length \(L\), with shared prefixes potentially reducing actual storage.

With a fixed-size child array, each character lookup takes \(O(1)\) time. If a map or another child-storage structure is used, the complexity may vary depending on the implementation.

## Implementation

The complete implementation is available in `Solution/solution.cpp`.

The solution defines a `TrieNode` class to represent individual nodes and a `Trie` class to implement insertion, searching, prefix counting, exact-word counting, and deletion. A driver program reads the operations, invokes the corresponding methods, and prints the results of query operations.

The input format and supported operations are documented in `Problem.md`.

## Conclusion

A Trie is an efficient data structure for storing and querying strings that share common prefixes. Unlike repeatedly scanning a collection of words, Trie operations process only the characters of the given word or prefix. Maintaining terminal information and appropriate word and prefix counts enables efficient exact-word searches, prefix queries, duplicate handling, and deletion.

This makes Tries particularly useful for autocomplete systems, dictionaries, spell-checking, prefix-based searching, and other applications involving frequent string queries.