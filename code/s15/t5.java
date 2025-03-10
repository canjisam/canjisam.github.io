package s15;
import java.util.*;
public class t5 {

	 public static void main(String[] args) {
	        // TODO Auto-generated method stub
	        Scanner sc = new Scanner(System.in);
	        // 在此输入您的代码...
	        int n = sc.nextInt();
	        for (int i = 0; i < n; i++) {
	            int a2 = sc.nextInt();
	            int a3 = sc.nextInt();
	            int a4 = sc.nextInt();
	            int b4 = sc.nextInt();
	            int b6 = sc.nextInt();

	            int ans = 0;
	            //所有座位坐满
	            while(b4>0 && a4>=1) {//4
	                b4--;a4--;ans+=4;             
	            }
	            while(b4>0 && a2>=2) {//2+2
	                b4--;a2-=2;ans+=4;
	            }
	            while(b6>0 && a4>=1 && a2>=1) {//4+2
	                b6--;a4--;a2--;ans+=6;
	            }
	            while(b6>0 && a3>=2) {//3+3
	                b6--;a3-=2;ans+=6;
	            }
	            while(b6>0 && a2>=3) {//2+2+2
	                b6--;a2-=3;ans+=6;
	            }
	            //座位坐不满
	            //空一座位
	            while(b6>0 && a3>=1 && a2>=1) {//3+2
	                b6--;a3--;a2--;ans+=5;
	            }
	            while(b4>0 && a3>=1) {//3
	                b4--;a3--;ans+=3;
	            }
	            //空俩座位
	            while(b6>0 && a4>=1) {//4
	                b6--;a4--;ans+=4;
	            }
	            while(b6>0 && a2>=2) {//2+2
	                b6--;a2-=2;ans+=4;
	            }
	            while(b4>0 && a2>=1) {//2
	                b4--;a2--;ans+=2;
	            }
	            //空仨座位
	            while(b6>0 && a3>=1) {//3
	                b6--;a3--;ans+=3;
	            }
	            //空四座位
	            while(b6>0 && a2>=1) {//2
	                b6--;a2--;ans+=2;
	            }
	            System.out.println(ans);
	        }

	        sc.close();
	    }


}
