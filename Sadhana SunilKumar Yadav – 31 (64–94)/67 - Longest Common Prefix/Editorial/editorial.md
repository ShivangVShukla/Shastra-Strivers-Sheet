# Editorial: Longest Common Prefix

## Difficulty
Easy

## Prerequisites
- Strings
- Sorting / Character Comparison

## Problem Statement
Given an array of strings, determine the longest string prefix common to all strings in the array.

## Approach & Algorithm
1. If the input array is empty, return `""`.
2. Sort the array of strings lexicographically.
3. After sorting, the most disparate strings in the array are the first string `first` and the last string `last`.
4. Any common prefix shared by all strings must be shared by `first` and `last`.
5. Compare `first` and `last` character by character from the beginning:
   - While characters match and pointers are within bounds, increment the count.
6. Return the substring of `first` up to the matching length.

## Complexity Analysis
- **Time Complexity:** $O(N \log N \cdot M)$ where $N$ is the number of strings and $M$ is the maximum string length (dominated by sorting).
- **Space Complexity:** $O(M)$ to store the result prefix.
