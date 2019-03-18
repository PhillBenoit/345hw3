
public class QuickSort {
    
    static private int[][] a;
    
    private static int column_index;
    
    private QuickSort() {
        
    }
    
    static public int[][] sort(int[][] b) {
        a = b;
        permuteColumns();
        return a;
    }
    
    static private void permuteColumns() {
        for (column_index = 0; column_index < a[0].length; column_index++)
            qSort(0, a.length-1);
    }
    
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
    
    static private int findMiddle(int start, int end) {return (start + end) / 2;}
    
    /**
     * 
     * @param start
     * @param end
     * @param value
     * @return
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
        
        //return new midpoint location
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
