package sorts;

public class SelectionSort {

	/*
	void selectionSort(int[] array, int startIndex)
	{
	    if ( startIndex >= array.length - 1 )
	        return;
	    int minIndex = startIndex;
	    for ( int index = startIndex + 1; index < array.length; index++ )
	    {
	        if (array[index] < array[minIndex] )
	            minIndex = index;
	    }
	    int temp = array[startIndex];
	    array[startIndex] = array[minIndex];
	    array[minIndex] = temp;
	    selectionSort(array, startIndex + 1);
	}
	
	*/
	
	public static int findMin(int[] array, int index)
	{
	    int min = index - 1;
	    if (index < array.length - 1)
	        min = findMin(array, index + 1);
	    if (array[index] < array[min])
	        min = index;
	    return min;
	}

	public static void selectionSort(int[] array)
	{
	    selectionSort(array, 0);
	}

	public static void selectionSort(int[] array, int left)
	{
	    if (left < array.length - 1)
	    {
	        swap(array, left, findMin(array, left));
	        selectionSort(array, left+1);
	    }
	}

	public static void swap(int[] array, int index1, int index2)
	{
	    int temp = array[index1];
	    array[index1] = array[index2];
	    array[index2] = temp;
	}
	
}
