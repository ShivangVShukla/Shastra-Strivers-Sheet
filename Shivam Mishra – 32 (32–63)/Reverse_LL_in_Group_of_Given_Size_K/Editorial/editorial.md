# Editorial: Reverse Linked List in Groups of K

## Topic
Linked List / Pointer Manipulation

## Problem Statement
Given the head of a singly linked list and an integer `k`, reverse the nodes of the linked list in consecutive groups of size `k`.

- Every complete group containing exactly `k` nodes must be reversed.
- If the remaining number of nodes at the end is less than `k`, those nodes must remain in their original order.
- The relative order of nodes within each reversed group must be reversed without changing the values stored in the nodes.

**Example 1**

**Input:**
```text
1 2 3 4 5
2
```

**Output:**
```text
2 1 4 3 5
```

**Explanation:** The linked list is divided into groups `[1, 2]`, `[3, 4]`, and `[5]`. The first two complete groups are reversed, while the last group remains unchanged because it contains fewer than `k = 2` nodes.

**Example 2**

**Input:**
```text
1 2 3 4 5
3
```

**Output:**
```text
3 2 1 4 5
```

**Explanation:** The first group `[1, 2, 3]` contains three nodes and is reversed to `[3, 2, 1]`. The remaining nodes `[4, 5]` form an incomplete group, so their order is preserved.

## Approach

The optimal iterative approach uses pointer manipulation to reverse each complete group of `k` nodes in place. It avoids creating additional linked lists or using an auxiliary array.

### Algorithm

1. **Handle the base cases:** If the linked list is empty or `k` is less than or equal to `1`, return the original head because no reversal is required.
2. **Create a dummy node:** Introduce a dummy node before the head. This simplifies handling the first group, especially when its head changes after reversal.
3. **Locate the end of the group:** Starting from the node immediately before the current group, move `k` steps to locate the group's last node.
4. **Check whether the group is complete:** If the `k`-th node does not exist, fewer than `k` nodes remain. Stop processing and leave these nodes unchanged.
5. **Save the next group's starting point:** Store the node immediately after the current group so that the remaining list stays connected during reversal.
6. **Reverse the current group:** Use three pointers:
   - `curr` points to the node currently being processed.
   - `prev` points to the previous node in the reversed portion.
   - `nextNode` temporarily stores the next node before the current link is changed.

   Initially, `prev` points to the first node after the group. This ensures that the reversed group's final node connects correctly to the rest of the list. Continue reversing links until all `k` nodes have been processed.
7. **Reconnect the group:** Connect the node before the group to the new group head. Then move the previous-group pointer to the original group head, which has become the group's tail.
8. **Repeat:** Continue the same process for subsequent groups until no complete group remains.
9. **Return the result:** Return `dummy.next`, which points to the new head of the linked list.

### Pointer Illustration

Consider the linked list `1 → 2 → 3 → 4 → 5` with `k = 2`.

**First group:**

```text
Before:
dummy → 1 → 2 → 3 → 4 → 5

Reverse [1, 2]:
dummy → 2 → 1 → 3 → 4 → 5
```

**Second group:**

```text
Reverse [3, 4]:
dummy → 2 → 1 → 4 → 3 → 5
```

The last node, `5`, is not reversed because fewer than two nodes remain.

**Final linked list:**

```text
2 → 1 → 4 → 3 → 5
```

## Cpp solution
```Cpp
class Solution {
public:
    ListNode* reverseKGroup(ListNode* head, int k) {
        if (head == nullptr || k <= 1) {
            return head;
        }

        ListNode dummy(0);
        dummy.next = head;
        ListNode* groupPrev = &dummy;

        while (true) {
            ListNode* kth = groupPrev;

            for (int i = 0; i < k && kth != nullptr; i++) {
                kth = kth->next;
            }

            if (kth == nullptr) {
                break;
            }

            ListNode* groupNext = kth->next;
            ListNode* prev = groupNext;
            ListNode* curr = groupPrev->next;

            while (curr != groupNext) {
                ListNode* nextNode = curr->next;
                curr->next = prev;
                prev = curr;
                curr = nextNode;
            }

            ListNode* oldGroupHead = groupPrev->next;
            groupPrev->next = kth;
            groupPrev = oldGroupHead;
        }

        return dummy.next;
    }
};
```

## Complexity Analysis

Let `n` be the total number of nodes in the linked list.

- **Time Complexity: O(n)** — Each node is visited a constant number of times while locating and reversing groups. Therefore, the total running time is linear in the number of nodes.
- **Auxiliary Space Complexity: O(1)** — The iterative implementation uses only a fixed number of pointers and a dummy node. Reversal happens in place without allocating additional nodes or using recursion.

## Implementation

Refer to `Solution/solution.cpp` for the complete C++ implementation.

The program reads the linked-list elements from the first input line and the group size `k` from the second line. It constructs the linked list, reverses each complete group, and prints the resulting sequence of node values.

The implementation uses an iterative strategy, preserves incomplete trailing groups, and handles edge cases such as an empty list, `k = 1`, and `k` greater than the list length.

## Edge Cases Considered

1. **Empty linked list:** Return an empty result.
2. **Single-node list:** No reversal is necessary.
3. **Group size `k = 1`:** Every group contains one node, so the list remains unchanged.
4. **Group size equals the list length:** Reverse the entire list as one group.
5. **Group size greater than the list length:** The entire list remains unchanged because no complete group exists.
6. **Incomplete final group:** Leave the remaining nodes in their original order.
7. **Duplicate and negative values:** The algorithm works independently of node values and reverses links based only on group positions.

## Conclusion

Reversing a linked list in groups of size `k` is efficiently solved through iterative pointer manipulation. A dummy node simplifies reconnection, and checking for the existence of a complete group prevents the final incomplete portion from being reversed. The resulting algorithm achieves **O(n) time complexity and O(1) auxiliary space complexity**, making it suitable for large linked lists.
