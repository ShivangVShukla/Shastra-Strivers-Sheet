#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct Fenwick {
    int n;
    vector<int> tree;
    Fenwick(int n) : n(n), tree(n + 1, 0) {}

    void add(int i, int delta) {
        for (; i <= n; i += i & -i) tree[i] += delta;
    }

    int query(int i) {
        int sum = 0;
        for (; i > 0; i -= i & -i) sum += tree[i];
        return sum;
    }

    int queryRange(int l, int r) {
        if (l > r) return 0;
        return query(r) - query(l - 1);
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n;
    if (!(cin >> n)) return 0;

    vector<long long> a(n);
    vector<long long> sorted_vals;
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
        sorted_vals.push_back(a[i]);
    }

    sort(sorted_vals.begin(), sorted_vals.end());
    sorted_vals.erase(unique(sorted_vals.begin(), sorted_vals.end()), sorted_vals.end());
    int total_unique = (int)sorted_vals.size();

    auto getRank = [&](long long val) {
        return (int)(lower_bound(sorted_vals.begin(), sorted_vals.end(), val) - sorted_vals.begin()) + 1;
    };

    int q;
    cin >> q;
    vector<pair<int, int>> queries(q);
    for (int i = 0; i < q; ++i) {
        cin >> queries[i].first;
        queries[i].second = i;
    }

    sort(queries.begin(), queries.end(), [](const pair<int, int>& left, const pair<int, int>& right) {
        return left.first > right.first;
    });

    vector<int> ans(q);
    Fenwick bit(total_unique);

    int cur_idx = n - 1;
    for (const auto& query : queries) {
        int target_idx = query.first;
        int q_id = query.second;

        while (cur_idx > target_idx) {
            bit.add(getRank(a[cur_idx]), 1);
            cur_idx--;
        }

        int rk = getRank(a[target_idx]);
        ans[q_id] = bit.queryRange(rk + 1, total_unique);
    }

    for (int i = 0; i < q; ++i) {
        if (i > 0) cout << " ";
        cout << ans[i];
    }
    cout << "\n";

    return 0;
}
