package searchengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * The FileReader class is used to read files from a path
 * The objects will contain the strings obtained from the file in a list. 
 * 
 * @author simol, gega, madbe, elsb
 */

public class FileReader {
    
    private List<String> listfromfile;

    public FileReader(){
        
        listfromfile = new ArrayList<>();

    }

    /**
     * 
     * reads the file into a list containing the file strings 
     * @param filename - takes a file as a parameter
     * @throws IOException
     */
    public void addFileContent(String filename) throws IOException{
        
        String file = Files.readString(Paths.get(filename)).strip();

        listfromfile = Files.readAllLines(Paths.get(file));

    }

    /**
     * 
     * @return returns the a list of Strings
     * @throws IOException
     */

    public List<String> getFile() throws IOException{
      
        return listfromfile;
        
    }

    
}
