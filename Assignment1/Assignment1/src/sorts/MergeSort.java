package sorts;


public class MergeSort
{

    public static int[] mergeSort(int[] list)
    {
        //If list is empty; no need to do anything
        if (list.length <= 1)
            return list;
         
        //Split the array in half
        int[] first = new int[list.length / 2];
        System.arraycopy(list, 0, first, 0, first.length);
        int[] second = new int[list.length - first.length];
        System.arraycopy(list, first.length, second, 0, second.length);
         
        //Sort each half recursively
        mergeSort(first);
        mergeSort(second);
         
        //Merge both halves together
        merge(first, second, list);
        return list;
    }
     
    private static void merge(int[] first, int[] second, int[] result)
    {

        //Index Position in first, second, and merged array
        int i=0, j=0, k=0;
         
        //Compare elements at i and j, and move smaller element to k
        while (i < first.length && j < second.length)
        	result[k++] = ( first[i] < second[j] ) ?  first[i++] : second[j++];
        //copy remaining elements from both halves - each half will have already sorted elements
        System.arraycopy(first, i, result, k, first.length - i);
        System.arraycopy(second, j, result, k, second.length - j);
    }
}
