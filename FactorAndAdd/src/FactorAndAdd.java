
public class FactorAndAdd {

	public static void main(String args[]){
		int count, term1, term2, limit, sum;
		term1 = 3;
		term2 = 5;
		limit = 1000;
		sum = 0;
		for(count = 0; count < limit; count++){
			if(count%term1 == 0 || count%term2 == 0){
				sum += count;
				System.out.println(sum);
			}
		}
		System.out.println(sum);
	}
}
