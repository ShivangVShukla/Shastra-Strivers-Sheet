# LFU Cache — Editorial

## Difficulty
Hard

## Prerequisites
- Hash Map
- Doubly Linked List
- LRU Cache
- Frequency counting
- Data structure design

## Problem Statement
We need to design an LFU (Least Frequently Used) cache.

Each cached key has a frequency representing how many cache operations have been performed on that key.

When inserting a new key into a full cache:

1. Remove the key with the smallest frequency.
2. If multiple keys have the same smallest frequency, remove the least recently used key among them.

The important requirement is that both `get` and `put` should work in `O(1)` average time.

## Why a Simple HashMap Is Not Enough
A normal hash map can find a key in `O(1)`, but it cannot efficiently answer:

> Which key currently has the smallest frequency?

We therefore need additional structure to group keys by frequency.

We also need an ordering inside each frequency group because equal-frequency keys use LRU as the tie-breaker.

## Data Structure

We maintain three main pieces of information.

### 1. Key Map

```text
key -> Node
```

This lets us find a key immediately.

### 2. Frequency Map

```text
frequency -> Doubly Linked List
```

Every frequency has its own doubly linked list.

Inside one list:

```text
front = most recently used
back  = least recently used
```

For example:

```text
frequency 1:  [newest] <-> ... <-> [oldest]

frequency 2:  [newest] <-> ... <-> [oldest]
```

### 3. Minimum Frequency

We keep:

```text
minFreq
```

which stores the smallest frequency currently present in the cache.

This allows eviction without scanning all frequencies.

## Node Structure

Each node stores:

```text
key
value
freq
prev
next
```

The key must be stored inside the node because when an eviction happens we need to remove that key from the main hash map.

## Core Operation: Increasing Frequency

Suppose a key currently has:

```text
frequency = 2
```

and we call `get(key)`.

Its frequency becomes:

```text
frequency = 3
```

We therefore:

1. Remove it from the frequency-2 list.
2. If frequency-2 becomes empty and `minFreq == 2`, increase `minFreq`.
3. Increment the node frequency.
4. Insert the node at the front of the frequency-3 list.

The node becomes the most recently used key among frequency 3.

## get Operation

For `get(key)`:

### Case 1 — Key does not exist

Return:

```text
-1
```

### Case 2 — Key exists

Return its value, but first increase its frequency.

So:

```text
get(key)
```

means both:

```text
read the value
+
increase usage frequency
```

## put Operation

### Case 1 — Key Already Exists

Find the node using the hash map.

Then:

1. Update its value.
2. Increase its frequency.

The `put` operation on an existing key therefore also counts as a use.

### Case 2 — Key Does Not Exist

If the cache is full:

```text
size == capacity
```

we must evict one node.

The node to remove is:

```text
back of frequency[minFreq]
```

because that node is the least recently used key among all keys with minimum frequency.

Then:

1. Remove it from its frequency list.
2. Remove it from the key map.
3. Decrease the cache size.

Finally:

1. Create the new node.
2. Set frequency to `1`.
3. Insert it at the front of frequency-1.
4. Set `minFreq = 1`.

## Example Trace

Consider:

```text
capacity = 2

put(1, 1)
put(2, 2)
get(1)
put(3, 3)
```

After:

```text
put(1,1)
```

```text
freq 1: [1]
minFreq = 1
```

After:

```text
put(2,2)
```

Both keys have frequency 1:

```text
freq 1: [2, 1]
```

Key 2 is the least recently used among frequency 1.

Now:

```text
get(1)
```

Key 1 moves from frequency 1 to frequency 2:

```text
freq 1: [2]
freq 2: [1]

minFreq = 1
```

Now:

```text
put(3,3)
```

The cache is full.

The minimum frequency is 1, and the frequency-1 list contains only key 2.

Therefore key 2 is evicted:

```text
freq 1: [3]
freq 2: [1]
```

## Equal-Frequency Tie

Suppose:

```text
capacity = 2

put(1,1)
put(2,2)
get(1)
get(2)
```

Both keys now have frequency 2.

The most recent access was key 2, so key 1 is the LRU key among frequency 2.

Therefore:

```text
put(3,3)
```

must evict key 1.

This is why every frequency needs its own LRU ordering.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    static class Node {
        int key;
        int value;
        int freq;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    static class DoublyLinkedList {
        Node head;
        Node tail;
        int size;

        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        void addFirst(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = null;
            node.next = null;
            size--;
        }

        Node removeLast() {
            if (size == 0) {
                return null;
            }

            Node node = tail.prev;
            remove(node);
            return node;
        }

        boolean isEmpty() {
            return size == 0;
        }
    }

    static class LFUCache {
        int capacity;
        int size;
        int minFreq;

        Map<Integer, Node> nodes;
        Map<Integer, DoublyLinkedList> freqLists;

        LFUCache(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.minFreq = 0;
            nodes = new HashMap<>();
            freqLists = new HashMap<>();
        }

        int get(int key) {
            Node node = nodes.get(key);

            if (node == null) {
                return -1;
            }

            increaseFrequency(node);
            return node.value;
        }

        void put(int key, int value) {
            if (capacity <= 0) {
                return;
            }

            Node existing = nodes.get(key);

            if (existing != null) {
                existing.value = value;
                increaseFrequency(existing);
                return;
            }

            if (size == capacity) {
                DoublyLinkedList list = freqLists.get(minFreq);
                Node removed = list.removeLast();

                nodes.remove(removed.key);
                size--;

                if (list.isEmpty()) {
                    freqLists.remove(minFreq);
                }
            }

            Node node = new Node(key, value);

            nodes.put(key, node);

            freqLists
                .computeIfAbsent(1, k -> new DoublyLinkedList())
                .addFirst(node);

            minFreq = 1;
            size++;
        }

        void increaseFrequency(Node node) {
            int oldFreq = node.freq;
            DoublyLinkedList oldList = freqLists.get(oldFreq);

            oldList.remove(node);

            if (oldList.isEmpty()) {
                freqLists.remove(oldFreq);

                if (minFreq == oldFreq) {
                    minFreq++;
                }
            }

            node.freq++;

            freqLists
                .computeIfAbsent(node.freq, k -> new DoublyLinkedList())
                .addFirst(node);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st =
            new StringTokenizer(br.readLine());

        int capacity = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        LFUCache cache = new LFUCache(capacity);

        StringBuilder output = new StringBuilder();
        boolean hasGet = false;

        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());

            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                int key = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());

                cache.put(key, value);
            } else {
                int key = Integer.parseInt(st.nextToken());
                int result = cache.get(key);

                if (hasGet) {
                    output.append(' ');
                }

                output.append(result);
                hasGet = true;
            }
        }

        if (!hasGet) {
            System.out.println("EMPTY");
        } else {
            System.out.println(output);
        }
    }
}
```

## Complexity Analysis

Let `Q` be the number of operations and `C` be the cache capacity.

### Time Complexity

Every hash map operation is `O(1)` average.

Every doubly linked list insertion/removal is also `O(1)`.

Therefore:

```text
get  -> O(1) average
put  -> O(1) average
```

For `Q` operations:

```text
O(Q)
```

### Space Complexity

We store at most `C` cache nodes.

Therefore:

```text
O(C)
```

## Important Edge Cases

### 1. Capacity = 1

Every new different key immediately evicts the previous key.

### 2. Repeated get

Repeatedly calling:

```text
get(1)
```

keeps increasing key 1's frequency.

### 3. Existing key with put

Calling:

```text
put(1, 50)
```

when key 1 already exists updates the value and increases its frequency.

### 4. Frequency Tie

When several keys have the same minimum frequency, the least recently used key within that frequency must be removed.

### 5. Missing Key

`get` on an absent key returns `-1` and does not modify the cache.

### 6. No get operations

The assignment harness prints `EMPTY` because there are no values to return.

## Why the Approach Works

The key map gives direct access to every node.

The frequency map separates nodes according to their current frequency.

Each frequency list maintains LRU order, so the least recently used node for a frequency is always available at the back.

Finally, `minFreq` tells us which frequency must be considered for eviction.

Therefore, both finding a key and performing an eviction can be completed without scanning the cache.

This satisfies the required `O(1)` average complexity for both `get` and `put`.
