# Editorial: Maximum Product Subarray

## Difficulty
Medium

## Prerequisites
- Arrays
- Dynamic Programming
- Prefix and Suffix Products
- Handling Negative Numbers

## Problem Statement

Given an integer array `nums`, find the maximum product of any non-empty contiguous subarray and return that product.

A subarray is a contiguous sequence of elements from the original array.

The input consists of an integer `n`, followed by `n` integers representing the array.

## Example 1

**Input**
```text
6
4 5 3 7 1 2
```

**Output**
```text
840
```

**Explanation**

The entire array produces the maximum product:

`4 × 5 × 3 × 7 × 1 × 2 = 840`

## Example 2

**Input**
```text
3
-5 0 -2
```

**Output**
```text
0
```

**Explanation**

Every contiguous subarray containing zero has a product of zero. The individual negative elements produce negative products, so the maximum product is `0`.

---

## Explanation and Approach

### Approach: Dynamic Programming with Maximum and Minimum Products

The main challenge is handling negative numbers and zeros correctly.

For a normal maximum-sum subarray problem, we generally track the maximum sum ending at each position. However, multiplication behaves differently because:

- Multiplying two positive numbers produces a positive product.
- Multiplying a positive and a negative number produces a negative product.
- Multiplying two negative numbers produces a positive product.
- Multiplying by zero resets the product to zero.

Therefore, tracking only the maximum product ending at each index is insufficient.

For example, consider the array:

`[2, -3, -4]`

After processing `2, -3`, the maximum product ending at the second position is `2`, while the minimum product is `-6`.

When we encounter `-4`, the minimum product `-6` becomes `24`, which is the new maximum product.

Thus, we maintain three variables:

- `maxEnding`: Maximum product of a subarray ending at the current index.
- `minEnding`: Minimum product of a subarray ending at the current index.
- `answer`: Maximum product found anywhere in the array so far.

### Algorithm

1. Initialize `maxEnding`, `minEnding`, and `answer` with the first array element.
2. Traverse the array from the second element to the last.
3. If the current element is negative, swap `maxEnding` and `minEnding`. Multiplication by a negative number reverses their roles.
4. Update `maxEnding` using the larger of:
   - The current element alone.
   - The current element multiplied by the previous maximum product.
5. Update `minEnding` using the smaller of:
   - The current element alone.
   - The current element multiplied by the previous minimum product.
6. Update `answer` with the maximum of its current value and `maxEnding`.
7. Return `answer` after processing the entire array.

## Dry Run

Consider the array:

`nums = [2, -3, -4]`

| Current element | maxEnding | minEnding | answer |
|---:|---:|---:|---:|
| 2 | 2 | 2 | 2 |
| -3 | -3 | -6 | 2 |
| -4 | 24 | -4 | 24 |

At `-3`, the maximum and minimum products swap roles before the updates. At `-4`, the previous minimum product becomes the maximum product because the product of two negative numbers is positive.

The final answer is **24**.

## C++ Solution

**File:** `Solution/solution.cpp`

```cpp
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int maxProduct(vector<int>& nums) {
        int maxEnding = nums[0];
        int minEnding = nums[0];
        int answer = nums[0];

        for (int i = 1; i < nums.size(); i++) {
            if (nums[i] < 0) {
                swap(maxEnding, minEnding);
            }

            maxEnding = max(nums[i],
                            maxEnding * nums[i]);

            minEnding = min(nums[i],
                            minEnding * nums[i]);

            answer = max(answer, maxEnding);
        }

        return answer;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<int> nums(n);

    for (int i = 0; i < n; i++) {
        cin >> nums[i];
    }

    Solution solution;
    cout << solution.maxProduct(nums) << '\n';

    return 0;
}
```

## Complexity Analysis

Let `n` be the number of elements in the array.

- **Time Complexity: `O(n)`** — Each element is processed exactly once, and each iteration performs a constant number of operations.
- **Auxiliary Space Complexity: `O(1)`** — Only a fixed number of variables are maintained, excluding the input array.

## Edge Cases

1. **Single element:** `[-5]` → `-5`
2. **All positive elements:** `[1, 2, 3, 4]` → `24`
3. **Two negative elements:** `[-2, -3]` → `6`
4. **Array containing zero:** `[-5, 0, -2]` → `0`
5. **Multiple negative elements:** `[-2, -3, -4]` → `12`
6. **Mixed positive and negative elements:** `[2, 3, -2, 4]` → `6`

## Key Takeaway

The crucial insight is to maintain both the maximum and minimum products ending at each position. A minimum negative product can become the maximum positive product when multiplied by another negative number.

This dynamic programming technique solves the Maximum Product Subarray problem in linear time and constant auxiliary space.
