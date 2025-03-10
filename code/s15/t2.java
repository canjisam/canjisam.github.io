package s15;

import java.util.*;

/**
 * 试题 B: 特殊数字序列
 * 
 * 【题目描述】
 * 找到不超过10^7的最大的数x，使得存在一个数字序列满足：
 * 1. 序列的第一个数是x的各位数字
 * 2. 从第二个数开始，每个数都是前面n个数的和，其中n是x的位数
 * 3. 这个序列中某一项的值恰好等于x
 * 
 * 【解题思路】
 * 1. 从10^7开始向下枚举每个数x
 * 2. 对于每个数x，先将其转换为数字序列
 * 3. 不断生成新的序列项（每项是前n个数的和）
 * 4. 如果某一项等于x，则找到答案
 * 5. 如果某一项大于x，则可以提前终止
 */
public class t2 {
    public static void main(String[] args) {
        int n = (int) 1e7;  // 设置上限为10^7
        
        // 从大到小枚举，找到第一个满足条件的数就是答案
        for(int i = n; i > 0; i--) { 
            if(check(i)) {
                System.out.println(i);
                break;
            }
        }
    }

    /**
     * 将数字转换为各位数字组成的列表
     * @param x 待转换的数字
     * @return 各位数字组成的列表
     */
    private static ArrayList<Integer> chat2list(int x) {
        ArrayList<Integer> list = new ArrayList<>();
        while (x != 0) {
            list.add(x % 10);
            x /= 10;
        }
        Collections.reverse(list);  // 反转列表使其符合从高位到低位的顺序
        return list;
    }

    /**
     * 检查一个数是否满足题目要求
     * @param x 待检查的数
     * @return 是否满足要求
     */
    private static boolean check(int x) {
        ArrayList<Integer> list = new ArrayList<>(chat2list(x));
        int n = list.size();  // 获取x的位数
        
        // 不断生成新的序列项
        for (int k = 0;; k++) {
            if (k > n) {
                int temp = 0;
                // 计算前n个数的和
                for (int i = k - n - 1; i < k - 1; i++) {
                    temp += list.get(i);
                }
                
                if (temp == x) {  // 找到答案
                    return true;
                } else if (temp > x) {  // 序列项超过x，可以提前终止
                    break;
                }
                list.add(temp);  // 将新的序列项添加到列表中
            }
        }
        
        return false;
    }
}

