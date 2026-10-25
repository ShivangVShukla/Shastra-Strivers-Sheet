# Editorial: Implement Min Stack

## Difficulty
Medium

## Prerequisites
- Stack (LIFO)
- Arrays or dynamic arrays
- Constant-time minimum tracking

## Problem Summary
Implement a stack supporting `push`, `pop`, `top`, and `getMin`, each in `O(1)` time.

## Approach: Store the Minimum Alongside Every Value
Maintain two stacks:
- `values`: the actual stack values.
- `mins`: the minimum value at each corresponding depth.

When pushing `x`, push `x` to `values` and push `min(x, currentMinimum)` to `mins`. If the stack is empty, `x` is the minimum.
When popping, pop from both stacks.
The top value is the last value in `values`, and the minimum is the last value in `mins`.

This preserves the minimum of every prefix of the stack, so removing an element automatically restores the previous minimum.

## Example Walkthrough
Operations:
`push 10`, `push 15`, `push 5`, `top`, `getMin`, `pop`, `getMin`

- After `push 10`: values = `[10]`, mins = `[10]`
- After `push 15`: values = `[10, 15]`, mins = `[10, 10]`
- After `push 5`: values = `[10, 15, 5]`, mins = `[10, 10, 5]`
- `top` returns `5`.
- `getMin` returns `5`.
- `pop` removes `5` from both stacks.
- `getMin` now returns `10`.

The output sequence for query operations is `5`, `5`, `10`.

## Algorithm
1. Initialize empty value and minimum stacks.
2. For `push x`, add `x` to the value stack and the current minimum to the minimum stack.
3. For `pop`, remove the top from both stacks.
4. For `top`, output the top of the value stack.
5. For `getMin`, output the top of the minimum stack.

## Complexity Analysis
- **Time Complexity:** `O(1)` per operation.
- **Space Complexity:** `O(Q)` for at most `Q` pushed elements.

## Edge Cases
- Negative values.
- Repeated minimum values.
- Increasing and decreasing sequences.
- Popping the current minimum and restoring the previous minimum.
- Values at both constraint boundaries.
