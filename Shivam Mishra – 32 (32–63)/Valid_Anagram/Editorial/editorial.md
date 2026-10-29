# Editorial: Valid Anagram

## Difficulty
Easy

## Prerequisites
- Strings
- Hashing
- Frequency Counting
- Arrays

## Problem Statement

Given two strings `s` and `t`, determine whether `t` is an anagram of `s`.

An **anagram** is formed by rearranging the characters of another string, using every character exactly the same number of times.

**Input:** Two strings, provided on separate lines.

**Output:** Print `true` if the strings are anagrams; otherwise, print `false`.

### Example 1

**Input**
```text
anagram
nagaram
```

**Output**
```text
true
```

**Explanation:** Both strings contain the same characters with identical frequencies.

### Example 2

**Input**
```text
dog
cat
```

**Output**
```text
false
```

**Explanation:** The strings contain different characters, so they are not anagrams.

### Example 3

**Input**
```text
aabb
abbb
```

**Output**
```text
false
```

**Explanation:** The frequency of character `a` and character `b` differs between the two strings.

## Explanation & Approach

Two strings are anagrams if and only if they have the same length and the frequency of every character is identical.

We can solve this problem using a frequency array of size 26 because the input consists only of lowercase English letters.

### Algorithm

1. Check whether the lengths of `s` and `t` are equal. If not, return `false`.
2. Create an integer frequency array of size 26, initialized to zero.
3. Traverse string `s` and increment the frequency of each character.
4. Traverse string `t` and decrement the frequency of each character.
5. If any frequency becomes negative, return `false`, because `t` contains more occurrences of a character than `s`.
6. If all characters are processed without a negative frequency, return `true`.

### Why Does This Work?

The frequency array records the difference between the number of occurrences of each character in the two strings.

After processing both strings, every frequency must be zero for the strings to be anagrams. During the second traversal, a negative frequency immediately proves that a character occurs too many times in `t`.

## Complexity Analysis

Let `n` and `m` be the lengths of strings `s` and `t`.

- **Time Complexity:** `O(n + m)`, because each string is traversed once.
- **Auxiliary Space Complexity:** `O(1)`, because the frequency array always contains exactly 26 entries.

## C++ Solution

The reference implementation is available in `Solution/solution.cpp`.

The program uses a `Solution` class and a frequency-counting approach.

See `Problem.md/valid_anagram.md` for the problem statement and input format.

## Test Case Format

Each input file contains two lines:

```text
anagram
nagaram
```

The corresponding expected output file contains:

```text
true
```
