#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int myAtoi(string input) {
        int n = input.size();
        int i = 0;
        int sign = 1;
        long long num = 0;

        while (i < n && input[i] == ' ') {
            i++;
        }

        if (i < n && (input[i] == '+' || input[i] == '-')) {
            if (input[i] == '-') {
                sign = -1;
            }
            i++;
        }

        long long limit = (sign == 1)
                            ? INT_MAX
                            : 2147483648LL;

        while (i < n && input[i] >= '0' && input[i] <= '9') {
            int digit = input[i] - '0';

            if (num > (limit - digit) / 10) {
                return (sign == 1) ? INT_MAX : INT_MIN;
            }

            num = num * 10 + digit;
            i++;
        }

        return static_cast<int>(sign * num);
    }
};

int main() {
    string input;
    getline(cin, input);

    Solution sol;
    cout << sol.myAtoi(input) << '\n';

    return 0;
}