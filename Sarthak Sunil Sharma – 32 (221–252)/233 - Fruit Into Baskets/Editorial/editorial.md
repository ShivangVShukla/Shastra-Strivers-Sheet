# Fruit Into Baskets — Editorial

## Difficulty
Medium

## Prerequisites
- Arrays
- Hash Map
- Sliding Window
- Two Pointers
- Frequency counting

## Problem Statement
We are given a row of trees represented by an integer array.

Each tree produces one fruit type.

We have exactly two baskets, and each basket can contain only one distinct fruit type. Therefore, during a contiguous collection process, we can have at most two distinct fruit types in the selected segment.

The goal is to find the maximum number of fruits that can be collected.

This is equivalent to finding:

> The longest contiguous subarray containing at most two distinct values.

## Key Observation

Consider a window:

```text
[left ........ right]
```

If this window contains:

```text
1 distinct type
```

it is valid.

If it contains:

```text
2 distinct types
```

it is also valid.

If it contains:

```text
3 or more distinct types
```

it is invalid because we only have two baskets.

Therefore, we can use a sliding window.

## Data Structure

Maintain a hash map:

```text
fruit type -> frequency inside current window
```

The number of keys in the map represents the number of distinct fruit types currently inside the window.

## Sliding Window Algorithm

For every `right`:

1. Add `fruits[right]` to the frequency map.
2. If the number of distinct types becomes greater than `2`, move `left`.
3. Decrease the frequency of `fruits[left]`.
4. If a frequency becomes zero, remove that fruit type from the map.
5. Continue until the window contains at most two distinct types.
6. Update the maximum length.

## Example Trace

Consider:

```text
fruits = [1, 2, 3, 2, 2]
```

Start:

```text
left = 0
```

Add `1`:

```text
[1]
```

One type — valid.

Add `2`:

```text
[1, 2]
```

Two types — valid.

Add `3`:

```text
[1, 2, 3]
```

Three types — invalid.

Move `left`:

Remove `1`.

The window becomes:

```text
[2, 3]
```

Now it is valid again.

Add the next `2`:

```text
[2, 3, 2]
```

Still two types.

Add the final `2`:

```text
[2, 3, 2, 2]
```

Still two types.

The maximum length is therefore:

```text
4
```

## Why We Need Frequencies

Suppose the current window is:

```text
[1, 2, 2, 3]
```

When we remove the first `1`, its frequency becomes zero, so type `1` must be removed from the map.

For type `2`:

```text
frequency = 2
```

Removing one `2` does not remove type `2` from the window.

Therefore, frequency tracking is necessary.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    public static int totalFruit(int[] fruits) {
        Map<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int answer = 0;

        for (int right = 0; right < fruits.length; right++) {
            int fruit = fruits[right];

            freq.put(fruit, freq.getOrDefault(fruit, 0) + 1);

            while (freq.size() > 2) {
                int leftFruit = fruits[left];

                freq.put(leftFruit, freq.get(leftFruit) - 1);

                if (freq.get(leftFruit) == 0) {
                    freq.remove(leftFruit);
                }

                left++;
            }

            answer = Math.max(answer, right - left + 1);
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());

        StringTokenizer st =
            new StringTokenizer(br.readLine());

        int[] fruits = new int[n];

        for (int i = 0; i < n; i++) {
            fruits[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(totalFruit(fruits));
    }
}
```

## Complexity Analysis

Let `n` be the number of trees.

### Time Complexity

The right pointer moves from left to right once.

The left pointer also only moves forward.

Each element is therefore added to and removed from the window at most once.

So the total time is:

```text
O(n)
```

### Space Complexity

The frequency map stores the distinct fruit types currently present in the window.

In this problem it never needs more than three entries during the adjustment step, and after adjustment the valid window has at most two.

Therefore, the effective auxiliary space is:

```text
O(1)
```

with respect to the number of fruit types maintained by the sliding window.

## Edge Cases

### 1. One Fruit Type

```text
[5, 5, 5, 5]
```

Only one basket is needed.

Answer:

```text
4
```

### 2. Exactly Two Fruit Types

```text
[1, 2, 1, 2, 1]
```

The entire array is valid.

Answer:

```text
5
```

### 3. Every Fruit Type Is Different

```text
[1, 2, 3, 4, 5]
```

At most two adjacent fruits can be collected.

Answer:

```text
2
```

### 4. Long Repeated Suffix

```text
[1, 2, 3, 3, 3, 3]
```

After removing type `1`, the suffix containing `2` and `3` can be maintained.

Answer:

```text
5
```

### 5. Single Tree

For:

```text
[7]
```

the answer is:

```text
1
```

## Why the Approach Works

The sliding window always represents a contiguous group of trees.

Whenever the window contains more than two distinct fruit types, it cannot be collected using two baskets, so we move the left boundary until the window becomes valid again.

Because the left boundary is moved only when necessary and both pointers move only forward, every valid window is considered efficiently.

The largest valid window is exactly the maximum number of fruits that can be collected.
