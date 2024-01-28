package searchengine;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * A webserver class used to create our searchengine.
 * 
 * Takes input in form of HttpExhanges and directly uses 
 * the WebPages & Searchengine class to create content from the input
 * 
 * @author simol, gega, madbe, elsb
 * 
 */

public class WebServer {
    
    private static final int PORT = 8080;
    private static final int BACKLOG = 0;
    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private WebPages webPages;
    private SearchEngine searchEngine;
    protected HttpServer server;

    public WebServer(int port) throws IOException {
      webPages = new WebPages();
      searchEngine = new SearchEngine();

      webPages.readFile("config.txt");
      searchEngine.makeIndex(webPages.getWebpages());

      server = HttpServer.create(new InetSocketAddress(port), BACKLOG);
      
      server.createContext("/", io -> respond(io, 200, "text/html", getWebFile("web/index.html")));
      server.createContext("/search", io -> handleSearch(io));
      server.createContext(
          "/favicon.ico", io -> respond(io, 200, "image/x-icon", getWebFile("web/favicon.ico")));
      server.createContext(
          "/code.js", io -> respond(io, 200, "application/javascript", getWebFile("web/code.js")));
      server.createContext(
          "/style.css", io -> respond(io, 200, "text/css", getWebFile("web/style.css")));
      server.start();

      String msg = " WebServer running on http://localhost:" + port + " ";
      System.out.println("╭" + "─".repeat(msg.length()) + "╮");
      System.out.println("│" + msg + "│");
      System.out.println("╰" + "─".repeat(msg.length()) + "╯");
      
    }

    /**
     * 
     * Takes a HTTP request received and a response to be generated in one exchange which will be converted into a string
     * and will be used as our searchterm string.
     * 
     * @param io - takes a HttpExchange io and creates a string
     * @return returns the string created from IO
     */

    public String getSearchTerm(HttpExchange io){

      String searchTerm = io.getRequestURI().getRawQuery().split("=")[1];

        return searchTerm;
    }

    /**
     * Uses the input IO and uses searches the inverted index to lookup a key according to the IO string 
     * and returns a list of pages mapped to the key.
     * Afterwards each found page is then put into the a list called response, which the method will return
     * 
     * @param io - takes a HttpExchange io and creates a string
     * @return - returns a list of strings 
     */

     public List<String> searchHandler(HttpExchange io) { // renamed to handleSearch
        
      List<String> response = new ArrayList<>();
      
      for (Page page : searchEngine.getSearchResults(getSearchTerm(io))) { 

        response.add(String.format("{\"url\": \"%s\", \"title\": \"%s\"}",

            page.getListOfWords().get(0).substring(6), page.getListOfWords().get(1)));
                
      }

      return response;

    }

     /**
       * 
       * Takes a list of strings and converts it into a bytearray to create server content 
       * 
       * @param response - takes a list of strings as input
       * @return returns a converted list of strings as a byte array 
       */

       public byte[] responseToByteArray(List<String> response){
        
        byte[] bytes = response.toString().getBytes(CHARSET);
        
        return bytes;
        
    }

    /**
     * Takes the io and converts it into a string. The string will be used as a key in the inverted index which has all the websites mapped to words.
     * if the input string is a key inside the map, the websites mapped to the key will be returned and put into a list. The list is then converted into a byte array
     * as the respond method uses the bytes to create webcontent.
     * 
     * @param io takes a HttpExchange input
     * 
     */

    public void handleSearch(HttpExchange io) { 

      List<String> IOresults = new ArrayList<>();

      IOresults = searchHandler(io);
      IOresults = searchHandler(io);
      
      respond(io, 200, "application/json", responseToByteArray(IOresults));
      respond(io, 200, "application/json", responseToByteArray(IOresults));

    }

    /**
     * Takes a filename as a path from a js file etc and returns a bytearray that will be used to create server content.
     * 
     * @param filename
     * takes a filename / path, as a string
     * @return
     * returns a bytearray
     */

    public byte[] getWebFile(String filename) {
      try {
        return Files.readAllBytes(Paths.get(filename));
      } catch (IOException e) {
        e.printStackTrace();
        return new byte[0];
      }
    }

    /**
     * Will be used to create server content..
     * 
     * @param io
     * a HttpExchange io
     * @param code
     * an int
     * @param mime
     * a string
     * @param response
     * a bytearray
     */

    public void respond(HttpExchange io, int code, String mime, byte[] response) {
      try {
        io.getResponseHeaders()
            .set("Content-Type", String.format("%s; charset=%s", mime, CHARSET.name()));
        io.sendResponseHeaders(200, response.length);
        io.getResponseBody().write(response);
      } catch (Exception e) {
      } finally {
        io.close();
      }
    }

    public static int getPort() {
        return PORT;
    }
    
 
    public static void main(String[] args) throws IOException {

      new WebServer(WebServer.getPort());
      
    }
}