package s15;
import java.util.*;
public class t6 {

	 static int n, m, q; // n:�ڵ�����m:������q:��ѯ����
	    static ArrayList<ArrayList<Integer>> g = new ArrayList<>(); // ͼ���ڽӱ��ʾ
	    static double ans = 0; // �洢���в�ѯ��ƽ�����
	    static Scanner sc = new Scanner(System.in);

	    public static void main(String[] args) {
	        floyd();
	        // bfs2();
	        sc.close();
	    }

	    // �������������BFS������
	    public static int bfs(int x, int step) {
	        Queue<int[]> q = new LinkedList<>(); // ����һ�����У��洢�ڵ�͵�ǰ����
	        boolean[] vis = new boolean[n + 1]; // ���ʱ�����飬��ֹ�ظ�����
	        q.offer(new int[]{x, 0}); // ��ʼ�ڵ�x������Ϊ0
	        vis[x] = true; // ���xΪ�ѷ���
	        int count = 1; // ��¼��step�����ڿ��Ե���Ľڵ�������ʼΪ1����㱾��

	        while (!q.isEmpty()) { // ���в�Ϊ��ʱ��������
	            int[] cur = q.poll(); // ȡ�������еĵ�һ��Ԫ��
	            int node = cur[0]; // ��ǰ�ڵ�
	            int ts = cur[1]; // ��ǰ����

	            if (ts < step) { // �����ǰ����С��Ŀ�경����������������ھ�
	                for (int neighbor : g.get(node)) { // ������ǰ�ڵ�������ھ�
	                    if (!vis[neighbor]) { // ����ھ�δ�����ʹ�
	                        vis[neighbor] = true; // ����ھ�Ϊ�ѷ���
	                        count++; // �ɵ���ڵ�����1
	                        q.offer(new int[]{neighbor, ts + 1}); // ���ھӼ��䲽����1�������
	                    }
	                }
	            }
	        }

	        return count; // ������step�����ڿ��Ե���Ľڵ���
	    }

	    public static void bfs2() {
	        n = sc.nextInt(); // ����ڵ���
	        m = sc.nextInt(); // �������
	        q = sc.nextInt(); // �����ѯ����

	        // ��ʼ���ڽӱ�
	        g.clear();
	        for (int i = 0; i <= n; i++) {
	            g.add(new ArrayList<>());
	        }

	        // ����ͼ
	        for (int i = 1; i <= m; i++) {
	            int a = sc.nextInt(); // ����ߵ����
	            int b = sc.nextInt(); // ����ߵ��յ�
	            g.get(a).add(b); // ����ͼ���������߶�Ҫ���
	            g.get(b).add(a);
	        }

	        // ����ÿ����ѯ
	        for (int i = 1; i <= q; i++) {
	            int x = sc.nextInt(); // ��ѯ�����
	            int y = sc.nextInt(); // ��ѯ�Ĳ���
	            ans += bfs(x, y); // ����ÿ����ѯ�Ľ�����ۼӵ�ans
	        }

	        // ���ƽ�������������λС��
	        System.out.printf("%.2f\n", ans / q);
	    }

	    static int[][] d = new int[1010][1010];

	    // Floyd�㷨
	    public static void floyd() {
	        Scanner sc = new Scanner(System.in);
	        long n, m, Q;
	        n = sc.nextInt();
	        m = sc.nextInt();
	        Q = sc.nextInt();

	        // ��ʼ���������
	        for (int i = 1; i <= n; i++) {
	            for (int j = 1; j <= n; j++) {
	                if (i == j) {
	                    d[i][j] = 0;
	                } else {
	                    d[i][j] = (int) 1e9;
	                }
	            }
	        }

	        // ����ͼ
	        for (int i = 0; i < m; i++) {
	            int a, b;
	            a = sc.nextInt();
	            b = sc.nextInt();
	            d[a][b] = d[b][a] = 1;
	        }

	        // Floyd�㷨�������·��
	        for (int k = 1; k <= n; k++) {
	            for (int i = 1; i <= n; i++) {
	                for (int j = 1; j <= n; j++) {
	                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
	                }
	            }
	        }

	        // ����ÿ����ѯ
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

	        // ���ƽ�������������λС��
	        System.out.printf("%.2f", ans / Q);
	    }

}
