package searchengine;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * 
 * TFIDF class test
 * 
 */

public class TFIDFTest {

    private static InvertedIndex IDFindex;
    private static TFIDF tfidf;

    @BeforeAll static void setup(){
        IDFindex = new InvertedIndex();
        tfidf = new TFIDF();
    }

    /*
     * checks that the TF method works with the IDF score and compute a final TF-IDF score using the TF-IDF
     */
    
    @Test void TFIDF_JavaCalculation(){

        Page nr1 = new Page();
        HashSet<Page> temp = new HashSet<>();
        String line = "java";
        nr1.addLine("hej");
        nr1.addLine("java");

        
        temp.add(nr1);
        IDFindex.makeInvertedIndex(temp);

        double lol = tfidf.TFIDFscore(line, nr1, IDFindex);

        assertTrue(lol > 0);
    }
}
