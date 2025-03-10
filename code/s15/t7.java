package s15;

import java.io.*;
import java.util.*;

public class t7 {

	static final int[][][][] p = { { // 对于L
			{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 } }, // 0
			{ { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 0 } }, // 正向90
			{ { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, // 180
			{ { 0, 2 }, { 1, 0 }, { 1, 1 }, { 1, 2 } } // 反向90
			}, { // 对于I
					{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } }, // 0
					{ { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } } // 180
			}, { // 对于T
					{ { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } }, // 0
					{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, -1 } }, // 正向90
					{ { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, // 180
					{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 } } // 反向90
			}, { // 对于S
					{ { 0, 0 }, { 0, 1 }, { -1, 1 }, { -1, 2 } }, // 0
					{ { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 } } // 90
			}

	};
	static int[][] g = new int[55][55];
	static int n;
	static boolean ok = false;

	public static void main(String[] args) {
		slowreadversion();// 89 ms 
		// fastreadversion();// 70 ms
	}

	public static void slowreadversion() {
		Scanner sc = new Scanner(System.in);
		// TODO Auto-generated method stub
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
			out.println(ok ? "Yes" : "No");
		}
		sc.close();
		out.flush();
	}

	public static boolean solve() {
		ok = false;
		dfs(0);
		return ok;
	}

	public static boolean judge(int i, int j, int x, int y) {
		for (int[] pos : p[i][j]) {
			int tx = x + pos[0], ty = y + pos[1];
			if (!(tx >= 1 && tx <= n && ty >= 1 && ty <= n) || g[tx][ty] != 1)
				return false;
		}
		return true;
	}

	static void fill(int i, int j, int x, int y, int target) {
		for (int[] pos : p[i][j]) {
			g[x + pos[0]][y + pos[1]] = target;
		}
	}

	static void dfs(int i) {
		// TODO Auto-generated method stub
		if (ok)
			return;
		if (i == 4)
			ok = true;
		else {
			for (int j = 0; j < p[i].length; j++) {
				if (ok)
					return;
				for (int x = 1; x <= n; x++) {
					for (int y = 1; y <= n; y++) {
						if (!judge(i, j, x, y))
							continue;
						fill(i, j, x, y, 2);
						dfs(i + 1);
						fill(i, j, x, y, 1);
					}
				}
			}
		}
	}

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
					// TODO: handle exception
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
