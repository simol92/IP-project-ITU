package searchengine;

import java.util.*;

/**
 * A Page class that will contain the content of a webpage
 * 
 * @author simol, gega, madbe, elsb
 */

public class Page {
    
    private List<String> page;
    private double firstscore;
    private double secondscore;
    private double currentscore;

    public Page(){

        firstscore = 0.0;
        secondscore = 0.0;
        currentscore = 0.0;
        page = new ArrayList<>();
        
    }

    /**
     * 
     * adds wanted strings to the list that will contain a website
     * @param 
     * takes a string
     */

    public void addLine(String line){

        page.add(line);
    }

    /**
     * If a line inside the pages list of strings contain uppercase letters,
     * convert them to lowercase
     * @param line
     * takes a string 
     */

    public void changeLineToLowercase(String line){
        String newline = line.toLowerCase();
        int index = page.indexOf(line);
        page.set(index,newline);
    }

    /**
     * returns the list of strings from the websites
     * @return
     * returns a list of strings
     */

    public List<String> getListOfWords(){

        return page;
    }

    /**
     * Returns the total number of words inside a page excluding the URL and the page title
     * @return
     * returns the number of words inside the page
     */

    public int totalAmountOfWords(){
        int numberofwords = 0;

        for (String string : page) {
            char first = string.charAt(0);
            if (!string.startsWith("*PAGE") && !Character.isUpperCase(first)){
                numberofwords++;
            } 
        }
        return numberofwords;
    }


    /**
     * This method returns the URL from the website 
     * @return a web page title as a string
     */

    public String getTitle(){

        return page.get(1);

    }

    /**
     * 
     * @param searchword
     * takes a searchword the page may contain
     * @return
     * 
     * returns true if the page contains the word
     * false if not
     * 
     */
    public boolean hasWord(String searchword){
        boolean success = false;
        for (String string : page) {
            if(string.toLowerCase().equalsIgnoreCase(searchword)){
                success = true;
                return success;
            } 
        }
        return success;
    }

    public void addFirstScore(double score){
        this.firstscore += score;
    }
    public void addSecondScore(double score){
        this.secondscore += score;
    }

    public void currentscore(double score){
        this.currentscore = score;
    }

    public double getFirstScore(){
        return firstscore;
    }

    public double getSecondScore(){
        return secondscore;
    }

    public double getCurrentScore(){
        return currentscore;
    }

    public void resetFirstScore(){
        firstscore = 0.0;
        
    }

    public void resetSecondScore(){
        secondscore = 0.0;
    }

}
