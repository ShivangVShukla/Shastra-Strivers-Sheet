#include <iostream>

using namespace std;

class Solution {
private:
    int digitSum(int n) {
        if (n == 0) return 0;
        return (n % 10) + digitSum(n / 10);
    }
public:
    int addDigits(int num) {
        if (num < 10) return num;
        return addDigits(digitSum(num));
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int num;
    if (cin >> num) {
        Solution sol;
        cout << sol.addDigits(num) << "\n";
    }
    return 0;
}
