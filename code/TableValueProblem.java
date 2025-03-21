import java.util.*;

public class TableValueProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 数字个数
        int m = scanner.nextInt(); // 查询数目
        
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        
        // 对数组进行排序，便于后续处理
        Arrays.sort(numbers);
        
        // 处理每个查询
        for (int i = 0; i < m; i++) {
            int target = scanner.nextInt(); // 目标表格权值
            boolean solved = false;
            
            // 尝试不同的行列组合
            for (int rowCount = 1; rowCount <= n && !solved; rowCount++) {
                for (int colCount = 1; colCount <= n - rowCount && !solved; colCount++) {
                    // 尝试找到一种行列组合，使得表格权值为target
                    solved = tryToSolve(numbers, n, rowCount, colCount, target);
                    if (solved) {
                        break;
                    }
                }
            }
            
            if (!solved) {
                System.out.println("No");
            }
        }
        
        scanner.close();
    }
    
    // 尝试找到一种行列组合，使得表格权值为target
    private static boolean tryToSolve(int[] numbers, int n, int rowCount, int colCount, int target) {
        // 使用回溯法尝试所有可能的行列组合
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        List<Integer> unused = new ArrayList<>();
        
        return backtrack(numbers, 0, rowCount, colCount, rows, cols, unused, target);
    }
    
    // 回溯法尝试所有可能的行列组合
    private static boolean backtrack(int[] numbers, int index, int rowCount, int colCount, 
                                    List<Integer> rows, List<Integer> cols, List<Integer> unused, int target) {
        // 如果已经处理完所有数字
        if (index == numbers.length) {
            // 检查行列数是否符合要求
            if (rows.size() == rowCount && cols.size() == colCount) {
                // 计算表格权值
                int value = calculateTableValue(rows, cols);
                if (value == target) {
                    // 输出结果
                    printResult(rows, cols);
                    return true;
                }
            }
            return false;
        }
        
        // 尝试将当前数字放入行头
        if (rows.size() < rowCount) {
            rows.add(numbers[index]);
            if (backtrack(numbers, index + 1, rowCount, colCount, rows, cols, unused, target)) {
                return true;
            }
            rows.remove(rows.size() - 1);
        }
        
        // 尝试将当前数字放入列头
        if (cols.size() < colCount) {
            cols.add(numbers[index]);
            if (backtrack(numbers, index + 1, rowCount, colCount, rows, cols, unused, target)) {
                return true;
            }
            cols.remove(cols.size() - 1);
        }
        
        // 尝试放弃当前数字
        unused.add(numbers[index]);
        if (backtrack(numbers, index + 1, rowCount, colCount, rows, cols, unused, target)) {
            return true;
        }
        unused.remove(unused.size() - 1);
        
        return false;
    }
    
    // 计算表格权值
    private static int calculateTableValue(List<Integer> rows, List<Integer> cols) {
        int value = 0;
        for (int row : rows) {
            for (int col : cols) {
                value += row * col;
            }
        }
        return value;
    }
    
    // 输出结果
    private static void printResult(List<Integer> rows, List<Integer> cols) {
        System.out.println("Yes");
        System.out.println(rows.size() + " " + cols.size());
        
        // 输出行头
        for (int i = 0; i < rows.size(); i++) {
            System.out.print(rows.get(i));
            if (i < rows.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
        
        // 输出列头
        for (int i = 0; i < cols.size(); i++) {
            System.out.print(cols.get(i));
            if (i < cols.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}