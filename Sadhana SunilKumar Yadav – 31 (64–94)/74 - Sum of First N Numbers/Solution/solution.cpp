#include <iostream>

using namespace std;

class Solution {
public:
    int NnumbersSum(int N) {
        if (N <= 0) return 0;
        return N + NnumbersSum(N - 1);
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int n;
    if (cin >> n) {
        Solution sol;
        cout << sol.NnumbersSum(n) << "\n";
    }
    return 0;
}
