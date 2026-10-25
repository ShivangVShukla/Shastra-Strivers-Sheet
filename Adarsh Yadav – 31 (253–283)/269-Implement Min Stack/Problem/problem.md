# Problem: Implement Min Stack

## Difficulty
Medium

## Tags
Stack, Design, Data Structure

## Problem Statement
Design a stack that supports the following operations in constant time: `push`, `pop`, `top`, and retrieving the minimum element.

Implement the `MinStack` class with these operations:
- `MinStack()`: Initializes the stack.
- `void push(int val)`: Pushes `val` onto the stack.
- `void pop()`: Removes the top element.
- `int top()`: Returns the top element.
- `int getMin()`: Returns the minimum element currently in the stack.

## Input Format
- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains an integer `Q`, the number of operations.
  - Each of the next `Q` lines contains one operation in one of these formats:
    - `push x`
    - `pop`
    - `top`
    - `getMin`

Each test case starts with an empty stack. The input guarantees that `pop`, `top`, and `getMin` are called only when the stack is non-empty.

## Output Format
For each test case, print the result of each `top` and `getMin` operation on a separate line. `push` and `pop` operations produce no output.

## Constraints
- `-10^5 <= val <= 10^5`
- The stack is non-empty whenever `pop`, `top`, or `getMin` is called.
- At most `5 * 10^4` operations are made per test case.
- `1 <= T <= 120`

## Example 1

### Input
```text
1
8
push -2
push 0
push -3
getMin
pop
top
getMin
top
```

### Output
```text
-3
0
-2
0
```

### Explanation
The minimum is `-3` before the pop. Removing `-3` makes `0` the top, and the minimum of the remaining stack is `-2`.

## Example 2

### Input
```text
1
8
push 5
push 1
getMin
push 3
pop
getMin
top
top
```

### Output
```text
1
1
1
1
```

## Notes
The problem statement describes stack operations rather than a particular JSON serialization. This package uses the plain-text operation format above so that each operation can be tested easily from files.
