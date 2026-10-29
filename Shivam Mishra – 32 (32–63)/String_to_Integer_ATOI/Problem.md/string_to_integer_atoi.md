# String to Integer (atoi)

## Problem Statement

Implement the function `myAtoi(s)` which converts the given string `s` into a 32-bit signed integer, similar to the C/C++ `atoi` function.

### Steps to Implement

1. **Ignore leading whitespace:** Skip any leading space characters (`' '`).

2. **Determine the sign:** If the next character is `'-'`, the number is negative. If it is `'+'`, the number is positive. If neither is found, assume the number is positive.

3. **Read digits:** Read consecutive digits and convert them into a number. Stop reading when a non-digit character is encountered or the end of the string is reached. Leading zeros should not affect the result.

4. **Clamp the result:** The result must be within the 32-bit signed integer range:
   - Minimum: `-2147483648`
   - Maximum: `2147483647`

   If the number exceeds this range, return the appropriate limit.

5. **Return the result:** Return the computed integer. If no digits are found, return `0`.

## Examples

### Example 1

**Input:**
```text
s = " -12345"