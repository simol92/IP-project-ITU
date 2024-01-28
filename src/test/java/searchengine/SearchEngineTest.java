package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

/*
 * 
 * searchengine test class
 * 
 */

public class SearchEngineTest {
    
    private static List<Page> result;
    private static InvertedIndex index;
    private static List<HashSet<Page> > setOfPages;
    private static HashSet<Page> set;
    private static SearchEngine se;
    private static WebPages wp;
    private static RefinedQuery query;

    @BeforeAll static void setUp()throws IOException{
      query = new RefinedQuery();
      se = new SearchEngine();
      wp = new WebPages();
      result = new ArrayList<>();
      index = new InvertedIndex();
      setOfPages = new ArrayList<>();
      set = new HashSet<>();
      wp.readFile("config.txt");
      se.makeIndex(wp.getWebpages());
      index.makeInvertedIndex(wp.getWebpages());

    }

    @AfterEach void resetList(){
        result.clear();
        setOfPages.clear();
       
        
    }

    @AfterAll static void clear(){
        wp.getWebpages().clear();
    }
    
    /*
     * checks that the method returns a list of pages in the correct desenscending order. 
     */

    @Test void getDescendingListFromMap_correctOrder(){
        Map<Page, Double> map = new HashMap<>();
        List<Page> temp = new ArrayList<>();
        Page toppage = new Page();
        Page bottompage = new Page();
        
        toppage.addFirstScore(0.2);
        bottompage.addFirstScore(0.1);
        map.put(toppage, toppage.getFirstScore());
        map.put(bottompage, bottompage.getFirstScore());
        temp.add(bottompage);
        temp.add(toppage);

        result = se.getDescendingListFromMap(map, temp);
        Page top = result.get(0);
        Page bottom = result.get(result.size()-1);

        assertTrue(bottom.getFirstScore() < top.getFirstScore());

    }
    /*
     * checks that the method assigns the correct TF score in descending order
     */  

    @Test void computeTForTFIDF_TFscoreTopscoreOverBottomscore(){
        boolean TrueForTF = true;
        Map<Page, Double> siteRank = new HashMap<>();
        String[] searchword = {"java"};
   
        setOfPages = query.buildPages(index, searchword);
        se.computeTForTFIDF(TrueForTF, siteRank, searchword,setOfPages,index,query);
        result = se.getDescendingListFromMap(siteRank, result);
       
        Page bottomscore = result.get(result.size()-1);
        Page topscore = result.get(0);
        
        assertTrue(bottomscore.getCurrentScore() < topscore.getCurrentScore());
    }

    /*
     * checks that the method assigns the correct TFIDF score in descending order
     */    

    @Test void computeTForTFIDF_TFIDFscoreTopscoreOverBottomscore(){
        boolean TrueForTF = false;
        Map<Page, Double> siteRank = new HashMap<>();
        String[] searchword = {"java"};
   
        setOfPages = query.buildPages(index, searchword);
        se.computeTForTFIDF(TrueForTF, siteRank, searchword,setOfPages,index,query);
        result = se.getDescendingListFromMap(siteRank, result);
       
        Page bottomscore = result.get(result.size()-1);
        Page topscore = result.get(0);
        
        assertTrue(bottomscore.getCurrentScore() < topscore.getCurrentScore());
    }

     /*
     * checks that a page from the result list contains the word and if the size the of list is correct 
     */


    @Test void getSearchResults_resultWithJava() throws IOException{
       
        result = se.getSearchResults("java");
        Page page = result.get(0);
        set = index.lookUp("java");

        assertTrue(page.hasWord("java"));
        assertEquals(set.size(), result.size());
    
    }

    /*
     * checks that a page from the result list contains the word and if the size the of list is correct 
     */

    @Test void getSearchResults_resultWithAsia() throws IOException{
        
        result = se.getSearchResults("asia");
        Page page = result.get(0);
        set = index.lookUp("asia");

        assertTrue(page.hasWord("asia"));
        assertEquals(set.size(), result.size());
    
    }

    /*
     * checks that a page from the result list does not return any pages with a atypical word
     */

    @Test void getSearchResults_noResults() throws IOException{
       
        result = se.getSearchResults("detbliverhvidjul");

        assertEquals(0, result.size());
    }

    /*
     * checks that a page contains the word combination of java and asia 
     */

    @Test void getSearchResults_withJavaAndAsia() throws IOException{
      
       
        result = se.getSearchResults("java%20asia");
        Page page = result.get(0);
        
        assertTrue(page.hasWord("asia"));
        assertTrue(page.hasWord("java"));
        assertEquals(5, result.size());
   
    }

    /*
     * checks that the resulting list contains pages with either java or asia.
     */

    @Test void getSearchResults_withJavaOrAsia() throws IOException{
        
        result = se.getSearchResults("java%20OR%20asia");
        Page withjava = new Page();
        Page withasia = new Page();

        for (Page page : result) {
            if(page.hasWord("java")){
                withjava = page;
            } else if (page.hasWord("asia")){
                withasia = page;
            }
        }
        assertTrue(withasia.hasWord("asia"));
        assertTrue(withjava.hasWord("java"));
        assertEquals(461, result.size());
    
    }

    /*
     * checks that the resulting list contains pages with the multiple searchwords split by "OR"
     */

    @Test void getSearchResults_with2MultiWordsSplitByOr() throws IOException{
      
     
        result = se.getSearchResults("java%20asia%20OR%20borneo%20indonesia");
        Page javaAndAsia = new Page();
        Page borneoAndIndonesia = new Page();

        for (Page page : result) {
            if(page.hasWord("asia") && page.hasWord("java")){
                javaAndAsia.addLine("asia");
                javaAndAsia.addLine("java");
            } 
            if (page.hasWord("borneo") && page.hasWord("indonesia")){
                borneoAndIndonesia.addLine("borneo");
                borneoAndIndonesia.addLine("indonesia");
            }
        }

        assertTrue(javaAndAsia.hasWord("java"));
        assertTrue(javaAndAsia.hasWord("asia"));
        assertTrue(borneoAndIndonesia.hasWord("borneo"));
        assertTrue(borneoAndIndonesia.hasWord("indonesia"));
        assertEquals(10, result.size());

    }
    /*
     * checks that the resulting list contains pages with the multiple searchwords split by multiple "OR"
     */

    @Test void getSearchResults_with3MultiWordsSplitByOr() throws IOException{
    
        result = se.getSearchResults("President%20USA%20OR%20Queen%20Denmark%20OR%20Chancellor%20Germany");

        Page presidentUSA = new Page();
        Page queenDenmark = new Page();
        Page chancellorGermany = new Page();

        for (Page page : result) {
            if(page.hasWord("President") && page.hasWord("USA")){
                presidentUSA.addLine("President");
                presidentUSA.addLine("USA");
            } 
            if (page.hasWord("Queen") && page.hasWord("Denmark")){
                queenDenmark.addLine("Queen");
                queenDenmark.addLine("Denmark");
            }
            if (page.hasWord("Chancellor") && page.hasWord("Germany")){
                chancellorGermany.addLine("Chancellor");
                chancellorGermany.addLine("Germany");
            }
        }

        assertTrue(presidentUSA.hasWord("President"));
        assertTrue(presidentUSA.hasWord("USA"));
        assertTrue(queenDenmark.hasWord("Queen"));
        assertTrue(queenDenmark.hasWord("Denmark"));
        assertTrue(chancellorGermany.hasWord("Chancellor"));
        assertTrue(chancellorGermany.hasWord("Germany"));
        assertEquals(91, result.size());
    }


    

}
