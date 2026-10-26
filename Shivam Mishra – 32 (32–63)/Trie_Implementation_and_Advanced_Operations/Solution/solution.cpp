
#include <bits/stdc++.h>
using namespace std;

class TrieNode {
public:
    TrieNode* children[26];
    int wordCount;
    int prefixCount;

    TrieNode() {
        for (int i = 0; i < 26; i++) {
            children[i] = nullptr;
        }
        wordCount = 0;
        prefixCount = 0;
    }
};

class Trie {
private:
    TrieNode* root;

    int getIndex(char ch) {
        return ch - 'a';
    }

public:
    Trie() {
        root = new TrieNode();
    }

    void insert(string word) {
        TrieNode* current = root;

        for (char ch : word) {
            int index = getIndex(ch);

            if (current->children[index] == nullptr) {
                current->children[index] = new TrieNode();
            }

            current = current->children[index];
            current->prefixCount++;
        }

        current->wordCount++;
    }

    int countWordsEqualTo(string word) {
        TrieNode* current = root;

        for (char ch : word) {
            int index = getIndex(ch);

            if (current->children[index] == nullptr) {
                return 0;
            }

            current = current->children[index];
        }

        return current->wordCount;
    }

    int countWordsStartingWith(string prefix) {
        TrieNode* current = root;

        for (char ch : prefix) {
            int index = getIndex(ch);

            if (current->children[index] == nullptr) {
                return 0;
            }

            current = current->children[index];
        }

        return current->prefixCount;
    }

    void erase(string word) {
        TrieNode* current = root;

        for (char ch : word) {
            int index = getIndex(ch);

            if (current->children[index] == nullptr) {
                return;
            }

            current = current->children[index];
        }

        // Remove only one occurrence of the complete word.
        if (current->wordCount == 0) {
            return;
        }

        current->wordCount--;

        current = root;

        for (char ch : word) {
            current = current->children[getIndex(ch)];
            current->prefixCount--;
        }
    }

    ~Trie() {
        deleteSubtree(root);
    }

private:
    void deleteSubtree(TrieNode* node) {
        if (node == nullptr) {
            return;
        }

        for (int i = 0; i < 26; i++) {
            deleteSubtree(node->children[i]);
        }

        delete node;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    Trie trie;
    int q;
    cin >> q;

    while (q--) {
        string operation, word;
        cin >> operation >> word;

        if (operation == "insert") {
            trie.insert(word);
        } else if (operation == "countWordsEqualTo") {
            cout << trie.countWordsEqualTo(word) << '\n';
        } else if (operation == "countWordsStartingWith") {
            cout << trie.countWordsStartingWith(word) << '\n';
        } else if (operation == "erase") {
            trie.erase(word);
        }
    }

    return 0;
}
