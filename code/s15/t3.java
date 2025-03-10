package s15;
import java.util.*;

/**
 * 试题 C: 数字重排
 * 
 * 【题目描述】
 * 给定一个正整数n，将其各个数位重新排列，可以得到若干个不同的数。
 * 请问这些数中第k小的数是多少？
 * 
 * 【输入格式】
 * 输入两个整数n和k，用空格分隔。
 * 
 * 【输出格式】
 * 输出一个整数，表示重排后第k小的数。
 * 
 * 【解题思路】
 * 1. 将数字n转换为字符数组，便于排列
 * 2. 使用回溯法生成所有可能的排列
 * 3. 去除重复的排列（考虑数字中有重复数字的情况）
 * 4. 将所有排列排序后取第k个
 */
public class t3 {
    static List<String> result = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String n = scan.next();  // 输入数字n
        int k = scan.nextInt();  // 输入k
        
        // 将数字转换为字符数组并排序
        char[] nums = n.toCharArray();
        Arrays.sort(nums);
        
        // 生成所有排列
        backtrack(nums, new boolean[nums.length], new StringBuilder());
        
        // 对结果排序并去重
        result = new ArrayList<>(new TreeSet<>(result));
        
        // 输出第k小的数
        System.out.println(result.get(k - 1));
        scan.close();
    }
    
    /**
     * 使用回溯法生成所有可能的排列
     * @param nums 原始数字数组
     * @param used 记录每个位置是否使用过
     * @param current 当前正在构建的排列
     */
    private static void backtrack(char[] nums, boolean[] used, StringBuilder current) {
        if (current.length() == nums.length) {
            result.add(current.toString());
            return;
        }
        
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            
            // 跳过重复数字
            if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;
            
            used[i] = true;
            current.append(nums[i]);
            backtrack(nums, used, current);
            current.setLength(current.length() - 1);
            used[i] = false;
        }
    }
}
