#include <iostream>
#include <vector>
#include <map>

static std::vector<std::vector<int> > edges;
static std::vector<bool> used;
static int t = 0;
static std::vector<int> inTime;
static std::vector<int> backEdgeTime;
static std::vector<bool> ans;

static int maxComponent = 0;
static std::map<std::pair<int, int>, int> edgesMap;
static std::vector<int> component;

void paint(int v, int color, int parent) {
    used[v] = true;
    for (int u : edges[v]) {
        if (u != parent) {
            if (!used[u]) {
                if (backEdgeTime[u] >= inTime[v]) {
                    component[edgesMap[std::pair<int, int>(v, u)]] = ++maxComponent;
                    paint(u, maxComponent, v);
                } else {
                    component[edgesMap[std::pair<int, int>(v, u)]] = color;
                    paint(u, color, v);
                }
            } else if (inTime[u] < inTime[v]) {
                component[edgesMap[std::pair<int, int>(v, u)]] = color;
            }
        }
    }
}

void dfs(int v, int p) {
    used[v] = true;
    inTime[v] = backEdgeTime[v] = t++;;
    for (int u : edges[v]) {
        if (u != p) {
            if (used[u]) {
                backEdgeTime[v] = std::min(backEdgeTime[v], inTime[u]);
            } else {
                dfs(u, v);
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
        edges[u - 1].push_back(v - 1);
        edges[v - 1].push_back(u - 1);
        edgesMap[std::pair<int, int>(v - 1, u - 1)] = i;
        edgesMap[std::pair<int, int>(u - 1, v - 1)] = i;
    }

    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i, -1);
        }
    }
    used.assign(n, false);
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            maxComponent++;
            paint(i, maxComponent, -1);
        }
    }

    std::vector<int> componentNumber;
    componentNumber.assign(maxComponent + 1, -1);
    int componentCounter = 0;

    for (int i = 0; i < m; ++i) {
        int comp = componentNumber[component[i]];
        if (comp == -1) {
            componentNumber[component[i]] = ++componentCounter;
            component[i] = componentCounter;
        } else {
            component[i] = comp;
        }
    }

    std::cout << componentCounter << std::endl;
    for (int i = 0; i < m; ++i) {
        std::cout << component[i] << " ";
    }
    return 0;
}
