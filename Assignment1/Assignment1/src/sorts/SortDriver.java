package sorts;

import java.util.Random;

public class SortDriver {

	public static void main(String[] args)
	{
		int[] list;

		System.out.println("Size \tRuntime");
		for (int size = 10000; size <= 20000; size+=20){

			list = new int[size];	
	    	getRandom(list);
	    	
			long startTime = System.nanoTime();
			MergeSort.mergeSort(list);
			long endTime   = System.nanoTime();
			long totalTime = endTime - startTime;

			System.out.println(size + ", \t" + totalTime);
		}

		System.out.println("Size \tRuntime");
		for (int size = 10000; size <= 20000; size+=20){

			list = new int[size];
	    	getRandom(list);
			long startTime = System.nanoTime();
			SelectionSort.selectionSort(list);
			long endTime   = System.nanoTime();
			long totalTime = endTime - startTime;
			System.out.println(size + ", \t" + totalTime);
		}

	}

	public static void getRandom(int[] array) {
		Random r = new Random();
		for (int a = 0; a < array.length; a++)
			array[a] = r.nextInt(20000)-10000;
	}

}

