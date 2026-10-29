
# Valid Anagram

Given two strings **s** and **t**, return **true** if t is an anagram of s, and **false** otherwise.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

### Example 1:

**Input :** s = "anagram" , t = "nagaram"

**Output :** true

**Explanation :**

We can rearrange the characters of string s to get string t as frequency of all characters from both strings is same.

### Example 2:

**Input :** s = "dog" , t = "cat"

**Output :** false

**Explanation :**

We cannot rearrange the characters of string s to get string t as frequency of all characters from both strings is not same.

### Constraints

- 1 <= s.length, t.length <= 5 * 10^4
- s and t consist of only lowercase English letters.

### Approach

Use a frequency array of size 26 to count the occurrences of each letter in s. Then decrement the corresponding frequency for every character in t.

If the lengths differ or any frequency becomes negative, the strings are not anagrams. Otherwise, they are anagrams.

### Complexity Analysis

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(1)
