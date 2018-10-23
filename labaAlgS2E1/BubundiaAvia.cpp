#include <fstream>
#include <vector>

int main() {
    std::ifstream fin("avia.in");
    int n;
    fin >> n;
    std::vector<std::vector<int>> d;
    d.resize(n);
    for (int i = 0; i < n; ++i) {
        d[i].resize(n);
        for (int j = 0; j < n; ++j) {
            fin >> d[i][j];
        }
    }
    fin.close();

    for (int k = 0; k < n; ++k) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                d[i][j] = std::min(d[i][j], std::max(d[i][k], d[k][j]));
            }
        }
    }

    int ans = 0;
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            ans = std::max(ans, d[i][j]);
        }
    }

    std::ofstream fout("avia.out");
    fout << ans;
    fout.close();
    return 0;
}

//4
//0 10 12 16
//11 0 8 9
//10 13 0 22
//13 10 17 0

//10
