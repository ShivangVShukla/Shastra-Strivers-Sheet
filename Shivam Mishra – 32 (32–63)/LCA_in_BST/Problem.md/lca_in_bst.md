# LCA in BST

## Problem Statement

Given a binary search tree and two values p and q that are guaranteed to exist, return the value of their lowest common ancestor (LCA). The LCA is the deepest node that is an ancestor of both values; a node may be an ancestor of itself. BST values are unique.

## Input Format

The level-order tree serialization is followed by `| p q`. Node values p and q are guaranteed to exist.

## Output Format

Print the value of the lowest common ancestor.

## Constraints

- 1 <= number of nodes <= 10000; 1 <= node value <= 100000; all values are unique; p and q are present.

## Examples

Example 1
Input: 5 3 6 2 4 null 7 | 2 4
Output: 3

Example 2
Input: 5 3 6 2 4 null 7 | 2 7
Output: 5

The tree is level-order serialized; a vertical bar separates tree tokens from p and q.
