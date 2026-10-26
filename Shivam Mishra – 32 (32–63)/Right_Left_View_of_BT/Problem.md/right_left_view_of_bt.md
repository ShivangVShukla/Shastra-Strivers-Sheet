# Right and Left View of a Binary Tree

## Problem Statement

Return the left view and right view of a binary tree. The left view contains the first node visible at each level from the left; the right view contains the last node visible at each level from the left. Return each view from root to deepest level.

## Input Format

The input is a space-separated level-order serialization. Use `null` for absent children; trailing nulls may be omitted.

## Output Format

Print two lines: `LEFT` followed by the left-view values, then `RIGHT` followed by the right-view values. Values are listed top to bottom.

## Constraints

- 1 <= number of nodes <= 10000; -1000 <= node value <= 1000.

## Examples

Example 1
Input: 1 2 3 4 null null 5 null 6
Output:
LEFT 1 2 4 6
RIGHT 1 3 5 6

The tree is level-order serialized; `null` denotes a missing child.
