package singletonSort;

import singletonSort.SingletonSort;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SortDriver {

	public static void main(String[] args)
	{
		int[] sortedArray = null;
		if (0 < args.length) {
			try {
				sortedArray = Files.lines(Paths.get(args[0]))
				        .mapToInt(Integer::parseInt).toArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
		   System.err.println("Invalid arguments count:" + args.length);
		   System.exit(0);
		}

		
		SingletonSort.singleSearch(sortedArray, 0, sortedArray.length-1);

	}
}
