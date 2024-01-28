package searchengine;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * A IDF test class to test the IDF 
 */

public class IDFTest {
    
    private static Page page_;
    private static String line;
    private static InvertedIndex index_;
    private static IDF idf;
    

    @BeforeAll static void setup() throws IOException{
        idf = new IDF();
        page_ = new Page();
        index_ = new InvertedIndex();
        line = "java";
        
    }

    @AfterEach void reset (){
        page_.getListOfWords().clear();
    }
    /*
     * The following test will test if a score is above zero after the calculation of IDF.
     */

    @Test void IDFScore_aboveZero(){
        Page nr1 = new Page();
        HashSet<Page> temp = new HashSet<>();
        
        nr1.addLine("hej");
        nr1.addLine("hej");
        nr1.addFirstScore(1.0);
        temp.add(nr1);
        index_.makeInvertedIndex(temp);

        double abovezero = idf.IDFscore(line, index_);
        nr1.addFirstScore(abovezero);
        
        assertTrue(nr1.getFirstScore() > 0 );
    }
}
