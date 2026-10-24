# Editorial: Sort Characters by Frequency

## Difficulty
Medium

## Prerequisites
- Strings
- Hash Map / Frequency Counting
- Custom Comparator Sorting

## Problem Statement
Given a string `s`, extract unique characters and sort them primarily in descending order of frequency, and secondarily in ascending alphabetical order for ties.

## Approach & Algorithm
1. Count the frequency of each character in `s` using a frequency map or array.
2. Extract all unique `{character, count}` pairs into a vector.
3. Sort the vector using a custom comparator:
   - If frequencies differ, the character with higher frequency comes first (`a.freq > b.freq`).
   - If frequencies are equal, break ties alphabetically (`a.char < b.char`).
4. Collect the characters into a result array and return.

## Complexity Analysis
- **Time Complexity:** $O(N + K \log K)$ where $N$ is length of string and $K \le 26$ is the number of unique characters. Since $K$ is bounded, sorting is $O(1)$, so total time is $O(N)$.
- **Space Complexity:** $O(K) = O(1)$ space for frequency map and unique characters.
