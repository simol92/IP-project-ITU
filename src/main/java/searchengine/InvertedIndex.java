package searchengine;


import java.util.*;

/**
 * Our inverted index class
 * 
 * The index consist of a map with searchwords as keys and a set of pages containing the searchword
 * 
 * @author simol, gega, madbe, elsb
 */

public class InvertedIndex {

    private Map<String, HashSet<Page>> index;

    public InvertedIndex () {

        index = new HashMap <>();
        
    }

    /**
     * 
     * Takes a searchword and looks it up into the database of the maps key-value pairs.
     * If a match is found, then it returns a hashSet of pages with each page containing the searchword
     * 
     * @param searchterm - takes a parameter input string which will be used as a searchterm
     * @return - return they values of the key - the searchterm will be the key as a lookup into the values mapped to the key
     * if the key is not present in the map, it will return an empty map 
     */
    
    public HashSet<Page> lookUp(String searchterm) {

        if(index.containsKey(searchterm)){

            return index.get(searchterm);

        } else {

            HashSet<Page> empty = new HashSet<>();

            return empty;

        }

    }

    /**
     * 
     * @param websites - takes a parameter input hashSet of pages
     * 
     * @Description 
     * Makes an inverted index via a hashmap. 
     * 
     * Takes a set of pages as parameter
     * 
     * For each page in the set of pages. 
     *     
     * Gets the words from each page and put it as key in the map with the page as value. 
     * 
     * IF The word already exist, and another page contains the same word
     * - add the page to the set mapped to the existing key 
     */


    public void makeInvertedIndex(HashSet<Page> websites) {

        
        for (Page newPage : websites) {
            
            for (String word: newPage.getListOfWords()) {

                HashSet<Page> existingList = new HashSet<>();

                if(index.containsKey(word)){ 

                        index.get(word).add(newPage);

                } else {

                    existingList.add(newPage);    

                    index.put(word, existingList); 
                }

            }


        }
       
    }

    public int getMapSize(){

        return index.size();

    }



}
