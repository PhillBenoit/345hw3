/**
 * Class that steps through unique pairs of factors for the given seed
 * 
 * @author Phillip Benoit
 *
 */
public class FactorStepper {
    
    /**
     * seed = starting number
     * root = square root of seed (all pairs past square root are anagrams)
     * factor = current factor
     * paired_value = number that factor multiplies with to produce seed
     * next = next factor
     */
    private int seed,root,factor,paired_value,next;
    
    /**
     * Constructor
     * 
     * @param seed number to get factors for
     */
    public FactorStepper(int seed) {
        
        //reject invalid numbers
        if (seed < 1) throw new IllegalArgumentException("This class can only "
                + "work with numbers greater than 0.");
        
        //set globals and initial values
        this.seed = seed;
        root = (int)Math.floor(Math.sqrt(seed));
        factor = next = 1;
        paired_value = seed;
        setNext();
    }
    
    /**
     * gets current factor
     * 
     * @return current factor
     */
    public int getFactor() {return factor;}
    
    /**
     * gets value paired to current factor
     * 
     * @return
     */
    public int getPairedValue() {return paired_value;}
    
    /**
     * Get next factor and increment data structure
     * 
     * @return next factor
     */
    public int getNext() {
        
        //prevent incrementing past the end of the list
        if (!hasNext()) throw new IndexOutOfBoundsException("Final factor of"
                + " " + seed + " already reached at " + factor);
        
        //set new values for the data structure
        factor = next;
        paired_value = seed/factor;
        setNext();
        
        //return the factor
        return factor;
    }
    
    /**
     * Test to see if there is another factor in the list
     * 
     * @return true if there is another value
     */
    public boolean hasNext() {
        return next != -1;
    }
    
    /**
     * set next value in the list
     */
    private void setNext() {
        
        //step through numbers until a factor is found or the end of the
        //range of numbers to test is reached
        do next++;
        while (next <= root && seed%next != 0);
        
        //set next to -1 if there are no more factors
        if (next > root) next = -1;
    }
}
