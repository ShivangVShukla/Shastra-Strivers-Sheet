# Editorial: Recursion Theory

## Difficulty
Learning / Fundamental

## 1. What is Recursion?
Recursion occurs when a function solves a problem by calling copies of itself with smaller or simplified inputs. Every recursive solution must satisfy two fundamental properties:
1. **Base Case:** One or more terminating conditions where the problem can be solved directly without further recursive calls. This prevents infinite cycles.
2. **Recursive Step (Inductive Step):** The reduction step that breaks the problem into a strictly smaller subproblem, ensuring progression toward the base case.

### Contrast: Recursion vs Iteration
| Characteristic | Recursion | Iteration |
| :--- | :--- | :--- |
| State Management | Implicit via Stack Frames on Call Stack | Explicit via Loop Variables |
| Termination | Base Condition | Loop Condition (e.g. `while (i < n)`) |
| Overhead | Function call overhead (stack frame allocation) | Minimal (registers and jump instructions) |
| Readability | Natural for hierarchical/divide-and-conquer structures (trees, graphs, permutations) | Better for simple linear sequences |

---

## 2. The Execution Call Stack & Memory Layout
When a program runs, its memory is divided into:
- **Code (Text) Segment:** Machine instructions.
- **Data / BSS Segment:** Global and static variables.
- **Heap Segment:** Dynamically allocated memory (`new`, `malloc`).
- **Stack Segment:** Contiguous memory region that manages function calls in LIFO (Last-In, First-Out) order.

### Activation Record (Stack Frame)
Each time a function is called, an activation record is pushed onto the stack containing:
- Function arguments and parameter copies.
- Local variables allocated within the function.
- Return address (memory address in the caller code to resume after returning).
- Saved register values and frame pointer.

When the function completes (hits a return statement or base case), its stack frame is popped off the stack, and control returns to the saved return address.

---

## 3. Stack Overflow
A **Stack Overflow** occurs when the execution stack exceeds its allocated size limit (typically 1 MB to 8 MB depending on the compiler and operating system).

### Common Causes:
1. Missing base case.
2. Base case condition is never reached (e.g., parameter decrements incorrectly).
3. Recursion depth exceeds maximum stack capacity (e.g. $N = 10^6$ in deep linear recursion).

---

## 4. Recursion Tree & Complexity Analysis
A **Recursion Tree** is a directed acyclic graph (tree) representing all recursive invocations.

- **Tree Height ($H$):** The length of the longest path from the root call to a leaf (base case). The maximum auxiliary memory used by the recursion is $O(H)$ because only stack frames along the current active path reside in memory concurrently.
- **Total Invocations ($N$):** The total number of nodes in the recursion tree determines the time complexity. For linear recursion, $N = O(\text{depth})$. For binary branching recursion like naive Fibonacci, $N = O(2^n)$.
