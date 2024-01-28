package searchengine;

import java.io.IOException;
import java.util.*;

/**
 * 
 * A Webpages class
 * 
 * Objects will contain Page objects in hashsets 
 * 
 * @author simol, gega, madbe, elsb
 */

public class WebPages {

    private List<List<String>> storingRawPages;
    private HashSet<Page> webpages;
    private List<String> filecontent;
    private FileReader reader;

    public WebPages(){

      storingRawPages = new ArrayList<>();
      webpages = new HashSet<>();
      filecontent = new ArrayList<>();
      reader = new FileReader();

    }
    
    /**
     * Takes each list from the input list and each list from that specific list will be put into a page object
     * afterwards the page object will be put into a hashSet containing all the pages
     * 
     * @param list - takes a list containing lists of strings 
     *
     */

    public void addWebPages(List<List<String>> list){
      

      for (List<String> rawPage : list) {

        Page page = new Page();
        for (String string : rawPage) {
            page.addLine(string);
        }

        //checks if any words is uppercase
        for (int i = 2; i < rawPage.size()-1; i++) {
          char first = rawPage.get(i).charAt(0);
          String line = rawPage.get(i);
          if(Character.isUpperCase(first)){
            page.changeLineToLowercase(line);
          } 
        }
        
        webpages.add(page);
        
      }

    }

    /**
     * 
     * Reads filecontent from a filepath and divides the content into a list of webpages.
     * Each webpage is then put into the addPages method, which will put each website into a set of pages. 
     * 
     * @param filename - takes a filename from a path
     * @throws IOException
     */

    public void readFile(String filename) throws IOException{

      reader.addFileContent(filename);
      
      filecontent = reader.getFile();
      
      int pagenumber = 0;
      int lastpagenumber = 0;
      
        for (int linenumber = 1; linenumber < filecontent.size()-1; linenumber++) {

          if (filecontent.get(linenumber).startsWith("*PAGE")) {

            String targetString = filecontent.get(linenumber);

            String nextString = filecontent.get(linenumber+1);

            int lastWord = targetString.lastIndexOf('/');

            String wordsAfterSlash = targetString.substring(lastWord + 1, targetString.length()).replace("_", "\s");

            String linefromfile = filecontent.get(linenumber);

            if(linefromfile.startsWith("*PAGE") && wordsAfterSlash.equals(nextString) && 
            !filecontent.get(linenumber+2).startsWith("*PAGE")) {

              storingRawPages.add(filecontent.subList(pagenumber, linenumber));

              pagenumber = linenumber;   

            }

          } else if(linenumber == filecontent.size()-2){

            lastpagenumber = filecontent.size();
            
            storingRawPages.add(filecontent.subList(pagenumber, lastpagenumber));
            
          }
          
        }
        addWebPages(storingRawPages);
        

    }

    /**
     * @return returns the set containing page objects 
     */


    public HashSet<Page> getWebpages(){
      
      return webpages;

    }
    

}