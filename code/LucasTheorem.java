import java.util.*;

public class LucasTheorem {
    // 快速幂计算 (a^b) % mod
    public static long fastPow(long a, long b, long mod) {
        long result = 1;
        a %= mod;
        while (b > 0) {
            if ((b & 1) == 1) {
                result = (result * a) % mod;
            }
            a = (a * a) % mod;
            b >>= 1;
        }
        return result;
    }
    
    // 使用费马小定理计算乘法逆元
    public static long modInverse(long a, long mod) {
        return fastPow(a, mod - 2, mod);
    }
    
    // 计算组合数 C(n,m) % mod，其中mod为质数
    public static long combination(long n, long m, long mod) {
        if (m > n) return 0;
        if (m > n - m) m = n - m;
        
        long numerator = 1;
        long denominator = 1;
        
        for (int i = 0; i < m; i++) {
            numerator = (numerator * (n - i)) % mod;
            denominator = (denominator * (i + 1)) % mod;
        }
        
        return (numerator * modInverse(denominator, mod)) % mod;
    }
    
    // Lucas定理实现
    public static long lucas(long n, long m, long p) {
        if (m == 0) return 1;
        return (combination(n % p, m % p, p) * 
                lucas(n / p, m / p, p)) % p;
    }
    
    // 扩展Lucas定理（处理质数幂）
    public static long extendedLucas(long n, long m, long p, long k) {
        // 计算p^k
        long pk = 1;
        for (int i = 0; i < k; i++) {
            pk *= p;
        }
        
        // 如果n和m足够小，直接计算
        if (n < pk && m < pk) {
            return combination(n, m, pk);
        }
        
        // 否则使用CRT和扩展Lucas定理
        // 这里简化处理，实际竞赛中需要完整实现
        return -1; // 表示需要更复杂的实现
    }
    
    // 示例：计算大组合数取模
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入n, m和p (p为质数):");
        long n = scanner.nextLong();
        long m = scanner.nextLong();
        long p = scanner.nextLong();
        
        System.out.println("C(" + n + ", " + m + ") % " + p + " = " + lucas(n, m, p));
        scanner.close();
    }
}