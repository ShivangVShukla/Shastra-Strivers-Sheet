
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    vector<string> letterCombinations(string digits) {
        vector<string> result;

        if (digits.empty()) {
            return result;
        }

        vector<string> mapping = {
            "", "", "abc", "def", "ghi",
            "jkl", "mno", "pqrs", "tuv", "wxyz"
        };

        string current;
        backtrack(digits, 0, current, result, mapping);

        return result;
    }

private:
    void backtrack(
        string& digits,
        int index,
        string& current,
        vector<string>& result,
        vector<string>& mapping
    ) {
        if (index == digits.size()) {
            result.push_back(current);
            return;
        }

        string letters = mapping[digits[index] - '0'];

        for (char ch : letters) {
            current.push_back(ch);

            backtrack(
                digits, index + 1,
                current, result, mapping
            );

            current.pop_back();
        }
    }
};

int main() {
    string digits;
    cin >> digits;

    Solution solution;
    vector<string> result = solution.letterCombinations(digits);

    for (int i = 0; i < result.size(); i++) {
        if (i > 0) cout << " ";
        cout << result[i];
    }

    cout << '\n';
    return 0;
}
