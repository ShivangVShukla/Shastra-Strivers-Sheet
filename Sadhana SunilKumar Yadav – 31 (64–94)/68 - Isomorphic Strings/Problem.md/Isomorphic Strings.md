### Problem 68: Isomorphic Strings
**Difficulty:** Easy | **Tags:** Strings, Hash Map

**Problem Statement**
Given two strings `s` and `t`, determine if they are isomorphic. Two strings `s` and `t` are isomorphic if the characters in `s` can be replaced to get `t`.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.

**Input Format**
* The first line contains string `s`.
* The second line contains string `t`.

**Output Format**
* Print `true` if the strings are isomorphic, otherwise `false`.

**Constraints**
* $1 \le s.length \le 10^5$
* $t.length == s.length$
* `s` and `t` consist of any valid ASCII characters.

**Examples**
**Input:**
```
egg
add
```
**Output:** `true`

**Input:**
```
apple
bbnbm
```
**Output:** `false`
