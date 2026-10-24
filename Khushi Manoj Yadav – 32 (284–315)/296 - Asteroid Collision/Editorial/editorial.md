# 296. Asteroid Collision - Editorial

## Approach
Use a stack to simulate collisions:
1. Iterate through each asteroid `x`:
   - An encounter occurs only if `x < 0` and the stack top `> 0` (moving towards each other).
   - If `top < |x|`, `top` explodes: pop it from stack and continue checking.
   - If `top == |x|`, both explode: pop `top` and mark `x` as destroyed.
   - If `top > |x|`, `x` explodes: mark `x` as destroyed.
   - If `x` survives all collisions, push `x` onto the stack.
2. Return the remaining elements from bottom to top. If empty, return `EMPTY`.

## Complexity
- Time Complexity: O(N) because each asteroid is pushed and popped at most once.
- Space Complexity: O(N)
