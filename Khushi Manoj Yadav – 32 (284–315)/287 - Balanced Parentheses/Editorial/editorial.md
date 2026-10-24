# 287. Balanced Parentheses - Editorial

## Approach
Use a LIFO stack:
1. Traverse the string character by character.
2. If an opening bracket (`(`, `{`, `[`) is encountered, push it onto the stack.
3. If a closing bracket (`)`, `}`, `]`) is encountered:
   - Check if stack is empty; if so, return `false`.
   - Pop the top bracket and verify it matches the current closing bracket. If not, return `false`.
4. After processing the string, return `true` if the stack is completely empty, else `false`.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
