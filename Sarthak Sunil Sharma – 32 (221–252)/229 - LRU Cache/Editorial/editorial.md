# Editorial: LRU Cache


## Difficulty

Medium


## Prerequisites

- Hash Map
- Doubly Linked List
- Hashing
- Data Structure Design


## Problem Statement

An LRU (Least Recently Used) cache stores a limited number of key-value pairs.

When the cache is full and a new key has to be inserted, the key that has not been used for the longest time must be removed.

Two operations are required:

- `get(key)` returns the value if the key exists and makes it most recently used.
- `put(key, value)` inserts or updates a key and makes it most recently used.

Both operations must work in `O(1)` average time.


## Explanation & Approach

### Step 1: What Information Do We Need?

For `get(key)`, we need to find a key immediately.

A `HashMap` gives average `O(1)` lookup.

However, a hash map alone does not tell us which key is the least recently used.


### Step 2: Maintain Usage Order

We need a structure that maintains keys in usage order.

Use a doubly linked list:

```text
Least Recently Used                         Most Recently Used
       HEAD <-> node <-> node <-> node <-> TAIL
```

The front contains the least recently used key.

The back contains the most recently used key.

Whenever a key is accessed or inserted, its node is moved to the back.


### Step 3: Combine HashMap and Doubly Linked List

The hash map stores:

```text
key -> node
```

Therefore:

- Find a node in `O(1)` average time.
- Remove a node from the linked list in `O(1)`.
- Insert a node at the back in `O(1)`.
- Remove the least recently used node from the front in `O(1)`.


### Step 4: Implement get()

For:

```text
get(key)
```

If the key does not exist:

```text
return -1
```

Otherwise:

1. Find its node using the hash map.
2. Remove it from its current position.
3. Move it to the most-recently-used position.
4. Return its value.


### Step 5: Implement put()

For:

```text
put(key, value)
```

If the key already exists:

1. Find its node.
2. Update its value.
3. Move it to the most-recently-used position.

If the key does not exist:

1. Create a new node.
2. Insert it at the most-recently-used position.
3. Add it to the hash map.
4. If the cache is now larger than its capacity, remove the least-recently-used node from the front and delete its key from the map.


## Example

### Input

```text
2 10
1 1 1
1 2 2
2 1
1 3 3
2 2
1 4 4
2 1
2 3
2 4
2 2
```

### Trace

After:

```text
put(1, 1)
put(2, 2)
```

the cache order is:

```text
1 -> 2
```

where `1` is least recently used and `2` is most recently used.

Then:

```text
get(1)
```

returns `1` and moves key `1` to the most-recently-used position:

```text
2 -> 1
```

Now:

```text
put(3, 3)
```

exceeds capacity, so key `2` is evicted:

```text
1 -> 3
```

Therefore:

```text
get(2) = -1
```


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    static class LRUCache {
        private final int capacity;
        private final Map<Integer, Node> map;
        private final Node head;
        private final Node tail;

        LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();

            head = new Node(0, 0);
            tail = new Node(0, 0);

            head.next = tail;
            tail.prev = head;
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addLast(Node node) {
            node.prev = tail.prev;
            node.next = tail;
            tail.prev.next = node;
            tail.prev = node;
        }

        private void moveToLast(Node node) {
            remove(node);
            addLast(node);
        }

        int get(int key) {
            Node node = map.get(key);

            if (node == null) {
                return -1;
            }

            moveToLast(node);
            return node.value;
        }

        void put(int key, int value) {
            Node node = map.get(key);

            if (node != null) {
                node.value = value;
                moveToLast(node);
                return;
            }

            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addLast(newNode);

            if (map.size() > capacity) {
                Node lru = head.next;
                remove(lru);
                map.remove(lru.key);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int capacity = fs.nextInt();
        int q = fs.nextInt();

        LRUCache cache = new LRUCache(capacity);
        StringBuilder out = new StringBuilder();
        boolean first = true;

        for (int i = 0; i < q; i++) {
            int type = fs.nextInt();

            if (type == 1) {
                int key = fs.nextInt();
                int value = fs.nextInt();
                cache.put(key, value);
            } else {
                int key = fs.nextInt();
                int value = cache.get(key);

                if (!first) {
                    out.append(' ');
                }
                out.append(value);
                first = false;
            }
        }

        if (first) {
            System.out.println("EMPTY");
        } else {
            System.out.println(out);
        }
    }

    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;

        FastScanner(InputStream in) {
            br = new BufferedReader(new InputStreamReader(in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) {
                    return null;
                }
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```


## Time Complexity

Each `get` operation performs:

```text
HashMap lookup       -> O(1) average
Linked-list movement -> O(1)
```

Each `put` operation performs:

```text
HashMap lookup       -> O(1) average
Linked-list movement -> O(1)
Possible eviction    -> O(1)
```

Therefore:

### Total Time Complexity

```text
O(Q)
```

where `Q` is the number of operations.

### Auxiliary Space Complexity

```text
O(capacity)
```


## Edge Cases

- Capacity `1`.
- `get` for a key that does not exist.
- Updating an existing key.
- Updating an existing key must also make it most recently used.
- Inserting when the cache is full.
- Repeated `get` operations on the same key.
- Repeated `put` operations on the same key.
- Keys and values at their allowed boundaries.
- A sequence containing only `put` operations.
- A sequence containing only `get` operations.


## Why the Approach Works

The hash map guarantees fast access to the node belonging to a key.

The doubly linked list maintains the exact usage order.

Whenever a key is used, its node is moved to the most-recently-used end.

Therefore, when the cache exceeds capacity, the node immediately after the dummy head is exactly the least recently used key.

Combining the two structures gives `O(1)` average time for both required operations.
