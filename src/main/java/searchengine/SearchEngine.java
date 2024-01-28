package searchengine;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A searchengine class to handle complex queries and ranks the result in descending order according to their 
 * TF-IDF rank
 * 
 * @author simol, gega, madbe, elsb
 */

public class SearchEngine {

    private List<Page> result;
    private InvertedIndex index;
    private List<HashSet<Page> > setOfPages;
    private RefinedQuery query;
    private TFIDF tfidfscore;
    private TF tfscore;
    private final boolean TrueForTF = false;

    public SearchEngine() {

      tfscore = new TF();
      tfidfscore = new TFIDF();
      query = new RefinedQuery();
      result = new ArrayList<>();
      index = new InvertedIndex();
      setOfPages = new ArrayList<>();

    }
    
    /**
     * 
     * @param websites - takes a hashSet of pages as input
     *
     *@Description converts a hashset of pages into a map with the inverted index methods
     * and maps words to a number of webpages
     * 
     */

    public void makeIndex(HashSet<Page> websites){

      index.makeInvertedIndex(websites);

    }

    /**
     * Clarification: 
     * To use either the TF or the TF-IDF ranking, we implemented a final boolean field called TrueForTF
     * which means if its true, it ranks the pages in descending order to the TF-ranking, or the TF-IDF ranking
     * if the TrueForTF field is false
     * 
     * this method returns either a TF or a TF-IDF score depending on wether the TrueForTF field is true or false.
     * we decided to use the TF-IDF score as a final score for the pages to get a more deeper score. 
     * Thats why the TrueForIDF field is false;
     * 
     * 
     * @param trueForTF
     * takes boolean thats either TRUE to get TF-scores or FALSE to get TF-IDFscores
     * @param searchTerm
     * a searchTerm as a string
     * @param page
     * a lookedUp page from the set of pages found by the searchword
     * @param idx
     * the invertedindex object to find the set of pages found by a specific searchword
     * @return
     * returns a double
     */

     public double getTForTFIDF(boolean trueForTF, String searchTerm, Page page, InvertedIndex idx) {
  
       if(trueForTF == true){
          return tfscore.TFScore(searchTerm, page);
        } else {
          return tfidfscore.TFIDFscore(searchTerm, page, idx);
        }
    }

    /**
     * First we put every entries from a map into an list. After that we sort each entry in the map / list by
     * comparing the entries value (TF/TF-IDF score) to sort them in descending order.
     * 
     * We then convert the list into a simple arraylist consisting of pages instead of map entries. We use the stream method
     * to get all the keys from the other list and keep the order of the TF/TF-IDF ranking in descending matter.
     * 
     * @param map 
     * takes a map and list as a parameter
     */

    public List<Page> getDescendingListFromMap(Map<Page , Double> map, List<Page> list){
      
      List<Map.Entry<Page, Double>> pagescores = new ArrayList<>(map.entrySet());
      Collections.sort(pagescores, (page1, page2) -> page2.getValue().compareTo(page1.getValue()));

      list = pagescores.stream().map(Map.Entry::getKey).collect(Collectors.toList());
      
      return list;
      
    }

    /**
     * This method calculates the TF/TF-IDF for every page belonging to the HashSet of Pages
     * thats found by the searchword!
     * If a searchword is split by "OR" and it has multiple words on each side,
     * then it loops through all the multiple words on every side split by "OR",
     * and computes the sum!
     * 
     * if searchweard is e.g "java asia OR borneo indonesia", then it finds the score of
     * "java" and adds its to "asia" on the left side of "OR"!
     * 
     * IF and only if, a page contains all the words, java, asia, borneo and indonesia
     * then it takes the maximum value of the two sums, which will be the final TF/TF-IDF score
     * of the page! 
     * 
     * @param map
     * @param searchWords
     * @param list
     * @param idx
     * 
     */

    public void computeTForTFIDF(boolean tfOrTfidf, Map<Page, Double> map, String [] searchWords, List<HashSet<Page>> list, InvertedIndex idx, RefinedQuery q){
      
      double initialscore = 0.0;
      double score = 0.0;

        for (Page page : q.getUniqueList(list)) {

          if(!map.containsKey(page)){

            for (String w : searchWords) {

              initialscore = getTForTFIDF(tfOrTfidf, w, page, idx);
              
              page.addFirstScore(initialscore);
              
            }

              map.put(page, page.getFirstScore());

              page.currentscore(page.getFirstScore());

              page.resetFirstScore();

          } else {

            for (String w : searchWords) {
              
              score = getTForTFIDF(tfOrTfidf, w, page, idx);
              
              page.addSecondScore(score);
              
            }

            if(page.getSecondScore() > page.getCurrentScore()) {

              map.replace(page, page.getFirstScore(), page.getSecondScore());
              
              page.currentscore(page.getSecondScore());

              page.resetSecondScore();
             
            } 
          
        } 
      }

    }

    /**
     * 
     * This is the main method of this class
     * 
     * This methods takes a searchterm and can handle the different queries in specific manners:
     * 
     * If the searchterm consist of a single word, 
     * then it only finds the pages related to that word and assigns a TF/TF-IDF score to each page.
     * 
     * If the searchterm consist of a two words split by whitespace, then it only finds pages containing both words, and
     * each page will be assigned a TF/TF-IDF that will be accumulated as a sum of boths word's TF/TF-IDF score on the page. 
     * in other words, if the search word are "asia java", then the page will be assigned a sum of "asia" and "java"s TF/TF-IDF score 
     * 
     * If the searchterm consist of two words split by "OR" for example:
     * "asia OR java"
     * then a page's TF/TF-IDF score will be the maximum score of those two.
     * 
     * If the searchterm consist of multiple words split by "OR" for example:
     * "asia java OR borneo indonesia"
     * then if a page has all the words (asia, java, borneo, indonesia), the TF/TF-IDF score of a page
     * will be the maximum sum of either "java asia" or "borneo indonesia". 
     * 
     * every searchterm will be mapped in a hashmap with pages and their respective accumulated TF-iDF
     * that map will then be converted to a list of pages, where order is sorted by their TF/TF-IDF score in descending order. 
     * 
     * @param searchTerm
     * Takes a searchterm as a string
     * @return
     * A list of pages containing the searchword and each page is 
     * assigned a TF-IDF score accumulated with the searchword / searchwords.
     * The list is ordered by the TF-IDF score of the page in descending order.
     */
    
    public List<Page> getSearchResults(String searchTerm) { 
      
      Map<Page, Double> siteRank = new HashMap<>();
      
      String[] subSearches = searchTerm.toLowerCase().split("%20or%20");
      
        for (String subsearch : subSearches) {
  
            String[] searchWords = subsearch.split("%20"); 
    
            setOfPages = query.buildPages(index, searchWords);
            
            computeTForTFIDF(TrueForTF, siteRank, searchWords,setOfPages, index, query);

        }
      
      result = getDescendingListFromMap(siteRank,result);

      return result;
          

    }
    
}