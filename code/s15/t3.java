package s15;
import java.util.*;

public class t3 {

	public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // 在此输入您的代码...
        int n = (int) 1e7;

        // System.err.println(chat2list(n));
        
        for(int i = n ; i > 0;i --) { 
            if(check(i)) { System.out.println(i); break; 
            }
        }
        
        scan.close();
    }

    private static ArrayList<Integer> chat2list(int x) {
        ArrayList<Integer> list = new ArrayList<>();
        while (x != 0) {
            list.add(x % 10);
            // System.err.print(x % 10 + " ");
            x /= 10;
        }
        Collections.reverse(list);
        return list;
    }

    private static boolean check(int x) {
        ArrayList<Integer> list = new ArrayList<>(chat2list(x));
        int n = list.size();
        
        for (int k = 0;; k++) {
            if (k > n) {
                int temp = 0;
                for (int i = k - n - 1; i < k - 1; i++) {
                    temp += list.get(i);
                }
                
                if (temp == x) {
                    return true;
                } else if (temp > x) {
                    break;
                }
                list.add(temp);
            }
        }
        
        return false;
    }


}
