# Editorial: Sliding Window Maximum


## Difficulty

Hard


## Prerequisites
- Sliding Window
- Deque
- Monotonic Queue
- Arrays


## Problem Statement

You are given an integer array `nums` and an integer `k`. A window of size `k` starts at the left side of the array and moves one position to the right at a time.

For every position of the window, find the maximum element inside that window and return all maximum values in order.


## Explanation & Approach

### Step 1: Understand Why a Simple Scan Is Slow

For every window, we could scan all `k` elements and find its maximum.

There are `N - K + 1` windows, so this approach can take:

```text
O(N × K)
```

In the worst case this is too slow.

We need a data structure that lets us:

1. Remove elements that leave the window.
2. Ignore elements that can never become the maximum.
3. Read the current maximum quickly.


### Step 2: Use a Monotonic Deque

Maintain a deque containing **indices**, not values.

The indices in the deque are arranged so that their corresponding values are in decreasing order:

```text
nums[dq[0]] >= nums[dq[1]] >= nums[dq[2]] ...
```

Therefore, the index at the front always represents the maximum value of the current window.

We store indices because we must know when an element leaves the sliding window.


### Step 3: Remove Expired Indices

Before processing the current index `i`, an index is outside the current window if:

```text
index <= i - k
```

Such indices are removed from the front of the deque.

This keeps only elements belonging to the current window.


### Step 4: Remove Smaller Elements from the Back

Suppose the current value is `nums[i]`.

While the value at the back of the deque is smaller than the current value, remove that index.

Why?

If:

```text
nums[old] <= nums[i]
```

and `old` is older than `i`, then `old` can never be the maximum of any future window containing `i`.

The current element is at least as large and will remain in the window longer.

Therefore, those smaller elements are useless.


### Step 5: The Front Is the Window Maximum

After removing expired and useless indices, add the current index to the back.

Once the first complete window has been formed (`i >= k - 1`):

```text
nums[dq.peekFirst()]
```

is the maximum for the current window.

Store it in the answer array.


## Example

### Input:

```text
8 3
1 3 -1 -3 5 3 6 7
```

### Trace:

For the first three elements:

```text
[1, 3, -1]
```

The deque keeps indices corresponding to:

```text
3, -1
```

so the maximum is:

```text
3
```

After sliding:

```text
[3, -1, -3]
```

The maximum is still:

```text
3
```

When `5` arrives, smaller values at the back are removed:

```text
[-1, -3, 5] -> 5
```

Continuing this process gives:

```text
3 3 5 5 6 7
```

Therefore:

```text
Answer = 3 3 5 5 6 7
```


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        int out = 0;

        for (int i = 0; i < n; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) {
                dq.pollLast();
            }

            dq.offerLast(i);

            if (i >= k - 1) {
                ans[out++] = nums[dq.peekFirst()];
            }
        }

        return ans;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int k = fs.nextInt();

        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = fs.nextInt();
        }

        int[] ans = maxSlidingWindow(nums, k);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ans.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(ans[i]);
        }
        System.out.println(sb);
    }

    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        FastScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```


## Time Complexity

Each index is inserted into the deque exactly once and removed from the deque at most once.

Therefore:

### Total Time Complexity:
`O(N)`.

### Space Complexity:
`O(K)` in the worst case because the deque can contain up to `K` indices.


## Edge Cases

- `K = 1` → every element is its own window.
- `K = N` → there is exactly one output, the maximum of the whole array.
- All values equal → every output is the same value.
- Strictly increasing array → the deque continuously removes smaller elements.
- Strictly decreasing array → the deque keeps several indices until they expire.
- Negative values → the algorithm works without any special handling.
- Duplicate maximum values → indices are maintained so that the newest equal maximum remains useful.
- Minimum and maximum allowed values should also be handled correctly.


## Why the Approach Works

The deque never removes an element that could still become the maximum of a future window.

An element is removed only when:

1. It has already left the current window, or
2. A newer element with an equal or larger value makes it permanently unnecessary.

Thus, the front of the deque is always the largest valid element in the current window.

Because every index enters and leaves the deque at most once, the complete array is processed in linear time.
