#include <iostream>

using namespace std;

class Solution {
private:
    bool helper(int num, long long d) {
        if (d * d > num) return true;
        if (num % d == 0) return false;
        return helper(num, d + 1);
    }
public:
    bool checkPrime(int num) {
        if (num <= 1) return false;
        return helper(num, 2);
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int num;
    if (cin >> num) {
        Solution sol;
        cout << (sol.checkPrime(num) ? "true" : "false") << "\n";
    }
    return 0;
}
