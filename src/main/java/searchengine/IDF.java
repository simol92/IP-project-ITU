package searchengine;

import java.util.*;

/**
 * Class to compute the Inverse Document Frequency
 * 
 * @author simol, gega, madbe, elsb
 * 
 */

public class IDF {

    /**
     * Computes the Inverse Document Frequency score: 
     * First we find the set of pages mapped to the specific searchword in our inverted index
     * 
     * Then we find the total amount of key-value pairs in the map database of our inverted index
     * and divides it with the size of the found pages matchting the searchword
     * 
     * afterwards we take the natural logarithm with base e of the result and returns a double value
     * that will be used in the TF-IDF class to compute the final score.
     * 
     * @param SearchWord
     * Specific searchword
     * 
     * @param index
     * Inverted index object to find database size and to lookup searchword
     * 
     * @return 
     * Returns an IDF double value 
     */

    public Double IDFscore (String SearchWord, InvertedIndex index) {
 
      double idf = 0.0;

      HashSet<Page> set = new HashSet<>();
      set.addAll(index.lookUp(SearchWord));
      
      double websitesWithWord = Double.valueOf(set.size());
      double totalnumberofwebsites = Double.valueOf(index.getMapSize());  

      idf= Math.log(totalnumberofwebsites / websitesWithWord);

      return idf;
      
    }
}
