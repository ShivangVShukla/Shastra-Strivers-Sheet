### Problem 69: Rotate String
**Difficulty:** Easy | **Tags:** Strings, Substring Matching

**Problem Statement**
Given two strings `s` and `goal`, return `true` if and only if `s` can become `goal` after some number of shifts on `s`.

A shift on `s` consists of moving the leftmost character of `s` to the rightmost position.

**Input Format**
* The first line contains string `s`.
* The second line contains string `goal`.

**Output Format**
* Print `true` if `s` can become `goal`, otherwise `false`.

**Constraints**
* $1 \le s.length, goal.length \le 1000$
* `s` and `goal` consist of lowercase English letters.

**Examples**
**Input:**
```
abcde
cdeab
```
**Output:** `true`

**Input:**
```
abcde
abced
```
**Output:** `false`
