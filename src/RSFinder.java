public class RSFinder {
    
    private int R,S;
    
    public RSFinder(int seed) {
        if (!searchRS(seed)) R = S = 0;
    }
    
    private boolean searchRS(int seed) {
        int root = (int)Math.floor(Math.sqrt(seed));
        for (int step = 2; step < root; step++)
            if (seed%step == 0) {
                
            }
        
        
        return false;
    }
    
    public int getR() {return R;}
    
    public int getS() {return S;}
    
    public boolean isPrime() {return (R == 0);}

}
