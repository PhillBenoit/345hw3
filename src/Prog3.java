import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Column Sort Program
 * 
 * @author Phillip Benoit
 *
 */
public class Prog3 {
    
    /**
     * R = Rows, S = Columns, N = number of elements
     */
    static private int R,S,N;

    /**
     * Entry point
     * 
     * @param args command line arguments args[0] = data file to read
     */
	public static void main(String[] args) {
		
	    //Deprecated code for testing FactorStepper class
	    //--------------------------------------------
	    //FactorStepper f = new FactorStepper(18);
		//while (f.hasNext()) System.out.println(f.getNext() + " " + f.getPairedValue());
		//System.out.println("-------------------==============----------------");
        //--------------------------------------------
	    
	    //test for file argument
	    if (args.length == 0) {
	        System.err.println("please specify an input file");
	        System.exit(-1);
	    }
	    
	    //read data from the file
	    ArrayList<Integer> numbers = null;
	    try {
            numbers = readFile(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
	    
	    //test for data
	    if (numbers.isEmpty()) {
	        System.err.println("File was empty.");
	        System.exit(-1);
	    }
	    
        //Deprecated code for testing file input
        //--------------------------------------------
	    //for (Integer number:numbers) System.out.println(number);
	    //System.out.println("Numbers in list: " + numbers.size());
        //--------------------------------------------
	    
	    //build number array
	    int[][] numbers_array = makeArray(numbers);
	    numbers.clear();
	    
	    //process time information
        final double BILLION = 1000000000.0;
        long   startTime,
               elapsedTime;
        double seconds;
        System.gc();
        startTime = System.nanoTime();
	    
	    //Step 1
        numbers_array = QuickSort.sort(numbers_array);
        
	    //Step 2
	    numbers_array = step2Transpose(numbers_array);
	    
	    //Step 3
	    numbers_array = QuickSort.sort(numbers_array);

        //Step 4
        numbers_array = step4Transpose(numbers_array);
        
        //Step 5
        numbers_array = QuickSort.sort(numbers_array);
        
        //Step 6
        numbers_array = step6Insert(numbers_array);
        
        //Step 7
        numbers_array = QuickSort.sort(numbers_array);
        
        //Step 8
        numbers_array = step8Remove(numbers_array);
        
        //complete time information
        elapsedTime = System.nanoTime() - startTime;
        seconds =  elapsedTime / BILLION;
        
        //display n, r, s, seconds
        System.out.printf("n = %d\nr = %d\ns = %d\nElapsed time = %.3f seconds."
                + "\n", N,R,S,seconds);
        //display list and check for accuracy
        int previous = numbers_array[0][0];
        System.out.println(previous);
        for (int step = 1; step < N; step++) {
            int cursor = numbers_array[step%R][step/R];
            if (cursor < previous) {
                System.err.println("sort did not work");
                System.exit(-1);
            }
            System.out.println(cursor);
            previous = cursor;
        }
        
        /*
        //Deprecated code for testing the array as it's being processed
        //--------------------------------------------
        
	    for (int x = 0; x < numbers_array.length; x++) {
	        for (int y = 0; y < numbers_array[0].length; y++)
	            System.out.print(numbers_array[x][y] + " ");
	        System.out.println();
	    }
	    System.out.printf("Rows: %d   Cols: %d\n",numbers_array.length,
	            numbers_array[0].length);
	    
        //--------------------------------------------
        */
	}
	
	/**
	 * Step 8 removes the MIN and MAX numbers added in step 6
	 * and reduces the array to original size
	 * 
	 * @param array array to be processed
	 * @return processed array
	 */
	private static int[][] step8Remove(int[][] array) {
	    int[][] new_array = new int[R][S];
	    
	    //offset represents the number of MIN numbers to skip
	    int offset = R/2;
	    
	    //destination in regular sized array
	    for (int destination = 0; destination < N; destination++) {
	        
	        //source in expanded array
	        int source = destination+offset;
	        
	        //calculated position based on number of rows
	        new_array[destination%R][destination/R] = array[source%R][source/R];
	    }
	    return new_array;
	}
	
	/**
	 * Inserts an additional column of MIN and MAX values for final sorting
	 * 
	 * @param array original array
	 * @return processed array
	 */
	private static int[][] step6Insert(int[][] array) {
	    int[][] new_array = new int[R][S+1];
	    
	    //number of min numbers to insert
	    int mins_to_insert = R/2;
	    
	    //add min to beginning and max to end
	    for (int step = 0; step < mins_to_insert; step++) {
	        new_array[step][0] = Integer.MIN_VALUE;
	        new_array[R-step-1][S] = Integer.MAX_VALUE;
	    }
	    
	    //add additional max number if odd numbered rows
	    if (R%2==1) new_array[mins_to_insert][S] = Integer.MAX_VALUE;
	    
	    //source in original array
	    for (int source = 0; source < N; source++) {
	        
	        //destination in expanded array
	        int destination = source + mins_to_insert;
	        
	        //calculated position based on number of rows
	        new_array[destination%R][destination/R] = array[source%R][source/R];
	    }
	    return new_array;
	}
	
    /**
     * Write destination array row by row
     * Read source array column by column
     * 
     * @param array source
     * @return destination
     */
	private static int[][] step4Transpose(int[][] array) {
        int[][] transposed_array = new int[R][S];
        for (int index = 0; index < N; index++)
            transposed_array[index%R][index/R] = array[index/S][index%S];
        return transposed_array;
    }
    
	/**
	 * Write destination column by column
	 * Read source row by row
	 * 
	 * @param array source
	 * @return destination
	 */
	private static int[][] step2Transpose(int[][] array) {
	    int[][] transposed_array = new int[R][S];
	    for (int index = 0; index < N; index++)
	        transposed_array[index/S][index%S] = array[index%R][index/R];
	    return transposed_array;
	}
	
	
	//Deprecated sort method
	private static int[][] columnSort(int[][] array) {
	    
	    //go row by row
	    for (int row_cursor = 0; row_cursor < array.length; row_cursor++)
	        
	        //go column by column
	        for (int column_cursor = 0; column_cursor < array[0].length;
	                column_cursor++) {
	            
	            //get bubble value and index from cursor
	            int bubble_value = array[row_cursor][column_cursor];
	            int bubble_index = row_cursor;
	            
	            //move values in column down until bubble is in position
	            while (bubble_index > 0 &&
	                    array[bubble_index-1][column_cursor] > bubble_value)
	                array[bubble_index][column_cursor]
	                        = array[--bubble_index][column_cursor];
	            
	            //write bubble to appropriate location
	            array[bubble_index][column_cursor] = bubble_value;
	        }
	    return array;
	}
	
	
	/**
	 * Establish an array from a list using max valid factor to determine
	 * row and column count
	 * 
	 * @param list source list
	 * @return new array
	 */
	private static int[][] makeArray(ArrayList<Integer> list) {
	    
	    //N never changes
	    N = list.size();
	    
	    //R and S are stored every time a valid pair is found
	    R = N;
	    S = 1;
        
	    //steps through factor pairs
	    FactorStepper size_factor_stepper = new FactorStepper(N);
	    
	    //keeps track of the test to determine if R and S are the right size
	    boolean right_size = true;
	    
	    //go through each factor list until the end or S becomes too large
	    while (size_factor_stepper.hasNext() && right_size) {
	        
	        //get next factor pair
	        size_factor_stepper.getNext();
	        
	        //test for size
	        right_size = RSTest(size_factor_stepper.getPairedValue(),
                    size_factor_stepper.getFactor());
	        
	        //write valid values (right size and R%S==0)
	        if (right_size && (size_factor_stepper.getPairedValue()%
	                size_factor_stepper.getFactor()==0)) {
	            R = size_factor_stepper.getPairedValue();
	            S = size_factor_stepper.getFactor();
	        }
	    }
	    
	    //Assign new array and return it
	    int[][] return_array = new int[R][S];
	    for (int step = 0; step < N; step++)
	        return_array[step/S][step%S] = list.get(step);
	    return return_array;
	}
	
	/**
	 * Test to see if R and S are of the appropriate size
	 * R >= 2 * (S-1)^2
	 * 
	 * @param R rows
	 * @param S columns
	 * @return true if equation is valid
	 */
	private static boolean RSTest(int R,int S) {
	    return R >= 2 * Math.pow((S-1), 2);
	}
	
	/**
	 * Read a file and return a list of integers
	 * 
	 * @param file_name path/name of a file
	 * @return list of integers read from the file
	 * @throws IOException on disk read errors
	 */
	private static ArrayList<Integer> readFile(String file_name) throws IOException {
	    File f = new File(file_name);
        ArrayList<Integer> number_list = new ArrayList<>();
        
        //test if the file is valid
        if(f.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            String next_line;
            
            //read each line from the file
            while ((next_line = reader.readLine()) != null) {
                
                //try to parse an integer from a line of input
                try {number_list.add(Integer.parseInt(next_line));}
                
                //stop program on errors
                catch (NumberFormatException e) {
                    System.err.println("Problem parsing " + next_line + " from line"
                            + " " + (number_list.size()+1));
                    System.exit(-1);
                }
            }
            
            //close the reader
            reader.close();
        }
        
        //reject invalid files
        else {
            System.err.println("Could not open the file: " + file_name);
            System.exit(-1);
        }
        
        return number_list;
	}

}