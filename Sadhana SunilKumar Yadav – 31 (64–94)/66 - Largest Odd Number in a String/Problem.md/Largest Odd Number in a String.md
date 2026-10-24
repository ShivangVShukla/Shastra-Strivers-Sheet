### Problem 66: Largest Odd Number in a String
**Difficulty:** Easy | **Tags:** Strings, Math

**Problem Statement**
Given a string `s`, representing a large integer, return the largest-valued odd integer (as a string) that is a substring of the given string `s`.

The number returned should not have leading zeros. But the given input string may have leading zeros. (If no odd number is found, then return empty string.)

**Input Format**
* A single line containing the numeric string `s`.

**Output Format**
* Print the largest odd number string. If none, print an empty line.

**Constraints**
* $1 \le s.length \le 10^5$
* `s` consists only of digits `'0'` through `'9'`.

**Examples**
**Input:** `5347`
**Output:** `5347`

**Input:** `0214638`
**Output:** `21463`

**Input:** `0012345`
**Output:** `12345`
