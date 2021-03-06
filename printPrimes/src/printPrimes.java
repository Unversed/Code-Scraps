
public class printPrimes {
	public static void main(String args[]){
		int limit = 1000;
		for(int i = 2; i<= limit; i++) if(isPrime(i)) System.out.println(i);
	}
	private static boolean isPrime(long num) {
		if(num <= 1) return false;
		if(num <= 3) return true;
		if(num % 2 == 0 || num % 3 == 0) return false;
		for(int i = 5; i * i <= num; i += 6){
			if(num % i == 0 || num % (i+2) == 0) return false;
		}
		return true;
	}
}