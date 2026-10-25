# Editorial: Floor and Ceil in a Binary Search Tree (BST)

## 1. Problem Overview

Given a Binary Search Tree (BST) and an integer `key`:
- **Floor:** The maximum value in the BST that is $\le \text{key}$ (or `-1` if no such element exists).
- **Ceil:** The minimum value in the BST that is $\ge \text{key}$ (or `-1` if no such element exists).

We must return `[floor, ceil]` efficiently.

---

## 2. Key Insights and Observations

In a BST:
- For any node `curr`, all nodes in its left subtree are $< \text{curr.val}$, and all nodes in its right subtree are $> \text{curr.val}$.

This enables a direct binary search:
1. **If `curr.val == key`:**
   `key` itself exists in the tree! Therefore, the greatest value $\le key$ is `key`, and the smallest value $\ge key$ is `key`. Both `floor` and `ceil` are `key`, and we can terminate immediately.
2. **If `curr.val < key`:**
   - `curr.val` is $\le key$, so it is our best candidate for `floor` found so far.
   - All values in `curr`'s left subtree are even smaller than `curr.val`, so none of them can provide a better floor or qualify as ceil.
   - Therefore, update `floor = curr.val` and move to `curr.right` to search for a potentially larger value that is still $\le key$.
3. **If `curr.val > key`:**
   - `curr.val` is $\ge key$, so it is our best candidate for `ceil` found so far.
   - All values in `curr`'s right subtree are even larger than `curr.val`, so none of them can qualify as floor or provide a tighter ceil.
   - Therefore, update `ceil = curr.val` and move to `curr.left` to search for a potentially smaller value that is still $\ge key$.

Because both bounds are updated along the **exact same root-to-leaf path**, we can compute both Floor and Ceil simultaneously in a **single pass** of $\mathcal{O}(H)$ time without any recursion or stack!

---

## 3. Algorithm

```java
public List<Integer> floorCeilOfBST(TreeNode root, int key) {
    int floor = -1;
    int ceil = -1;

    TreeNode curr = root;
    while (curr != null) {
        if (curr.val == key) {
            floor = curr.val;
            ceil = curr.val;
            break;
        }

        if (curr.val < key) {
            floor = curr.val;
            curr = curr.right;
        } else {
            ceil = curr.val;
            curr = curr.left;
        }
    }

    return Arrays.asList(floor, ceil);
}
```

---

## 4. Step-by-Step Dry Run

Given BST:
```text
          8
        /   \
       4     12
      / \   /  \
     2   6 10  14
```

### Dry Run 1: `key = 11`
- Initialize `floor = -1`, `ceil = -1`.
- `curr = 8`: $8 < 11 \implies$ `floor = 8`, move `curr = 12`.
- `curr = 12`: $12 > 11 \implies$ `ceil = 12`, move `curr = 10`.
- `curr = 10`: $10 < 11 \implies$ `floor = 10`, move `curr = curr.right` (null).
- Loop terminates.
- **Result:** `[10, 12]`.

### Dry Run 2: `key = 15`
- `curr = 8`: $8 < 15 \implies$ `floor = 8`, move `curr = 12`.
- `curr = 12`: $12 < 15 \implies$ `floor = 12`, move `curr = 14`.
- `curr = 14`: $14 < 15 \implies$ `floor = 14`, move `curr = null`.
- Loop terminates. `ceil` was never assigned.
- **Result:** `[14, -1]`.

### Dry Run 3: `key = 1`
- `curr = 8`: $8 > 1 \implies$ `ceil = 8`, move `curr = 4`.
- `curr = 4`: $4 > 1 \implies$ `ceil = 4`, move `curr = 2`.
- `curr = 2`: $2 > 1 \implies$ `ceil = 2`, move `curr = null`.
- Loop terminates. `floor` was never assigned.
- **Result:** `[-1, 2]`.

---

## 5. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(H)$
  Traverses a single downward path from the root to a leaf.
  - Balanced BST: $\mathcal{O}(\log N)$
  - Skewed BST: $\mathcal{O}(N)$
- **Auxiliary Space Complexity:** $\mathcal{O}(1)$
  Operates using only pointer variable `curr` and integer variables `floor` and `ceil`. Zero recursion stack and zero auxiliary data structures. 100% stack-safe for deep/skewed trees up to $N = 5000$.

---

## 6. Edge Cases Handled

1. **Exact Match (`key` in tree):** `floor == ceil == key`.
2. **`key` Smaller than Minimum Node:** `floor = -1`, `ceil = min_node.val`.
3. **`key` Larger than Maximum Node:** `floor = max_node.val`, `ceil = -1`.
4. **Single Node BST ($N = 1$):** Correctly assigns floor/ceil depending on whether `key` is smaller, equal, or larger.
5. **Completely Skewed BST:** Handled in $\mathcal{O}(N)$ with zero risk of stack overflow.
