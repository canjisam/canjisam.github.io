package s15;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class t8 {
    static class bitversion {
        // 定义模数，用于防止整数溢出
        static int moda = (int) 1e9 + 7;
        // 点的数量
        static int n;
        // 存储所有点的数组
        static Point[] arr;
        // 三个树状数组，分别对应颜色0, 1, 2的y坐标计数
        static int[] btr = new int[100005], btg = new int[100005], btb = new int[100005];
        // 最终答案
        static int ans = 0;
        // 输入工具
        static Scanner sc = new Scanner(System.in);

        // 定义点类，包含x, y坐标和颜色c
        static class Point {
            int a, b, c;

            Point(int a, int b, int c) {
                this.a = a;
                this.b = b;
                this.c = c;
            }
        }

        public static void solve() {
            // 读取点的数量
            n = sc.nextInt();
            // 初始化点数组
            arr = new Point[n];
            for (int i = 0; i < n; i++) {
                // 读取每个点的x, y坐标和颜色
                int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
                arr[i] = new Point(a, b, c);
            }
            // 按照x, y, c排序点
            Arrays.sort(arr, (o1, o2) -> {
                if (o1.a != o2.a) {
                    return o1.a - o2.a;
                } else if (o1.b != o2.b) {
                    return o1.b - o2.b;
                } else
                    return o1.c - o2.c;
            });
            // 遍历每个点
            for (int i = 0; i < n; i++) {
                // 如果当前点的x坐标与前一个点不同，则更新前一个相同x坐标的点的信息到树状数组中
                if (i > 0 && arr[i].a != arr[i - 1].a) {
                    int la = arr[i - 1].a;
                    for (int j = i - 1; j >= 0; j--) {
                        if (arr[j].a != la)
                            break;
                        // 根据颜色将y坐标插入对应的树状数组
                        if (arr[j].c == 0)
                            bta(btr, arr[j].b, 1);
                        else if (arr[j].c == 1)
                            bta(btg, arr[j].b, 1);
                        else
                            bta(btb, arr[j].b, 1);
                    }
                }
                int now = 0;
                // 计算当前点的有效配对数量
                if (arr[i].c == 0) {
                    now += btq(btg, 100000) - btq(btg, arr[i].b);
                    now += btq(btb, 100000) - btq(btb, arr[i].b);
                } else if (arr[i].c == 1) {
                    now += btq(btr, 100000) - btq(btr, arr[i].b);
                    now += btq(btb, 100000) - btq(btb, arr[i].b);
                } else {
                    now += btq(btr, 100000) - btq(btr, arr[i].b);
                    now += btq(btg, 100000) - btq(btg, arr[i].b);
                }
                // 更新最终答案，并取模
                ans = (ans + now) % moda;
            }
            // 输出结果
            System.out.println(ans);
        }

        // 返回最低位的1的位置
        static int lowbit(int a) {
            return a & (-a);
        }

        // 在树状数组bt中位置a处增加值b
        static void bta(int[] bt, int a, int b) {
            for (int i = a; i <= 100000; i += lowbit(i)) {
                bt[i] += b;
            }
        }

        // 查询树状数组bt中从1到a的和
        static int btq(int[] bt, int a) {
            int ret = 0;
            for (int i = a; i >= 1; i -= lowbit(i)) {
                ret += bt[i];
            }
            return ret;
        }
    }

    static class fwtversion {
        static class FenwickTree {
            int n;
            int[] tree;

            public FenwickTree(int n) {
                this.n = n;
                tree = new int[n + 1];
            }

            int lowbit(int i) {
                return i & -i;
            }

            void add(int i, int val) {
                for (; i <= n; i += lowbit(i)) {
                    tree[i] += val;
                }
            }

            int preSum(int i) {
                int ret = 0;
                for (; i > 0; i -= lowbit(i)) {
                    ret += tree[i];
                }
                return ret;
            }

            int rangeSum(int l, int r) {
                return preSum(r) - preSum(l - 1);
            }
        }

        static int maxn = 100010;
        static int ans = 0;
        static int mod = (int) 1e9 + 7;

        public static void slove() {
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            List<int[]> arr = new ArrayList<>();

            // 读入所有的矩形
            for (int i = 0; i < n; i++) {
                int l = sc.nextInt(); // x坐标
                int w = sc.nextInt(); // y坐标
                int c = sc.nextInt(); // 颜色
                arr.add(new int[]{l, w, c});
            }

            // 对矩形进行排序，先按x升序，然后按y升序
            arr.sort((o1, o2) -> {
                if (o1[0] != o2[0]) return Integer.compare(o1[0], o2[0]);
                else return Integer.compare(o1[1], o2[1]);
            });

            // 建立三种颜色对应的树状数组
            FenwickTree[] tree = new FenwickTree[3];
            for (int i = 0; i < 3; i++) tree[i] = new FenwickTree(maxn);

            // 枚举排序好的arr
            for (int[] a : arr) {
                int l = a[0];
                int w = a[1];
                int c = a[2];
                for (int i = 0; i < 3; i++) {
                    if (c == i) continue; // 相同颜色被排除掉
                    ans = (ans + tree[i].rangeSum(w + 1, maxn)) % mod; // 累加答案
                }
                tree[c].add(w, 1); // 更新树状数组
            }
            System.out.println(ans);
        }
    }
    static class stversion{
    	static class SegmentTree {
            int n;
            int[] tree;

            public SegmentTree(int n) {
                this.n = n;
                tree = new int[4 * n];
            }

            // 更新树状数组，在位置i处增加值val
            void update(int idx, int val, int node, int start, int end) {
                if (start == end) {
                    tree[node] += val;
                } else {
                    int mid = (start + end) / 2;
                    if (idx <= mid) {
                        update(idx, val, 2 * node, start, mid);
                    } else {
                        update(idx, val, 2 * node + 1, mid + 1, end);
                    }
                    tree[node] = tree[2 * node] + tree[2 * node + 1];
                }
            }

            // 查询树状数组中从l到r的和
            int query(int l, int r, int node, int start, int end) {
                if (r < start || end < l) {
                    return 0;
                }
                if (l <= start && end <= r) {
                    return tree[node];
                }
                int mid = (start + end) / 2;
                int leftQuery = query(l, r, 2 * node, start, mid);
                int rightQuery = query(l, r, 2 * node + 1, mid + 1, end);
                return leftQuery + rightQuery;
            }

            // 更新接口
            void update(int idx, int val) {
                update(idx, val, 1, 0, n - 1);
            }

            // 查询接口
            int query(int l, int r) {
                return query(l, r, 1, 0, n - 1);
            }
        }

        static int maxn = 100010;
        static int ans = 0;
        static int mod = (int) 1e9 + 7;

        public static void solve() {
            Scanner sc = new Scanner(System.in);
            // 读取点的数量
            int n = sc.nextInt();
            List<int[]> arr = new ArrayList<>();

            // 读入所有的矩形
            for (int i = 0; i < n; i++) {
                int l = sc.nextInt(); // x坐标
                int w = sc.nextInt(); // y坐标
                int c = sc.nextInt(); // 颜色
                arr.add(new int[]{l, w, c});
            }

            // 对矩形进行排序，先按x升序，然后按y升序
            arr.sort((o1, o2) -> {
                if (o1[0] != o2[0]) return Integer.compare(o1[0], o2[0]);
                else return Integer.compare(o1[1], o2[1]);
            });

            // 建立三种颜色对应的线段树
            SegmentTree[] tree = new SegmentTree[3];
            for (int i = 0; i < 3; i++) tree[i] = new SegmentTree(maxn);

            // 枚举排序好的arr
            for (int[] a : arr) {
                int l = a[0]; // x坐标
                int w = a[1]; // y坐标
                int c = a[2]; // 颜色
                for (int i = 0; i < 3; i++) {
                    if (c == i) continue; // 相同颜色被排除掉
                    // 累加答案，计算当前点的有效配对数量
                    ans = (ans + tree[i].query(w + 1, maxn - 1)) % mod;
                }
                // 更新线段树
                tree[c].update(w, 1);
            }
            // 输出结果
            System.out.println(ans);
        }
    }
    public static void main(String args[]) {
//        fwtversion.slove();
//        bitversion.solve();
    	stversion.solve();
    }
}



