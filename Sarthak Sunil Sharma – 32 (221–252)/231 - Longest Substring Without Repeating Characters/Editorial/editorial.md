# Longest Substring Without Repeating Characters — Editorial

## Difficulty
Medium

## Prerequisites
- Strings
- Hashing
- Sliding Window
- Two Pointers
- Last-seen index technique

## Problem Statement
Given a string `s`, find the length of the longest substring that contains no repeated characters.

The substring must be contiguous.

For example, in:

```text
abcabcbb
```

the answer is `3` because `abc`, `bca`, and `cab` are valid substrings of maximum length.

## Key Observation

We need a window in which every character is unique.

Suppose our current window is:

```text
left -------- right
```

If `s[right]` is already present inside this window, the current window is invalid.

Instead of moving `left` one character at a time, we can remember the last position of every character and jump `left` directly to the correct position.

This gives a linear-time solution.

## Data Structure

Maintain:

```text
lastSeen[character] = most recent index of that character
```

For example, while processing:

```text
abcda
```

when we reach the second `a`, we know that the previous `a` occurred at index `0`.

Therefore the left boundary can jump to:

```text
0 + 1 = 1
```

and the new valid window becomes:

```text
bcda
```

## Sliding Window Approach

Use two concepts:

```text
left  = start of current valid window
right = current character being processed
```

For every `right`:

1. Check whether the current character has been seen before.
2. If its previous position is inside the current window, move `left`.
3. Store the current index as its latest position.
4. Calculate the current window length.
5. Update the maximum answer.

The crucial update is:

```text
left = max(left, lastSeen[ch] + 1)
```

## Why `max` Is Important

Consider:

```text
abba
```

Process the characters:

```text
a b b a
0 1 2 3
```

When processing the second `b` at index `2`:

```text
lastSeen[b] = 1
```

so:

```text
left = 2
```

Now process `a` at index `3`.

The previous `a` was at index `0`, but index `0` is already outside the current window because:

```text
left = 2
```

Therefore we must **not** move `left` backwards to `1`.

Using:

```text
left = max(left, lastSeen[a] + 1)
```

keeps:

```text
left = max(2, 1)
     = 2
```

This is a very important detail.

## Example Trace

For:

```text
pwwkew
```

### Character `p`

```text
window = p
length = 1
```

### Character `w`

```text
window = pw
length = 2
```

### Second `w`

The previous `w` was at index `1`.

Move:

```text
left = 2
```

Now the window is:

```text
w
```

### Character `k`

```text
window = wk
length = 2
```

### Character `e`

```text
window = wke
length = 3
```

### Final `w`

The previous `w` is inside the current window, so move `left`.

The valid window becomes:

```text
kew
```

Its length is:

```text
3
```

Therefore the answer is:

```text
3
```

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();

        int left = 0;
        int answer = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right);

            if (lastSeen.containsKey(ch)) {
                left = Math.max(left, lastSeen.get(ch) + 1);
            }

            lastSeen.put(ch, right);

            answer = Math.max(answer, right - left + 1);
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();

        if (s == null) {
            s = "";
        }

        System.out.println(lengthOfLongestSubstring(s));
    }
}
```

## Complexity Analysis

Let `n` be the length of the string.

### Time Complexity

Every character is processed once.

The left pointer only moves forward.

Therefore:

```text
O(n)
```

### Space Complexity

The hash map stores the most recent position of each distinct character.

Therefore:

```text
O(min(n, character-set-size))
```

and in the general case:

```text
O(n)
```

## Edge Cases

### 1. Empty String

```text
Input:
<empty>

Output:
0
```

There is no character, so the longest valid substring has length `0`.

### 2. All Characters Same

```text
aaaaa
```

Answer:

```text
1
```

### 3. All Characters Unique

```text
abcdef
```

Answer:

```text
6
```

### 4. Duplicate Outside Current Window

The previous occurrence of a character may already be outside the current window.

Therefore, `left` must never move backwards.

### 5. Spaces and Symbols

The input is not restricted to lowercase English letters. Spaces, digits, uppercase letters, and symbols must be handled as normal characters.

## Why the Approach Works

At every step, the window from `left` to `right` is maintained so that all characters inside it are unique.

When a duplicate appears, the previous position of that character tells us exactly how far the left boundary must move.

Because `left` only moves forward and every character is processed once, the algorithm runs in linear time.

Thus, the maximum window length found during the scan is exactly the length of the longest substring without repeating characters.
