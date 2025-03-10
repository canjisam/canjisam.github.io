package s15;

import java.util.*;
public class t4 {

	public static void main(String[] args) {
		  Scanner sc = new Scanner(System.in);
		        int n = sc.nextInt();
		        int[] a = new int [n];
		        while(sc.hasNext()) {
		            String s = sc.next();
		            
		            if(s.equals("add")) {
		                int x = sc.nextInt();
		                a[0]++;
		            }else if (s.equals("sync")) {
		                int x = sc.nextInt();
		                a[x] = Math.min(a[x]+1 ,a[0]);
		            }else if (s.equals("query")) {
		                int ans = Integer.MAX_VALUE;
		                for(int i = 1 ; i < n ; i ++) {
		                    System.err.print(a[i]+ " " );
		                    ans = Math.min(a[i],ans);
		                }
		                System.out.println(ans);
		            }
		        }
		        //在此输入您的代码...
		        sc.close();
		    }

}
