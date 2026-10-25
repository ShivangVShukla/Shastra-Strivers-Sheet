#include <iostream>
#include <vector>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n;
    if (!(cin >> n)) return 0;

    vector<vector<int>> M(n, vector<int>(n));
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            cin >> M[i][j];
        }
    }

    int a = 0, b = n - 1;
    while (a < b) {
        if (M[a][b] == 1) {
            a++;
        } else {
            b--;
        }
    }

    int cand = a;
    bool is_celeb = true;
    for (int i = 0; i < n; ++i) {
        if (i != cand) {
            if (M[cand][i] == 1 || M[i][cand] == 0) {
                is_celeb = false;
                break;
            }
        }
    }

    cout << (is_celeb ? cand : -1) << "\n";
    return 0;
}
