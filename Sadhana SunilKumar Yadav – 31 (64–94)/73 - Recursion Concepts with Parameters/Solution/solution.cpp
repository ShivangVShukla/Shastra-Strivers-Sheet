// Demonstration of Parameterized vs Functional Recursion in C++17
#include <iostream>

using namespace std;

class RecursionDemonstrator {
public:
    // 1. Parameterized Recursion: Sum of 1 to N
    // Accumulator 'currentSum' carries state forward down the stack
    void parameterizedSum(int n, int currentSum, int& result) {
        if (n <= 0) {
            result = currentSum;
            return;
        }
        parameterizedSum(n - 1, currentSum + n, result);
    }

    // 2. Functional Recursion: Sum of 1 to N
    // Returns value back up the stack as calls unwind
    int functionalSum(int n) {
        if (n <= 0) return 0;
        return n + functionalSum(n - 1);
    }

    // 3. Tail-Recursive Factorial
    long long tailFactorial(int n, long long accumulator = 1) {
        if (n <= 1) return accumulator;
        return tailFactorial(n - 1, accumulator * n);
    }
};

int main() {
    RecursionDemonstrator rd;
    int n = 5;

    int paramResult = 0;
    rd.parameterizedSum(n, 0, paramResult);
    cout << "Parameterized Sum of 1.." << n << ": " << paramResult << endl;

    int funcResult = rd.functionalSum(n);
    cout << "Functional Sum of 1.." << n << ": " << funcResult << endl;

    cout << "Tail Factorial of " << n << ": " << rd.tailFactorial(n) << endl;

    return 0;
}
