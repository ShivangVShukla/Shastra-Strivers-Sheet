# Editorial: Convert Min Heap to Max Heap

## Difficulty
Easy

## Prerequisites
- Arrays
- Binary Heap representation
- Min Heap and Max Heap properties
- Heapify

## Problem Statement

You are given an array that represents a valid Min Heap. Convert the same array into a valid Max Heap. For index `i`, the children are `2*i+1` and `2*i+2`. A Max Heap requires every parent to be greater than or equal to its children.

## Explanation & Approach

Convert the heap directly in the same array. Leaves are already valid heaps, so process only non-leaf nodes from `n/2 - 1` down to `0`. For each node, compare it with its children, swap with the larger child when necessary, and continue downward until the Max Heap property is restored.

## Example / Trace

Input: `1 3 5 7 9 8`

Bottom-up Max-Heapify produces `9 7 8 1 3 5`. Now `9 >= 7,8`, `7 >= 1,3`, and `8 >= 5`, so the result is a valid Max Heap.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static void maxHeapify(long[] a, int n, int i) {
        while (true) {
            int largest=i, l=2*i+1, r=2*i+2;
            if (l<n && a[l]>a[largest]) largest=l;
            if (r<n && a[r]>a[largest]) largest=r;
            if (largest==i) break;
            long t=a[i]; a[i]=a[largest]; a[largest]=t; i=largest;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine().trim());
        long[] a=new long[n];
        StringTokenizer st=new StringTokenizer(br.readLine());
        for(int i=0;i<n;i++) a[i]=Long.parseLong(st.nextToken());
        for(int i=n/2-1;i>=0;i--) maxHeapify(a,n,i);
        StringBuilder out=new StringBuilder();
        for(int i=0;i<n;i++){ if(i>0) out.append(' '); out.append(a[i]); }
        System.out.println(out);
    }
}
```

## Complexity
- Time: `O(n)`
- Extra Space: `O(1)` apart from the input array.

## Edge Cases
- `n = 1`: already both a Min Heap and Max Heap.
- All elements equal.
- Negative values.
- Duplicate values.

## Why the Approach Works

When a node is processed, its children have already been converted into valid Max Heaps because we move bottom-up. Max-Heapify therefore makes the current subtree valid. After the root is processed, the entire array is a valid Max Heap.
