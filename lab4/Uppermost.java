package lab4;
import java.util.Arrays;
import java.util.Stack;

public class Uppermost {    
    /***
     * Given a list of slopes and intercepts where slopes[i] & intercepts[i] compose line i,
     * return a list of celing lines that form the upper bound. 
     * @param slopes
     * @param intercepts
     * @return upper bound lines
     */
    public static Stack<Integer> visibleLines(double[] slopes, double[] intercepts){
        Integer[] idx = new Integer[slopes.length]; // original indicies. if i is the i th smallest slope, idx[i] = original index of the line.
        Stack<Integer> ret = new Stack<>();

        // Preserve original indicies
        for (int i = 0; i < idx.length; i++){
            idx[i] = i;
        }

        // Sort - n log n
        Arrays.sort(idx, (a,b) -> {
            int cmp = Double.compare(slopes[a],slopes[b]);
            if(cmp != 0){
                return cmp;
            }
            return Double.compare(intercepts[a], intercepts[b]);
        });
        
        // for every x
        for(int i = 0; i < idx.length; i++){
            int l3 = idx[i];
            
            // for every stack
            while(ret.size() >= 2){
                int l2 = ret.pop();
                int l1 = ret.peek();
    
                double s1 = slopes[l1];
                double s2 = slopes[l2];
                double s3 = slopes[l3];
                
                double i1 = intercepts[l1];
                double i2 = intercepts[l2];
                double i3 = intercepts[l3];

                double x12 = getInterceptX(s1, i1, s2, i2);
                double x23 = getInterceptX(s2, i2, s3, i3);

                if(x12 < x23){
                    ret.push(l2);
                    break;
                }
            }
            ret.push(l3);
        }
        return ret;
    }

    public static double getInterceptX(double s1, double i1, double s2, double i2){
        double x = (i2-i1)/(s1-s2);
        return x;
    }
    public static void main(String[] args) {
        double[] slopes = {-1, 2, 3, 4};
        double[] intercepts = {0, 0, 0, -100};
        Stack<Integer> result = visibleLines(slopes, intercepts);
        System.out.println(result.toString());
    }
}
