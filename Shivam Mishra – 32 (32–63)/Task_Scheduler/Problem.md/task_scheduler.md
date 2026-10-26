# 54. Task Scheduler

## Problem Statement

Tasks are uppercase English letters. Every task requires one CPU interval, and identical tasks must have at least n intervals between executions. During those intervals the CPU may execute other tasks or remain idle. Return the minimum total intervals needed to execute all tasks.

## Input Format

The input contains `taskCount cooldown`, followed by `taskCount` uppercase letters.

## Output Format

Print the minimum number of CPU intervals, including idle intervals.

## Constraints

- 1 <= tasks.length <= 10000; tasks[i] is A-Z; 0 <= cooldown n <= 100.

## Examples

Example 1
Input: 6 2 A A A B B B
Output: 8

Example 2
Input: 6 1 A C A B D B
Output: 6

Input starts with the number of tasks and cooldown, followed by each task as a single uppercase letter.

