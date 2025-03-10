package s15;

import java.util.*;

/**
 * 试题 D: 数据同步
 * 
 * 【题目描述】
 * 有n个数据节点，编号从0到n-1。节点0是主节点，其他节点是从节点。
 * 支持以下三种操作：
 * 1. add x：主节点的值增加1
 * 2. sync x：将编号为x的从节点的值增加1，但不超过主节点的值
 * 3. query：查询所有从节点中的最小值
 * 
 * 【输入格式】
 * 第一行一个整数n，表示节点数量
 * 接下来若干行，每行一个操作
 * 
 * 【输出格式】
 * 对于每个query操作，输出一个整数表示答案
 * 
 * 【解题思路】
 * 1. 使用数组记录每个节点的值
 * 2. 主节点（下标0）记录当前的最大值
 * 3. 从节点的值不能超过主节点的值
 * 4. query时遍历所有从节点找最小值
 */
public class t4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();  // 读取节点数量
        int[] nodes = new int[n];  // 存储各节点的值
        
        // 处理每个操作
        while (sc.hasNext()) {
            String op = sc.next();  // 读取操作类型
            
            if (op.equals("add")) {
                int x = sc.nextInt();
                nodes[0]++;  // 主节点值加1
            } else if (op.equals("sync")) {
                int x = sc.nextInt();
                // 从节点同步时，不能超过主节点的值
                nodes[x] = Math.min(nodes[x] + 1, nodes[0]);
            } else if (op.equals("query")) {
                // 查找所有从节点中的最小值
                int minVal = Integer.MAX_VALUE;
                for (int i = 1; i < n; i++) {
                    minVal = Math.min(nodes[i], minVal);
                }
                System.out.println(minVal);
            }
        }
        
        sc.close();
    }
}
