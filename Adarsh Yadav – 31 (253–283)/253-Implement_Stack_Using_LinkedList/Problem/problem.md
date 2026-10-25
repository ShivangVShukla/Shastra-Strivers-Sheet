### Problem: Implement Stack using Linkedlist

**Difficulty:** Easy
**Tags:** Linked List, Stack, Design

**Problem Statement**
Implement a Last-In-First-Out (LIFO) stack using a singly linked list. The implemented stack should support the following operations: `push`, `pop`, `top`, and `isEmpty`.

Implement the `LinkedListStack` class:
* `void push(int x)`: Pushes element `x` onto the stack.
* `int pop()`: Removes and returns the top element of the stack.
* `int top()`: Returns the top element of the stack without removing it.
* `boolean isEmpty()`: Returns `true` if the stack is empty, `false` otherwise.

You are given a sequence of operations to perform on an initially empty stack. Print the result of each operation.

**Input Format**
* The first line contains a single integer `Q`, the number of operations.
* Each of the next `Q` lines contains one operation, in one of these forms: `push x`, `pop`, `top`, `isEmpty`.

**Output Format**
* Print one line per operation, in order:
  * for `push`, print `null`
  * for `pop` and `top`, print the returned integer
  * for `isEmpty`, print `true` or `false`

**Constraints**
* 1 ≤ Q ≤ 100
* 1 ≤ x ≤ 100
* `pop` and `top` are only called when the stack is non-empty.

**Example 1**

**Input:**
5
push 3
push 7
pop
top
isEmpty

**Output:**
null
null
7
3
false

**Explanation:**
```text
LinkedListStack stack = new LinkedListStack();
stack.push(3);
stack.push(7);
stack.pop();     // returns 7
stack.top();     // returns 3
stack.isEmpty(); // returns false
```

**Example 2**

**Input:**
1
isEmpty

**Output:**
true

**Explanation:**
```text
LinkedListStack stack = new LinkedListStack();
stack.isEmpty(); // returns true
```
