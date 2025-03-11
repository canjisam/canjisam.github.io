package s15;

import java.io.*;
import java.util.*;

public class t7 {

    // 定义四种多米诺骨牌的不同旋转状态下的坐标偏移量
    static final int[][][][] p = { 
        { // 对于L形
            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 } }, // 0度
            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 0 } }, // 90度
            { { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, // 180度
            { { 0, 2 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }  // 270度
        }, 
        { // 对于I形
            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } }, // 0度
            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } }  // 90度
        }, 
        { // 对于T形
            { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } }, // 0度
            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, -1 } }, // 90度
            { { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, // 180度
            { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 } }  // 270度
        }, 
        { // 对于S形
            { { 0, 0 }, { 0, 1 }, { -1, 1 }, { -1, 2 } }, // 0度
            { { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 } }   // 90度
        }
    };

    // 定义网格大小
    static int[][] g = new int[55][55];
    static int n;
    static boolean ok = false;

    public static void main(String[] args) {
        slowreadversion(); // 使用慢速读取版本 89ms
        // fastreadversion(); // 使用快速读取版本 70ms
    }

    // 慢速读取版本
    public static void slowreadversion() {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while ((t--) > 0) {
            n = sc.nextInt();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    g[i][j] = sc.nextInt();
                }
            }

            ok = false;
            dfs(0);
            System.out.println(ok ? "Yes" : "No");
        }
        sc.close();
        System.out.flush();
    }

    // 解决问题的方法
    public static boolean solve() {
        ok = false;
        dfs(0);
        return ok;
    }

    // 判断是否可以在指定位置放置指定类型的多米诺骨牌
    public static boolean judge(int type, int rotation, int x, int y) {
        for (int[] pos : p[type][rotation]) {
            int tx = x + pos[0], ty = y + pos[1];
            if (!(tx >= 1 && tx <= n && ty >= 1 && ty <= n) || g[tx][ty] != 1)
                return false;
        }
        return true;
    }

    // 填充或清除指定位置的多米诺骨牌
    static void fill(int type, int rotation, int x, int y, int target) {
        for (int[] pos : p[type][rotation]) {
            g[x + pos[0]][y + pos[1]] = target;
        }
    }

    // 深度优先搜索（DFS）来尝试放置多米诺骨牌
    static void dfs(int placed) {
        if (ok)
            return;
        if (placed == 4)
            ok = true;
        else {
            for (int rotation = 0; rotation < p[placed].length; rotation++) {
                if (ok)
                    return;
                for (int x = 1; x <= n; x++) {
                    for (int y = 1; y <= n; y++) {
                        if (!judge(placed, rotation, x, y))
                            continue;
                        fill(placed, rotation, x, y, 2); // 放置多米诺骨牌
                        dfs(placed + 1);
                        fill(placed, rotation, x, y, 1); // 清除多米诺骨牌
                    }
                }
            }
        }
    }

    // 快速读取版本
    static FR in = new FR();
    static PrintWriter out = new PrintWriter(System.out);

    public static void fastreadversion() {
        int t = in.nextInt();
        while ((t--) > 0) {
            n = in.nextInt();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    g[i][j] = in.nextInt();
                }
            }
            out.println(solve() ? "Yes" : "No");
        }
        out.flush();
    }

    // 自定义快速读取类
    static class FR {
        static BufferedReader bufferedReader;
        static StringTokenizer stringTokenizer;

        FR() {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            String string;
            while (stringTokenizer == null || !stringTokenizer.hasMoreElements()) {
                try {
                    string = bufferedReader.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stringTokenizer = new StringTokenizer(string);
            }
            return stringTokenizer.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
}



