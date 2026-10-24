### Problem 233: Fruit Into Baskets
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Hash Map, Arrays

**Problem Statement**

You are visiting a farm with a row of fruit trees. The trees are represented by an integer array `fruits`, where `fruits[i]` is the type of fruit produced by the `i`-th tree.

You have two baskets, and each basket can hold only one type of fruit. There is no limit on how many fruits of that type a basket can hold.

Starting from any tree, you must collect exactly one fruit from every tree while moving only to the right. You must stop when you reach a tree whose fruit type cannot be placed into either basket.

Return the maximum number of fruits you can collect.

In other words, find the length of the longest contiguous subarray containing **at most two distinct values**.

**Input Format**

* The first line contains an integer `n`, the number of trees.
* The second line contains `n` integers representing the fruit type of each tree.

**Output Format**

Print one integer representing the maximum number of fruits that can be collected.

**Constraints**

* `1 ≤ n ≤ 10^5`
* `1 ≤ fruits[i] ≤ 10^5`

**Example 1**

**Input:**
```text
3
1 2 1
```

**Output:**
```text
3
```

**Explanation:**

There are only two fruit types, `1` and `2`, so all three fruits can be collected.

**Example 2**

**Input:**
```text
4
0 1 2 2
```

**Output:**
```text
3
```

**Explanation:**

The longest valid segment is `[1, 2, 2]`, which contains only two distinct fruit types.

**Example 3**

**Input:**
```text
5
1 2 3 2 2
```

**Output:**
```text
4
```

**Explanation:**

The longest valid segment is `[2, 3, 2, 2]`, which contains exactly two distinct fruit types.

**Approach:**

Use a sliding window with two pointers.

* Maintain a window `[left, right]`.
* Store the frequency of every fruit type inside the current window.
* Expand the window by moving `right`.
* If the window contains more than two distinct fruit types, move `left` forward and decrease frequencies until only two distinct types remain.
* Update the maximum window length.

At all times, the maintained window contains at most two distinct fruit types, so its length is a valid number of fruits that can be collected.

**Complexity:**

* Time Complexity: `O(n)`
* Space Complexity: `O(n)` in the general case for the frequency map.
