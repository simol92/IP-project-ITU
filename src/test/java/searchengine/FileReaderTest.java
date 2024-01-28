package searchengine;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/*
 * 
 * FileReader class test
 * 
 */

public class FileReaderTest {
    
    private static List<String> listfromfile;
    private static FileReader reader;

    @BeforeAll static void setup(){

        listfromfile = new ArrayList<>();
        reader = new FileReader();
    }

    @AfterEach void reset(){
        listfromfile.clear();
    }

   /**
    * Test if the reader method reads the file from the path
    * @throws IOException
    */

    @Test void addFileContent_WithContent() throws IOException{

        reader.addFileContent("config.txt");
        
        listfromfile = reader.getFile();

        assertTrue(!listfromfile.isEmpty());

    }

    /**
     * tests that the Filereader does not add something it shouldnt 
     */

    @Test void addFileContent_WithoutContent() {

        try{
            reader.addFileContent("");

        } catch (Exception e){

            try {
                listfromfile = reader.getFile();
            } catch (IOException e1) {
                
                e1.printStackTrace();
            }
        }

        assertTrue(listfromfile.isEmpty());

    }

}
