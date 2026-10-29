# Editorial: 50. Best Time to Buy and Sell Stock

## Difficulty

Easy

## Prerequisites

- Arrays
- greedy
- one-pass optimization

## Problem Understanding

Given n daily stock prices, determine the maximum profit obtainable by buying once and selling once on a later day. If no positive profit is possible, return 0. The buy and sell days must be different, and buying must occur before selling.

## Approach

Scan prices from left to right while tracking the minimum price seen so far and the best profit found. For each price, first treat it as a possible selling price: `profit = currentPrice - minPrice`. Update the answer if this is better, then update the minimum price if today is cheaper. Updating the answer before the minimum is conceptually important: the buy day must precede the sell day.

Initialize the answer to zero so that declining prices correctly produce no transaction and profit zero. Every price is examined once; no pair enumeration is necessary.

## Step-by-Step Algorithm

1. Initialize `minPrice` to the first price and `maxProfit` to 0.
2. Visit each subsequent price as a candidate selling price.
3. Compute `currentPrice - minPrice` and maximize `maxProfit`.
4. Update `minPrice` with the current price for future selling days.
5. Return `maxProfit`.

## Worked Example

For prices `[10, 7, 5, 8, 11, 9]`, the running minimum becomes 5 and selling at 11 yields profit 6. No later transaction improves it, so the answer is 6. For a strictly decreasing sequence, the answer remains 0.

## Correctness Argument

Before processing day i, `minPrice` is the minimum price among all earlier days. Thus `price[i] - minPrice` is the best profit for a transaction selling on day i. Taking the maximum across all days considers the best valid transaction; starting at zero also allows no transaction when every trade would lose money.

## Complexity Analysis

**Time:** O(n), one pass. **Auxiliary space:** O(1).

## Implementation Notes

- The reference implementation is in `Solution/solution.cpp`.
- Inputs follow the exact whitespace-separated conventions documented in `Problem.md/best_time_to_buy_and_sell_stock.md`.
- The generator is in `Generator/gen.java` and writes 101 input/output pairs to `Testcases/input` and `Testcases/output`.
- To regenerate tests, run `javac gen.java` and `java gen` from the problem folder or its `Generator` directory. The generator searches for the `Testcases` directory in the current folder and its parent.

## Conclusion

Remembering only the cheapest earlier buying price reduces a quadratic search over pairs to a simple linear scan.
