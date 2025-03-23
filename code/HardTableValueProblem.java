import java.util.*;

public class HardTableValueProblem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // 数字个数
        int m = scanner.nextInt(); // 查询数目
        
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }
        
        // 预处理：计算所有可能的子集和
        Map<Integer, List<Set<Integer>>> subsetSums = precomputeSubsetSums(numbers, n);
        
        // 处理每个查询
        for (int i = 0; i < m; i++) {
            int target = scanner.nextInt(); // 目标表格权值
            boolean solved = false;
            
            // 特殊情况：目标值为0
            if (target == 0) {
                System.out.println("Yes");
                System.out.println("1 1");
                System.out.println("0");
                System.out.println("0");
                continue;
            }
            
            // 尝试找到解决方案
            solved = solveWithFactorization(numbers, n, target, subsetSums);
            
            if (!solved) {
                System.out.println("No");
            }
        }
        
        scanner.close();
    }
    
    // 预计算所有可能的子集和
    private static Map<Integer, List<Set<Integer>>> precomputeSubsetSums(int[] numbers, int n) {
        Map<Integer, List<Set<Integer>>> result = new HashMap<>();
        
        // 限制子集大小，避免内存溢出
        int maxSubsetSize = Math.min(n, 16); // 限制子集大小为16
        
        for (int size = 1; size <= maxSubsetSize; size++) {
            generateSubsets(numbers, 0, n, size, 0, new HashSet<>(), result);
        }
        
        return result;
    }
    
    // 生成所有大小为size的子集
    private static void generateSubsets(int[] numbers, int start, int n, int size, int sum, 
                                       Set<Integer> current, Map<Integer, List<Set<Integer>>> result) {
        if (current.size() == size) {
            // 将当前子集添加到对应和的列表中
            result.computeIfAbsent(sum, k -> new ArrayList<>()).add(new HashSet<>(current));
            return;
        }
        
        for (int i = start; i < n; i++) {
            current.add(i);
            generateSubsets(numbers, i + 1, n, size, sum + numbers[i], current, result);
            current.remove(i);
        }
    }
    
    // 使用因子分解的方法解决问题
    private static boolean solveWithFactorization(int[] numbers, int n, int target, 
                                                Map<Integer, List<Set<Integer>>> subsetSums) {
        // 1. 尝试单行多列或多行单列的情况
        for (int i = 0; i < n; i++) {
            int num = numbers[i];
            if (target % num == 0) {
                int complement = target / num;
                
                // 检查是否存在和为complement的子集
                if (subsetSums.containsKey(complement)) {
                    for (Set<Integer> subset : subsetSums.get(complement)) {
                        if (!subset.contains(i)) {
                            // 找到解决方案
                            List<Integer> rows = new ArrayList<>();
                            rows.add(num);
                            
                            List<Integer> cols = new ArrayList<>();
                            for (int idx : subset) {
                                cols.add(numbers[idx]);
                            }
                            
                            printResult(rows, cols);
                            return true;
                        }
                    }
                }
            }
        }
        
        // 2. 尝试因子分解的方法
        List<Integer> factors = factorize(target);
        for (int factor : factors) {
            int complement = target / factor;
            
            // 检查是否存在和为factor和complement的两个不相交子集
            if (subsetSums.containsKey(factor) && subsetSums.containsKey(complement)) {
                for (Set<Integer> rowIndices : subsetSums.get(factor)) {
                    for (Set<Integer> colIndices : subsetSums.get(complement)) {
                        // 检查两个子集是否不相交
                        if (Collections.disjoint(rowIndices, colIndices)) {
                            // 找到解决方案
                            List<Integer> rows = new ArrayList<>();
                            for (int idx : rowIndices) {
                                rows.add(numbers[idx]);
                            }
                            
                            List<Integer> cols = new ArrayList<>();
                            for (int idx : colIndices) {
                                cols.add(numbers[idx]);
                            }
                            
                            printResult(rows, cols);
                            return true;
                        }
                    }
                }
            }
        }
        
        // 3. 尝试更一般的情况
        return tryGeneralCase(numbers, n, target);
    }
    
    // 因子分解
    private static List<Integer> factorize(int n) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                factors.add(i);
                if (i != n / i) {
                    factors.add(n / i);
                }
            }
        }
        return factors;
    }
    
    // 尝试更一般的情况
    private static boolean tryGeneralCase(int[] numbers, int n, int target) {
        // 使用Meet in the Middle算法
        int half = n / 2;
        
        // 计算前半部分的所有可能的行和列组合
        Map<Integer, List<Pair>> firstHalf = new HashMap<>();
        generateRowColCombinations(numbers, 0, half, 0, 0, new ArrayList<>(), new ArrayList<>(), firstHalf);
        
        // 计算后半部分的所有可能的行和列组合
        Map<Integer, List<Pair>> secondHalf = new HashMap<>();
        generateRowColCombinations(numbers, half, n, 0, 0, new ArrayList<>(), new ArrayList<>(), secondHalf);
        
        // 尝试合并两部分的结果
        for (Map.Entry<Integer, List<Pair>> entry : firstHalf.entrySet()) {
            int value1 = entry.getKey();
            int value2 = target - value1;
            
            if (secondHalf.containsKey(value2)) {
                for (Pair pair1 : entry.getValue()) {
                    for (Pair pair2 : secondHalf.get(value2)) {
                        // 合并行和列
                        List<Integer> rows = new ArrayList<>(pair1.rows);
                        rows.addAll(pair2.rows);
                        
                        List<Integer> cols = new ArrayList<>(pair1.cols);
                        cols.addAll(pair2.cols);
                        
                        // 确保行和列不为空
                        if (!rows.isEmpty() && !cols.isEmpty()) {
                            printResult(rows, cols);
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    // 生成所有可能的行和列组合
    private static void generateRowColCombinations(int[] numbers, int start, int end, int rowSum, int colSum,
                                                 List<Integer> rows, List<Integer> cols,
                                                 Map<Integer, List<Pair>> result) {
        if (start == end) {
            int value = 0;
            for (int row : rows) {
                for (int col : cols) {
                    value += row * col;
                }
            }
            
            result.computeIfAbsent(value, k -> new ArrayList<>()).add(new Pair(new ArrayList<>(rows), new ArrayList<>(cols)));
            return;
        }
        
        // 将当前数字放入行
        rows.add(numbers[start]);
        generateRowColCombinations(numbers, start + 1, end, rowSum + numbers[start], colSum, rows, cols, result);
        rows.remove(rows.size() - 1);
        
        // 将当前数字放入列
        cols.add(numbers[start]);
        generateRowColCombinations(numbers, start + 1, end, rowSum, colSum + numbers[start], rows, cols, result);
        cols.remove(cols.size() - 1);
        
        // 不使用当前数字
        generateRowColCombinations(numbers, start + 1, end, rowSum, colSum, rows, cols, result);
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
    
    // 行列对的数据结构
    static class Pair {
        List<Integer> rows;
        List<Integer> cols;
        
        Pair(List<Integer> rows, List<Integer> cols) {
            this.rows = rows;
            this.cols = cols;
        }
    }
}