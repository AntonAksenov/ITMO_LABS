#include <iostream>
#include <vector>
#include <map>

static std::vector<std::vector<std::pair<int, int>>> edges;
static std::vector<bool> used;
static int t = 0;
static std::vector<int> inTime;
static std::vector<int> backEdgeTime;
static std::vector<bool> ans;

static int maxComponent = 0;
static std::vector<int> component;

void paint(int v, int color, int e) {
    used[v] = true;
    for (std::pair<int,int> pair : edges[v]) {
        int u = pair.first;
        int k = pair.second;
        if (k != e) {
            if (!used[u]) {
                if (backEdgeTime[u] >= inTime[v]) {
                    component[k] = ++maxComponent;
                    paint(u, maxComponent, k);
                } else {
                    component[k] = color;
                    paint(u, color, k);
                }
            } else if (inTime[u] < inTime[v]) {
                component[k] = color;
            }
        }
    }
}

void dfs(int v, int e) {
    used[v] = true;
    inTime[v] = backEdgeTime[v] = t++;;
    for (std::pair<int,int> pair : edges[v]) {
        int u = pair.first;
        int k = pair.second;
        if (k != e) {
            if (used[u]) {
                backEdgeTime[v] = std::min(backEdgeTime[v], inTime[u]);
            } else {
                dfs(u, k);
                backEdgeTime[v] = std::min(backEdgeTime[v], backEdgeTime[u]);
            }
        }
    }
}

int main() {
    int n, m;
    std::cin >> n >> m;
    edges.resize(n);
    used.assign(n, 0);
    ans.assign(n, 0);
    t = 0;
    inTime.assign(n, 0);
    backEdgeTime.assign(n, 0);
    component.assign(m, 0);

    for (int i = 0; i < m; ++i) {
        int v, u;
        std::cin >> v >> u;
        edges[u - 1].push_back(std::make_pair(v - 1, i));
        edges[v - 1].push_back(std::make_pair(u - 1, i));
    }

    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i, -1);
        }
    }
    used.assign(n, false);
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            paint(i, maxComponent, -1);
        }
    }

    std::cout << maxComponent << std::endl;
    for (int i = 0; i < m; ++i) {
        std::cout << component[i] << " ";
    }
    return 0;
}
