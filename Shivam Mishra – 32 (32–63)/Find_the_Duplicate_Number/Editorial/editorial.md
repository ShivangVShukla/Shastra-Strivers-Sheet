# Editorial: Find the Duplicate Number

## Approach

We can solve this problem using **Floyd’s Tortoise-and-Hare Cycle Detection Algorithm**. The key idea is to treat each array value as the next index to visit, forming a linked-list-like structure.

Since the array contains `n + 1` integers, and every integer lies in the range `[1, n]`, at least one number must repeat. The repeated number creates a cycle in this structure, and the entrance to that cycle is the duplicate number.

### Algorithm

1. Initialize two pointers, `slow` and `fast`, at `nums[0]`.
2. Move `slow` one step at a time using `slow = nums[slow]`.
3. Move `fast` two steps at a time using `fast = nums[nums[fast]]`.
4. Continue until both pointers meet inside the cycle.
5. Reset `slow` to `nums[0]`, keeping `fast` at the meeting point.
6. Move both pointers one step at a time until they meet again. This meeting point is the duplicate number.
7. Return the duplicate without modifying the input array.

### Why Does It Work?

Each array value points to another index. Because there are more array elements than possible values, the traversal must eventually revisit a value and form a cycle.

Floyd’s algorithm guarantees that the two pointers meet inside this cycle. After resetting one pointer to the starting position and moving both at the same speed, they meet at the cycle entrance, which corresponds to the duplicate number.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each pointer traverses the structure linearly.
- **Space Complexity:** `O(1)` — Only two pointers are used, so no additional data structure is required.

## Implementation Notes

The reference C++ implementation is available in `Solution/solution.cpp`.

The program reads the array length followed by the array elements, then prints the duplicate number.

The input format is documented in `Problem.md/find_the_duplicate_number.md`.

The implementation uses Floyd’s cycle detection algorithm, does not modify the array, and satisfies the constant-extra-space requirement.

## Solution: `Solution/solution.cpp`

```cpp
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int findDuplicate(vector<int>& nums) {
        int slow = nums[0];
        int fast = nums[0];

        // Phase 1: Find the intersection point.
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Phase 2: Find the entrance of the cycle.
        slow = nums[0];

        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int m;
    cin >> m;

    vector<int> nums(m);
    for (int i = 0; i < m; i++) {
        cin >> nums[i];
    }

    Solution solution;
    cout << solution.findDuplicate(nums) << '\n';

    return 0;
}
```

## Example

**Input**
```text
5
1 3 4 2 2
```

**Output**
```text
2
```

The duplicate number is `2`. The algorithm identifies it as the cycle entrance without sorting or changing the array.
