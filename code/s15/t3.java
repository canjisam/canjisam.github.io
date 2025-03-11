package s15;

import java.util.*;

public class t3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // 读取数组的长度 n
        int n = sc.nextInt();
        // 初始化一个长度为 n 的数组 a，初始值为 0
        int[] a = new int[n];
        
        // 循环读取输入的操作
        while (sc.hasNext()) {
            String s = sc.next();
            
            if (s.equals("add")) {
                // 如果操作是 "add"，则增加数组第一个元素的计数
                int x = sc.nextInt(); // 读取但不使用 x，因为题意中不需要这个参数
                a[0]++;
            } else if (s.equals("sync")) {
                // 如果操作是 "sync"，则更新指定位置的元素
                int x = sc.nextInt();
                // 更新 a[x] 为 a[x] + 1 和 a[0] 中的较小值
                a[x] = Math.min(a[x] + 1, a[0]);
            } else if (s.equals("query")) {
                // 如果操作是 "query"，则查询数组中最小的非零元素
                int ans = Integer.MAX_VALUE;
                for (int i = 1; i < n; i++) {
                    // 打印当前数组元素（调试用）
                    System.err.print(a[i] + " ");
                    // 更新答案为当前数组元素和当前答案中的较小值
                    ans = Math.min(a[i], ans);
                }
                // 输出查询结果
                System.out.println(ans);
            }
        }
        
        // 关闭Scanner对象以释放资源
        sc.close();
    }
}



