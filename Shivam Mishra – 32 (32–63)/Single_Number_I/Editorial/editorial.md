# Editorial: Single Number I

## Approach

The problem gives an array in which every element appears exactly twice except for one element, which appears only once. The goal is to find that unique element.

We can solve this problem efficiently using the **Bitwise XOR (`^`) operator**.

The XOR operator follows these important properties:

1. **Self-cancellation:** `x ^ x = 0`
2. **Identity:** `x ^ 0 = x`
3. **Commutative property:** `a ^ b = b ^ a`
4. **Associative property:** `(a ^ b) ^ c = a ^ (b ^ c)`

When we XOR all elements of the array, every number appearing twice cancels itself out and becomes zero. The only element left is the number that appears once.

For example, consider the array:

`[4, 1, 2, 1, 2]`

Applying XOR to every element:

`4 ^ 1 ^ 2 ^ 1 ^ 2`

Using the cancellation property, the duplicate elements cancel each other:

`4 ^ (1 ^ 1) ^ (2 ^ 2)`

`= 4 ^ 0 ^ 0`

`= 4`

Therefore, the answer is `4`.

## Algorithm

1. Initialize a variable `result` to `0`.
2. Traverse the array from beginning to end.
3. XOR each element with `result` and store the result back in the variable.
4. After processing all elements, return `result`.

## C++ Solution

The implementation uses a `Solution` class containing the `singleNumber()` method. The `main()` function reads the array size and its elements, calls the method, and prints the unique number.

```cpp
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int singleNumber(vector<int>& nums) {
        int result = 0;

        for (int num : nums) {
            result ^= num;
        }

        return result;
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

    Solution sol;
    cout << sol.singleNumber(nums) << '\n';

    return 0;
}
```

## Complexity Analysis

- **Time Complexity: O(n)** — Each of the `n` array elements is processed exactly once.
- **Auxiliary Space Complexity: O(1)** — The algorithm uses only one additional variable, `result`, regardless of the array size. The input vector is not counted as auxiliary space.

## Implementation Notes

The reference C++ implementation is located at `Solution/solution.cpp`.

Input follows the format documented in `Problem.md/single_number_i.md`: the first line contains the array size `n`, and the second line contains the `n` integers.

The solution uses XOR rather than sorting or a frequency map, achieving linear time complexity and constant auxiliary space.