### Problem: Asteroid Collision

**Difficulty:** Medium
**Tags:** Stack, Array, Simulation

**Problem Statement**
You are given an integer array `asteroids` representing asteroids in a row. Each asteroid moves at the same speed.

The absolute value of an asteroid represents its size. The sign of an asteroid represents its direction: positive (`+`) means moving right, negative (`-`) means moving left.

Collision rules:
* Asteroids moving in the same direction never collide.
* When two asteroids moving in opposite directions collide, the smaller asteroid explodes and the larger asteroid continues moving in the same direction.
* If both asteroids are equal in size, both explode.
* Collisions are resolved one at a time, from left to right. If an asteroid survives a collision, it continues moving and may collide immediately with the next asteroid in its path.

Return the state of the asteroids after all collisions as an array in the same order.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each test case consists of two lines:
  * The first line contains a single integer `n`, the number of asteroids.
  * The second line contains `n` space-separated integers, the elements of `asteroids`.

**Output Format**
* For each test case, print the surviving asteroids on a single line, space-separated, in their original order.
* If no asteroid survives, print an empty line.

**Constraints**
* 1 ≤ T ≤ 10
* 2 ≤ n ≤ 10^5
* -10^6 ≤ asteroids[i] ≤ 10^6
* asteroids[i] != 0

**Example 1**

**Input:**
1
5
1 2 3 -4 -2

**Output:**
-4 -2

**Explanation:**
* Asteroid 3 and -4 collide → 3 explodes, -4 survives.
* Asteroid -4 continues and collides with 2 → 2 explodes, -4 continues.
* Asteroid -4 collides with 1 → 1 explodes, -4 continues.
* The next asteroid -2 is moving left → no collision.

Final state: [-4, -2].

**Example 2**

**Input:**
1
8
5 10 -5 -10 8 -8 -3 12

**Output:**
5 12

**Explanation:**
* Asteroid 10 and -5 collide → -5 explodes, 10 survives.
* Asteroid 10 and -10 collide → both explode.
* Asteroid 8 and -8 collide → both explode.
* Asteroid -3 moves left → collides with 5 (right-moving) → 5 > 3 → -3 explodes, 5 survives.
* Asteroid 12 moves right → no collision with 5 because it is behind → 12 survives.

Final state: [5, 12].
