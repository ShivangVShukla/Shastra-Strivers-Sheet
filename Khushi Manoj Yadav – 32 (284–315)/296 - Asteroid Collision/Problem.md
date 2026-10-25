### Problem 296: Asteroid Collision
**Difficulty:** Medium | **Tags:** Stack, Array, Simulation

**Problem Statement**
We are given an array `asteroids` of integers representing asteroids in a row.
For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

**Input Format**
- The first line contains an integer `N`, the number of asteroids.
- The second line contains `N` space-separated non-zero integers representing `asteroids`.

**Output Format**
- Print the surviving asteroids separated by spaces.
- If no asteroids survive, print `EMPTY`.

**Constraints**
- `1 <= N <= 10^5`
- `-1000 <= asteroids[i] <= 1000`
- `asteroids[i] != 0`

**Example**
**Input:**
```text
3
5 10 -5
```
**Output:**
```text
5 10
```
