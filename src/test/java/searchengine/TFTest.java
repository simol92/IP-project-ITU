package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/*
 * 
 * TF test class
 * 
 */
public class TFTest {
    
    private static Page page;
    private static String line;
    private static TF tf;

    @BeforeAll static void setup(){
        tf = new TF();
        page = new Page();
        line = "";
    }

    @AfterEach void reset (){
        page.getListOfWords().clear();
    }
    /**
     * checks that a invalid score is not assigned to a page, if the word is not present in a page
     */
    @Test void TFScore_noWord(){
        String line1 = "hej";
        String line2 = "med";
        String line3 = "dig";
        page.addLine(line1);
        page.addLine(line2);
        page.addLine(line3);
        Double zero = tf.TFScore(line, page);

        assertEquals(0, zero);
    }

    /**
     * checks that a page is assigned a score, as "hej" is present
     */

    @Test void TFScore_WithOneWord(){
        String line1 = "hej";
        String line2 = "med";
        String line3 = "dig";
        page.addLine(line1);
        page.addLine(line2);
        page.addLine(line3);
        Double abovezero = tf.TFScore(line1, page);

        assertTrue(abovezero > 0);
    }

}
