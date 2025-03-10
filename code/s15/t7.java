package s15;

import java.io.*;
import java.util.*;

public class t7 {

	static final int[][][][] p = { { // 基础L
			{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 } }, // 0
			{ { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 0 } }, // 顺时90
			{ { 0, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, // 180
			{ { 0, 2 }, { 1, 0 }, { 1, 1 }, { 1, 2 } } // 逆时90
			}, { // 基础I
					{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 } }, // 0
					{ { 0, 0 }, { 0, 1 }, { 0, 2 }, { 0, 3 } } // 180
			}, { // 基础T
					{ { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } }, // 0
					{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, -1 } }, // 顺时90
					{ { 0, 1 }, { 1, 0 }, { 1, 1 }, { 1, 2 } }, // 180
					{ { 0, 0 }, { 1, 0 }, { 2, 0 }, { 1, 1 } } // 逆时90
			}, { // 基础S
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
			for (int i = 1; i <= n && !ok; i++) {
				for (int j = 1; j <= n && !ok; j++) {
					if (g[i][j] == 0) {
						for (int k = 0; k < p.length && !ok; k++) {
							for (int l = 0; l < p[k].length && !ok; l++) {
								if (check(i, j, k, l)) {
									ok = true;
								}
							}
						}
					}
				}
			}
			if (ok) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
		sc.close();
	}

	public static boolean check(int x, int y, int k, int l) {
		for (int i = 0; i < 4; i++) {
			int nx = x + p[k][l][i][0];
			int ny = y + p[k][l][i][1];
			if (nx < 1 || nx > n || ny < 1 || ny > n || g[nx][ny] == 1) {
				return false;
			}
		}
		return true;
	}

	public static void fastreadversion() {
		FastReader fr = new FastReader();
		int t = fr.nextInt();
		while ((t--) > 0) {
			n = fr.nextInt();
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					g[i][j] = fr.nextInt();
				}
			}
			ok = false;
			for (int i = 1; i <= n && !ok; i++) {
				for (int j = 1; j <= n && !ok; j++) {
					if (g[i][j] == 0) {
						for (int k = 0; k < p.length && !ok; k++) {
							for (int l = 0; l < p[k].length && !ok; l++) {
								if (check(i, j, k, l)) {
									ok = true;
								}
							}
						}
					}
				}
			}
			if (ok) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
