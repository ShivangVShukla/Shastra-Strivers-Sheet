// Demonstration of Recursion Theory concepts in C++17
#include <iostream>

using namespace std;

// Example 1: Linear Recursion with clear Base Case & Call Stack progression
void printNumbersAscending(int n) {
    // Base Case: stops recursion when n reaches 0
    if (n <= 0) return;

    // Recursive Call: head recursion progresses toward base case
    printNumbersAscending(n - 1);

    // Unwinding phase: prints on the way back up
    cout << n << " ";
}

// Example 2: Tree Recursion (branching)
int fibonacciTree(int n) {
    if (n <= 0) return 0; // Base case 1
    if (n == 1) return 1; // Base case 2
    return fibonacciTree(n - 1) + fibonacciTree(n - 2); // 2 recursive branches
}

int main() {
    cout << "Demonstration of Recursion Theory:" << endl;
    cout << "Ascending numbers 1 to 5: ";
    printNumbersAscending(5);
    cout << endl;
    cout << "Fibonacci(6): " << fibonacciTree(6) << endl;
    return 0;
}
