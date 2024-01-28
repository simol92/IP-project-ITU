package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

/*
 * 
 * RefinedQuery test class
 * 
 */

public class RefinedQueryTest {

    private static List<HashSet<Page>> setofpages;
    private static InvertedIndex newindex;
    private static RefinedQuery query;

    @BeforeAll static void setup(){
        query = new RefinedQuery();
        setofpages = new ArrayList<>();
        newindex = new InvertedIndex();

    }
    @AfterAll static void clear(){
        
    }

    /**
     * checks that the list of hashsets isnt empty after addition
     */

    @Test void buildPages_addPages(){
        query = new RefinedQuery();
        Page nr1 = new Page();
        HashSet<Page> temp = new HashSet<>();
        String line1 = "hej";
        String [] line = line1.split("");
        nr1.addLine("hej");
        temp.add(nr1);
        setofpages = query.buildPages(newindex, line);
        assertTrue( setofpages.size() > 0);
     
    }

    /**
     * checks that the method doesnt retain something that it shouldnt 
     */

    @Test void getOverlap_nothingRetained(){
        Page nr1 = new Page();
        Page nr2 = new Page();
        HashSet<Page> firstset = new HashSet<>();
        firstset.add(nr1);
        firstset.add(nr2);


        Page nr3 = new Page();
        HashSet<Page> secondset = new HashSet<>();
        secondset.add(nr3);

        secondset = query.getOverlap(firstset, secondset);

        assertEquals(0 , secondset.size());
    }
    
}
