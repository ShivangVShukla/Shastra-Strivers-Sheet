# Editorial: Minimum and Maximum in BST

## 1. Problem Overview

We are given the root of a valid Binary Search Tree (BST) with $N$ unique integer values.
Our goal is to find the minimum and maximum values present in the BST and return them as `[min, max]`.

---

## 2. Key Insights and Observations

A Binary Search Tree (BST) maintains the following property for every node $u$:
- All keys in $u$'s left subtree are strictly smaller than $u.\text{val}$.
- All keys in $u$'s right subtree are strictly greater than $u.\text{val}$.

From this fundamental invariant:
1. **Minimum Element:**
   Starting from `root`, whenever a node has a left child, its left child (and descendants) will always be strictly smaller than the current node. Therefore, the minimum value in any non-empty BST is located at the **leftmost node** (the node reached by repeatedly taking `left` pointers until `curr.left == null`).

2. **Maximum Element:**
   Similarly, whenever a node has a right child, its right child (and descendants) will always be strictly greater than the current node. Therefore, the maximum value in any non-empty BST is located at the **rightmost node** (the node reached by repeatedly taking `right` pointers until `curr.right == null`).

---

## 3. Algorithm

```java
public int[] findMinMax(TreeNode root) {
    if (root == null) return new int[0];

    // Find minimum: walk all the way to the left
    TreeNode curr = root;
    while (curr.left != null) {
        curr = curr.left;
    }
    int minVal = curr.val;

    // Find maximum: walk all the way to the right
    curr = root;
    while (curr.right != null) {
        curr = curr.right;
    }
    int maxVal = curr.val;

    return new int[]{minVal, maxVal};
}
```

---

## 4. Step-by-Step Dry Run (Example 1)

Given BST:
```text
          8
        /   \
       3     12
      / \   /  \
     1   6 10  14
```

1. **Find Min:**
   - Start at root: `curr = 8`.
   - `curr.left` is `3` $\ne$ null $\implies$ `curr = 3`.
   - `curr.left` is `1` $\ne$ null $\implies$ `curr = 1`.
   - `curr.left` is null. Stop.
   - `minVal = 1`.

2. **Find Max:**
   - Start at root: `curr = 8`.
   - `curr.right` is `12` $\ne$ null $\implies$ `curr = 12`.
   - `curr.right` is `14` $\ne$ null $\implies$ `curr = 14`.
   - `curr.right` is null. Stop.
   - `maxVal = 14`.

3. **Result:** `[1, 14]`.

---

## 5. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(H)$
  Where $H$ is the height of the BST.
  - Balanced BST: $H = \mathcal{O}(\log N)$, requiring around $\approx 17$ pointer hops for $N = 10^5$.
  - Skewed BST: $H = \mathcal{O}(N)$ in the worst case (e.g. completely left-skewed or right-skewed).
  - Overall time complexity is strictly $\mathcal{O}(H) \le \mathcal{O}(N)$, completing in milliseconds.

- **Auxiliary Space Complexity:** $\mathcal{O}(1)$
  Operates purely by iterative pointer advancement using `curr`. Zero recursion stack frames, zero dynamic heap allocations. Completely immune to stack overflow even for $N = 10^5$ skewed trees.

---

## 6. Comparison with General Binary Tree

| Property | General Binary Tree | Binary Search Tree (BST) |
| :--- | :--- | :--- |
| **Search Min/Max** | Must visit all $N$ nodes ($\mathcal{O}(N)$) | Walk down one path ($\mathcal{O}(H)$) |
| **Auxiliary Space** | $\mathcal{O}(H)$ stack / queue | $\mathcal{O}(1)$ iterative |
| **Number of Visits** | $N$ nodes | At most $2H$ nodes |

---

## 7. Edge Cases Handled

1. **Single Node BST ($N = 1$):** `root.left == null` and `root.right == null`. Both min and max equal `root.val`: `[val, val]`.
2. **Completely Left-Skewed BST:** Root has only left descendants. `max = root.val`, `min = deepest_left.val`.
3. **Completely Right-Skewed BST:** Root has only right descendants. `min = root.val`, `max = deepest_right.val`.
4. **Large Negative and Positive Values:** Supports $-10^9 \le \text{Node.val} \le 10^9$ without 32-bit integer overflow.
5. **Scale $N = 10^5$ Nodes:** Completes in $<0.05$s, well within the 2.0s time limit.
