# Editorial: Serialize and De-serialize Binary Tree

## 1. Problem Overview

We need to design two complementary operations:
1. **`serialize(root)`**: Encodes a binary tree into a single compact string.
2. **`deserialize(data)`**: Decodes the serialized string back into the exact original binary tree structure.

The reconstructed tree must be structurally identical to the original tree, preserving all node values and parent-child connections.

---

## 2. Key Insights and Observations

1. **Why Plain Traversals are Insufficient:**
   A standard preorder or inorder traversal without markers for empty children (`null`) cannot uniquely identify a binary tree unless the tree is a BST or both preorder and inorder traversals are provided.
   However, if we explicitly mark empty children with a sentinel character (such as `#`), a **single traversal** uniquely represents the binary tree!

2. **Why BFS (Level-Order) is Optimal:**
   - **Stack-Safety:** A recursive DFS traversal could exceed the call stack depth on skewed trees of size $N = 10^4$. BFS uses a queue on the heap, ensuring $\mathcal{O}(1)$ recursion depth and immunity to stack overflow.
   - **Locality:** Nodes are processed level-by-level, matching standard binary tree serialization conventions.

---

## 3. Approaches

### Approach 1: BFS Level-Order Serialization (Optimal & Stack-Safe)

#### Serialization:
1. If `root == null`, return an empty string `""`.
2. Initialize an empty `StringBuilder` and a `Queue<TreeNode>` containing `root`.
3. While the queue is not empty:
   - Dequeue `curr`.
   - If `curr == null`:
     Append `"#,"` to the string builder.
   - If `curr != null`:
     Append `curr.val + ","` to the string builder.
     Enqueue `curr.left` (even if null).
     Enqueue `curr.right` (even if null).
4. Return the resulting string.

#### Deserialization:
1. If `data == null || data.isEmpty()`, return `null`.
2. Split the string by comma `,` into an array of string tokens.
3. If the first token is `"#"` or empty, return `null`.
4. Create the root node `TreeNode root = new TreeNode(Integer.parseInt(tokens[0]))`.
5. Initialize a `Queue<TreeNode>` and enqueue `root`.
6. Maintain a pointer `i = 1` into the tokens array.
7. While the queue is not empty and `i < tokens.length`:
   - Dequeue `parent = queue.poll()`.
   - **Left Child:**
     If `!tokens[i].equals("#")`:
       `parent.left = new TreeNode(Integer.parseInt(tokens[i]))`.
       Enqueue `parent.left`.
     Increment `i`.
   - **Right Child:**
     If `i < tokens.length && !tokens[i].equals("#")`:
       `parent.right = new TreeNode(Integer.parseInt(tokens[i]))`.
       Enqueue `parent.right`.
     Increment `i`.
8. Return `root`.

#### Complexity
- **Time Complexity:**
  - `serialize`: $\mathcal{O}(N)$ — Each node and null reference is visited once.
  - `deserialize`: $\mathcal{O}(N)$ — Each token is parsed once to create nodes and links.
- **Space Complexity:**
  - $\mathcal{O}(N)$ — Queue stores at most $\mathcal{O}(N)$ nodes on the heap; string representation is proportional to $N$.

---

### Approach 2: DFS Preorder Traversal with Sentinel Markers

#### Algorithm
- **Serialize:** Preorder DFS (`Root -> Left -> Right`). Append `node.val + ","` or `"#,"`.
- **Deserialize:** Split tokens into a list/queue. Recursively pop the next token: if `#`, return `null`; otherwise create node and recursively assign `.left` and `.right`.

#### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ for both methods.
- **Space Complexity:** $\mathcal{O}(N)$ heap space + $\mathcal{O}(H)$ call stack depth. (Note: On skewed trees of depth $10^4$, recursive DFS risks `StackOverflowError` in constrained environments).

---

## 4. Detailed Dry Run (Example 1)

Given Tree:
```text
        2
       / \
      1   3
```

1. **`serialize(root)`:**
   - Enqueue `2`.
   - Poll `2`: append `"2,"`, enqueue `1`, enqueue `3`.
   - Poll `1`: append `"1,"`, enqueue `null`, enqueue `null`.
   - Poll `3`: append `"3,"`, enqueue `null`, enqueue `null`.
   - Poll 4 nulls: append `"#,#,#,#,"`.
   - Output string: `"2,1,3,#,#,#,#,"`.

2. **`deserialize(data)`:**
   - Tokens: `["2", "1", "3", "#", "#", "#", "#"]`.
   - Root is `2`. Enqueue `2`. `i = 1`.
   - Poll `2`:
     - Token `tokens[1] = "1"` $\ne$ `#` $\implies$ `2.left = 1`, enqueue `1`.
     - Token `tokens[2] = "3"` $\ne$ `#` $\implies$ `2.right = 3`, enqueue `3`.
     - `i` advances to 3.
   - Poll `1`:
     - `tokens[3] = "#"` $\implies$ `1.left = null`.
     - `tokens[4] = "#"` $\implies$ `1.right = null`.
   - Poll `3`:
     - `tokens[5] = "#"` $\implies$ `3.left = null`.
     - `tokens[6] = "#"` $\implies$ `3.right = null`.
   - Queue is empty. Returns root `2`. Exact tree is restored!

---

## 5. Comparison Summary

| Metric | BFS Queue (Approach 1) | DFS Preorder (Approach 2) |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Space** | $\mathcal{O}(N)$ heap | $\mathcal{O}(N)$ heap + $\mathcal{O}(H)$ stack |
| **Call Stack Risk** | **None** (100% stack-safe) | Yes (on skewed trees $N = 10^4$) |
| **String Format** | Level-order CSV | Preorder CSV |

---

## 6. Edge Cases Handled

1. **Single Node ($N = 1$):** `[0]` or `[10000]` serialized and restored accurately.
2. **Left Skewed Tree:** Up to $N = 10^4$ nodes, queue-based BFS prevents stack overflow.
3. **Right Skewed Tree:** Fully preserved without degradation.
4. **Complete / Balanced Tree:** Fast level-by-level processing.
5. **Node Values:** Valid non-negative integers $\in [0, 10^4]$.
