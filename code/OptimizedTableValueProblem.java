import java.util.*;

public class OptimizedTableValueProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 数字个数
        int m = scanner.nextInt(); // 查询数目
        
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        
        // 处理每个查询
        for (int i = 0; i < m; i++) {
            int target = scanner.nextInt(); // 目标表格权值
            boolean solved = false;
            
            // 优化：先尝试单行单列的情况（特殊情况处理）
            if (target == 0) {
                // 如果目标值为0，可以选择空行或空列
                System.out.println("Yes");
                System.out.println("1 1");
                System.out.println("0");
                System.out.println("0");
                solved = true;
                continue;
            }
            
            // 尝试使用更高效的方法
            solved = solveEfficiently(numbers, n, target);
            
            if (!solved) {
                System.out.println("No");
            }
        }
        
        scanner.close();
    }
    
    // 更高效的解决方法
    private static boolean solveEfficiently(int[] numbers, int n, int target) {
        // 1. 尝试单行多列的情况
        for (int i = 0; i < n; i++) {
            int row = numbers[i];
            if (target % row == 0) {
                int colSum = target / row;
                List<Integer> cols = findSubsetWithSum(numbers, n, i, colSum);
                if (cols != null) {
                    printResult(Collections.singletonList(row), cols);
                    return true;
                }
            }
        }
        
        // 2. 尝试多行单列的情况
        for (int i = 0; i < n; i++) {
            int col = numbers[i];
            if (target % col == 0) {
                int rowSum = target / col;
                List<Integer> rows = findSubsetWithSum(numbers, n, i, rowSum);
                if (rows != null) {
                    printResult(rows, Collections.singletonList(col));
                    return true;
                }
            }
        }
        
        // 3. 尝试两行多列的情况
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int row1 = numbers[i];
                int row2 = numbers[j];
                
                // 检查是否存在一组列，使得 row1*sum(cols) + row2*sum(cols) = target
                // 即 (row1 + row2)*sum(cols) = target
                if (target % (row1 + row2) == 0) {
                    int colSum = target / (row1 + row2);
                    Set<Integer> exclude = new HashSet<>();
                    exclude.add(i);
                    exclude.add(j);
                    List<Integer> cols = findSubsetWithSumExcluding(numbers, n, exclude, colSum);
                    if (cols != null) {
                        List<Integer> rows = new ArrayList<>();
                        rows.add(row1);
                        rows.add(row2);
                        printResult(rows, cols);
                        return true;
                    }
                }
            }
        }
        
        // 4. 尝试多行两列的情况
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int col1 = numbers[i];
                int col2 = numbers[j];
                
                // 检查是否存在一组行，使得 sum(rows)*col1 + sum(rows)*col2 = target
                // 即 sum(rows)*(col1 + col2) = target
                if (target % (col1 + col2) == 0) {
                    int rowSum = target / (col1 + col2);
                    Set<Integer> exclude = new HashSet<>();
                    exclude.add(i);
                    exclude.add(j);
                    List<Integer> rows = findSubsetWithSumExcluding(numbers, n, exclude, rowSum);
                    if (rows != null) {
                        List<Integer> cols = new ArrayList<>();
                        cols.add(col1);
                        cols.add(col2);
                        printResult(rows, cols);
                        return true;
                    }
                }
            }
        }
        
        // 5. 尝试更一般的情况（使用动态规划查找可能的行和列组合）
        return tryGeneralCase(numbers, n, target);
    }
    
    // 查找数组中和为sum的子集，排除索引为excludeIndex的元素
    private static List<Integer> findSubsetWithSum(int[] numbers, int n, int excludeIndex, int sum) {
        Set<Integer> exclude = new HashSet<>();
        exclude.add(excludeIndex);
        return findSubsetWithSumExcluding(numbers, n, exclude, sum);
    }
    
    // 查找数组中和为sum的子集，排除指定索引集合中的元素
    private static List<Integer> findSubsetWithSumExcluding(int[] numbers, int n, Set<Integer> excludeIndices, int sum) {
        // 使用动态规划查找子集和
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        
        Map<Integer, Integer> selected = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            if (excludeIndices.contains(i)) continue;
            
            int num = numbers[i];
            for (int j = sum; j >= num; j--) {
                if (dp[j - num]) {
                    dp[j] = true;
                    selected.put(j, i);
                }
            }
        }
        
        if (!dp[sum]) return null;
        
        // 回溯找出构成和为sum的元素
        List<Integer> result = new ArrayList<>();
        int remaining = sum;
        
        while (remaining > 0) {
            int idx = selected.get(remaining);
            result.add(numbers[idx]);
            remaining -= numbers[idx];
        }
        
        return result;
    }
    
    // 尝试更一般的情况
    private static boolean tryGeneralCase(int[] numbers, int n, int target) {
        // 生成所有可能的行组合（使用位运算）
        for (int rowMask = 1; rowMask < (1 << n); rowMask++) {
            List<Integer> rows = new ArrayList<>();
            Set<Integer> rowIndices = new HashSet<>();
            
            for (int i = 0; i < n; i++) {
                if ((rowMask & (1 << i)) != 0) {
                    rows.add(numbers[i]);
                    rowIndices.add(i);
                }
            }
            
            // 计算行和
            int rowSum = 0;
            for (int row : rows) {
                rowSum += row;
            }
            
            // 检查是否存在一组列，使得 rowSum * colSum = target
            if (target % rowSum == 0) {
                int colSum = target / rowSum;
                List<Integer> cols = findSubsetWithSumExcluding(numbers, n, rowIndices, colSum);
                if (cols != null) {
                    printResult(rows, cols);
                    return true;
                }
            }
            
            // 限制搜索空间，避免超时
            if (rowMask > (1 << 10)) break;
        }
        
        return false;
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