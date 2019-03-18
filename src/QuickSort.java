/**
 * Quick Sort algorithm that processes columns independently
 * 
 * @author Phillip Benoit
 *
 */
public class QuickSort {
    
    /**
     * array to sort
     */
    static private int[][] a;
    
    /**
     * current column index
     */
    private static int column_index;
    
    /**
     * private constructor to prevent instantiation
     */
    private QuickSort() {
        
    }
    
    /**
     * Entry point for algorithm
     * 
     * @param b array to sort
     * @return sorted array
     */
    static public int[][] sort(int[][] b) {
        a = b;
        
        //run each column through the sort
        for (column_index = 0; column_index < a[0].length; column_index++)
            qSort(0, a.length-1);

        return a;
    }
    
    /**
     * Recursive sorting algorithm
     * 
     * @param start first element to examine
     * @param end last element to examine
     */
    static private void qSort(int start, int end) {
        
        //find middle element
        int middle_index = findMiddle(start, end);
        
        //move middle element to the end
        swap(middle_index, end, a[middle_index][column_index]);
        
        //Shuffle array using the middle element's value.
        //partition() moves elements before and after the middle element.
        //Out of bounds indices are passed to satisfy pre-increment loops.
        //New location of middle value is returned.
        middle_index = partition(start-1, end, a[end][column_index]);
        
        //move element to the new location
        swap(middle_index, end, a[middle_index][column_index]);
        
        //recursive call to all elements before the middle 
        if ( middle_index-start > 1) qSort(start, middle_index-1);

        //recursive call to all elements after the middle 
        if ( end-middle_index > 1) qSort(middle_index+1, end);
    }
    
    /**
     * Calculates midpoint between two numbers
     * 
     * @param start first number
     * @param end last number
     * @return midpoint (average)
     */
    static private int findMiddle(int start, int end) {return (start + end) / 2;}
    
    /**
     * Calculates index for middle value while swapping elements so that all
     * less come before and all greater come after.
     * 
     * @param start first element to examine
     * @param end last element to examine
     * @param value quantity to compare
     * @return index for middle value
     */
    static private int partition(int start, int end, int value) {
        do {

            //find larger element before middle by
            //stepping forward from the beginning
            while (a[++start][column_index] < value);
            
            //find smaller element after middle by
            //stepping backwards from the end
            //(also stops when start and end indices match)
            while (end > start && a[--end][column_index] > value);
            
            //swap them
            //(last swap will always be a swap to itself)
            swap(start, end, a[start][column_index]);
        
        //do this until the start and end indices match
        } while (start < end);
        
        //return new midpoint index
        return start;
    }
    
    /**
     * Swaps two elements
     * 
     * @param source index of source
     * @param destination index of destination
     * @param source_value value at source index
     */
    static private void swap(int source, int destination, int source_value) {
        a[source][column_index] = a[destination][column_index];
        a[destination][column_index] = source_value;
    }

}
