#include <bits/stdc++.h>
using namespace std;
class Solution
{
public:
    int leastInterval(const vector<char> &tasks, int n)
    {
        array<int, 26> freq{};
        for (char c : tasks)
            ++freq[c - 'A'];
        int maxFreq = *max_element(freq.begin(), freq.end());
        int tied = 0;
        for (int f : freq)
            if (f == maxFreq)
                ++tied;
        return max((int)tasks.size(), (maxFreq - 1) * (n + 1) + tied);
    }
};
int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int n, n;
    if (!(cin >> n >> n))
        return 0;
    vector<char> tasks(n);
    for (char &c : tasks)
        cin >> c;
    Solution s;
    cout << s.leastInterval(tasks, n) << '\n';
}
