### Problem: Implement Queue using Linkedlist

**Difficulty:** Easy
**Tags:** Linked List, Queue, Design

**Problem Statement**
Implement a First-In-First-Out (FIFO) queue using a singly linked list. The implemented queue should support the following operations: `push`, `pop`, `peek`, and `isEmpty`.

Implement the `LinkedListQueue` class:
* `void push(int x)`: Adds element `x` to the end of the queue.
* `int pop()`: Removes and returns the front element of the queue.
* `int peek()`: Returns the front element of the queue without removing it.
* `boolean isEmpty()`: Returns `true` if the queue is empty, `false` otherwise.

You are given a sequence of operations to perform on an initially empty queue. Print the result of each operation.

**Input Format**
* The first line contains a single integer `Q`, the number of operations.
* Each of the next `Q` lines contains one operation, in one of these forms: `push x`, `pop`, `peek`, `isEmpty`.

**Output Format**
* Print one line per operation, in order:
  * for `push`, print `null`
  * for `pop` and `peek`, print the returned integer
  * for `isEmpty`, print `true` or `false`

**Constraints**
* 1 ≤ Q ≤ 100
* 1 ≤ x ≤ 100
* `pop` and `peek` are only called when the queue is non-empty.

**Example 1**

**Input:**
5
push 3
push 7
peek
pop
isEmpty

**Output:**
null
null
3
3
false

**Explanation:**
```text
LinkedListQueue queue = new LinkedListQueue();
queue.push(3);
queue.push(7);
queue.peek();    // returns 3
queue.pop();     // returns 3
queue.isEmpty(); // returns false
```

**Example 2**

**Input:**
3
push 2
pop
isEmpty

**Output:**
null
2
true

**Explanation:**
```text
LinkedListQueue queue = new LinkedListQueue();
queue.push(2);
queue.pop();     // returns 2
queue.isEmpty(); // returns true
```
