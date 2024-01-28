package searchengine;

import java.util.*;

/**
 * A Term Frequency class to calculate the frequency 
 * of a specific word or the impact of a word inside a page 
 * 
 * @author simol, gega, madbe, elsb
 * 
 */

public class TF {

    /**
     * 
     * A method to calculate the TF score
     * 
     * @param SearchWord
     * Specific searchword that will be used as the term 
     * @param page
     * Takes a page from a set of pages found by the lookup method inside the searchengine class
     * @return
     * returns the TF score as a double thats accumulated by: 
     * 
     *  the frecuency of the word in the page divided by the totalt number of words in the page
     * 
     *  ( tf = frequency / totalnumberofwords )
     * 
     */

    public Double TFScore(String SearchWord, Page page) {
        
        double total = Double.valueOf(page.totalAmountOfWords()); // total number of words in page
        double frequency = 0.0; // total amount of times the word occurs
        double tf = 0.0; // total amount of times the word occurs / total number of words in page
        
         if(page.hasWord(SearchWord)){
            
            frequency = Double.valueOf(Collections.frequency(page.getListOfWords(), SearchWord));
           
        }

        tf = frequency / total;

        return tf;
        
    }

}
