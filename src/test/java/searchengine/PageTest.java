package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;

/*
 * 
 * Page test class
 * 
 */

public class PageTest {

    private static Page page;
    private static List<String> test;
    
    @BeforeAll static void setup(){
        page = new Page();
        test = new ArrayList<>();

    }

    @AfterEach void resetList(){
        page.getListOfWords().clear();
    }

    /**
     * checks that there is not any added lines to the page
     */
    @Test void addLine_noLines(){
    
        assertEquals(0, page.getListOfWords().size());
    }

    /**
     * checks that there is exactly one line in the page
     */

    @Test void addLine_addOneLine(){
        String line = "";
        page.addLine(line);

        assertEquals(1, page.getListOfWords().size());
    }

    /**
     * checks that if there is a line with any uppercase letters, make them lowercase!
     */

    @Test void changeLineToLowerCase_WithUpperCase(){
        String line = "Line";
        page.addLine(line);
        page.changeLineToLowercase(line);
        
        test = page.getListOfWords();
        
        assertEquals("line", test.get(0));
    }

    /**
     * checks that if there is a line with any uppercase letters, make them lowercase!
     */

    @Test void changeLineToLowerCase_AnotherWithUpperCase(){
        String line = "Hej";
        page.addLine(line);
        page.changeLineToLowercase(line);
        
        test = page.getListOfWords();
        
        assertEquals("hej", test.get(0));
    }

    /**
     * checks that if there is not any uppercase letters, dont do anything!
     */

    @Test void changeLineToLowerCase_NoUpperCase(){
        String line = "line";
        page.addLine(line);
        page.changeLineToLowercase(line);
        
        test = page.getListOfWords();
        
        assertEquals("line", test.get(0));
    }

    /**
     * checks for words inside a page excluding urls and titles
     */

    @Test void totalAmountOfWords_nowords(){
    
        page.addLine("*PAGE");
        page.addLine("First");

        assertEquals(0, page.totalAmountOfWords());

    }

    /**
     * checks if a page consist of one word excluding urls and titles
     */

    @Test void totalAmountOfWords_Onewords(){
    
        page.addLine("*PAGE");
        page.addLine("word");

        assertEquals(1, page.totalAmountOfWords());

    }

    /**
     * checks if a page consist of two words excluding urls and titles
     */
    
    @Test void totalAmountOfWords_TwoWords(){
    
        page.addLine("page");
        page.addLine("first");

        assertEquals(2, page.totalAmountOfWords());

    }

    /**
     * checks if a page has the word "ja", if its added
     */

    @Test void hasWord_TrueWithOneWord(){
        page.addLine("ja");

        assertEquals(true, page.hasWord("ja"));

    }

    /**
     * checks if a page has the word "ja" and "nej", if its added
     */

    @Test void containsWord_TrueWithTwoWords(){
        page.addLine("ja");
        page.addLine("nej");

        assertEquals(true, page.hasWord("ja"));

    }

    /**
     * checks if a page has the wrong word
     */


    @Test void containsWord_False(){
        page.addLine("ja");

        assertEquals(false, page.hasWord("nej"));

    }

    /**
     * checks if a pages first ranking score is resetted, it should be 0
     */

    @Test void resetFirstScore_reset(){
        page.addFirstScore(10);
        page.resetFirstScore();

        assertEquals(0, page.getFirstScore());

    }

    /**
     * checks if a pages second ranking score is resetted, it should be 0
     */

    @Test void resetSecondScore_reset(){
        page.addSecondScore(10);
        page.resetSecondScore();

        assertEquals(0, page.getSecondScore());

    }

    /**
     * checks if the pages correct title is returned
     */

    @Test void getTitle_returnthetile(){
        page.addLine("*PAGE");
        page.addLine("TITLE");

        assertEquals("TITLE", page.getTitle());
    }

    /**
     * checks if the pages current score is returned correctly
     */


    @Test void getCurrentScore_returnthescore(){
        page.currentscore(0);

        assertEquals(0, page.getCurrentScore());
    }
}
