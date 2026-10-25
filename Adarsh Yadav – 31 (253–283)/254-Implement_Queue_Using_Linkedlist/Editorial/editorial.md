# Editorial: Implement Queue using Linkedlist


## Difficulty

Easy


## Prerequisites
- Singly Linked List
- Queue (FIFO)


## Problem Statement

Implement a First-In-First-Out (FIFO) queue using a singly linked list, supporting `push(x)`, `pop()`, `peek()` and `isEmpty()`. Given a sequence of operations on an initially empty queue, print the result of each one.


## Explanation & Approach

### Step 1: Two Ends, Two Pointers

A queue adds at the back and removes from the front. In a singly linked list, removing from the head is O(1), but adding at the tail is only O(1) if we keep a direct pointer to the last node. So we keep two pointers:

- `head`: the front of the queue (next to be removed)
- `tail`: the back of the queue (where new elements are added)

Both start as `null`.

### Step 2: Operations

- **push(x):** create a new node. If the queue is empty (`tail == null`), both `head` and `tail` point to it. Otherwise, set `tail.next` to the new node and move `tail` to it.
- **pop():** read `head.val`, move `head` to `head.next`, and return the saved value. If `head` is now `null`, the queue became empty, so also set `tail = null`.
- **peek():** return `head.val` without changing anything.
- **isEmpty():** return `head == null`.

Each operation is a constant number of pointer updates.

### Step 3: Output Rules

- `push` returns nothing, so print `null`.
- `pop` and `peek` print the integer returned.
- `isEmpty` prints `true` or `false`.

### Step 4: Things to Watch For

- **Reset `tail` when the queue empties.** If `tail` keeps pointing at the removed node, the next `push` links onto a dead node, and the new element is never reachable from `head`.
- In `pop`, save the value **before** moving `head`.
- The constraints guarantee `pop` and `peek` are never called on an empty queue, so no extra empty check is needed.


## Example

### Input:

```text
5
push 3
push 7
peek
pop
isEmpty
```

### Trace:

| Operation | Queue (front → back) | Output |
|-----------|----------------------|--------|
| push 3    | 3                    | null   |
| push 7    | 3 → 7                | null   |
| peek      | 3 → 7                | 3      |
| pop       | 7                    | 3      |
| isEmpty   | 7                    | false  |


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    static class LinkedListQueue {
        private static class Node {
            int val;
            Node next;

            Node(int val) {
                this.val = val;
            }
        }

        private Node head;
        private Node tail;

        void push(int x) {
            Node node = new Node(x);
            if (tail == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }

        int pop() {
            int v = head.val;
            head = head.next;
            if (head == null) {
                tail = null;
            }
            return v;
        }

        int peek() {
            return head.val;
        }

        boolean isEmpty() {
            return head == null;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        LinkedListQueue queue = new LinkedListQueue();
        StringBuilder sb = new StringBuilder();

        while (q-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();

            switch (op) {
                case "push":
                    queue.push(Integer.parseInt(st.nextToken()));
                    sb.append("null");
                    break;
                case "pop":
                    sb.append(queue.pop());
                    break;
                case "peek":
                    sb.append(queue.peek());
                    break;
                default:
                    sb.append(queue.isEmpty());
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
