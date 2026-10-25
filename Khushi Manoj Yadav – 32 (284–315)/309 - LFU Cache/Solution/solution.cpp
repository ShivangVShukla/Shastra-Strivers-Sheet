#include <iostream>
#include <unordered_map>
#include <list>
#include <vector>

using namespace std;

struct Node {
    int key;
    long long val;
    int freq;
};

class LFUCache {
private:
    int cap;
    int minFreq;
    unordered_map<int, Node> keyTable;
    unordered_map<int, list<int>> freqTable;
    unordered_map<int, list<int>::iterator> iterTable;

    void updateFreq(int key) {
        int freq = keyTable[key].freq;
        freqTable[freq].erase(iterTable[key]);
        if (freqTable[freq].empty()) {
            freqTable.erase(freq);
            if (minFreq == freq) minFreq++;
        }
        keyTable[key].freq = freq + 1;
        freqTable[freq + 1].push_front(key);
        iterTable[key] = freqTable[freq + 1].begin();
    }

public:
    LFUCache(int capacity) : cap(capacity), minFreq(0) {}

    long long get(int key) {
        if (cap <= 0 || keyTable.find(key) == keyTable.end()) return -1;
        updateFreq(key);
        return keyTable[key].val;
    }

    void put(int key, long long val) {
        if (cap <= 0) return;
        if (keyTable.find(key) != keyTable.end()) {
            keyTable[key].val = val;
            updateFreq(key);
            return;
        }
        if ((int)keyTable.size() >= cap) {
            int evictKey = freqTable[minFreq].back();
            freqTable[minFreq].pop_back();
            if (freqTable[minFreq].empty()) {
                freqTable.erase(minFreq);
            }
            keyTable.erase(evictKey);
            iterTable.erase(evictKey);
        }
        keyTable[key] = {key, val, 1};
        freqTable[1].push_front(key);
        iterTable[key] = freqTable[1].begin();
        minFreq = 1;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int cap, q;
    if (!(cin >> cap >> q)) return 0;

    LFUCache cache(cap);
    vector<long long> out;

    while (q--) {
        int type;
        cin >> type;
        if (type == 1) {
            int k;
            long long v;
            cin >> k >> v;
            cache.put(k, v);
        } else if (type == 2) {
            int k;
            cin >> k;
            out.push_back(cache.get(k));
        }
    }

    if (out.empty()) {
        cout << "EMPTY\n";
    } else {
        for (size_t i = 0; i < out.size(); ++i) {
            if (i > 0) cout << " ";
            cout << out[i];
        }
        cout << "\n";
    }

    return 0;
}
