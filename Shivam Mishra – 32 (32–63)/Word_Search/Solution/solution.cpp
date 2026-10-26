
#include <bits/stdc++.h>
using namespace std;

class Solution {
private:
    int n, m;
    int dx[4] = {-1, 1, 0, 0};
    int dy[4] = {0, 0, -1, 1};

    bool dfs(vector<vector<char>>& board, string& word,
             int x, int y, int idx) {

        if (idx == (int)word.size()) {
            return true;
        }

        if (x < 0 || x >= n || y < 0 || y >= m) {
            return false;
        }

        if (board[x][y] != word[idx]) {
            return false;
        }

        char original = board[x][y];
        board[x][y] = '#';

        for (int k = 0; k < 4; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];

            if (dfs(board, word, nx, ny, idx + 1)) {
                board[x][y] = original;
                return true;
            }
        }

        board[x][y] = original;
        return false;
    }

public:
    bool exist(vector<vector<char>>& board, string word) {
        n = board.size();

        if (n == 0) return word.empty();

        m = board[0].size();

        if (word.empty()) return true;
        if ((int)word.size() > n * m) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == word[0]) {
                    if (dfs(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n, m;
    cin >> n >> m;

    vector<vector<char>> board(n, vector<char>(m));

    for (int i = 0; i < n; i++) {
        string row;
        cin >> row;

        for (int j = 0; j < m; j++) {
            board[i][j] = row[j];
        }
    }

    string word;
    cin >> word;

    Solution sol;
    cout << (sol.exist(board, word) ? "true" : "false") << '\n';

    return 0;
}
