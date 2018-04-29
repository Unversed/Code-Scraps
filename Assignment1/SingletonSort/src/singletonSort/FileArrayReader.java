package singletonSort;


import java.io.*;
import java.util.Scanner;


public class FileArrayReader {

	public static int[] readLines(File filename) throws IOException {
		
		Scanner s = null;
		int i = 0;
		int[] array = new int[1000];
		
		try {
			s = new Scanner(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
		while(s.hasNextInt())
		    array[i++] = s.nextInt();
		return array;
		
	}
}



