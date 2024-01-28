package searchengine;

/**
 * A class to accumulate the TF-IDF score of a word 
 * 
 * @author simol, gega, madbe, elsb
 * 
 */

public class TFIDF {

    private double TFDIFscore;
    private TF tfscore;
    private IDF idfscore;

    public TFIDF() {
        tfscore = new TF();
        idfscore = new IDF();
        TFDIFscore = 0.0;
    
    }

    /**
     * 
     * This method will calculate the final TF-IDF score, which takes the TF score
     * and multiply with the IDF score.
     * 
     * The final equation of our TF-IDF algorithm is 
     * 
     * (word frequency in page / total number of words in page) * log(total websites in the databases / the number of pages the word appears in)
     * 
     * @param SearchWord
     * Specific searchword that will be used as the term 
     * @param page
     * Takes a page from a set of pages found by the lookup method inside the searchengine class
     * @param index
     * Inverted index object to find database size and to lookup searchword
     * @return
     * returns the TF-IDF as a double value that will be assigned to a page.
     */
    
    public double TFIDFscore(String SearchWord, Page page, InvertedIndex index) {

        double tf = tfscore.TFScore(SearchWord, page);

        double idf = idfscore.IDFscore(SearchWord, index);

        TFDIFscore = tf * idf;

        return TFDIFscore;

    }


}
