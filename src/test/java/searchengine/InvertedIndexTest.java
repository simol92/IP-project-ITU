package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * 
 * Inverted index class test
 * 
 */

public class InvertedIndexTest {

    private static InvertedIndex index;
    private static HashSet<Page> set;
  
    @BeforeAll static void setUp()throws IOException{
   
      index = new InvertedIndex();
      set = new HashSet<>();

      Page nr1 = new Page();
        HashSet<Page> temp = new HashSet<>();
        
        nr1.addLine("hej");
        nr1.addLine("hej");
        nr1.addFirstScore(1.0);
        temp.add(nr1);
        index.makeInvertedIndex(temp);

    }

    @AfterEach void resetList(){
        set.clear();
        
    }

    /**
     * This test will make sure that the inverted index works and contains exactly one page
     */
    @Test void makeInvertedIndex_containsOneElement(){
        InvertedIndex tempindex = new InvertedIndex();
        Page temppage = new Page();
        temppage.addLine("hej");
        HashSet<Page> tempset = new HashSet<>();
        tempset.add(temppage);
        tempindex.makeInvertedIndex(tempset);

        set = tempindex.lookUp("hej");

        assertEquals(1, tempindex.getMapSize());
        
    }

    /**
     * checks that it doesnt return any page, if there isnt any mapped pages 
     */

    @Test void makeInvertedIndex_buildEmptyIndex(){
        InvertedIndex tempindex = new InvertedIndex();
        HashSet<Page> emptyset = new HashSet<>();
        
        tempindex.makeInvertedIndex(emptyset);

        set = tempindex.lookUp("none");

        assertTrue(tempindex.getMapSize() == 0);
    }

    /**
     * checks that it return the correct number of pages
     */

    @Test void lookUp_return1page(){

        set = index.lookUp("hej");

        assertEquals(1, index.getMapSize());
    }
    /**
     * checks that it return nothing, as there isnt a page containing the word "nej"
     */

    @Test void lookUp_returnEmptySet(){

        set = index.lookUp("nej");

        assertTrue(set.size() == 0);
    }



}
