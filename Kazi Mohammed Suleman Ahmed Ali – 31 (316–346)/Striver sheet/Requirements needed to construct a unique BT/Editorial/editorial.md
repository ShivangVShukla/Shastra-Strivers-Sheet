# Editorial: Requirements needed to construct a unique BT

## Problem in One Line
Given two traversal types (1 = Preorder, 2 = Inorder, 3 = Postorder), decide whether that pair is enough to reconstruct exactly one binary tree.

## Intuition

Think about what each traversal tells you about the **root** and the **left/right split**:

| Traversal | Position of root | Does it reveal the left/right split? |
|-----------|------------------|--------------------------------------|
| Preorder  | First element    | No: it only says "root, then everything else" |
| Postorder | Last element     | No: same problem, just reversed |
| Inorder   | Anywhere         | **Yes**: elements left of the root are the left subtree, elements right of it are the right subtree |

- **Preorder or postorder** gives the root, but not where the left subtree ends and the right subtree begins.
- **Inorder** gives the split, but not which element is the root.

Reconstruction needs both pieces of information, so the pair must combine **inorder** with **one of the other two**.

## Brute Force vs Optimal

**Brute force (conceptual):** For each pair, take a small set of nodes, enumerate every binary tree, compute both traversals, and check whether exactly one tree produces that traversal pair. This is exponential in the number of nodes and totally unnecessary here.

**Optimal:** The input space is only 3 x 3 = 9 pairs, and the answer follows from a rule:

> Answer is `true` **iff exactly one of the two values is 2 (Inorder)**.

## Case Analysis (all 9 pairs)

| a | b | Pair | Unique? | Reason |
|---|---|------|---------|--------|
| 1 | 1 | Pre + Pre   | false | No left/right split information |
| 1 | 2 | Pre + In    | **true**  | Pre gives root, In gives split |
| 1 | 3 | Pre + Post  | false | Counter-example: root with a single child, which could be left or right, gives the same Pre and Post |
| 2 | 1 | In + Pre    | **true**  | Same as (1,2) |
| 2 | 2 | In + In     | false | Same sequence twice, no root information |
| 2 | 3 | In + Post   | **true**  | Post gives root (last), In gives split |
| 3 | 1 | Post + Pre  | false | Same as (1,3) |
| 3 | 2 | Post + In   | **true**  | Same as (2,3) |
| 3 | 3 | Post + Post | false | No left/right split information |

### Why Pre + Post is NOT enough
Take a root `1` with a single child `2`.
- If `2` is the left child: Preorder = `1 2`, Postorder = `2 1`.
- If `2` is the right child: Preorder = `1 2`, Postorder = `2 1`.

Both trees produce identical traversals, so the tree is ambiguous. (Preorder + Postorder is enough only for *full* binary trees, which this problem does not assume.)

## Edge Cases
- **Same traversal twice** (`1 1`, `2 2`, `3 3`) is always `false`. Duplicate information adds nothing.
- **Order of the pair does not matter**: `(1,2)` and `(2,1)` give the same answer.
- **Pre + Post** is the tricky one: it looks plausible, but is `false`.
- Constraints guarantee `1 <= a, b <= 3`, so no invalid input handling is required.

## Dry Run

**Input:** `1 3`
- `a == 2`? No. `b == 2`? No.
- Neither is inorder, so `false`.

**Input:** `3 2`
- `a == 2`? No. `b == 2`? Yes, and `a != 2`.
- Exactly one is inorder, so `true`.

**Input:** `2 2`
- Both are inorder, so the "exactly one" condition fails, giving `false`.

## Complexity

| | Complexity |
|---|---|
| Time  | O(1) |
| Space | O(1) |

## Code Walk-through

```java
static boolean uniqueBinaryTree(int a, int b) {
    return (a == 2 && b != 2) || (b == 2 && a != 2);
}
```

- `(a == 2 && b != 2)`: the first traversal is inorder and the second is not.
- `(b == 2 && a != 2)`: the second traversal is inorder and the first is not.
- The `||` of these is an XOR: exactly one of the two is inorder.

The rest of `Solution.java` is I/O boilerplate: a custom byte-buffer `FastScanner` (64 KB buffer, manual integer parsing) reads `a` and `b`, and a `PrintWriter` prints `true` or `false`. No recursion is used, so there is no stack-depth concern.
