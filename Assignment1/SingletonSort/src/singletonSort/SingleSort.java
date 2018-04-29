import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class singleSort {
	/* Divide and conquer method, finds element appearing only once */ 
	/* in a sorted list containing pairs of equivalent integers */

	public static void singleSearch(Integer[] sortedArray, int lowIndex, int highIndex)
	{
		/* If the lowIndex is greater than the highIndex the array is invalid */

		if(lowIndex > highIndex) {
			System.out.println("The array is invalid");
			return;
		}
		if(lowIndex == highIndex) {
			System.out.println("The singleton is "+sortedArray[lowIndex]);
			return;
		}

		/* establish sortedArrayay middle index */
		int middleIndex = (lowIndex + highIndex)/2;
		/* If middleIndex is even and the follow element is its equal, */ 
		/* then singleton lies on right side, else it is on the left side */
		if(middleIndex % 2 == 0)
		{
			if(sortedArray[middleIndex].compareTo(sortedArray[middleIndex+1]) == 0)
				singleSearch(sortedArray, middleIndex+2, highIndex);
			else
				singleSearch(sortedArray, lowIndex, middleIndex);
		}
		/* If the middleIndex is odd and the previous element is its equal, */ 
		/* then singleton lies on the right side, else it is on the left side */
		else if(middleIndex % 2 == 1)
		{
			if(sortedArray[middleIndex].compareTo(sortedArray[middleIndex-1]) == 0)
				singleSearch(sortedArray, middleIndex-2, highIndex);
			else
				singleSearch(sortedArray, lowIndex, middleIndex-1);
		}
	}

	public static void main(String[] args)
	{
	
		ArrayList<Integer> list = new ArrayList<Integer>();
		Scanner s = null;
		
		try {
			s = new Scanner(new File(args[0]));
			s.useDelimiter(", ");
			
			while (s.hasNext()){
			    if(s.hasNextInt())
			        list.add(s.nextInt());
			    else
			        s.next();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Integer[] sortedArray = new Integer[list.size()];
		sortedArray = list.toArray(sortedArray);

		singleSearch(sortedArray, 0, sortedArray.length-1);
	}
}