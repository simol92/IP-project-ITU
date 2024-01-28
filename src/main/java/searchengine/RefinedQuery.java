package searchengine;

import java.util.*;

/**
 * A class containing helper methods to handle the complex queries 
 * 
 * @author simol, gega, madbe, elsb
 * 
 */

public class RefinedQuery {
    
    /**
     * 
     * @param index
     * takes an inverted index object as parameter, which will be used to  
     * @param searchWords
     * @return
     */

    public List<HashSet<Page>> buildPages(InvertedIndex index, String[] searchWords){
        List<HashSet<Page>> list = new ArrayList<>();
        for (String string : searchWords) {
            
            list.add(index.lookUp(string));

          }        
        return list;
    }

    public HashSet<Page> getOverlap(HashSet<Page>  first, HashSet<Page>  second) {

        HashSet<Page> result = new HashSet<>();

        result.addAll(first);

        result.retainAll(second); 
        
        return result;
        
    }

    public HashSet<Page> getUniqueList(List<HashSet<Page>> temp) { 

        HashSet<Page> retainedSet = new HashSet<>();
        HashSet<Page> setToBeRetainedFrom = new HashSet<>();
        
        retainedSet = temp.get(0); 

        for (HashSet<Page> setofpages : temp) {

            setToBeRetainedFrom = setofpages;

            retainedSet = getOverlap(retainedSet, setToBeRetainedFrom);

        }

        return retainedSet;

    }


}
