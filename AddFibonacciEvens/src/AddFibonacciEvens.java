
public class AddFibonacciEvens {
	public static void main(String args[]){
		long t1, t2, t3, limit, sum;
		t1 = 1;
		t2 = 1;
		t3 = 0;
		limit = 4000000;
		sum = 0;
		while(t3 <= limit){
			t3 = t1 + t2;
			t1 = t2;
			t2 = t3;
			if(t3 < limit && t3%2 == 0){
				System.out.println("t3: " + t3);
				sum += t3;
				System.out.println("sum:" + sum);
			}
		}
	}
}
