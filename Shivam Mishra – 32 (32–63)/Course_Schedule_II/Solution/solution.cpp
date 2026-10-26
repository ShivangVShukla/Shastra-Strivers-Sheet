
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    vector<int> findOrder(int N, vector<vector<int>>& arr) {
        vector<vector<int>> adj(N);
        vector<int> indegree(N, 0);

        for (const auto& p : arr) {
            int a = p[0];
            int b = p[1];

            adj[b].push_back(a);
            indegree[a]++;
        }

        priority_queue<int, vector<int>, greater<int>> pq;

        for (int i = 0; i < N; i++) {
            if (indegree[i] == 0) {
                pq.push(i);
            }
        }

        vector<int> order;

        while (!pq.empty()) {
            int course = pq.top();
            pq.pop();

            order.push_back(course);

            for (int next : adj[course]) {
                indegree[next]--;

                if (indegree[next] == 0) {
                    pq.push(next);
                }
            }
        }

        if ((int)order.size() != N) {
            return {};
        }

        return order;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, M;
    cin >> N >> M;

    vector<vector<int>> arr(M);

    for (int i = 0; i < M; i++) {
        int a, b;
        cin >> a >> b;
        arr[i] = {a, b};
    }

    Solution solution;
    vector<int> answer = solution.findOrder(N, arr);

    if (answer.empty()) {
        cout << "[]\n";
    } else {
        for (int i = 0; i < (int)answer.size(); i++) {
            if (i > 0) cout << ' ';
            cout << answer[i];
        }
        cout << '\n';
    }

    return 0;
}
