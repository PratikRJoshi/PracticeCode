import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**Definition for an interval.*/
class Interval {
      int start;
      int end;
      Interval() { start = 0; end = 0; }
      Interval(int s, int e) { start = s; end = e; }
  }
 
class CompareIntervals implements Comparator<Interval> {
 
        public int compare(Interval one, Interval two){
            return one.start - two.start;
       
    };
}

public class MergeInterval {
    public List<Interval> merge(List<Interval> intervals) {
         List<Interval> mergedIntervals = new ArrayList<Interval>();
         
         if(intervals == null || intervals.size()==0)
            return mergedIntervals;
            
        Collections.sort(intervals, new CompareIntervals());  //this will sort the intervals according to the start values
        
        
        
       
        
        mergedIntervals.add(intervals.get(0));                  //add the first sorted interval
        
        for(int i=1, j=0;i<intervals.size();){
            //if the end of one interval is greater than the start of the next interval
            if(mergedIntervals.get(j).end>=intervals.get(i).start){
                //if the end of the first interval is less than the the end of the next, then the next end becomes the new merged end
                if(mergedIntervals.get(j).end>=intervals.get(i).end){
                    // mergedIntervals.add(intervals.get(i));
                    i++;
                }
                //else the first end bcomes the new merged end
                else{
                    //replace the current interval in the output list with the new interval
                    mergedIntervals.set(j, new Interval(mergedIntervals.get(j).start, intervals.get(i).end));
                    i++;
                }
            }
            else{
                mergedIntervals.add(intervals.get(i));
                j++;
                i++;
            }
        }
        return mergedIntervals;
    }
    
    public static void main(String args[]){
    	Interval one = new Interval(5, 9);
    	Interval two = new Interval(11, 19);
    	Interval three = new Interval(15, 39);
    	
    	List<Interval> inputList = new ArrayList<Interval>();
    	inputList.add(one);
    	inputList.add(two);
    	inputList.add(three);
    	
    	MergeInterval m = new MergeInterval();
    	
    	List<Interval> resultList = m.merge(inputList);
    	
    	for(int i=0;i<resultList.size();i++)
    		System.out.println("{"+resultList.get(i).start+", "+resultList.get(i).end+"}");
    }
}

//check this link for explanation on custom comparator - http://www.mkyong.com/java/java-object-sorting-example-comparable-and-comparator/