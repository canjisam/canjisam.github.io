package s15;

import java.util.*;

public class t2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        // 定义一个大数 n，这里设置为 1e7（即 10,000,000）
        int n = (int) 1e7;

        // 从 n 开始向下遍历，寻找符合条件的数
        for(int i = n; i > 0; i--) { 
            if(check(i)) { 
                System.out.println(i); 
                break; 
            }
        }
        
        // 关闭Scanner对象以释放资源
        scan.close();
    }

    /**
     * 将整数转换为数字列表
     * @param x 要转换的整数
     * @return 包含每个位数字的列表
     */
    private static ArrayList<Integer> chat2list(int x) {
        ArrayList<Integer> list = new ArrayList<>();
        while (x != 0) {
            // 取出当前最低位的数字并添加到列表中
            list.add(x % 10);
            x /= 10;
        }
        // 反转列表以恢复原始顺序
        Collections.reverse(list);
        return list;
    }

    /**
     * 检查是否存在某个连续子序列的和等于给定的数
     * @param x 要检查的整数
     * @return 如果存在符合条件的子序列则返回 true，否则返回 false
     */
    private static boolean check(int x) {
        // 将整数转换为数字列表
        ArrayList<Integer> list = new ArrayList<>(chat2list(x));
        int n = list.size();
        
        // 初始化 k 为 0，用于生成新的数字
        for (int k = 0;; k++) {
            // 如果 k 大于 n，则计算一个新的数字
            if (k > n) {
                int temp = 0;
                // 计算从 k-n-1 到 k-2 的子序列和
                for (int i = k - n - 1; i < k - 1; i++) {
                    temp += list.get(i);
                }
                
                // 如果子序列和等于 x，则返回 true
                if (temp == x) {
                    return true;
                } 
                // 如果子序列和大于 x，则退出循环
                else if (temp > x) {
                    break;
                }
                // 否则，将新计算的数字添加到列表中
                list.add(temp);
            }
        }
        
        // 如果没有找到符合条件的子序列，则返回 false
        return false;
    }
}



