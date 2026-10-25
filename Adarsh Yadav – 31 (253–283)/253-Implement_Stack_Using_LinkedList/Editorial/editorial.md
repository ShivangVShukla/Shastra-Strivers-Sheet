# Editorial: Implement Stack using Linkedlist


## Difficulty

Easy


## Prerequisites
- Singly Linked List
- Stack (LIFO)


## Problem Statement

Implement a Last-In-First-Out (LIFO) stack using a singly linked list, supporting `push(x)`, `pop()`, `top()` and `isEmpty()`. Given a sequence of operations on an initially empty stack, print the result of each one.


## Explanation & Approach

### Step 1: Where Should the Top Live?

A stack only ever touches one end. In a singly linked list, the only end we can insert at and remove from in O(1) is the **head** (inserting or removing at the tail would need a walk through the whole list). So the head of the list is the top of the stack.

### Step 2: Operations

Keep a single pointer `head` (initially `null`).

- **push(x):** create a new node holding `x` whose `next` is the current `head`, then set `head` to this node.
- **pop():** read `head.val`, move `head` to `head.next`, return the saved value.
- **top():** return `head.val` without changing anything.
- **isEmpty():** return `head == null`.

Each operation is a constant number of pointer updates.

### Step 3: Output Rules

- `push` returns nothing, so print `null`.
- `pop` and `top` print the integer returned.
- `isEmpty` prints `true` or `false`.

### Step 4: Things to Watch For

- In `pop`, save the value **before** moving `head`, otherwise it is lost.
- The constraints guarantee `pop` and `top` are never called on an empty stack, so no extra empty check is needed.
- A stack that becomes empty again after popping must report `isEmpty() == true` (`head` goes back to `null`).


## Example

### Input:

```text
5
push 3
push 7
pop
top
isEmpty
```

### Trace:

| Operation | List (head → tail) | Output |
|-----------|--------------------|--------|
| push 3    | 3                  | null   |
| push 7    | 7 → 3              | null   |
| pop       | 3                  | 7      |
| top       | 3                  | 3      |
| isEmpty   | 3                  | false  |


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    static class LinkedListStack {
        private static class Node {
            int val;
            Node next;

            Node(int val, Node next) {
                this.val = val;
                this.next = next;
            }
        }

        private Node head;

        void push(int x) {
            head = new Node(x, head);
        }

        int pop() {
            int v = head.val;
            head = head.next;
            return v;
        }

        int top() {
            return head.val;
        }

        boolean isEmpty() {
            return head == null;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        LinkedListStack stack = new LinkedListStack();
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();

            switch (op) {
                case "push":
                    stack.push(Integer.parseInt(st.nextToken()));
                    sb.append("null");
                    break;
                case "pop":
                    sb.append(stack.pop());
                    break;
                case "top":
                    sb.append(stack.top());
                    break;
                default:
                    sb.append(stack.isEmpty());
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $Q$ be the number of operations.

### Total Time Complexity:
$O(1)$ per operation, so $O(Q)$ overall.

### Space Complexity:
$O(Q)$ in the worst case (all operations are pushes), one node per stored element.
