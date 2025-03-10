package s15;
import java.util.Scanner;

/**
 * 试题 A: 数字计算
 * 
 * 【题目描述】
 * 计算表达式 202420242024/2 * 24 的值。
 * 
 * 【解题思路】
 * 1. 注意数字202420242024超出了int范围，需要使用long类型
 * 2. 为避免中间结果溢出，先除以2再乘以24
 * 3. 使用L后缀表示long类型字面量
 */
public class t1 {
    public static void main(String[] args) {
        // 由于这是一个纯计算题，不需要输入
        // 计算 202420242024/2 * 24
        // 使用L后缀声明long类型，避免溢出
        System.out.println(202420242024L/2 * 24);
    }
}
