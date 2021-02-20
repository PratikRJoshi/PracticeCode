import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IteratorOfIterators {

    List<Iterator<Integer>> iteratorList;
    Iterator<Integer> currentIterator;
    int iteratorIndex, elementIndex;
    public IteratorOfIterators(List<Iterator<Integer>> iteratorList) {
        this.iteratorList = iteratorList;
        this.iteratorIndex = 0;
        this.elementIndex = 0;
        this.currentIterator = this.iteratorList.get(iteratorIndex);
    }

    public boolean hasNext(){
       if (iteratorList.get(iteratorIndex).hasNext()){
            return true;
        } else if(iteratorIndex + 1 < iteratorList.size()){
                iteratorIndex++;
                currentIterator = iteratorList.get(iteratorIndex);
                elementIndex = 0;
                return true;
            }
        return false;
    }

    public int next() {
        if (hasNext()){
            return this.currentIterator.next();
        }
        return  -1;
    }

    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1,2);
        List<Integer> l2 = Arrays.asList(5,6);
        List<Integer> l3 = Arrays.asList();

        List<Iterator<Integer>> iteratorList = Arrays.asList(l1.iterator(), l2.iterator(), l3.iterator());
        IteratorOfIterators it = new IteratorOfIterators(iteratorList);


        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        while (true){
//            executorService.execute(it::hasNext);
//        }

        executorService = Executors.newCachedThreadPool();

        while(it.hasNext()){
            System.out.println((it.next()));

        }
    }
}
