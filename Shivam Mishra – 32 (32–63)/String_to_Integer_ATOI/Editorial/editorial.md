# Editorial: String to Integer (atoi)

## Difficulty
Medium

## Prerequisites
- Strings
- String Parsing
- Integer Overflow
- Implementation

## Problem Statement

Implement the `atoi` (ASCII to Integer) function that converts a string into a signed 32-bit integer.

Given a string `input`, convert it into an integer according to the following rules:

1. **Skip leading whitespace:** Ignore all leading space characters (`' '`).
2. **Handle the sign:** If the first non-space character is `'-'` or `'+'`, determine the sign of the result. If neither is present, assume the number is positive.
3. **Convert digits:** Read consecutive decimal digits (`'0'` to `'9'`) and construct the integer.
4. **Stop at a non-digit:** Stop parsing as soon as a character that is not a decimal digit is encountered. Ignore the remaining characters.
5. **Handle overflow:** If the parsed number exceeds the signed 32-bit integer range, clamp it to the nearest boundary:
   - Maximum: `INT_MAX = 2147483647`
   - Minimum: `INT_MIN = -2147483648`

If no digits are encountered after processing the whitespace and optional sign, return `0`.

### Examples

| Input | Output | Explanation |
|---|---:|---|
| `"42"` | `42` | A valid positive integer |
| `"   -42"` | `-42` | Leading spaces and a negative sign |
| `"+123"` | `123` | Explicit positive sign |
| `"4193 with words"` | `4193` | Parsing stops at the first space after the digits |
| `"words and 987"` | `0` | The first non-space character is not a digit |
| `"2147483648"` | `2147483647` | Positive overflow |
| `"-2147483649"` | `-2147483648` | Negative overflow |
| `"+-12"` | `0` | No digits follow the first sign |

## Explanation and Approach

We can solve this problem using a single traversal of the string. The important part is to handle the sign and integer overflow correctly.

### Step 1: Skip leading spaces

Start at index `0` and move forward while the current character is a space.

For example:

`"   123abc"` → start parsing at index `3`.

Only leading spaces are skipped. Spaces encountered after the digits have started terminate the conversion.

### Step 2: Determine the sign

Initialize `sign = 1`.

- If the current character is `'-'`, set `sign = -1`.
- If it is `'+'`, keep `sign = 1`.
- Otherwise, continue without changing the sign.

Advance the index after consuming the sign.

Only one sign is permitted. For example, `"+-12"` returns `0` because the second character is not a digit.

### Step 3: Accumulate digits

Maintain a variable `num`, initially `0`.

For each digit, calculate:

`num = num * 10 + digit`

The digit's numeric value is obtained using:

`digit = input[i] - '0'`

For example, parsing `"456"` proceeds as follows:

| Digit | Calculation | `num` |
|---|---|---:|
| `4` | `0 * 10 + 4` | 4 |
| `5` | `4 * 10 + 5` | 45 |
| `6` | `45 * 10 + 6` | 456 |

Stop when the current character is not between `'0'` and `'9'`.

### Step 4: Prevent integer overflow

A signed 32-bit integer has the range:

`-2147483648` to `2147483647`

Notice that the negative boundary has a magnitude one greater than the positive boundary. Therefore, we use a sign-dependent limit:

- For positive numbers: `2147483647`
- For negative numbers: `2147483648`

Before appending a digit, check whether:

`num > (limit - digit) / 10`

If this condition is true, the next digit would exceed the allowed limit. Return `INT_MAX` for a positive number or `INT_MIN` for a negative number immediately.

This check is performed **before** multiplication and addition, so overflow is prevented rather than detected after it happens.

### Step 5: Apply the sign and return

Once parsing finishes, multiply the accumulated number by `sign` and return the result.

We use `long long` for the accumulator so it can safely represent the magnitude `2147483648`, which is necessary for `INT_MIN`.

## Algorithm

1. Initialize the index, sign, and numeric accumulator.
2. Skip leading spaces.
3. Read an optional `+` or `-` sign.
4. Determine the maximum permitted magnitude from the sign.
5. Iterate over consecutive digits:
   - Convert the character into a numeric digit.
   - Check whether appending it would exceed the limit.
   - If overflow would occur, return the appropriate boundary.
   - Otherwise, append the digit to the accumulator.
6. Multiply the accumulated number by the sign and return it.

## C++ Solution

File: `Solution/solution.cpp`

```cpp
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int myAtoi(string input) {
        int n = input.size();
        int i = 0;
        int sign = 1;
        long long num = 0;

        // Step 1: Skip leading spaces
        while (i < n && input[i] == ' ') {
            i++;
        }

        // Step 2: Handle optional sign
        if (i < n && (input[i] == '+' || input[i] == '-')) {
            if (input[i] == '-') {
                sign = -1;
            }
            i++;
        }

        // Step 3: Set the maximum allowed magnitude
        long long limit = (sign == 1)
                              ? INT_MAX
                              : 2147483648LL;

        // Step 4: Parse consecutive digits
        while (i < n && input[i] >= '0' && input[i] <= '9') {
            int digit = input[i] - '0';

            // Step 5: Check overflow before updating num
            if (num > (limit - digit) / 10) {
                return (sign == 1) ? INT_MAX : INT_MIN;
            }

            num = num * 10 + digit;
            i++;
        }

        // Step 6: Apply sign and return
        return static_cast<int>(sign * num);
    }
};

int main() {
    string input;
    getline(cin, input);

    Solution sol;
    cout << sol.myAtoi(input) << '\n';

    return 0;
}
```

## Complexity Analysis

Let `n` be the length of the input string.

- **Time Complexity: O(n)** — Each character is processed at most once. Leading spaces and consecutive digits are scanned in a single pass.
- **Auxiliary Space Complexity: O(1)** — The algorithm uses a constant number of variables, excluding the storage of the input string.

## Edge Cases to Consider

1. Empty string: `""` → `0`
2. Only spaces: `"   "` → `0`
3. Sign without digits: `"+"` or `"-"` → `0`
4. Leading zeros: `"000123"` → `123`
5. Non-digit after digits: `"123abc"` → `123`
6. Non-digit before digits: `"abc123"` → `0`
7. Positive overflow: `"2147483648"` → `2147483647`
8. Negative overflow: `"-2147483649"` → `-2147483648`
9. Maximum valid positive integer: `"2147483647"` → `2147483647`
10. Minimum valid negative integer: `"-2147483648"` → `-2147483648`

## Key Takeaways

- Parsing must follow the specified order: whitespace, sign, then digits.
- Conversion stops at the first non-digit after parsing begins.
- Overflow must be checked before updating the accumulated number.
- The negative integer limit has a magnitude one greater than the positive limit.
- A single-pass solution achieves linear time and constant auxiliary space.

## Input Format

The input consists of one line containing the string to convert. The output is the resulting signed 32-bit integer.

Example:

Input:
```text
  -42abc
```

Output:
```text
-42
```
