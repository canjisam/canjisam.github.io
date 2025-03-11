package s15;
import java.util.Scanner;
// 1:无需package
// 2: 类名必须Main, 不可修改

public class t5{
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		// TODO Auto-generated method stub
        int N = sc.nextInt();
        double p = sc.nextDouble();
        double q = 1 - p;
        double minn = Double.MAX_VALUE;
        int minK = Integer.MAX_VALUE;
        int k;
        for(k = N; k >= 1; k --) {
        	if(N % k == 0) {
        		double wugan = Math.pow(q, k);// 全部不感染
        		double e = (N / k ) * (wugan  * 1  + (1 + k) * (1 - wugan));
        		//对于每个可能的 K，计算每组的期望测试剂消耗。
        		//公式中的 (1−p) K表示该组中没有宠物被感染的概率，而 1−(1−p)K表示该组中至少有一只宠物被感染的概率。
        		//如果组中没有宠物被感染，只需要消耗 1 次试剂；
        		//如果组中有宠物被感染，则需要消耗 1+K 次试剂（1 次初始检测 + K 次重新检测）。 	
        		if(k == 1) e = N;
        		if(e <= minn) {
        			minK = Math.min(minK, k);
        			minn = e;
        		}
        	}
        }
        System.out.println(minK);
		sc.close();
	}

}