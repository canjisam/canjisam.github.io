package s15;

import java.util.Scanner;

public class t1 {

    public static void main(String[] args) {
        // 创建一个Scanner对象以读取用户输入
        Scanner scan = new Scanner(System.in);
        
        /*
         * 计算表达式 202420242024L / 2 * 24 的值
         * 这里使用了长整型（Long）来存储大数值 202420242024L
         * 首先进行除法运算 202420242024L / 2，得到结果 101210121012L
         * 然后将结果乘以 24，得到最终结果 2429042864288L
         */
        System.out.println(202420242024L / 2 * 24);
        
        // 关闭Scanner对象以释放资源
        scan.close();
    }
}



