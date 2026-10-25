# Editorial: Isomorphic Strings

## Difficulty
Easy

## Prerequisites
- Strings
- Hash Map / Character Mapping

## Problem Statement
Determine if characters in string `s` can be mapped one-to-one to characters in string `t` preserving order.

## Approach & Algorithm
1. Check if `s.length() != t.length()`. If so, return `false`.
2. Maintain two lookup tables: `m1` mapping characters from `s` to `t`, and `m2` mapping characters from `t` to `s` (or store the last seen position of each character).
3. If using last seen positions:
   - For index `i`, compare `m1[s[i]]` and `m2[t[i]]`.
   - If they do not match, a conflict exists; return `false`.
   - Update `m1[s[i]] = i` and `m2[t[i]] = i`.
4. If all characters match valid bi-directional mappings, return `true`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ where $N$ is the length of the strings.
- **Space Complexity:** $O(1)$ since character set size is bounded (256 ASCII).
