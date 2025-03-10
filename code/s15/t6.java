package s15;
import java.util.*;

/**
 * 试题 F: 可达性统计
 * 
 * 【题目描述】
 * 给定一个无向图，有n个节点和m条边。
 * 对于每个查询(x,y)，求从节点x出发，最多走y步能到达多少个不同的节点。
 * 输出所有查询的平均值。
 * 
 * 【输入格式】
 * 第一行三个整数n,m,q，表示节点数、边数和查询数
 * 接下来m行，每行两个整数a,b，表示节点a和b之间有一条无向边
 * 接下来q行，每行两个整数x,y，表示一个查询
 * 
 * 【输出格式】
 * 输出一个实数，表示所有查询结果的平均值，保留两位小数
 * 
 * 【解题思路】
 * 本题提供了两种解法：
 * 1. BFS（广度优先搜索）：
 *    - 对每个查询，用BFS遍历从起点出发最多y步能到达的所有节点
 *    - 使用visited数组避免重复访问
 *    - 记录步数确保不超过限制
 * 
 * 2. Floyd算法：
 *    - 预处理出所有点对之间的最短路径
 *    - 对每个查询(x,y)，统计与x的距离不超过y的节点数
 *    - 这种方法在多次查询时更高效
 */
public class t6 {
    static int n, m, q;  // n:节点数，m:边数，q:查询数量
    static ArrayList<ArrayList<Integer>> g = new ArrayList<>();  // 图的邻接表表示
    static double ans = 0;  // 存储所有查询的平均结果
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        floyd();  // 使用Floyd算法解决
        // bfs2();  // 或者使用BFS解决
        sc.close();
    }

    /**
     * BFS方法计算从节点x出发，最多走step步能到达的不同节点数
     * @param x 起始节点
     * @param step 最大步数限制
     * @return 可到达的不同节点数
     */
    public static int bfs(int x, int step) {
        Queue<int[]> q = new LinkedList<>();  // 创建队列，存储节点和当前步数
        boolean[] vis = new boolean[n + 1];  // 访问标记数组，防止重复访问
        q.offer(new int[]{x, 0});  // 初始节点x，步数为0
        vis[x] = true;  // 标记x为已访问
        int count = 1;  // 记录可到达节点数，初始为1（包括自己）

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int node = cur[0];  // 当前节点
            int ts = cur[1];    // 当前步数

            if (ts < step) {  // 如果当前步数小于限制，继续搜索邻居
                for (int neighbor : g.get(node)) {
                    if (!vis[neighbor]) {
                        vis[neighbor] = true;
                        count++;
                        q.offer(new int[]{neighbor, ts + 1});
                    }
                }
            }
        }

        return count;
    }

    /**
     * 使用BFS方法解决整个问题
     */
    public static void bfs2() {
        n = sc.nextInt();  // 读入节点数
        m = sc.nextInt();  // 读入边数
        q = sc.nextInt();  // 读入查询数量

        // 初始化邻接表
        g.clear();
        for (int i = 0; i <= n; i++) {
            g.add(new ArrayList<>());
        }

        // 建图
        for (int i = 1; i <= m; i++) {
            int a = sc.nextInt();  // 读入边的起点
            int b = sc.nextInt();  // 读入边的终点
            g.get(a).add(b);  // 无向图，两个方向都要连边
            g.get(b).add(a);
        }

        // 处理每个查询
        for (int i = 1; i <= q; i++) {
            int x = sc.nextInt();  // 查询的起点
            int y = sc.nextInt();  // 查询的步数
            ans += bfs(x, y);  // 累加每个查询的结果
        }

        // 输出平均结果，保留两位小数
        System.out.printf("%.2f\n", ans / q);
    }

    static int[][] d = new int[1010][1010];  // Floyd算法的距离矩阵

    /**
     * 使用Floyd算法解决问题
     */
    public static void floyd() {
        Scanner sc = new Scanner(System.in);
        long n, m, Q;
        n = sc.nextInt();
        m = sc.nextInt();
        Q = sc.nextInt();

        // 初始化距离矩阵
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == j) {
                    d[i][j] = 0;
                } else {
                    d[i][j] = (int) 1e9;
                }
            }
        }

        // 建图
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            d[a][b] = d[b][a] = 1;  // 无向图，距离为1
        }

        // Floyd算法计算最短路径
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }

        // 处理每个查询
        double ans = 0;
        for (int i = 0; i < Q; i++) {
            int x = sc.nextInt();  // 查询起点
            int y = sc.nextInt();  // 查询步数限制
            for (int j = 1; j <= n; j++) {
                if (d[x][j] <= y) {  // 如果最短路径不超过y
                    ans = ans + 1;    // 该节点可达
                }
            }
        }

        // 输出平均结果，保留两位小数
		System.out.printf("%.2f", ans / Q);
	}

}