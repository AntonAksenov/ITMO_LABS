#include <iostream>
#include <vector>
#include <ctime>
#include <algorithm>

std::vector<int> parent;
std::vector<std::pair<int, std::pair<int, int>>> edges;

int root(int v) {
    if (parent[v] == -1) {
        return v;
    } else {
        return parent[v] = root(parent[v]);
    }
}

bool union_(int a, int b) {
    int ra = root(a);
    int rb = root(b);
    if (ra != rb) {
        if (rand() % 2) {
            parent[ra] = rb;
        } else {
            parent[rb] = ra;
        }
        return true;
    }
    return false;
}

int main() {
    srand(time(0));

    int n, m;
    std::cin >> n >> m;
    parent.assign(n, -1);
    edges.resize(m);
    for (int i = 0; i < m; ++i) {
        std::cin >> edges[i].second.first >> edges[i].second.second >> edges[i].first;
        edges[i].second.first--;
        edges[i].second.second--;
    }

    std::sort(edges.begin(), edges.end());
    int z = 0;
    long long ans = 0;
    for (int i = 0; i < m && z < n - 1; ++i) {
        if (union_(edges[i].second.first, edges[i].second.second)) {
            ++z;
            ans += edges[i].first;
        }
    }

    std::cout << ans;
    return 0;
}

//4 4
//1 2 1
//2 3 2
//3 4 5
//4 1 4

//7
