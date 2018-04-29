package singletonSort;

/* Divide and conquer method to find the element 
 * that appears only once in a sorted list containing 
 * pairs of equivalent integer
 * 
 * */

public class SingletonSort {

	public static void singleSearch(int[] sortedArray, int lowIndex, int highIndex)
	{
		/* If the lowIndex is greater than 
		 * the highIndex the array is invalid
		 */
		if(lowIndex > highIndex) {
			System.out.println("The array is invalid");
			return;
		} else  if(lowIndex == highIndex) {
			System.out.println("The singleton is "+sortedArray[lowIndex]);
			return;
		} else {

			// establish sortedArrayay middle index
			int middleIndex = (lowIndex + highIndex)/2;

			/* If the middleIndex is even 
			 * and the follow element is its equal, 
			 * then singleton lies on right side, 
			 * else it is on the left side
			 */
			if(middleIndex % 2 == 0)
			{
				if(sortedArray[middleIndex] == sortedArray[middleIndex+1])
					singleSearch(sortedArray, middleIndex+2, highIndex);
				else
					singleSearch(sortedArray, lowIndex, middleIndex);
			}
			/* If the middleIndex is odd 
			 * and the previous element is its equal, 
			 * then singleton lies on right side, 
			 * else it is on the left side
			 */
			else
			{
				if(sortedArray[middleIndex] == sortedArray[middleIndex-1])
					singleSearch(sortedArray, middleIndex-2, highIndex);
				else
					singleSearch(sortedArray, lowIndex, middleIndex-1);
			}
		}
	}
}
