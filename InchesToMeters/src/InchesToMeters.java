
public class InchesToMeters {

	public static void main(String args[]){
		double meters;
		int inches, counter = 0;
		
		for(inches = 1; inches <= 144; inches++) {
			meters = inches / 39.37;
			
			counter++;
			if(counter == 12){
				System.out.println(inches + " inches is " + meters + " meters.");
				counter = 0;
			}
		}
		
	}
}
