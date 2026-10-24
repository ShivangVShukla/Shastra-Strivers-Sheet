# Editorial: Stock Span Problem


## Difficulty

Medium


## Prerequisites
- Stack
- Monotonic Stack
- Arrays
- Next Greater Element pattern


## Problem Statement

Given the prices of a stock for `N` consecutive days, find the span of the stock price for every day.

The span for a day is the maximum number of consecutive days ending on that day for which every previous price is less than or equal to the current day's price.


## Explanation & Approach

### Step 1: Understand the Span

For each price `price[i]`, we move backwards while the previous prices are less than or equal to `price[i]`.

For example:

```text
100 80 60 70 60 75 85
```

For `75`, the previous consecutive prices that are `<= 75` are:

```text
60, 70, 60, 75
```

So the span is `4`.


### Step 2: Why Brute Force Is Slow

For every day, we can scan backwards until we find a price greater than the current price.

In the worst case, such as an increasing price sequence, this can take:

```text
O(N²)
```

We can do better using a monotonic stack.


### Step 3: Maintain a Decreasing Stack

The stack stores indices of prices that are useful as the nearest previous greater element.

Before processing `i`, repeatedly remove indices whose prices are less than or equal to `price[i]`.

```text
while stack is not empty
      and price[stack.peek()] <= price[i]
    pop
```

After this process:

- If the stack is empty, there is no previous greater price.
- Otherwise, `stack.peek()` is the nearest previous day with a greater price.


### Step 4: Calculate the Span

If the stack is empty:

```text
span[i] = i + 1
```

because every previous day belongs to the span.

Otherwise:

```text
span[i] = i - stack.peek()
```

because the previous greater price is the first day that stops the span.

Then push the current index onto the stack.


## Example

### Input

```text
7
100 80 60 70 60 75 85
```

### Trace

| Day | Price | Stack after processing | Span |
|---:|---:|---|---:|
| 0 | 100 | [0] | 1 |
| 1 | 80 | [0,1] | 1 |
| 2 | 60 | [0,1,2] | 1 |
| 3 | 70 | [0,1,3] | 2 |
| 4 | 60 | [0,1,3,4] | 1 |
| 5 | 75 | [0,1,5] | 4 |
| 6 | 85 | [6] | 6 |

Therefore:

```text
1 1 1 2 1 4 6
```


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static int[] calculateSpan(int[] price) {
        int n = price.length;
        int[] span = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && price[stack.peek()] <= price[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                span[i] = i + 1;
            } else {
                span[i] = i - stack.peek();
            }

            stack.push(i);
        }

        return span;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();

        int[] price = new int[n];
        for (int i = 0; i < n; i++) {
            price[i] = fs.nextInt();
        }

        int[] span = calculateSpan(price);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            if (i > 0) sb.append(' ');
            sb.append(span[i]);
        }

        System.out.println(sb);
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
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
