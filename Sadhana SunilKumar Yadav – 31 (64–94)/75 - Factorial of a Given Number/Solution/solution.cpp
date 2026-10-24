#include <iostream>

using namespace std;

class Solution {
public:
    int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int n;
    if (cin >> n) {
        Solution sol;
        cout << sol.factorial(n) << "\n";
    }
    return 0;
}
