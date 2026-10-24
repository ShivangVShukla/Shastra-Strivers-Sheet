# Editorial: Recursion Concepts with Parameters

## Difficulty
Learning / Intermediate

## 1. Parameterized vs Functional Recursion

### A. Parameterized Recursion
In **Parameterized Recursion**, you carry an accumulator or state variable as an argument through the function parameters.
- **Mental Model:** *"I accumulate the partial result as I go deeper. When I hit the base case, my parameter holds the final answer."*
- **Example: Sum of 1 to N (Parameterized)**
```cpp
void solve(int i, int currentSum) {
    if (i < 1) {
        cout << currentSum << endl;
        return;
    }
    solve(i - 1, currentSum + i);
}
```
- State flows **forward (downward)** into the stack.

---

### B. Functional Recursion
In **Functional Recursion**, the function returns a value, relying on the mathematical relation between the original problem and subproblems.
- **Mental Model:** *"I will break the problem down until the base case returns a known value, then combine answers as stack frames unwind."*
- **Example: Sum of 1 to N (Functional)**
```cpp
int solve(int n) {
    if (n == 0) return 0;
    return n + solve(n - 1);
}
```
- State flows **backward (upward)** as stack frames return.

---

## 2. Comparison Table
| Feature | Parameterized Recursion | Functional Recursion |
| :--- | :--- | :--- |
| Return Type | Typically `void` or returns accumulated parameter | Value type (`int`, `string`, `vector`, etc.) |
| Where Work Happens | On the way **in** (before recursive call) | On the way **out** (after recursive call returns) |
| Base Case Role | Prints, saves, or yields the accumulated parameter | Returns the identity element (e.g. 0 for sum, 1 for product) |
| Suitability | Path tracking, combinations, tail-call optimization | Divide-and-conquer, dynamic programming, recurrence relations |

---

## 3. Tail Recursion and Compiler Optimization
A recursive function is **tail-recursive** if the recursive call is the absolute final action in the execution path.
When a call is tail-recursive, a compiler with optimization enabled (`-O2` or `-O3`) can reuse the current stack frame instead of allocating a new one, reducing auxiliary space from $O(N)$ to $O(1)$.
