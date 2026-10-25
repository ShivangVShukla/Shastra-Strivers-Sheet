### Problem 72: Recursion Theory
**Difficulty:** Learning | **Tags:** Recursion, Fundamentals, Call Stack, Space Complexity

**Overview**
Recursion is a programming technique in which a function calls itself directly or indirectly to break down complex problems into smaller, identical subproblems until reaching a base condition.

**Core Theoretical Concepts**
1. **What is Recursion?**
   - The anatomy of a recursive method: Base case and Recursive progression.
   - Contrast with Iteration (Loop counter vs Call Stack frames).
2. **The Function Call Stack & Activation Records**
   - How operating systems and language runtimes manage function calls in memory.
   - Local variables, return addresses, parameter values in stack frames.
   - Pushing and popping activation records.
3. **Stack Overflow Exception**
   - What causes stack overflow (missing or faulty base cases, infinite recursion, excessive depth).
   - How system limits on call stack size enforce maximum recursion depth.
4. **Recursion Tree Visualization**
   - Visualizing branch progression, tree depth, and total node evaluations.
   - Linear recursion vs Tree (branching) recursion.
5. **Time & Space Complexity in Recursion**
   - Time Complexity: $\sum \text{work per call} = \text{Total Nodes} \times \text{Cost per Node}$.
   - Auxiliary Space Complexity: Proportional to the maximum height of the recursion tree (maximum active stack frames).
