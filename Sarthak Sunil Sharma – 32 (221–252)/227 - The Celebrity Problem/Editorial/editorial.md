# Editorial: The Celebrity Problem


## Difficulty

Medium


## Prerequisites
- Matrix
- Stack
- Two Pointers
- Greedy Elimination


## Problem Statement

At a party there are `N` people.

A celebrity satisfies two conditions:

1. Every other person knows the celebrity.
2. The celebrity knows nobody.

You are given an `N x N` matrix `M`.

```text
M[i][j] = 1
```

means person `i` knows person `j`.

```text
M[i][j] = 0
```

means person `i` does not know person `j`.

Return the index of the celebrity if one exists. Otherwise return `-1`.


## Explanation & Approach

### Step 1: Why Checking Every Person Is Expensive

For every person, we could check:

- Does everyone know this person?
- Does this person know nobody?

Doing this for all people can require `O(N²)` checks.

The standard elimination approach finds a single possible candidate in `O(N)` matrix queries and then verifies it in `O(N)`.


### Step 2: Eliminate Impossible Candidates

Start with:

```text
candidate = 0
```

Compare the candidate with every other person `i`.

There are two possibilities:

```text
M[candidate][i] == 1
```

The candidate knows `i`, so the candidate cannot be a celebrity.

Therefore:

```text
candidate = i
```

Otherwise:

```text
M[candidate][i] == 0
```

The candidate does not know `i`.

Then `i` cannot be a celebrity if:

```text
M[i][candidate] == 0
```

However, the cleanest standard formulation is to eliminate one person at every comparison using:

```text
if M[candidate][i] == 1
    candidate = i
```

If `M[candidate][i] == 0`, keep the current candidate.

Why is this valid?

If a person knows someone else, that person cannot be the celebrity. Therefore the candidate can always be replaced when the current candidate is known to know another person.


### Step 3: Verify the Candidate

Elimination only gives a **possible** celebrity.

We must still verify:

#### Condition 1

The candidate must know nobody:

```text
M[candidate][i] == 0
```

for every `i != candidate`.

#### Condition 2

Everyone else must know the candidate:

```text
M[i][candidate] == 1
```

for every `i != candidate`.

If both conditions hold, return the candidate.

Otherwise return:

```text
-1
```


## Example

### Input

```text
3
0 1 0
0 0 0
0 1 0
```

### Elimination

Start:

```text
candidate = 0
```

Compare with person `1`:

```text
M[0][1] = 1
```

Person `0` knows person `1`, so person `0` cannot be the celebrity.

```text
candidate = 1
```

Compare with person `2`:

```text
M[1][2] = 0
```

Keep candidate `1`.

Now verify person `1`:

```text
M[1][0] = 0
M[1][2] = 0
```

Person `1` knows nobody.

Also:

```text
M[0][1] = 1
M[2][1] = 1
```

Everyone knows person `1`.

Therefore:

```text
Answer = 1
```


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static int findCelebrity(int[][] matrix) {
        int n = matrix.length;

        int candidate = 0;

        for (int i = 1; i < n; i++) {
            if (matrix[candidate][i] == 1) {
                candidate = i;
            }
        }

        for (int i = 0; i < n; i++) {
            if (i == candidate) {
                continue;
            }

            if (matrix[candidate][i] == 1 ||
                matrix[i][candidate] == 0) {
                return -1;
            }
        }

        return candidate;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = fs.nextInt();
            }
        }

        System.out.println(findCelebrity(matrix));
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
```


## Time Complexity

Candidate elimination takes:

```text
O(N)
```

Candidate verification takes:

```text
O(N)
```

Therefore:

### Total Time Complexity

```text
O(N)
```

The input matrix itself contains `N²` values, so reading the matrix requires `O(N²)` input processing.

### Auxiliary Space Complexity

The algorithm itself uses:

```text
O(1)
```

extra space after the matrix is available.


## Edge Cases

- `N = 1` → the only person is the celebrity.
- No celebrity exists.
- Exactly one celebrity exists.
- All people know everyone else.
- Nobody knows anyone.
- A candidate initially looks like a celebrity but fails verification.
- A candidate knows someone and is immediately eliminated.
- Large matrices.


## Why the Approach Works

Whenever the current candidate knows another person, the candidate cannot be a celebrity, so that candidate is safely eliminated.

After processing every person, at most one person can remain as a possible celebrity.

The final verification checks both defining properties of a celebrity:

1. The candidate knows nobody.
2. Everyone else knows the candidate.

Therefore, the returned person is a celebrity exactly when both conditions hold.
