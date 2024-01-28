package searchengine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

/*
 * 
 * WebPages class test 
 * 
 */

public class WebPagesTest {
    
    private static List<List<String>> storingRawPages;
    private static List<String> filecontent;
    private static WebPages wp;

    @BeforeAll static void setup(){
        wp = new WebPages();
        wp.getWebpages().clear();
        filecontent = new ArrayList<>();
        storingRawPages = new ArrayList<>();
    }

    @AfterEach void clearALL(){
        wp.getWebpages().clear();
    }

    /**
     * checks that the number webpages is empty, if anything is not loaded into it
     */

    @Test void addWebPages_NoContent(){
        
        assertTrue(wp.getWebpages().isEmpty());
    }

    /**
     * checks that exactly one page is added.
     */

    @Test void addWebPages_addOne(){

        filecontent.add("word");
        filecontent.add("word");
        filecontent.add("word");
        filecontent.add("word");
        filecontent.add("*PAGE:https://en.wikipedia.org/wiki/Anatolia");
        filecontent.add("Anatolia");
        storingRawPages.add(filecontent);
        wp.addWebPages(storingRawPages);
        
        assertEquals(1, wp.getWebpages().size());
        
    }

    /**
     * checks that the correct number of websites of the file (25011 in medium) is read from the file.
     * @throws IOException
     */

    @Test void readFile_WithCorrectNrOfSites() throws IOException{
       
        wp.readFile("config.txt");

        assertEquals(25011, wp.getWebpages().size());
        
    }

     /**
     * checks that the number of websites is zero, if a incorrect file path is given
     * @throws IOException
     */

    @Test void readFile_NoContent() throws IOException{
        try {

            wp.readFile("config");
        } catch(Exception e){
            e.printStackTrace();
        }
       
        assertEquals(0, wp.getWebpages().size());
    }

}
