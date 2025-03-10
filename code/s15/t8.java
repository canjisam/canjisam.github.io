package s15;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class t8 {
    static class bitversion {
        // ����ģ�������ڷ�ֹ�������
        static int moda = (int) 1e9 + 7;
        // �������
        static int n;
        // �洢���е������
        static Point[] arr;
        // ������״���飬�ֱ��Ӧ��ɫ0, 1, 2��y�������
        static int[] btr = new int[100005], btg = new int[100005], btb = new int[100005];
        // ���մ�
        static int ans = 0;
        // ���빤��
        static Scanner sc = new Scanner(System.in);

        // ������࣬����x, y�������ɫc
        static class Point {
            int a, b, c;

            Point(int a, int b, int c) {
                this.a = a;
                this.b = b;
                this.c = c;
            }
        }

        public static void solve() {
            // ��ȡ�������
            n = sc.nextInt();
            // ��ʼ��������
            arr = new Point[n];
            for (int i = 0; i < n; i++) {
                // ��ȡÿ�����x, y�������ɫ
                int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
                arr[i] = new Point(a, b, c);
            }
            // ����x, y, c�����
            Arrays.sort(arr, (o1, o2) -> {
                if (o1.a != o2.a) {
                    return o1.a - o2.a;
                } else if (o1.b != o2.b) {
                    return o1.b - o2.b;
                } else
                    return o1.c - o2.c;
            });
            // ����ÿ����
            for (int i = 0; i < n; i++) {
                // �����ǰ���x������ǰһ���㲻ͬ�������ǰһ����ͬx����ĵ����Ϣ����״������
                if (i > 0 && arr[i].a != arr[i - 1].a) {
                    int la = arr[i - 1].a;
                    for (int j = i - 1; j >= 0; j--) {
                        if (arr[j].a != la)
                            break;
                        // ������ɫ��y��������Ӧ����״����
                        if (arr[j].c == 0)
                            bta(btr, arr[j].b, 1);
                        else if (arr[j].c == 1)
                            bta(btg, arr[j].b, 1);
                        else
                            bta(btb, arr[j].b, 1);
                    }
                }
                int now = 0;
                // ���㵱ǰ�����Ч�������
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
                // �������մ𰸣���ȡģ
                ans = (ans + now) % moda;
            }
            // ������
            System.out.println(ans);
        }

        // �������λ��1��λ��
        static int lowbit(int a) {
            return a & (-a);
        }

        // ����״����bt��λ��a������ֵb
        static void bta(int[] bt, int a, int b) {
            for (int i = a; i <= 100000; i += lowbit(i)) {
                bt[i] += b;
            }
        }

        // ��ѯ��״����bt�д�1��a�ĺ�
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

            // �������еľ���
            for (int i = 0; i < n; i++) {
                int l = sc.nextInt(); // x����
                int w = sc.nextInt(); // y����
                int c = sc.nextInt(); // ��ɫ
                arr.add(new int[]{l, w, c});
            }

            // �Ծ��ν��������Ȱ�x����Ȼ��y����
            arr.sort((o1, o2) -> {
                if (o1[0] != o2[0]) return Integer.compare(o1[0], o2[0]);
                else return Integer.compare(o1[1], o2[1]);
            });

            // ����������ɫ��Ӧ����״����
            FenwickTree[] tree = new FenwickTree[3];
            for (int i = 0; i < 3; i++) tree[i] = new FenwickTree(maxn);

            // ö������õ�arr
            for (int[] a : arr) {
                int l = a[0];
                int w = a[1];
                int c = a[2];
                for (int i = 0; i < 3; i++) {
                    if (c == i) continue; // ��ͬ��ɫ���ų���
                    ans = (ans + tree[i].rangeSum(w + 1, maxn)) % mod; // �ۼӴ�
                }
                tree[c].add(w, 1); // ������״����
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

            // ������״���飬��λ��i������ֵval
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

            // ��ѯ��״�����д�l��r�ĺ�
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

            // ���½ӿ�
            void update(int idx, int val) {
                update(idx, val, 1, 0, n - 1);
            }

            // ��ѯ�ӿ�
            int query(int l, int r) {
                return query(l, r, 1, 0, n - 1);
            }
        }

        static int maxn = 100010;
        static int ans = 0;
        static int mod = (int) 1e9 + 7;

        public static void solve() {
            Scanner sc = new Scanner(System.in);
            // ��ȡ�������
            int n = sc.nextInt();
            List<int[]> arr = new ArrayList<>();

            // �������еľ���
            for (int i = 0; i < n; i++) {
                int l = sc.nextInt(); // x����
                int w = sc.nextInt(); // y����
                int c = sc.nextInt(); // ��ɫ
                arr.add(new int[]{l, w, c});
            }

            // �Ծ��ν��������Ȱ�x����Ȼ��y����
            arr.sort((o1, o2) -> {
                if (o1[0] != o2[0]) return Integer.compare(o1[0], o2[0]);
                else return Integer.compare(o1[1], o2[1]);
            });

            // ����������ɫ��Ӧ���߶���
            SegmentTree[] tree = new SegmentTree[3];
            for (int i = 0; i < 3; i++) tree[i] = new SegmentTree(maxn);

            // ö������õ�arr
            for (int[] a : arr) {
                int l = a[0]; // x����
                int w = a[1]; // y����
                int c = a[2]; // ��ɫ
                for (int i = 0; i < 3; i++) {
                    if (c == i) continue; // ��ͬ��ɫ���ų���
                    // �ۼӴ𰸣����㵱ǰ�����Ч�������
                    ans = (ans + tree[i].query(w + 1, maxn - 1)) % mod;
                }
                // �����߶���
                tree[c].update(w, 1);
            }
            // ������
            System.out.println(ans);
        }
    }
    public static void main(String args[]) {
//        fwtversion.slove();
//        bitversion.solve();
    	stversion.solve();
    }
}



