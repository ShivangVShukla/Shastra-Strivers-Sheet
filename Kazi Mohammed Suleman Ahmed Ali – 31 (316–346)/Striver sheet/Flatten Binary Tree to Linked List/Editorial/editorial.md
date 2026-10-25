# Editorial: Flatten Binary Tree to Linked List

## 1. Problem Overview

We are given the `root` of a binary tree. Our task is to re-wire the pointers of the nodes **in-place** such that:
1. Each node's `left` pointer becomes `null`.
2. Each node's `right` pointer points to the node that immediately follows it in the standard **preorder traversal** (`Root -> Left -> Right`) of the original tree.
3. No new nodes are created.

---

## 2. Key Observations

1. **Preorder Traversal Sequence:**
   Preorder traversal visits `Root`, then the entire `Left` subtree, then the entire `Right` subtree.
   Therefore, for any node $U$:
   - The head of $U$'s flattened list is $U$ itself.
   - The first node after $U$ in preorder is the root of its left subtree (if non-null).
   - The first node of its right subtree must be attached after the **rightmost (last) node of the flattened left subtree**.

2. **In-Place Modification Challenge:**
   If we simply set `curr.right = curr.left` during a standard forward preorder traversal, we lose the reference to `curr.right` unless we save it.

---

## 3. Approaches

### Approach 1: Reverse Preorder DFS (Right $\to$ Left $\to$ Root)

#### Intuition
Standard preorder visits: `Root -> Left -> Right`.
If we traverse in reverse: `Right -> Left -> Root`, when we are at node `curr`, both its left and right subtrees have already been completely flattened and re-arranged!
We can maintain a global/instance pointer `prev` representing the head of the already processed suffix.
When visiting `curr`:
- `curr.right = prev`
- `curr.left = null`
- `prev = curr`

#### Algorithm
1. Initialize `prev = null`.
2. Define `flatten(node)`:
   - If `node == null`, return.
   - Recursively call `flatten(node.right)`.
   - Recursively call `flatten(node.left)`.
   - Set `node.right = prev`.
   - Set `node.left = null`.
   - Update `prev = node`.

#### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ — Each node is visited once.
- **Space Complexity:** $\mathcal{O}(H)$ — Recursion stack up to tree height $H$ ($\mathcal{O}(N)$ in the worst case for a skewed tree).

---

### Approach 2: Iterative Traversal with an Explicit Stack

#### Intuition
We simulate preorder traversal iteratively using a Stack:
1. Push `root` to stack.
2. While stack is not empty:
   - Pop `curr`.
   - If `curr.right != null`, push `curr.right` onto stack.
   - If `curr.left != null`, push `curr.left` onto stack.
   - If the stack is not empty, `st.peek()` is the next node in preorder! Set `curr.right = st.peek()`.
   - Set `curr.left = null`.

#### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ — Each node is pushed and popped exactly once.
- **Space Complexity:** $\mathcal{O}(N)$ — Stack stores up to $N$ nodes.

---

### Approach 3: Morris-like Pointer Rewiring ($\mathcal{O}(1)$ Auxiliary Space) — Optimal

#### Intuition
Can we achieve $\mathcal{O}(1)$ auxiliary space without using recursion or an explicit stack?
Yes! By utilizing the structure of the binary tree itself:
For any node `curr` with a non-null `left` subtree:
1. In preorder traversal, the right subtree of `curr` comes strictly **after** the last node of `curr`'s left subtree.
2. In preorder, the last node of the left subtree is its **rightmost node** (the in-order predecessor of `curr`'s right child).
3. Find this rightmost node `pred`:
   ```java
   TreeNode pred = curr.left;
   while (pred.right != null) {
       pred = pred.right;
   }
   ```
4. Splice `curr.right` to `pred.right`:
   ```java
   pred.right = curr.right;
   ```
5. Move the left subtree to the right:
   ```java
   curr.right = curr.left;
   curr.left = null;
   ```
6. Advance `curr` to `curr.right`. Repeat until `curr == null`.

#### Step-by-Step Dry Run (Example 1)
Given Tree:
```text
        1
       / \
      2   5
     / \   \
    3   4   6
```

1. **`curr = 1`**:
   - `curr.left = 2` (not null).
   - Find rightmost node of 2's subtree: start at 2, go right to 4. `4.right == null`.
   - `pred = 4`.
   - Attach `pred.right = curr.right` $\to$ `4.right = 5`.
   - Move left subtree to right: `curr.right = 2`, `curr.left = null`.
   - Tree structure now:
     ```text
     1
      \
       2
      / \
     3   4
          \
           5
            \
             6
     ```
   - Advance `curr = curr.right` $\to$ `curr = 2`.

2. **`curr = 2`**:
   - `curr.left = 3` (not null).
   - Find rightmost node of 3's subtree: `3.right == null`, so `pred = 3`.
   - Attach `pred.right = curr.right` $\to$ `3.right = 4`.
   - Move left to right: `curr.right = 3`, `curr.left = null`.
   - Tree structure now:
     ```text
     1 -> 2 -> 3 -> 4 -> 5 -> 6
     ```
   - Advance `curr = curr.right` $\to$ `curr = 3`.

3. **`curr = 3`**: `curr.left == null`. Advance `curr = curr.right` $\to$ 4.
4. **`curr = 4`**: `curr.left == null`. Advance `curr = curr.right` $\to$ 5.
5. **`curr = 5`**: `curr.left == null`. Advance `curr = curr.right` $\to$ 6.
6. **`curr = 6`**: `curr.left == null`. Advance `curr = curr.right` $\to$ `null`.
7. Loop terminates. The tree is completely flattened!

#### Complexity Analysis of Morris Approach
- **Time Complexity:** $\mathcal{O}(N)$
  Although there is a nested `while` loop to find the predecessor, each edge in the tree is traversed at most twice (once to find the predecessor and once when `curr` traverses through it). Total operations are bounded by $2N \in \mathcal{O}(N)$.
- **Auxiliary Space Complexity:** $\mathcal{O}(1)$
  Only a few pointer variables (`curr`, `pred`) are used. No call stack, no heap allocation.

---

## 4. Comparison Summary

| Approach | Time Complexity | Auxiliary Space | Pros | Cons |
| :--- | :--- | :--- | :--- | :--- |
| **Reverse Preorder DFS** | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ | Very short code (6 lines) | Can cause StackOverflow on skewed trees of depth 2000 |
| **Iterative Stack** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Intuitive simulation | Uses $\mathcal{O}(N)$ extra heap memory |
| **Morris Traversal** | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Optimal, stack-safe, zero extra memory | Requires careful pointer manipulation |

---

## 5. Edge Cases Handled

1. **Empty Tree ($N = 0$):** `root == null` $\implies$ immediately return `[]`.
2. **Single Node ($N = 1$):** `curr.left == null`, while loop advances to `null`, returns `[val]`.
3. **Already Flattened Tree (Right-skewed):** All `curr.left == null`, traverses directly to the end in $\mathcal{O}(N)$.
4. **Completely Left-skewed Tree:** Each node has a left child; repeatedly splices until transformed into a right-skewed list.
5. **Negative Values:** Node values $\in [-100, 100]$ are preserved without modification.
