package s15;
import java.util.*;
public class t6 {

	 static int n, m, q; // n:节点数，m:边数，q:查询次数
	    static ArrayList<ArrayList<Integer>> g = new ArrayList<>(); // 图的邻接表表示
	    static double ans = 0; // 存储所有查询的平均结果
	    static Scanner sc = new Scanner(System.in);

	    public static void main(String[] args) {
	        floyd();
	        // bfs2();
	        sc.close();
	    }

	    // 广度优先搜索（BFS）函数
	    public static int bfs(int x, int step) {
	        Queue<int[]> q = new LinkedList<>(); // 创建一个队列，存储节点和当前步数
	        boolean[] vis = new boolean[n + 1]; // 访问标记数组，防止重复访问
	        q.offer(new int[]{x, 0}); // 初始节点x，步数为0
	        vis[x] = true; // 标记x为已访问
	        int count = 1; // 记录在step步数内可以到达的节点数，初始为1（起点本身）

	        while (!q.isEmpty()) { // 队列不为空时继续遍历
	            int[] cur = q.poll(); // 取出队列中的第一个元素
	            int node = cur[0]; // 当前节点
	            int ts = cur[1]; // 当前步数

	            if (ts < step) { // 如果当前步数小于目标步数，则继续遍历其邻居
	                for (int neighbor : g.get(node)) { // 遍历当前节点的所有邻居
	                    if (!vis[neighbor]) { // 如果邻居未被访问过
	                        vis[neighbor] = true; // 标记邻居为已访问
	                        count++; // 可到达节点数加1
	                        q.offer(new int[]{neighbor, ts + 1}); // 将邻居及其步数加1加入队列
	                    }
	                }
	            }
	        }

	        return count; // 返回在step步数内可以到达的节点数
	    }

	    public static void bfs2() {
	        n = sc.nextInt(); // 输入节点数
	        m = sc.nextInt(); // 输入边数
	        q = sc.nextInt(); // 输入查询次数

	        // 初始化邻接表
	        g.clear();
	        for (int i = 0; i <= n; i++) {
	            g.add(new ArrayList<>());
	        }

	        // 构建图
	        for (int i = 1; i <= m; i++) {
	            int a = sc.nextInt(); // 输入边的起点
	            int b = sc.nextInt(); // 输入边的终点
	            g.get(a).add(b); // 无向图，所以两边都要添加
	            g.get(b).add(a);
	        }

	        // 处理每个查询
	        for (int i = 1; i <= q; i++) {
	            int x = sc.nextInt(); // 查询的起点
	            int y = sc.nextInt(); // 查询的步数
	            ans += bfs(x, y); // 计算每个查询的结果并累加到ans
	        }

	        // 输出平均结果，保留两位小数
	        System.out.printf("%.2f\n", ans / q);
	    }

	    static int[][] d = new int[1010][1010];

	    // Floyd算法
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

	        // 构建图
	        for (int i = 0; i < m; i++) {
	            int a, b;
	            a = sc.nextInt();
	            b = sc.nextInt();
	            d[a][b] = d[b][a] = 1;
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
	            int x, y;
	            x = sc.nextInt();
	            y = sc.nextInt();
	            for (int j = 1; j <= n; j++) {
	                if (d[x][j] <= y) {
	                    ans = ans + 1;
	                }
	            }
	        }

	        // 输出平均结果，保留两位小数
	        System.out.printf("%.2f", ans / Q);
	    }

}
