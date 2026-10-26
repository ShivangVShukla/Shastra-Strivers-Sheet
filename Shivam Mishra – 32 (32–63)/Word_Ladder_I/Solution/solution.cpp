
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int wordLadderLength(string startWord, string targetWord,
                         vector<string>& wordList) {
        unordered_set<string> words(wordList.begin(), wordList.end());

        if (!words.count(targetWord)) {
            return 0;
        }

        queue<pair<string, int>> q;
        q.push({startWord, 1});

        unordered_set<string> visited;
        visited.insert(startWord);

        while (!q.empty()) {
            string word = q.front().first;
            int steps = q.front().second;
            q.pop();

            if (word == targetWord) {
                return steps;
            }

            for (int i = 0; i < (int)word.size(); i++) {
                char original = word[i];

                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if (ch == original) continue;

                    word[i] = ch;

                    if (words.count(word) && !visited.count(word)) {
                        visited.insert(word);
                        q.push({word, steps + 1});
                    }
                }

                word[i] = original;
            }
        }

        return 0;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N;
    cin >> N;

    vector<string> wordList(N);
    for (int i = 0; i < N; i++) {
        cin >> wordList[i];
    }

    string startWord, targetWord;
    cin >> startWord >> targetWord;

    Solution sol;
    cout << sol.wordLadderLength(startWord, targetWord, wordList)
         << '\n';

    return 0;
}
