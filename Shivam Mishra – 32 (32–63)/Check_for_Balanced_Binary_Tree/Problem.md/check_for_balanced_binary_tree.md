# Check for Balanced Binary Tree

## Problem Statement

Given a binary tree, determine whether it is height-balanced. A tree is balanced when, at every node, the heights of its left and right subtrees differ by at most one.

## Input Format

The input is a space-separated level-order serialization of the tree. Use `null` for absent nodes; trailing nulls may be omitted.

## Output Format

Print `Yes` if the tree is height-balanced; otherwise print `No`.

## Constraints

- 1 <= number of nodes <= 10000; -100 <= node value <= 100.

## Examples

Example 1
Input: 3 9 20 null null 15 7
Output: Yes

Example 2
Input: 1 2 null null 3
Output: No

The input is a level-order serialization; `null` denotes a missing child and unnecessary trailing nulls may be omitted.

